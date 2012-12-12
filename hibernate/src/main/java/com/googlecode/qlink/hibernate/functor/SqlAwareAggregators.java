package com.googlecode.qlink.hibernate.functor;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


import com.googlecode.qlink.api.functor.Aggregator;
import com.googlecode.qlink.api.functor.Function2;
import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.core.functor.Aggregators;

public class SqlAwareAggregators
{

	@SuppressWarnings("rawtypes")
	private static final CountAggregator COUNT_AGGREGATOR = new CountAggregator();

	public static class CountAggregator<T>
		extends Aggregators.CountAggregator<T>
		implements SqlClauseSnippet
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

		@Override
		public String getSqlClause()
		{
			return "count(*)";
		}

		@Override
		public List<Object> getParams()
		{
			return Collections.emptyList();
		}
	}

	public static class SumOfPropertyAggregator<T, TRes>
		extends Aggregators.SumOfPropertyAggregator<T, TRes>
		implements SqlClauseSnippet
	{

		public SumOfPropertyAggregator(TProperty<TRes> prop)
		{
			super(prop);
		}

		@Override
		public String toString()
		{
			return "aggregator: sumOf " + getProp();
		}

		@Override
		public String getSqlClause()
		{
			return "sum(" + getProp().getName() + ")";
		}

		@Override
		public List<Object> getParams()
		{
			return Collections.emptyList();
		}
	}

	public static class MinOfPropertyAggregator<T, R extends Comparable<R>>
		extends Aggregators.MinOfPropertyAggregator<T, R>
		implements SqlClauseSnippet
	{

		public MinOfPropertyAggregator(TProperty<R> prop)
		{
			super(prop);
		}

		@Override
		public String toString()
		{
			return "aggregator: minOf " + getProp();
		}

		@Override
		public String getSqlClause()
		{
			return "min(" + getProp().getName() + ")";
		}

		@Override
		public List<Object> getParams()
		{
			return Collections.emptyList();
		}
	}

	public static class MaxOfPropertyAggregator<T, R extends Comparable<R>>
		extends Aggregators.MaxOfProperty<T, R>
		implements SqlClauseSnippet
	{

		public MaxOfPropertyAggregator(TProperty<R> prop)
		{
			super(prop);
		}

		@Override
		public String toString()
		{
			return "aggregator: maxOf " + getProp();
		}

		@Override
		public String getSqlClause()
		{
			return "max(" + getProp().getName() + ")";
		}

		@Override
		public List<Object> getParams()
		{
			return Collections.emptyList();
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> Aggregator<T, Long> count()
	{
		return COUNT_AGGREGATOR;
	}

	public static <T, TRes> Aggregator<T, TRes> sumOfProperty(final TProperty<TRes> prop)
	{
		return new SumOfPropertyAggregator<T, TRes>(prop);
	}

	public static <T, R extends Comparable<R>> Aggregator<T, R> minOfProperty(final TProperty<R> prop)
	{
		return new MinOfPropertyAggregator<T, R>(prop);
	}

	public static <T, R extends Comparable<R>> Aggregator<T, R> maxOfProperty(final TProperty<R> prop)
	{
		return new MaxOfPropertyAggregator<T, R>(prop);
	}

	public static class AggregatorToFunction2Adaptor<T, O>
		implements Function2<Collection<T>, Integer, O>, SqlClauseSnippet

	{
		private final Aggregator<T, O> delegate;

		public AggregatorToFunction2Adaptor(Aggregator<T, O> delegate)
		{
			this.delegate = delegate;
		}

		@Override
		public O apply(Collection<T> lst, Integer elementIndex)
		{
			return delegate.aggregate(lst);
		}

		@Override
		public String getSqlClause()
		{
			return ((SqlClauseSnippet) delegate).getSqlClause();
		}

		@Override
		public List<Object> getParams()
		{
			return ((SqlClauseSnippet) delegate).getParams();
		}

		public Aggregator<T, O> getDelegate()
		{
			return delegate;
		}
	}

	public static <T, O> Function2<Collection<T>, Integer, O> asFunctionWithIndex(final Aggregator<T, O> agg)
	{
		return new AggregatorToFunction2Adaptor<T, O>(agg);
	}
}
