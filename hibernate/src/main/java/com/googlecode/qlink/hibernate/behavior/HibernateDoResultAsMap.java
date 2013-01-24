package com.googlecode.qlink.hibernate.behavior;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.googlecode.qlink.api.behavior.DoResultAsMap;
import com.googlecode.qlink.api.functor.Aggregator;
import com.googlecode.qlink.api.functor.Function2;
import com.googlecode.qlink.api.functor.Predicate;
import com.googlecode.qlink.api.functor.SamplePredicate;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.KeyedFactory;
import com.googlecode.qlink.core.context.blocks.GroupByBlock;
import com.googlecode.qlink.core.context.enums.EGroupByBlockType;
import com.googlecode.qlink.core.context.enums.ETransformResultType;
import com.googlecode.qlink.core.functor.SamplePredicates;
import com.googlecode.qlink.core.utils.SimpleAssert;
import com.googlecode.qlink.core.utils.StackPruningUtils;
import com.googlecode.qlink.core.utils.TypedBeanUtils;
import com.googlecode.qlink.hibernate.functor.SqlAwareAggregators.AggregatorToFunction2Adaptor;
import com.googlecode.qlink.hibernate.functor.SqlAwareFunctions;
import com.googlecode.qlink.hibernate.functor.SqlClauseSnippet;
import com.googlecode.qlink.hibernate.pruning.HibernatePruningRules;
import com.googlecode.qlink.hibernate.utils.SqlAwareFunctionUtils;
import com.googlecode.qlink.tuples.Tuples;

public class HibernateDoResultAsMap<K, V, TPlugin>
	extends HibernateDoResultSupport
	implements DoResultAsMap<K, V, TPlugin>
{

	private KeyedFactory<Class, List> groupByListFactory = new KeyedFactory<Class, List>() {

		@Override
		public List create(Class key)
		{
			return new ArrayList();
		}
	};

	public HibernateDoResultAsMap(IPipelineContext ctxt)
	{
		super(ctxt);
	}

	public void setGroupByListFactory(KeyedFactory<Class, List> groupByListFactory)
	{
		this.groupByListFactory = groupByListFactory;
	}

	@Override
	public Map<K, V> toMap()
	{
		String groupBySelectClause = getGroupBySelectClause();

		Stack<Pair<EGroupByBlockType, GroupByBlock>> groupByStack = getCtxt().getPipelineDef().getGroupByStack();
		Stack<Pair<EGroupByBlockType, GroupByBlock>> indexStack = getCtxt().getPipelineDef().getIndexStack();
		int groupByKeyLength = groupByStack.size();

		/*
		 * either group by stack or index stack should be present, not the both
		 */
		SimpleAssert.isTrue((groupByStack.size() != 0 && indexStack.size() == 0)
			|| (indexStack.size() != 0 && groupByStack.size() == 0));

		boolean isGroupBy = groupByStack.size() != 0;

		SamplePredicate samplePredicate = getSamplePredicate();

		List<?> lstRes;
		if (groupBySelectClause != null && samplePredicate == null) {
			/*
			 * use group by select to perform everything in SQL
			 */
			lstRes = doInSql();
		}
		else {
			lstRes = (List<?>) makeRowResultInSql(false);
		}

		Function2 indexer = null;
		if (isGroupBy) {
			indexer =
				StackPruningUtils.createGroupByFunction(HibernatePruningRules.groupByPruner, getCtxt().getPipelineDef()
					.getGroupByStack());
		}
		else {
			indexer =
				StackPruningUtils.createIndexByFunction(HibernatePruningRules.groupByPruner, getCtxt().getPipelineDef()
					.getIndexStack());
		}
		SimpleAssert.notNull(indexer, "indexer cannot be null for " + getCtxt());

		Map<K, V> res = new HashMap<K, V>();
		if (isGroupBy) {
			if (groupBySelectClause == null || samplePredicate != null) {

				int i = 0;
				for (Object obj : lstRes) {
					Pair<K, V> entry = (Pair<K, V>) indexer.apply(obj, i);
					List lst = (List) res.get(entry.getFirst());

					if (lst == null) {
						lst = groupByListFactory.create(getCtxt().getPipelineDef().getValueClass());
						res.put(entry.getFirst(), (V) lst);
					}
					lst.add(entry.getSecond());
				}

				Function2<Object, Integer, ?> groupSelectFunction =
					(Function2<Object, Integer, ?>) createSelectGroupFunction();

				if (groupSelectFunction != null) {

					int ind = 0;
					for (Map.Entry<K, V> entry : res.entrySet()) {
						Object val = groupSelectFunction.apply(entry.getValue(), ind++);

						entry.setValue((V) val);
					}
				}

			}
			else {
				ETransformResultType resType = getHibernateCtxt().getPipelineDef().getSelectGroupResultType();
				Class resCls = getHibernateCtxt().getPipelineDef().getSelectGroupResultClass();

				for (Object obj : lstRes) {

					Object[] ar = (Object[]) obj;

					/*
					 * for now key is always a tuple
					 */
					Object key = Tuples.createTupleOrObjectFor(Arrays.copyOf(ar, groupByKeyLength));
					Object val =
						createValForSelectGroup(resType, resCls, Arrays.copyOfRange(ar, groupByKeyLength, ar.length));

					res.put((K) key, (V) val);
				}
			}
		}
		else {
			int i = 0;
			for (Object obj : lstRes) {
				Pair<K, V> entry = (Pair<K, V>) indexer.apply(obj, i);
				if (res.containsKey(entry.getFirst())) {
					throw new IllegalArgumentException("unique index, but got double occurence of the key "
						+ entry.getFirst());
				}
				res.put(entry.getFirst(), entry.getSecond());
			}
		}

		@SuppressWarnings("unchecked")
		Predicate<V> havingPredicate =
			(Predicate<V>) StackPruningUtils.createHavingPredicate(HibernatePruningRules.filterPruner, //
				getCtxt().getPipelineDef().getHavingStack());

		if (havingPredicate != null && groupBySelectClause == null) {
			Iterator<Map.Entry<K, V>> iter = res.entrySet().iterator();
			for (; iter.hasNext();) {
				Map.Entry<K, V> entry = iter.next();
				if (havingPredicate.evaluate(entry.getValue()) == false) {
					iter.remove();
				}
			}
		}

		return res;
	}

	private Object createValForSelectGroup(ETransformResultType resType, Class<?> resCls, Object[] args)
	{
		switch (resType) {

			case arrayObject:
				return args;

			case newObject:
				return TypedBeanUtils.createObjectForClass(resCls, args);

			case tuple:
				return Tuples.createTupleOrObjectFor(args);

			default:
				throw new IllegalArgumentException("unrecognized transformation: " + resType + ", " + resCls);
		}
	}

	private List<?> doInSql()
	{
		Predicate<?> havingPredicate =
			StackPruningUtils.createHavingPredicate(HibernatePruningRules.filterPruner, getCtxt().getPipelineDef()
				.getHavingStack());

		Predicate<?> filterPredicate =
			StackPruningUtils.createFilterPredicate(HibernatePruningRules.filterPruner, getCtxt().getPipelineDef()
				.getFilterStack());

		String whereClause = getWhereClause(filterPredicate);

		String orderClause = getOrderClause();

		String fromClause = getFromClause();

		String groupByClause = getGroupByClause();

		String groupBySelectClause = getGroupBySelectClause();

		SqlClauseSnippet havingClausePredicate = (SqlClauseSnippet) havingPredicate;
		String havingClause = havingClausePredicate == null ? null : havingClausePredicate.getSqlClause();

		String hql = "";
		hql += "SELECT " + groupByClause + " ";
		if (groupBySelectClause != null) {
			hql += ", " + groupBySelectClause;
		}
		hql += " FROM " + fromClause + " ";

		if (whereClause != null) {
			hql += "WHERE " + whereClause + " ";
		}

		if (orderClause != null) {
			hql += "ORDER BY " + orderClause + " ";
		}

		hql += "GROUP BY " + groupByClause;
		if (havingClause != null) {
			hql += " HAVING " + havingClause;
		}

		HibernateTemplate template = getHibernateCtxt().getHibernateTemplate();
		List<Object> params = havingClausePredicate == null ? null : havingClausePredicate.getParams();

		List<?> res = null;
		if (params != null) {
			res = template.find(hql, params.toArray(new Object[]{}));
		}
		else {
			res = template.find(hql);
		}

		return res;
	}

	public String getGroupByClause()
	{

		SqlClauseSnippet indexer =
			SqlAwareFunctions.getSqlClauseSnippet(StackPruningUtils.createGroupByFunction(
				HibernatePruningRules.groupByPruner, getCtxt().getPipelineDef().getGroupByStack()));

		if (indexer == null) {
			return null;
		}

		return indexer.getSqlClause();
	}

	public String getHavingClause()
	{
		return null;
	}

	public String getGroupBySelectClause()
	{
		Function2[] functions =
			SqlAwareFunctionUtils.getTransformFunctions(getHibernateCtxt().getPipelineDef().getSelectGroupStack());
		if (functions == null || functions.length == 0) {
			return null;
		}
		String res = "";
		boolean first = true;
		for (Function2<?, Integer, ?> f : functions) {

			String sqlSnippet = null;
			if (f instanceof SqlClauseSnippet) {
				sqlSnippet = ((SqlClauseSnippet) f).getSqlClause();
			}

			if (f instanceof AggregatorToFunction2Adaptor) {
				Aggregator<?, ?> agg = ((AggregatorToFunction2Adaptor) f).getDelegate();
				sqlSnippet = ((SqlClauseSnippet) agg).getSqlClause();
			}

			res += (first ? "" : ", ") + sqlSnippet;
			first = false;
		}
		return res;
	}

	private SamplePredicate getSamplePredicate()
	{
		SamplePredicate samplePredicate = getCtxt().getPipelineDef().getSamplePredicate();
		if (samplePredicate == null) {
			samplePredicate =
				SamplePredicates.createSamplePredicate(getCtxt().getPipelineDef().getSampleType(), getCtxt()
					.getPipelineDef().getSampleParam());
		}

		return samplePredicate;
	}

	@Override
	public int size()
	{
		return toMap().size();
	}

	@Override
	public boolean isEmpty()
	{
		return size() == 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public TPlugin plugin()
	{
		return (TPlugin) getCtxt().getPlugin();
	}

}
