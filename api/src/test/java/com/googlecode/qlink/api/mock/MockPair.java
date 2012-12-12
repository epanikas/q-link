package com.googlecode.qlink.api.mock;

import com.googlecode.qlink.api.tuple.Pair;

public class MockPair<A, B>
	implements Pair<A, B>
{
	private final A first;
	private final B second;

	public MockPair(A first, B second)
	{
		super();
		this.first = first;
		this.second = second;
	}

	@Override
	public A getFirst()
	{
		return first;
	}

	@Override
	public B getSecond()
	{
		return second;
	}

	@Override
	public int size()
	{
		return 2;
	}

	@Override
	public Object get(int i)
	{
		return i == 0 ? first : second;
	}

	@Override
	public Object[] toArray()
	{
		return new Object[]{first,second};
	}
}
