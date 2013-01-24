package com.googlecode.qlink.core.functor;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.googlecode.qlink.api.behavior.DoResultAsList;
import com.googlecode.qlink.api.functor.Predicate;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.enums.EFilterCondition;
import com.googlecode.qlink.core.context.enums.EFilterJunction;
import com.googlecode.qlink.core.utils.SimpleAssert;

public class Predicates
{

	private Predicates()
	{
		// private ctor
	}

	public static class JunctionPredicate<T>
		implements Predicate<T>
	{
		private final Predicate<T> p1;
		private final Predicate<T> p2;
		private final EFilterJunction junction;

		public JunctionPredicate(Predicate<T> p1, EFilterJunction junction, Predicate<T> p2)
		{
			this.p1 = p1;
			this.p2 = p2;
			this.junction = junction;
		}

		public Predicate<T> getP1()
		{
			return p1;
		}

		public Predicate<T> getP2()
		{
			return p2;
		}

		public EFilterJunction getJunction()
		{
			return junction;
		}

		@Override
		public boolean evaluate(T object)
		{
			switch (this.junction) {
				case and:
					return this.p1.evaluate(object) && this.p2.evaluate(object);

				case or:
					return this.p1.evaluate(object) || this.p2.evaluate(object);

				default:
					throw new IllegalStateException("unrecognized junction " + this.junction);
			}
		}

		@Override
		public String toString()
		{
			return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("p1", p1)
				.append("junction", junction).append("p2", p2).toString();
		}

	}

	public static class PropertyPredicate<T>
		implements Predicate<T>
	{
		private final String prop;
		private final EFilterCondition condition;
		private final Object val;

		public PropertyPredicate(String prop, EFilterCondition condition, Object val)
		{
			this.prop = prop;
			this.condition = condition;
			this.val = val;
		}

		public String getProp()
		{
			return prop;
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
		public boolean evaluate(T object)
		{

			Object propVal = null;
			if (prop != null) {
				try {
					propVal = PropertyUtils.getProperty(object, prop);
				}
				catch (IllegalAccessException e) {
					throw new IllegalStateException(e);
				}
				catch (InvocationTargetException e) {
					throw new IllegalStateException(e);
				}
				catch (NoSuchMethodException e) {
					throw new IllegalStateException(e);
				}
			}
			else {
				propVal = object;
			}

			return compareTwo(propVal, condition, val);
		}

		@Override
		public String toString()
		{
			return new ToStringBuilder(this).append("prop", prop).append("condition", condition).append("val", val)
				.toString();
		}

	}

	@SuppressWarnings("unchecked")
	public static boolean compareTwo(Object val1, EFilterCondition condition, Object val2)
	{

		if (condition != EFilterCondition.eq && condition != EFilterCondition.neq) {
			SimpleAssert.notNull(val1);
			SimpleAssert.notNull(val2);

		}
		if (condition != EFilterCondition.eq && condition != EFilterCondition.neq && condition != EFilterCondition.in) {
			SimpleAssert.isTrue(val1 instanceof Comparable, val1 + " is not comparable");
		}

		switch (condition) {
			case eq:
				return val1 == null ? val1 == val2 : val1.equals(val2);

			case neq:
				return val1 == null ? val1 != val2 : val1.equals(val2) == false;

			case gt:
				return ((Comparable<Object>) val1).compareTo(val2) > 0;

			case lt:
				return ((Comparable<Object>) val1).compareTo(val2) < 0;

			case ge:
				return ((Comparable<Object>) val1).compareTo(val2) >= 0;

			case le:
				return ((Comparable<Object>) val1).compareTo(val2) <= 0;

			case between:
				Pair<?, ?> param = (Pair<?, ?>) val2;
				return ((Comparable<Object>) val1).compareTo(param.getFirst()) >= 0
					&& ((Comparable<Object>) val1).compareTo(param.getSecond()) <= 0;

			case in:
				if (val2.getClass().isArray()) {
					return CollectionUtils.cardinality(val1, Arrays.asList((Object[]) val2)) > 0;
				}

				if (val2 instanceof DoResultAsList<?, ?>) {
					List<?> values = ((DoResultAsList<?, ?>) val2).toList();
					return CollectionUtils.cardinality(val1, values) > 0;
				}

				throw new IllegalStateException("unrecognized parameter for in " + val2);

			default:
				throw new IllegalStateException("unrecognized condition " + condition);
		}
	}

	public static <T> Predicate<T> booleanConst(final boolean val)
	{
		return new Predicate<T>() {
			@Override
			public boolean evaluate(T object)
			{
				return val;
			}
		};
	}

	public static <T> Predicate<T> junctionPredicate(Predicate<T> p1, EFilterJunction junction, Predicate<T> p2)
	{
		return new JunctionPredicate<T>(p1, junction, p2);
	}

	public static <T> Predicate<T> propertyPredicate(String propName, EFilterCondition condition, Object val)
	{
		return new PropertyPredicate<T>(propName, condition, val);
	}
}
