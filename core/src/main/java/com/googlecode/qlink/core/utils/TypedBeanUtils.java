package com.googlecode.qlink.core.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import org.apache.commons.beanutils.PropertyUtils;

import com.googlecode.qlink.api.functor.TProperty;

public class TypedBeanUtils
{

	public static <R> R getPropertyAs(Object bean, TProperty<R> prop)
	{
		return getPropertyAs(bean, prop.getName(), prop.getPropertyCls());
	}

	@SuppressWarnings("unchecked")
	public static <R> R getPropertyAs(Object bean, String propName, Class<R> cls)
	{
		Object res = null;
		try {
			res = PropertyUtils.getProperty(bean, propName);
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

		SimpleAssert.isTrue(cls.isAssignableFrom(res.getClass()), cls + " is not assignable from " + res.getClass());

		return (R) res;
	}

	@SuppressWarnings("unchecked")
	public static <R> Constructor<R> findConstructor(Class<R> cls, Class<?>[] params)
	{
		Constructor<?>[] constructors = cls.getConstructors();
		for (Constructor<?> con : constructors) {
			Class<?>[] conParams = con.getParameterTypes();
			if (Arrays.equals(conParams, params)) {
				return (Constructor<R>) con;
			}
		}

		return null;
	}

	public static <R> R createObjectForClass(Class<R> cls, Object[] args)
	{
		Class<?>[] paramCls = new Class<?>[args.length];
		int i = 0;
		for (Object o : args) {
			paramCls[i++] = o.getClass();
		}

		Constructor<R> con = TypedBeanUtils.findConstructor(cls, paramCls);
		SimpleAssert.notNull(con, "can't find ctor for " + cls + " and " + paramCls + ", actual " + args);
		try {
			return con.newInstance(args);
		}
		catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e);
		}
		catch (InstantiationException e) {
			throw new IllegalArgumentException(e);
		}
		catch (IllegalAccessException e) {
			throw new IllegalArgumentException(e);
		}
		catch (InvocationTargetException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
