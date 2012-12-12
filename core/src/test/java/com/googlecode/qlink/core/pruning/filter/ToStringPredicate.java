package com.googlecode.qlink.core.pruning.filter;

import com.googlecode.qlink.api.functor.Predicate;

public class ToStringPredicate<T>
	implements Predicate<T>
{
	private final String strExpr;

	public ToStringPredicate(String strExpr)
	{
		this.strExpr = strExpr;
	}

	@Override
	public boolean evaluate(Object object)
	{
		return false;
	}

	@Override
	public String toString()
	{
		return strExpr;
	}
}
