package com.googlecode.qlink.tuples;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.utils.SimpleAssert;

public class PairImpl<A, B>
	implements Pair<A, B>
{
	private final A first;
	private final B second;
	private final int hashCode;

	public PairImpl(A a, B b)
	{
		this.first = a;
		this.second = b;
		int i1 = this.first == null ? 0 : this.first.hashCode();
		int i2 = this.second == null ? 0 : this.second.hashCode();
		this.hashCode = i1 + 31 * i2;
	}

	@Override
	public A getFirst()
	{
		return this.first;
	}

	@Override
	public B getSecond()
	{
		return this.second;
	}

	private static boolean safeEquals(Object a, Object b)
	{
		return a == null ? a == b : a.equals(b);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof PairImpl == false) {
			return false;
		}

		PairImpl<?, ?> that = (PairImpl<?, ?>) obj;
		return safeEquals(this.first, that.first) && safeEquals(this.second, that.second);
	}

	@Override
	public int hashCode()
	{
		return this.hashCode;
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append(this.first).append(this.second)
			.toString();
	}

	@Override
	public Object[] toArray()
	{
		return new Object[]{this.first,this.second};
	}

	@Override
	public int size()
	{
		return 2;
	}

	@Override
	public Object get(int i)
	{
		SimpleAssert.isTrue(i < 2);

		return i == 0 ? getFirst() : getSecond();
	}

}
