package com.googlecode.qlink.core.functor;

import java.util.Comparator;

public class Comparators
{

	private static class NaturalComparator<T extends Comparable<T>>
		implements Comparator<T>
	{

		@Override
		public int compare(T o1, T o2)
		{
			return o1.compareTo(o2);
		}

	}

	public static <T extends Comparable<T>> Comparator<T> naturalComparator()
	{
		return new NaturalComparator<T>();
	}

}
