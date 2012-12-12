package com.googlecode.qlink.core.context.blocks;

import com.googlecode.qlink.api.functor.Function;
import com.googlecode.qlink.api.functor.Function2;
import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.enums.EGroupByBlockType;
import com.googlecode.qlink.core.functor.Functions;

public class GroupByBlock
{

	private final EGroupByBlockType type;

	private final TProperty<?> property;
	private final Function2<?, Integer, Pair<?, ?>> keyValueIndexer;
	private final Function2<?, Integer, ?> keyIndexer;

	private GroupByBlock(EGroupByBlockType type, TProperty<?> property,
					Function2<?, Integer, Pair<?, ?>> keyValueIndexer, Function2<?, Integer, ?> keyIndexer)
	{
		this.type = type;
		this.property = property;
		this.keyValueIndexer = keyValueIndexer;
		this.keyIndexer = keyIndexer;
	}

	public EGroupByBlockType getType()
	{
		return type;
	}

	public Function2<?, Integer, Pair<?, ?>> getKeyValueIndexer()
	{
		return keyValueIndexer;
	}

	public Function2<?, Integer, ?> getKeyIndexer()
	{
		return keyIndexer;
	}

	public TProperty<?> getProperty()
	{
		return property;
	}

	public static GroupByBlock forProperty(TProperty<?> prop)
	{
		return new GroupByBlock(EGroupByBlockType.property, prop, null, null);
	}

	public static GroupByBlock forIndexer(Function<?, Pair<?, ?>> ind)
	{
		return new GroupByBlock(EGroupByBlockType.indexer, null, Functions.adaptToFunctionWithIndex(ind), null);
	}

	public static GroupByBlock forIndexer(Function2<?, Integer, Pair<?, ?>> ind)
	{
		return new GroupByBlock(EGroupByBlockType.indexer, null, ind, null);
	}

	public static GroupByBlock forKeyValueIndexer(Function2<?, Integer, Pair<?, ?>> ind)
	{
		return new GroupByBlock(EGroupByBlockType.indexer, null, ind, null);
	}

	public static GroupByBlock forKeyIndexer(Function2<?, Integer, ?> keyInd)
	{
		return new GroupByBlock(EGroupByBlockType.customKey, null, null, keyInd);
	}

	@Override
	public String toString()
	{
		switch (type) {
			case property:
				return property.getName();

			case indexer:
				return keyValueIndexer.toString();

			case customKey:
				return keyIndexer.toString();

			case endOfStack:
				return type.name();

			default:
				return "unknown";
		}
	}
}
