package com.googlecode.qlink.core.pruning.order;

import java.util.Comparator;

public class ToStringComparator<T> implements Comparator<T> {
	private final String expr;

	public ToStringComparator(String expr) {
		this.expr = expr;
	}

	@Override
	public int compare(T o1, T o2) {
		return ((Comparable<T>) o1).compareTo(o2);
	}

	@Override
	public String toString() {
		return expr;
	}
}
