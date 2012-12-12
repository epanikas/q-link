package com.googlecode.qlink.core.functor;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;

import com.googlecode.qlink.api.functor.Function2;
import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.api.functor.Visitor2;

public class Visitors
{

	private Visitors()
	{
		// shouldn't be instantiated
	}

	public static class AssignPropertyFromPropertyVisitor<T>
		implements Visitor2<T, Integer>
	{
		private final TProperty<?> lhs;
		private final TProperty<?> rhs;

		public AssignPropertyFromPropertyVisitor(TProperty<?> lhs, TProperty<?> rhs)
		{
			this.lhs = lhs;
			this.rhs = rhs;
		}

		@Override
		public void apply(T a, Integer b)
		{
			try {
				Object val = PropertyUtils.getProperty(a, rhs.getName());
				PropertyUtils.setProperty(a, lhs.getName(), val);
			}
			catch (IllegalAccessException e) {
				throw new IllegalArgumentException(e);
			}
			catch (InvocationTargetException e) {
				throw new IllegalArgumentException(e);
			}
			catch (NoSuchMethodException e) {
				throw new IllegalArgumentException(e);
			}

		}
	}

	public static class AssignPropertyFromValueVisitor<T>
		implements Visitor2<T, Integer>
	{

		private final TProperty<?> lhs;
		private final Object val;

		public AssignPropertyFromValueVisitor(TProperty<?> lhs, Object val)
		{
			this.lhs = lhs;
			this.val = val;
		}

		@Override
		public void apply(T a, Integer b)
		{
			try {
				PropertyUtils.setProperty(a, lhs.getName(), val);
			}
			catch (IllegalAccessException e) {
				throw new IllegalArgumentException(e);
			}
			catch (InvocationTargetException e) {
				throw new IllegalArgumentException(e);
			}
			catch (NoSuchMethodException e) {
				throw new IllegalArgumentException(e);
			}
		}
	}
	public static class AssignPropertyFromFunctionVisitor<T>
		implements Visitor2<T, Integer>
	{

		private final TProperty<?> lhs;
		private final Function2<T, Integer, ?> f;

		public AssignPropertyFromFunctionVisitor(TProperty<?> lhs, Function2<T, Integer, ?> f)
		{
			this.lhs = lhs;
			this.f = f;
		}

		@Override
		public void apply(T a, Integer b)
		{
			try {
				Object val = f.apply(a, b);
				PropertyUtils.setProperty(a, lhs.getName(), val);
			}
			catch (IllegalAccessException e) {
				throw new IllegalArgumentException(e);
			}
			catch (InvocationTargetException e) {
				throw new IllegalArgumentException(e);
			}
			catch (NoSuchMethodException e) {
				throw new IllegalArgumentException(e);
			}
		}
	}

}
