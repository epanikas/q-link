package com.googlecode.qlink.core.functor;

import com.googlecode.qlink.api.functor.Function2;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.tuples.Tuples;

public class CustomKeyIndexer<K, T>
	implements Function2<T, Integer, Pair<K, T>>
{
	private final Function2<T, Integer, K> customFunc;

	public CustomKeyIndexer(Function2<T, Integer, K> customFunc)
	{
		this.customFunc = customFunc;
	}

	public Function2<T, Integer, K> getCustomFunc()
	{
		return customFunc;
	}

	@Override
	public Pair<K, T> apply(T t, Integer i)
	{
		return Tuples.tie(customFunc.apply(t, i), t);
	}

	@Override
	public String toString()
	{
		return customFunc.toString();
	}
}
