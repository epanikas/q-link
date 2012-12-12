package com.googlecode.qlink.tuples;

import com.googlecode.qlink.api.tuple.Tuple4;

public class Tuple4Impl<T1, T2, T3, T4>
	implements Tuple4<T1, T2, T3, T4>
{

	private final T1 first;
	private final T2 second;
	private final T3 third;
	private final T4 fourth;
	private final Object[] arr;

	public Tuple4Impl(T1 first, T2 second, T3 third, T4 fourth)
	{
		this.first = first;
		this.second = second;
		this.third = third;
		this.fourth = fourth;
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
	public T4 getFourth()
	{
		return fourth;
	}

	@Override
	public int size()
	{
		return 4;
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
