package com.googlecode.qlink.mem.utils;


import com.googlecode.qlink.api.functor.Aggregator;
import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.core.context.enums.EAggregatorType;
import com.googlecode.qlink.core.functor.Aggregators;

public class MemAggregationUtils
{

	private MemAggregationUtils()
	{
		// private ctor
	}

	@SuppressWarnings({"unchecked","rawtypes"})
	public static <T, TRes> Aggregator<T, TRes> chooseAggregator(EAggregatorType type, final TProperty<TRes> prop)
	{
		switch (type) {
			case sum:
				if (prop != null) {
					return Aggregators.sumOfProperty(prop);
				}
				return (Aggregator<T, TRes>) Aggregators.<T> sum();

			case min:
				if (prop != null) {
					return Aggregators.minOfProperty((TProperty<Comparable>) prop);
				}
				return (Aggregator<T, TRes>) Aggregators.min();

			case max:
				if (prop != null) {
					return Aggregators.maxOfProperty((TProperty<Comparable>) prop);
				}
				return (Aggregator<T, TRes>) Aggregators.<T> max();

			case count:
				return (Aggregator<T, TRes>) Aggregators.<T> count();

			default:
				throw new IllegalArgumentException("can't find the correspondng aggregator for " + type);
		}

	}

}
