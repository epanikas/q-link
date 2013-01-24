package com.googlecode.qlink.hibernate.behavior;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.googlecode.qlink.api.functor.Function2;
import com.googlecode.qlink.api.functor.Predicate;
import com.googlecode.qlink.api.functor.SamplePredicate;
import com.googlecode.qlink.api.functor.Visitor2;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.PipelineContextAwareSupport;
import com.googlecode.qlink.core.functor.SamplePredicates;
import com.googlecode.qlink.core.utils.StackPruningUtils;
import com.googlecode.qlink.hibernate.context.HibernatePipelineContext;
import com.googlecode.qlink.hibernate.functor.SqlAwareFunctions;
import com.googlecode.qlink.hibernate.functor.SqlClauseSnippet;
import com.googlecode.qlink.hibernate.pruning.HibernatePruningRules;
import com.googlecode.qlink.hibernate.utils.SqlAwareFunctionUtils;

class HibernateDoResultSupport
	extends PipelineContextAwareSupport
{
	//	private Predicate<?> filterPredicate;
	//	private SamplePredicate samplePredicate;

	protected HibernateDoResultSupport(IPipelineContext ctxt)
	{
		super(ctxt);
	}

	public Function2<?, Integer, ?> createSelectGroupFunction()
	{
		return SqlAwareFunctionUtils.createTransformFunctionInternal(getCtxt().getPipelineDef().getSelectGroupStack(),
			getCtxt().getPipelineDef().getSelectGroupResultType(), getCtxt().getPipelineDef()
				.getSelectGroupResultClass());
	}

	public Function2<?, Integer, ?> createTransformFunction()
	{
		return SqlAwareFunctionUtils.createTransformFunctionInternal(getCtxt().getPipelineDef().getTransformStack(),
			getCtxt().getPipelineDef().getTransformResultType(), getCtxt().getPipelineDef().getTransformResultClass());
	}

	protected HibernatePipelineContext getHibernateCtxt()
	{
		return (HibernatePipelineContext) getCtxt();
	}

	public String getFromClause()
	{
		return getHibernateCtxt().getSourceCls().getName();
	}

	public String getWhereClause(Predicate<?> filterPredicate)
	{

		if (filterPredicate == null || filterPredicate instanceof SqlClauseSnippet == false) {
			return null;
		}

		final SqlClauseSnippet f = (SqlClauseSnippet) filterPredicate;
		return f.getSqlClause();
	}

	public String getOrderClause()
	{
		Comparator<?> orderComparator =
			StackPruningUtils.createComparator(HibernatePruningRules.orderPruner, getCtxt().getPipelineDef()
				.getOrderStack());

		if (orderComparator == null || orderComparator instanceof SqlClauseSnippet == false) {
			return null;
		}

		final SqlClauseSnippet c = (SqlClauseSnippet) orderComparator;
		return c.getSqlClause();
	}

	public String getSelectClause(SamplePredicate samplePredicate)
	{

		if (samplePredicate != null) {
			return null;
		}

		final SqlClauseSnippet selectFunc = SqlAwareFunctions.getSqlClauseSnippet(createTransformFunction());

		Function2 f = createSelectGroupFunction();

		final SqlClauseSnippet selectGroupFunc = (SqlClauseSnippet) f;
		if (selectFunc != null) {
			return selectFunc.getSqlClause();
		}
		if (selectGroupFunc != null) {
			return selectGroupFunc.getSqlClause();
		}
		return null;
	}

	public List<Object> getQueryParams()
	{
		Predicate<?> filterPredicate =
			StackPruningUtils.createFilterPredicate(HibernatePruningRules.filterPruner, getCtxt().getPipelineDef()
				.getFilterStack());

		if (filterPredicate == null || filterPredicate instanceof SqlClauseSnippet == false) {
			return null;
		}

		final SqlClauseSnippet f = (SqlClauseSnippet) filterPredicate;
		List<Object> params = new ArrayList<Object>(f == null ? null : (f).getParams());

		CollectionUtils.filter(params, new org.apache.commons.collections.Predicate() {

			@Override
			public boolean evaluate(Object obj)
			{
				return obj != null;
			}
		});

		return params;
	}

	@SuppressWarnings("unchecked")
	protected Object makeRowResultInSql(boolean countOnly)
	{
		SamplePredicate samplePredicate = createSamplePredicate();

		Predicate<?> filterPredicate =
			StackPruningUtils.createFilterPredicate(HibernatePruningRules.filterPruner, getCtxt().getPipelineDef()
				.getFilterStack());

		String hql = doComposeHqlQuery(countOnly);

		List<Object> params = getQueryParams();

		HibernateTemplate template = getHibernateCtxt().getHibernateTemplate();

		Object res = null;
		if (params != null && params.size() > 0) {
			res = template.find(hql, params.toArray(new Object[]{}));
		}
		else {
			res = template.find(hql);
		}

		if (countOnly && samplePredicate == null) {
			return res;
		}

		/*
		 * reapply in memory the missing processing parts
		 */

		List<?> lstRes = (List<?>) res;

		//		final Predicate<Object> f =
		//			(Predicate<Object>) StackPruningUtils.createFilterPredicate(HibernatePruningRules.filterPruner, getCtxt()
		//				.getPipelineDef().getFilterStack());

		final Predicate<Object> f = (Predicate<Object>) filterPredicate;
		if (filterPredicate != null && filterPredicate instanceof SqlClauseSnippet == false) {

			CollectionUtils.filter(lstRes, new org.apache.commons.collections.Predicate() {

				@Override
				public boolean evaluate(Object obj)
				{
					return f.evaluate(obj);
				}
			});
		}

		final Comparator<Object> c =
			(Comparator<Object>) StackPruningUtils.createComparator(HibernatePruningRules.orderPruner, getCtxt()
				.getPipelineDef().getOrderStack());
		if (c != null && c instanceof SqlClauseSnippet == false) {
			Collections.sort(lstRes, c);
		}

		if (samplePredicate != null) {
			Iterator<?> it = lstRes.iterator();
			int n = lstRes.size();
			for (int i = 0; i < n; ++i) {
				it.next();
				if (samplePredicate.evaluate(i, n) == false) {
					it.remove();
				}
			}
		}

		if (countOnly) {
			return lstRes.size();
		}

		final Visitor2<Object, Integer> visitor =
			(Visitor2<Object, Integer>) StackPruningUtils.createVisitor(HibernatePruningRules.visitorPruner, getCtxt()
				.getPipelineDef().getVisitStack());
		if (visitor != null) {
			int i = 0;
			for (Object obj : lstRes) {
				visitor.apply(obj, i++);
			}
		}

		return res;
	}

	public boolean isPureHql()
	{
		Predicate<?> filter =
			StackPruningUtils.createFilterPredicate(HibernatePruningRules.filterPruner, getCtxt().getPipelineDef()
				.getFilterStack());

		Comparator<?> comparator =
			StackPruningUtils.createComparator(HibernatePruningRules.orderPruner, getCtxt().getPipelineDef()
				.getOrderStack());

		Visitor2<?, Integer> visitor =
			StackPruningUtils.createVisitor(HibernatePruningRules.visitorPruner, getCtxt().getPipelineDef()
				.getVisitStack());

		SamplePredicate sample = createSamplePredicate();

		if (filter != null && filter instanceof SqlClauseSnippet == false) {
			return false;
		}

		if (comparator != null && comparator instanceof SqlClauseSnippet == false) {
			return false;
		}

		if (sample != null) {
			return false;
		}

		if (visitor != null) {
			return false;
		}

		return true;
	}

	public String composeHqlQuery(boolean countOnly)
	{
		return doComposeHqlQuery(countOnly);
	}

	private String doComposeHqlQuery(boolean countOnly)
	{
		SamplePredicate samplePredicate = createSamplePredicate();

		/*
		 * we can use count in sql only in case of absence sampling predicate, 
		 * since it can alter the count result
		 */
		boolean useSqlCount = countOnly && samplePredicate == null;

		Predicate<?> filterPredicate =
			StackPruningUtils.createFilterPredicate(HibernatePruningRules.filterPruner, getCtxt().getPipelineDef()
				.getFilterStack());

		String whereClause = getWhereClause(filterPredicate);

		String orderClause = getOrderClause();

		String selectClause = useSqlCount ? "count(1)" : getSelectClause(samplePredicate);

		String fromClause = getFromClause();

		String hql = composeHqlQueryFromComponents(selectClause, fromClause, whereClause, orderClause);

		return hql;
	}

	String composeHqlQueryFromComponents(String selectClause, String fromClause, String whereClause, String orderClause)
	{

		String hql = "";
		if (selectClause != null) {
			hql += "SELECT " + selectClause + " ";
		}

		hql += " FROM " + fromClause + " ";

		if (whereClause != null) {
			hql += "WHERE " + whereClause + " ";
		}

		if (orderClause != null) {
			hql += "ORDER BY " + orderClause + " ";
		}

		return hql;
	}

	private SamplePredicate createSamplePredicate()
	{

		SamplePredicate sp = getCtxt().getPipelineDef().getSamplePredicate();
		if (sp == null) {
			sp =
				SamplePredicates.createSamplePredicate(getCtxt().getPipelineDef().getSampleType(), getCtxt()
					.getPipelineDef().getSampleParam());
		}

		return sp;
	}

}
