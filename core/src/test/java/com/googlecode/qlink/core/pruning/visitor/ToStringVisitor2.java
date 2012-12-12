package com.googlecode.qlink.core.pruning.visitor;

import com.googlecode.qlink.api.functor.Visitor2;

public class ToStringVisitor2<T>
	implements Visitor2<T, Integer>
{
	private final String strExpr;

	public ToStringVisitor2(String strExpr)
	{
		this.strExpr = strExpr;
	}

	@Override
	public void apply(T a, Integer b)
	{
		// do nothing
	}

	@Override
	public String toString()
	{
		return strExpr;
	}

}
