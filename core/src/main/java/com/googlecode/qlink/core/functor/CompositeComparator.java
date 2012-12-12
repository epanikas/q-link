package com.googlecode.qlink.core.functor;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class CompositeComparator<T>
	implements Comparator<T>
{
	private final List<Comparator<T>> comparators;

	public CompositeComparator(Comparator<T>... comparators)
	{
		this.comparators = Arrays.asList(comparators);
	}

	@Override
	public int compare(T o1, T o2)
	{
		for (Comparator<T> c : comparators) {
			int res = c.compare(o1, o2);
			if (res != 0) {
				return res;
			}
		}

		return 0;
	}

	public List<Comparator<T>> getComparators()
	{
		return comparators;
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)//
			.append("comparatorList", comparators).toString();
	}

}
