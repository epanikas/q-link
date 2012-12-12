package com.googlecode.qlink.core.utils;

public class SimpleAssert {

	public static void notNull(Object obj) {
		notNull(obj, "the object cannot be null");
	}

	public static void notNull(Object obj, String msg) {
		doAssert(obj != null, msg);
	}

	public static void isEquals(Object a, Object b) {
		isEquls(a, b, a + " not equals " + b);
	}

	public static void isEquls(Object a, Object b, String msg) {
		if (a == null) {
			doAssert(a == b, msg);
		} else {
			doAssert(a.equals(b), msg);
		}
	}

	public static void isTrue(boolean res) {
		doAssert(res, "this expression must be true");
	}

	public static void isTrue(boolean res, String msg) {
		doAssert(res, msg);
	}

	private static final void doAssert(boolean res, String msg) {
		if (!res) {
			throw new AssertionException(msg);
		}
	}

}
