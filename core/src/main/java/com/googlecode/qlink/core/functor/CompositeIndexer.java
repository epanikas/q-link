package com.googlecode.qlink.core.functor;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.googlecode.qlink.api.functor.Function2;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.utils.SimpleAssert;
import com.googlecode.qlink.tuples.Tuples;

public class CompositeIndexer<K1, K2, V, T>
	implements Function2<T, Integer, Pair<Pair<K1, K2>, V>>
{
	private final Function2<T, Integer, Pair<K1, V>> ind1;
	private final Function2<T, Integer, Pair<K2, V>> ind2;

	public CompositeIndexer(Function2<T, Integer, Pair<K1, V>> ind1, Function2<T, Integer, Pair<K2, V>> ind2)
	{
		this.ind1 = ind1;
		this.ind2 = ind2;
	}

	public Function2<T, Integer, Pair<K1, V>> getInd1()
	{
		return ind1;
	}

	public Function2<T, Integer, Pair<K2, V>> getInd2()
	{
		return ind2;
	}

	@Override
	public Pair<Pair<K1, K2>, V> apply(T t, Integer i)
	{
		Pair<K1, V> p1 = ind1.apply(t, i);
		Pair<K2, V> p2 = ind2.apply(t, i);
		/*
		 * here we require that the two objects are identical
		 */
		SimpleAssert.isTrue(p1.getSecond().equals(p2.getSecond()));

		return Tuples.tie((Pair<K1, K2>) Tuples.tie(p1.getFirst(), p2.getFirst()), p1.getSecond());
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append(ind1).append(ind2).toString();
	}
}
