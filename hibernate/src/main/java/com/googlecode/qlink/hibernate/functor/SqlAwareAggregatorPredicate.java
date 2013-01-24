package com.googlecode.qlink.hibernate.functor;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.googlecode.qlink.api.functor.Aggregator;
import com.googlecode.qlink.core.context.enums.EFilterCondition;
import com.googlecode.qlink.core.functor.AggregatorPredicate;

public class SqlAwareAggregatorPredicate<T>
	extends AggregatorPredicate<T>
	implements SqlClauseSnippet
{

	public SqlAwareAggregatorPredicate(Aggregator<T, T> aggregator, EFilterCondition condition, Object val)
	{
		super(aggregator, condition, val);
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this).append("aggregator", getAggregator()).append("condition", getCondition())
			.append("val", getVal()).toString();
	}

	@Override
	public String getSqlClause()
	{
		return ((SqlClauseSnippet) getAggregator()).getSqlClause()
			+ SqlAwarePredicates.propertyConditionToSql(getCondition(), getVal());
	}

	@Override
	public List<Object> getParams()
	{
		return Arrays.asList(getVal());
	}
}
