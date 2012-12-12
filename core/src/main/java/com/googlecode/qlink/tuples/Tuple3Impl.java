package com.googlecode.qlink.tuples;

import com.googlecode.qlink.api.tuple.Tuple3;

public class Tuple3Impl<T1, T2, T3>
	implements Tuple3<T1, T2, T3>
{

	private final T1 first;
	private final T2 second;
	private final T3 third;
	private final Object[] arr;

	public Tuple3Impl(T1 first, T2 second, T3 third)
	{
		this.first = first;
		this.second = second;
		this.third = third;
		this.arr = new Object[]{first,second,third};
	}

	@Override
	public T1 getFirst()
	{
		return first;
	}

	@Override
	public T2 getSecond()
	{
		return second;
	}

	@Override
	public T3 getThird()
	{
		return third;
	}

	@Override
	public int size()
	{
		return 3;
	}

	@Override
	public Object get(int i)
	{
		return arr[i];
	}

	@Override
	public Object[] toArray()
	{
		return arr;
	}

}
