package com.googlecode.qlink.hibernate.functor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.googlecode.qlink.api.behavior.DoResultAsList;
import com.googlecode.qlink.api.functor.Predicate;
import com.googlecode.qlink.api.tuple.Tuple;
import com.googlecode.qlink.core.behavior.DoResultAsListDelegator;
import com.googlecode.qlink.core.context.enums.EFilterCondition;
import com.googlecode.qlink.core.context.enums.EFilterJunction;
import com.googlecode.qlink.core.functor.Predicates.JunctionPredicate;
import com.googlecode.qlink.core.functor.Predicates.PropertyPredicate;
import com.googlecode.qlink.hibernate.behavior.HibernateDoResultAsList;

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
			sqlClause = prop + " " + propertyConditionToSql(condition, val);
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
			if (getVal() == null) {
				return Collections.emptyList();
			}

			if (getVal().getClass().isArray()) {
				return Arrays.asList((Object[]) getVal());
			}

			if (getVal() instanceof List<?>) {
				return (List<Object>) getVal();
			}

			if (getVal() instanceof Tuple) {
				return Arrays.asList(((Tuple) getVal()).toArray());
			}

			if (getVal() instanceof DoResultAsList) {
				DoResultAsList<?, ?> delegate = (DoResultAsList<?, ?>) getVal();
				if (getVal() instanceof DoResultAsListDelegator) {
					delegate = ((DoResultAsListDelegator<?, ?>) getVal()).getDoResultAsListDelegate();
				}

				if (delegate instanceof HibernateDoResultAsList) {
					HibernateDoResultAsList<?, ?> hibernateDelegate = (HibernateDoResultAsList<?, ?>) delegate;

					if (hibernateDelegate.isPureHql()) {
						return hibernateDelegate.getQueryParams();
					}
					else {
						return (List<Object>) hibernateDelegate.toList();
					}
				}
				else {
					return (List<Object>) delegate.toList();
				}
			}

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

	public static String propertyConditionToSql(EFilterCondition cond, Object val)
	{
		switch (cond) {
			case eq:
				return val == null ? "is null" : "= ?";

			case neq:
				return val == null ? "is not null" : "!= ?";

			case gt:
				return "> ?";

			case lt:
				return "< ?";

			case ge:
				return ">= ?";

			case le:
				return "<= ?";

			case between:
				return "between ? and ?";

			case in:
				String inCondition = "in (";

				if (val.getClass().isArray()) {
					inCondition += listToCondition(Arrays.asList((Object[]) val));
				}
				else if (val instanceof List<?>) {
					inCondition += listToCondition((List<?>) val);
				}
				else if (val instanceof DoResultAsList) {
					DoResultAsList<?, ?> delegate = (DoResultAsList<?, ?>) val;
					if (val instanceof DoResultAsListDelegator) {
						delegate = ((DoResultAsListDelegator<?, ?>) val).getDoResultAsListDelegate();
					}

					if (delegate instanceof HibernateDoResultAsList) {
						inCondition += extractSqlCondition((HibernateDoResultAsList<?, ?>) delegate);
					}
					else {
						inCondition += listToCondition(delegate.toList());
					}
				}
				else {
					throw new IllegalArgumentException("unrecognized type for in expression " + val);
				}
				inCondition += ")";

				return inCondition;

			default:
				throw new IllegalArgumentException("unrecognized condition " + cond);
		}

	}

	private static String extractSqlCondition(HibernateDoResultAsList<?, ?> delegate)
	{
		if (delegate.isPureHql()) {
			return delegate.composeHqlQuery(/*countOnly*/false);
		}

		return listToCondition(delegate.toList());
	}

	private static String listToCondition(List<?> lst)
	{
		String condition = "";
		boolean first = true;
		for (Object v : lst) {
			condition += (first ? "?" : ", ?");
			first = false;
		}

		return condition;
	}
}
