package com.googlecode.qlink.tuples;

import java.util.Arrays;

import com.googlecode.qlink.api.tuple.Tuple;
import com.googlecode.qlink.api.tuple.TupleN;
import com.googlecode.qlink.core.utils.SimpleAssert;

public class TupleNImpl<THead extends Tuple, B>
	implements TupleN<THead, B>
{
	private final THead head;
	private final B last;

	public TupleNImpl(THead head, B last)
	{
		this.head = head;
		this.last = last;
	}

	@Override
	public THead getHead()
	{
		return head;
	}

	@Override
	public B getLast()
	{
		return last;
	}

	@Override
	public Object[] toArray()
	{
		Object[] headArr = head.toArray();
		Object[] resArr = Arrays.copyOf(headArr, headArr.length + 1);
		resArr[headArr.length] = last;

		return resArr;
	}

	@Override
	public int size()
	{
		return head.size() + 1;
	}

	@Override
	public Object get(int i)
	{
		SimpleAssert.isTrue(i < size());
		return i < head.size() ? head.get(i) : last;
	}

}
