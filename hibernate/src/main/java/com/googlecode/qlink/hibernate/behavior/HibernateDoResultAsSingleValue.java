package com.googlecode.qlink.hibernate.behavior;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.googlecode.qlink.api.behavior.DoResultAsList;
import com.googlecode.qlink.api.behavior.DoResultAsSingleValue;
import com.googlecode.qlink.api.functor.Aggregator;
import com.googlecode.qlink.api.functor.Folder;
import com.googlecode.qlink.api.functor.Predicate;
import com.googlecode.qlink.api.functor.Reducer;
import com.googlecode.qlink.api.functor.SamplePredicate;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.PipelineContextAwareSupport;
import com.googlecode.qlink.core.context.blocks.TransformBlock;
import com.googlecode.qlink.core.context.enums.EAggregatorType;
import com.googlecode.qlink.core.context.enums.EFoldSide;
import com.googlecode.qlink.core.context.enums.ETransformResultType;
import com.googlecode.qlink.core.functor.SamplePredicates;
import com.googlecode.qlink.core.utils.AggregationUtils;
import com.googlecode.qlink.core.utils.SimpleAssert;
import com.googlecode.qlink.core.utils.StackPruningUtils;
import com.googlecode.qlink.hibernate.context.HibernatePipelineContext;
import com.googlecode.qlink.hibernate.functor.SqlClauseSnippet;
import com.googlecode.qlink.hibernate.pruning.HibernatePruningRules;
import com.googlecode.qlink.hibernate.utils.SqlAwareAggregationUtils;

public class HibernateDoResultAsSingleValue<T, TPlugin>
	extends PipelineContextAwareSupport
	implements DoResultAsSingleValue<T, TPlugin>
{

	private final HibernateDoResultAsList<T, TPlugin> doResultAsList;

	@SuppressWarnings("unchecked")
	public HibernateDoResultAsSingleValue(IPipelineContext ctxt)
	{
		super(ctxt);
		doResultAsList = (HibernateDoResultAsList<T, TPlugin>) getCtxt().getFactory().create(DoResultAsList.class);
	}

	private HibernatePipelineContext getHibernateCtxt()
	{
		return (HibernatePipelineContext) getCtxt();
	}

	@SuppressWarnings({"unchecked","rawtypes"})
	@Override
	public T toValue()
	{
		List<?> rowLst = doResultAsList.toList();

		SamplePredicate samplePredicate = getCtxt().getPipelineDef().getSamplePredicate();
		if (samplePredicate == null) {
			samplePredicate =
				SamplePredicates.createSamplePredicate(getCtxt().getPipelineDef().getSampleType(), getCtxt()
					.getPipelineDef().getSampleParam());
		}

		if (samplePredicate != null) {
			SimpleAssert.isEquals(1, rowLst.size());
			return (T) rowLst.get(0);
		}

		Reducer<T> reducer = (Reducer<T>) getCtxt().getPipelineDef().getReducer();
		Folder<Object, T> folder = (Folder<Object, T>) getCtxt().getPipelineDef().getFolder();

		if (reducer != null || folder != null) {
			List<?> lst = doResultAsList.toList();

			if (reducer != null) {
				return AggregationUtils.<T> applyReducer((List<T>) lst, reducer);
			}

			if (folder != null) {
				T initVal = (T) getCtxt().getPipelineDef().getFoldInit();
				EFoldSide side = getCtxt().getPipelineDef().getFoldSide();
				return AggregationUtils.<Object, T> applyFolder((List<Object>) lst, folder, initVal, side);
			}
		}

		List<Aggregator<?, ?>> aggregators = new ArrayList<Aggregator<?, ?>>();
		for (Pair<EAggregatorType, TransformBlock> p : getCtxt().getPipelineDef().getAggregators()) {
			TransformBlock tb = p.getSecond();
			Aggregator<?, ?> aggregator = tb.getAggregator();
			if (aggregator == null) {
				aggregator = SqlAwareAggregationUtils.chooseAggregator(tb.getAggregatorType(), tb.getProperty());
			}
			aggregators.add(aggregator);
		}

		if (aggregators.size() == 0) {
			throw new IllegalArgumentException("neither reduce nor fold were specified for " + getCtxt());
		}

		boolean canApplyInSql = true;
		for (Aggregator<?, ?> r : aggregators) {
			if (r instanceof SqlClauseSnippet == false) {
				canApplyInSql = false;
				break;
			}
		}

		Object[] aggregatedResult = null;

		if (canApplyInSql) {
			aggregatedResult = applyAggregatorsInSql(aggregators);
		}
		else {
			List<?> lst = doResultAsList.toList();

			aggregatedResult = new Object[aggregators.size()];
			int i = 0;
			for (Aggregator<?, ?> r : aggregators) {
				aggregatedResult[i++] = r.aggregate((List) lst);
			}
		}

		ETransformResultType aggregateResultType = getCtxt().getPipelineDef().getAggregatedResultType();
		Class<?> aggregateResultClass = getCtxt().getPipelineDef().getAggregatedResultClass();

		return (T) AggregationUtils.adaptAggregatedResult(aggregatedResult,
			getCtxt().getPipelineDef().getAggregators(), aggregateResultType, aggregateResultClass);
	}

	private Object[] applyAggregatorsInSql(List<Aggregator<?, ?>> aggregators)
	{
		Predicate<?> filterPredicate =
			StackPruningUtils.createFilterPredicate(HibernatePruningRules.filterPruner, getCtxt().getPipelineDef()
				.getFilterStack());

		String whereClause = doResultAsList.getWhereClause(filterPredicate);

		String orderClause = doResultAsList.getOrderClause();

		String selectClause = createAggregatingSelectClause(aggregators);

		String fromClause = doResultAsList.getFromClause();

		String hql = doResultAsList.composeHqlQueryFromComponents(selectClause, fromClause, whereClause, orderClause);

		List<Object> params = doResultAsList.getQueryParams();

		HibernateTemplate template = getHibernateCtxt().getHibernateTemplate();

		List<?> res = null;
		if (params != null) {
			res = template.find(hql, params.toArray(new Object[]{}));
		}
		else {
			res = template.find(hql);
		}

		SimpleAssert.isTrue(res.size() == 1, "expected single result, got " + res.size() + " for " + hql);

		return res.get(0).getClass().isArray() ? (Object[]) res.get(0) : new Object[]{res.get(0)};
	}

	private String createAggregatingSelectClause(List<Aggregator<?, ?>> aggregators)
	{
		String selectClause = "";
		boolean first = true;
		for (Aggregator<?, ?> r : aggregators) {
			selectClause += (first ? "" : ", ") + ((SqlClauseSnippet) r).getSqlClause();
			first = false;
		}
		return selectClause;
	}

	@SuppressWarnings("unchecked")
	@Override
	public TPlugin plugin()
	{
		return (TPlugin) getCtxt().getPlugin();
	}

}
