package com.googlecode.qlink.hibernate.functor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.googlecode.qlink.api.functor.Predicate;
import com.googlecode.qlink.core.context.enums.EFilterCondition;
import com.googlecode.qlink.core.context.enums.EFilterJunction;
import com.googlecode.qlink.core.functor.Predicates.JunctionPredicate;
import com.googlecode.qlink.core.functor.Predicates.PropertyPredicate;

public class SqlAwarePredicates
{

	public static class SqlAwareJunctionPredicate<T>
		extends JunctionPredicate<T>
		implements SqlClauseSnippet
	{

		private final String sqlClause;

		public SqlAwareJunctionPredicate(Predicate<T> p1, EFilterJunction junction, Predicate<T> p2)
		{
			super(p1, junction, p2);

			String sqlSnippet1 = ((SqlClauseSnippet) getP1()).getSqlClause();
			String sqlSnippet2 = ((SqlClauseSnippet) getP2()).getSqlClause();

			sqlClause = "(" + sqlSnippet1 + ") " + getJunction() + " (" + sqlSnippet2 + ")";
		}

		@Override
		public String toString()
		{
			return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("p1", getP1())
				.append("junction", getJunction()).append("p2", getP2()).toString();
		}

		@Override
		public String getSqlClause()
		{
			return sqlClause;
		}

		@Override
		public List<Object> getParams()
		{
			List<Object> res = new ArrayList<Object>();
			res.addAll(((SqlClauseSnippet) getP1()).getParams());
			res.addAll(((SqlClauseSnippet) getP2()).getParams());

			return res;
		}
	}

	public static class SqlAwarePropertyPredicate<T>
		extends PropertyPredicate<T>
		implements SqlClauseSnippet
	{
		private final String sqlClause;

		public SqlAwarePropertyPredicate(String prop, EFilterCondition condition, Object val)
		{
			super(prop, condition, val);
			sqlClause = prop + " " + conditionToSqlCond(condition) + " ?";
		}

		@Override
		public String toString()
		{
			return new ToStringBuilder(this).append("prop", getProp()).append("condition", getCondition())
				.append("val", getVal()).toString();
		}

		@Override
		public String getSqlClause()
		{
			return sqlClause;
		}

		@Override
		public List<Object> getParams()
		{
			return Arrays.asList(getVal());
		}
	}

	public static class SqlAwareBeginEndPredicate<T>
		implements Predicate<T>, SqlClauseSnippet
	{
		private final Predicate<T> delegate;

		public SqlAwareBeginEndPredicate(Predicate<T> delegate)
		{
			this.delegate = delegate;
		}

		@Override
		public boolean evaluate(T object)
		{
			return delegate.evaluate(object);
		}

		@Override
		public String getSqlClause()
		{
			return "(" + ((SqlClauseSnippet) delegate).getSqlClause() + ")";
		}

		@Override
		public List<Object> getParams()
		{
			return ((SqlClauseSnippet) delegate).getParams();
		}

	}

	public static <T> Predicate<T> junctionPredicate(Predicate<T> p1, EFilterJunction junction, Predicate<T> p2)
	{
		return new SqlAwareJunctionPredicate<T>(p1, junction, p2);
	}

	public static <T> Predicate<T> beginEndPredicate(Predicate<T> delegate)
	{
		return new SqlAwareBeginEndPredicate<T>(delegate);
	}

	public static <T> Predicate<T> propertyPredicate(String propName, EFilterCondition condition, Object val)
	{
		return new SqlAwarePropertyPredicate<T>(propName, condition, val);
	}

	public static String conditionToSqlCond(EFilterCondition cond)
	{
		switch (cond) {
			case eq:
				return "=";

			case gt:
				return ">";

			case lt:
				return "<";

			case ge:
				return ">=";

			default:
				throw new IllegalArgumentException("unrecognized condition " + cond);
		}
	}

}
