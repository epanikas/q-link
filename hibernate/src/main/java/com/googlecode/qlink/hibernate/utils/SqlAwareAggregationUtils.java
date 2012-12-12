package com.googlecode.qlink.hibernate.utils;


import com.googlecode.qlink.api.functor.Aggregator;
import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.core.context.enums.EAggregatorType;
import com.googlecode.qlink.core.functor.Aggregators;
import com.googlecode.qlink.hibernate.functor.SqlAwareAggregators;

public class SqlAwareAggregationUtils
{

	private SqlAwareAggregationUtils()
	{
		// private ctor
	}

	@SuppressWarnings({"unchecked","rawtypes"})
	public static <T, TRes> Aggregator<T, TRes> chooseAggregator(EAggregatorType type, final TProperty<TRes> prop)
	{
		switch (type) {
			case sum:
				if (prop != null) {
					return SqlAwareAggregators.sumOfProperty(prop);
				}
				return (Aggregator<T, TRes>) Aggregators.<T> sum();

			case min:
				if (prop != null) {
					return SqlAwareAggregators.minOfProperty((TProperty<Comparable>) prop);
				}
				return (Aggregator<T, TRes>) Aggregators.<T> min();

			case max:
				if (prop != null) {
					return SqlAwareAggregators.maxOfProperty((TProperty<Comparable>) prop);
				}
				return (Aggregator<T, TRes>) Aggregators.<T> max();

			case count:
				return (Aggregator<T, TRes>) SqlAwareAggregators.<T> count();

			default:
				throw new IllegalArgumentException("can't find the correspondng aggregator for " + type);
		}
	}

}
