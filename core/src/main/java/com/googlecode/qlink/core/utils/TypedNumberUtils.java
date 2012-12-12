package com.googlecode.qlink.core.utils;

import java.util.HashMap;
import java.util.Map;

import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.tuples.Tuples;

public class TypedNumberUtils
{

	private static final Map<Class<?>, Integer> numberRank = new HashMap<Class<?>, Integer>();

	static {
		numberRank.put(Double.class, 1);
		numberRank.put(Float.class, 2);
		numberRank.put(Long.class, 3);
		numberRank.put(Integer.class, 4);
		numberRank.put(Byte.class, 5);
		numberRank.put(Short.class, 6);
	}

	public static double toDouble(Object obj)
	{
		if (obj instanceof Integer) {
			return ((Integer) obj).doubleValue();
		}
		if (obj instanceof Double) {
			return ((Double) obj).doubleValue();
		}
		if (obj instanceof Long) {
			return ((Long) obj).doubleValue();
		}
		if (obj instanceof Short) {
			return ((Short) obj).doubleValue();
		}
		if (obj instanceof Byte) {
			return ((Byte) obj).doubleValue();
		}
		if (obj instanceof Float) {
			return ((Float) obj).doubleValue();
		}

		throw new IllegalArgumentException("can't cast to number " + obj);
	}

	public static boolean isNumber(Class<?> cls)
	{
		return Number.class.isAssignableFrom(cls);
	}

	public static Pair<Object, Object> coarseToCommonValue(Object val1, Object val2)
	{
		Class<?> cls1 = val1.getClass();
		Class<?> cls2 = val2.getClass();

		if (!isNumber(cls1) && !isNumber(cls2)) {
			return Tuples.tie(val1, val2);
		}

		if ((isNumber(cls1) && !isNumber(cls2)) || (!isNumber(cls1) && isNumber(cls2))) {
			throw new IllegalArgumentException("can't coarse " + cls1 + " and " + cls2 + " to common value: " + val1
				+ ", " + val2);
		}

		/*
		 * at this point the two objects are numbers
		 */
		Integer rank1 = numberRank.get(cls1);
		Integer rank2 = numberRank.get(cls2);

		if (rank1 == null) {
			throw new IllegalArgumentException("unrecognized number type " + cls1);
		}

		if (rank2 == null) {
			throw new IllegalArgumentException("unrecognized number type " + cls2);
		}

		if (rank1 < rank2) {
			return Tuples.tie(val1, coarseToNumberType(val2, cls1));
		}

		return Tuples.tie(coarseToNumberType(val1, cls2), val2);
	}

	public static Object coarseToNumberType(Object val, Class<?> numberCls)
	{
		if (Integer.class.equals(numberCls)) {
			return Integer.valueOf((int) TypedNumberUtils.toDouble(val));
		}
		if (Long.class.equals(numberCls)) {
			return Long.valueOf((long) TypedNumberUtils.toDouble(val));
		}
		if (Double.class.equals(numberCls)) {
			return Double.valueOf(TypedNumberUtils.toDouble(val));
		}
		if (Short.class.equals(numberCls)) {
			return Short.valueOf((short) TypedNumberUtils.toDouble(val));
		}
		if (Float.class.equals(numberCls)) {
			return Float.valueOf((float) TypedNumberUtils.toDouble(val));
		}
		if (Byte.class.equals(numberCls)) {
			return Double.valueOf((byte) TypedNumberUtils.toDouble(val));
		}

		throw new IllegalArgumentException("can't recognize type " + numberCls);
	}
}
