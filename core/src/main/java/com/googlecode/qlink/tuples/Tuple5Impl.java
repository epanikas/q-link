package com.googlecode.qlink.tuples;

import com.googlecode.qlink.api.tuple.Tuple5;

public class Tuple5Impl<T1, T2, T3, T4, T5>
	implements Tuple5<T1, T2, T3, T4, T5>
{

	private final T1 first;
	private final T2 second;
	private final T3 third;
	private final T4 fourth;
	private final T5 fifth;
	private final Object[] arr;

	public Tuple5Impl(T1 first, T2 second, T3 third, T4 fourth, T5 fifth)
	{
		super();
		this.first = first;
		this.second = second;
		this.third = third;
		this.fourth = fourth;
		this.fifth = fifth;

		this.arr = new Object[]{first,second,third,fourth,fifth};
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
	public T5 getFifth()
	{
		return fifth;
	}

	@Override
	public int size()
	{
		return 5;
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
