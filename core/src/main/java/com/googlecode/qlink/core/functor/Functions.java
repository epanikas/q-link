package com.googlecode.qlink.core.functor;

import com.googlecode.qlink.api.functor.Function;
import com.googlecode.qlink.api.functor.Function2;
import com.googlecode.qlink.api.functor.Visitor;
import com.googlecode.qlink.api.functor.Visitor2;
import com.googlecode.qlink.core.utils.TypedBeanUtils;

public class Functions
{
	private Functions()
	{
		// private ctor
	}

	public static class PropertyAccessor<T, R>
		implements Function<T, R>
	{
		private final String propName;
		private final Class<R> propClass;

		public PropertyAccessor(String propName, Class<R> propClass)
		{
			this.propName = propName;
			this.propClass = propClass;
		}

		public String getPropName()
		{
			return propName;
		}

		public Class<R> getPropClass()
		{
			return propClass;
		}

		@Override
		public R apply(T input)
		{
			return TypedBeanUtils.getPropertyAs(input, propName, propClass);
		}
	}

	public static <T, R> Function<T, R> propertyAccessor(String propName, Class<R> propClass)
	{
		return new Functions.PropertyAccessor<T, R>(propName, propClass);
	}

	public static <T, R> Function<T, R> constant(final R constant)
	{
		return new Function<T, R>() {
			@Override
			public R apply(T input)
			{
				return constant;
			}
		};
	}

	@SuppressWarnings("unchecked")
	public static <A, B> Function2<A, Integer, B> adaptToFunctionWithIndex(final Function<A, B> f)
	{
		if (f instanceof Function2) {
			return (Function2<A, Integer, B>) f;
		}

		return new FunctionWithIndexAdapter<A, B>(f);
	}

	public static class FunctionWithIndexAdapter<A, B>
		implements Function2<A, Integer, B>
	{
		private final Function<A, B> delegate;

		public FunctionWithIndexAdapter(Function<A, B> delegate)
		{
			this.delegate = delegate;
		}

		public Function<A, B> getDelegate()
		{
			return delegate;
		}

		@Override
		public B apply(A input, Integer elementIndex)
		{
			return delegate.apply(input);
		}

		@Override
		public String toString()
		{
			return delegate.toString();
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> Visitor2<T, Integer> adaptToVisitorWithIndex(final Visitor<T> f)
	{
		if (f instanceof Function2) {
			return (Visitor2<T, Integer>) f;
		}

		return new VisitorWithIndexAdapter<T>(f);
	}

	public static class VisitorWithIndexAdapter<T>
		implements Visitor2<T, Integer>
	{
		private final Visitor<T> delegate;

		public VisitorWithIndexAdapter(Visitor<T> delegate)
		{
			this.delegate = delegate;
		}

		@Override
		public void apply(T a, Integer b)
		{
			delegate.apply(a);
		};
	}
}
