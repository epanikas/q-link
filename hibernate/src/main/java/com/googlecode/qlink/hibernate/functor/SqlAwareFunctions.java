package com.googlecode.qlink.hibernate.functor;

import java.util.Collections;
import java.util.List;


import com.googlecode.qlink.api.functor.Function;
import com.googlecode.qlink.api.functor.Function2;
import com.googlecode.qlink.core.functor.Functions.FunctionWithIndexAdapter;
import com.googlecode.qlink.core.functor.Functions.PropertyAccessor;

public class SqlAwareFunctions
{
	private SqlAwareFunctions()
	{
		// private ctor
	}

	private static class SqlAwarePropertyAccessor<T, R>
		extends PropertyAccessor<T, R>
		implements Function<T, R>, SqlClauseSnippet
	{

		public SqlAwarePropertyAccessor(String propName, Class<R> propClass)
		{
			super(propName, propClass);
		}

		@Override
		public String getSqlClause()
		{
			return getPropName();
		}

		@Override
		public List<Object> getParams()
		{
			return Collections.emptyList();
		}
	}

	private static class ConstantValue<T, R>
		implements Function<T, R>, SqlClauseSnippet
	{

		private final R value;

		public ConstantValue(R value)
		{
			this.value = value;
		}

		@Override
		public String getSqlClause()
		{
			return "'" + value.toString() + "'";
		}

		@Override
		public List<Object> getParams()
		{
			return Collections.emptyList();
		}

		@Override
		public R apply(Object input)
		{
			return value;
		}
	}

	public static <T, R> Function<T, R> propertyAccessor(String propName, Class<R> propClass)
	{
		return new SqlAwarePropertyAccessor<T, R>(propName, propClass);
	}

	public static <T, R> Function<T, R> constant(final R constant)
	{
		return new ConstantValue<T, R>(constant);
	}

	public static SqlClauseSnippet getSqlClauseSnippet(Function2<?, ?, ?> f)
	{
		if (f == null) {
			return null;
		}

		if (f instanceof SqlClauseSnippet) {
			return (SqlClauseSnippet) f;
		}

		else if (f instanceof FunctionWithIndexAdapter) {
			return (SqlClauseSnippet) ((FunctionWithIndexAdapter<?, ?>) f).getDelegate();
		}

		throw new IllegalArgumentException("can't find SqlClauseSnippet for " + f);

	}

}
