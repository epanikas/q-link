package com.googlecode.qlink.core.pruning.indexer;

import com.googlecode.qlink.api.functor.Function;
import com.googlecode.qlink.api.tuple.Pair;

public class ToStringIndexer<T, K, V>
	implements Function<T, Pair<K, V>>
{
	private final String expr;

	public ToStringIndexer(String expr)
	{
		this.expr = expr;
	}

	@Override
	public Pair<K, V> apply(T input)
	{
		return null;
	}

	@Override
	public String toString()
	{
		return expr;
	}

}
