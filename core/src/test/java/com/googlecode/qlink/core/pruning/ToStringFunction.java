package com.googlecode.qlink.core.pruning;

import com.googlecode.qlink.api.functor.Function;

public class ToStringFunction<A, B>
	implements Function<A, B>
{
	private final String expr;

	public ToStringFunction(String expr)
	{
		this.expr = expr;
	}

	@Override
	public B apply(A input)
	{
		return null;
	}

	@Override
	public String toString()
	{
		return expr;
	}

}
