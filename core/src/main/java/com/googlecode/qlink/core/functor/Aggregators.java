package com.googlecode.qlink.core.functor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.googlecode.qlink.api.functor.Aggregator;
import com.googlecode.qlink.api.functor.Function;
import com.googlecode.qlink.api.functor.Function2;
import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.enums.EAggregatorType;
import com.googlecode.qlink.core.utils.SimpleAssert;
import com.googlecode.qlink.core.utils.TypedBeanUtils;
import com.googlecode.qlink.core.utils.TypedNumberUtils;

@SuppressWarnings({"rawtypes","unchecked"})
public class Aggregators
{

	private static final CountAggregator COUNT_AGGREGATOR = new CountAggregator();

	private static final SumAggregator SUM_AGGREGATOR = new SumAggregator();

	private static final MinAggregator MIN_AGGREGATOR = new MinAggregator();

	private static final MaxAggregator MAX_AGGREGATOR = new MaxAggregator();

	public static class CountAggregator<T>
		implements Aggregator<T, Long>
	{

		@Override
		public Long aggregate(Collection<T> lst)
		{
			return Long.valueOf(lst.size());
		}

		@Override
		public String toString()
		{
			return "aggregator: count";
		}
	}

	private static class PropertyAggregatorSupport<T, R>
	{

		private final EAggregatorType aggType;
		private final TProperty<R> prop;

		public PropertyAggregatorSupport(EAggregatorType aggType, TProperty<R> prop)
		{
			this.aggType = aggType;
			this.prop = prop;
		}

		public EAggregatorType getAggregatorType()
		{
			return aggType;
		}

		public TProperty<R> getProp()
		{
			return prop;
		}

		@Override
		public String toString()
		{
			return "aggregator: " + aggType + " " + getProp();
		}
	}

	public static class SumOfPropertyAggregator<T, TRes>
		extends PropertyAggregatorSupport<T, TRes>
		implements Aggregator<T, TRes>
	{

		public SumOfPropertyAggregator(TProperty<TRes> prop)
		{
			super(EAggregatorType.sum, prop);
		}

		@Override
		public TRes aggregate(Collection<T> lst)
		{
			double sum = 0;
			for (T obj : lst) {
				double val = TypedNumberUtils.toDouble(TypedBeanUtils.getPropertyAs(obj, getProp()));
				sum += val;
			}

			return (TRes) Double.valueOf(sum);
		}
	}

	public static class SumAggregator<T>
		implements Aggregator<T, T>
	{

		@Override
		public T aggregate(Collection<T> lst)
		{

			SimpleAssert.isTrue(lst.size() > 0, "can't sum up empty list");

			double sum = 0;
			for (T obj : lst) {
				double val = TypedNumberUtils.toDouble(obj);
				sum += val;
			}

			Class<T> resCls = (Class<T>) lst.iterator().next().getClass();
			return (T) TypedNumberUtils.coarseToNumberType(sum, resCls);
		}
	}

	public static class MinOfPropertyAggregator<T, R extends Comparable<R>>
		extends PropertyAggregatorSupport<T, R>
		implements Aggregator<T, R>
	{

		public MinOfPropertyAggregator(TProperty<R> prop)
		{
			super(EAggregatorType.min, prop);
		}

		@Override
		public R aggregate(Collection<T> lst)
		{
			List<R> tmp = new ArrayList<R>();
			for (T t : lst) {
				tmp.add(TypedBeanUtils.getPropertyAs(t, getProp()));
			}

			return (R) MIN_AGGREGATOR.aggregate(tmp);
		}
	}

	public static class MinAggregator<T extends Comparable<T>>
		implements Aggregator<T, T>
	{
		@Override
		public T aggregate(Collection<T> lst)
		{
			return Collections.min(lst);
		}
	}

	public static class MaxOfProperty<T, R extends Comparable<R>>
		extends PropertyAggregatorSupport<T, R>
		implements Aggregator<T, R>
	{

		public MaxOfProperty(TProperty<R> prop)
		{
			super(EAggregatorType.max, prop);
		}

		@Override
		public R aggregate(Collection<T> lst)
		{
			List<R> tmp = new ArrayList<R>();
			for (T t : lst) {
				tmp.add(TypedBeanUtils.getPropertyAs(t, getProp()));
			}

			return (R) MAX_AGGREGATOR.aggregate(tmp);
		}
	}

	public static class MaxAggregator<T extends Comparable<T>>
		implements Aggregator<T, T>
	{

		@Override
		public T aggregate(Collection<T> lst)
		{
			return Collections.max(lst);
		}
	}

	public static <T> Aggregator<T, Long> count()
	{
		return COUNT_AGGREGATOR;
	}

	public static <T> Aggregator<T, T> sum()
	{
		return SUM_AGGREGATOR;
	}

	public static <T> Aggregator<T, T> max()
	{
		return MAX_AGGREGATOR;
	}

	public static <T> Aggregator<T, T> min()
	{
		return MIN_AGGREGATOR;
	}

	public static <T, R> Aggregator<T, R> sumOfProperty(final TProperty<R> prop)
	{
		return new SumOfPropertyAggregator<T, R>(prop);
	}

	public static <T, R extends Comparable<R>> Aggregator<T, R> minOfProperty(final TProperty<R> prop)
	{
		return new MinOfPropertyAggregator<T, R>(prop);
	}

	public static <T, R extends Comparable<R>> Aggregator<T, R> maxOfProperty(final TProperty<R> prop)
	{
		return new MaxOfProperty<T, R>(prop);
	}

	public static <T, R> Function<Pair<?, Collection<T>>, R> adaptToPair(final Aggregator<T, R> agg)
	{
		return new Function<Pair<?, Collection<T>>, R>() {

			@Override
			public R apply(Pair<?, Collection<T>> input)
			{
				return agg.aggregate(input.getSecond());
			}

		};
	}

	public static class AggregatorToFunctionWithIndexAdaptor<T, O>
		implements Function2<Collection<T>, Integer, O>
	{
		private final Aggregator<T, O> delegate;

		public AggregatorToFunctionWithIndexAdaptor(Aggregator<T, O> delegate)
		{
			this.delegate = delegate;
		}

		@Override
		public O apply(Collection<T> lst, Integer elementIndex)
		{
			return delegate.aggregate(lst);
		}

		public Aggregator<T, O> getDelegate()
		{
			return delegate;
		}
	}

	public static <T, O> Function2<Collection<T>, Integer, O> asFunctionWithIndex(final Aggregator<T, O> agg)
	{
		return new AggregatorToFunctionWithIndexAdaptor<T, O>(agg);
	}

}
