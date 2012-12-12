package com.googlecode.qlink.core.functor;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.googlecode.qlink.api.functor.Visitor2;

public class CompositeVisitor<T>
	implements Visitor2<T, Integer>
{
	private final List<Visitor2<T, Integer>> visitors;

	public CompositeVisitor(Visitor2<T, Integer>... visitors)
	{
		this.visitors = Arrays.asList(visitors);
	}

	public List<Visitor2<T, Integer>> getComparators()
	{
		return visitors;
	}

	@Override
	public void apply(T a, Integer b)
	{
		for (Visitor2<T, Integer> v : visitors) {
			v.apply(a, b);
		}
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)//
			.append(visitors).toString();
	}

}
