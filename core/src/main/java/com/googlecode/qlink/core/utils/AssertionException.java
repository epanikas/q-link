package com.googlecode.qlink.core.utils;

public class AssertionException
	extends RuntimeException
{

	private static final long serialVersionUID = -3238925735277147671L;

	public AssertionException()
	{
		super();
	}

	public AssertionException(String message, Throwable cause)
	{
		super(message, cause);
	}

	public AssertionException(String s)
	{
		super(s);
	}

	public AssertionException(Throwable cause)
	{
		super(cause);
	}

}
