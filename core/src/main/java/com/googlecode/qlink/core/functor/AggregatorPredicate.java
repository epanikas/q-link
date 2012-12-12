package com.googlecode.qlink.core.functor;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.googlecode.qlink.api.functor.Aggregator;
import com.googlecode.qlink.api.functor.Predicate;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.enums.EFilterCondition;
import com.googlecode.qlink.core.utils.TypedNumberUtils;

public class AggregatorPredicate<T>
	implements Predicate<List<T>>
{
	private final Aggregator<T, T> aggregator;
	private final EFilterCondition condition;
	private final Object val;

	public AggregatorPredicate(Aggregator<T, T> aggregator, EFilterCondition condition, Object val)
	{
		this.aggregator = aggregator;
		this.condition = condition;
		this.val = val;
	}

	public Aggregator<T, T> getAggregator()
	{
		return aggregator;
	}

	public EFilterCondition getCondition()
	{
		return condition;
	}

	public Object getVal()
	{
		return val;
	}

	@Override
	public boolean evaluate(List<T> lst)
	{

		T aggregatedValue = aggregator.aggregate(lst);

		Pair<Object, Object> coarsedToCommon = TypedNumberUtils.coarseToCommonValue(aggregatedValue, val);

		return Predicates.compareTwo(coarsedToCommon.getFirst(), condition, coarsedToCommon.getSecond());
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this).append("agg", aggregator).append("condition", condition).append("val", val)
			.toString();
	}

}
