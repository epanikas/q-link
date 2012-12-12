package com.googlecode.qlink.api.mock;

import com.googlecode.qlink.api.functor.TProperty;

public class MockProperty<R>
	implements TProperty<R>
{

	@Override
	public Class<R> getPropertyCls()
	{
		return null;
	}

	@Override
	public String getName()
	{
		return "";
	}

	@SuppressWarnings("unused")
	public static <R> TProperty<R> create(String propName, Class<R> propCls)
	{
		return null;
	}

}
