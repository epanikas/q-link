package com.googlecode.qlink.core.context.blocks;

import com.googlecode.qlink.api.functor.Aggregator;
import com.googlecode.qlink.api.functor.Function;
import com.googlecode.qlink.api.functor.Function2;
import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.core.context.enums.EAggregatorType;
import com.googlecode.qlink.core.context.enums.ETransformBlockType;

public class TransformBlock
{
	private final ETransformBlockType type;
	private final Function<?, ?> function;
	private final Function2<?, ?, ?> function2;
	private final Object constant;
	private final TProperty<?> property;
	private final EAggregatorType aggregatorType;
	private final Aggregator<?, ?> aggregator;

	private TransformBlock(ETransformBlockType type, Function<?, ?> function, Function2<?, ?, ?> function2,
					Object constant, TProperty<?> property, EAggregatorType aggregatorType, Aggregator<?, ?> aggregator)
	{
		this.type = type;
		this.function = function;
		this.function2 = function2;
		this.constant = constant;
		this.property = property;
		this.aggregatorType = aggregatorType;
		this.aggregator = aggregator;
	}

	public Object getConstant()
	{
		return constant;
	}

	public TProperty<?> getProperty()
	{
		return property;
	}

	public Function<?, ?> getFunction()
	{
		return function;
	}

	public Function2<?, ?, ?> getFunction2()
	{
		return function2;
	}

	public ETransformBlockType getType()
	{
		return type;
	}

	public EAggregatorType getAggregatorType()
	{
		return aggregatorType;
	}

	public Aggregator<?, ?> getAggregator()
	{
		return aggregator;
	}

	public static <R> TransformBlock forProperty(TProperty<R> tp)
	{
		return new TransformBlock(ETransformBlockType.property, null, null, null, tp, null, null);
	}

	public static <R> TransformBlock forConstant(R constant)
	{
		return new TransformBlock(ETransformBlockType.constant, null, null, constant, null, null, null);
	}

	public static <T, R> TransformBlock forFunction(Function<T, R> f)
	{
		return new TransformBlock(ETransformBlockType.functor, f, null, null, null, null, null);
	}

	public static <T, R> TransformBlock forFunction2(Function2<T, Integer, R> f)
	{
		return new TransformBlock(ETransformBlockType.functor, null, f, null, null, null, null);
	}

	public static TransformBlock forSelf()
	{
		return new TransformBlock(ETransformBlockType.elem, null, null, null, null, null, null);
	}

	public static TransformBlock forElemIndex()
	{
		return new TransformBlock(ETransformBlockType.elem, null, null, null, null, null, null);
	}

	public static TransformBlock forKey()
	{
		return new TransformBlock(ETransformBlockType.key, null, null, null, null, null, null);
	}

	public static <R> TransformBlock forKeyField(TProperty<R> tp)
	{
		return new TransformBlock(ETransformBlockType.key, null, null, null, tp, null, null);
	}

	public static TransformBlock forValue()
	{
		return new TransformBlock(ETransformBlockType.value, null, null, null, null, null, null);
	}

	public static <R> TransformBlock forAggregatorType(EAggregatorType aggType, TProperty<R> prop)
	{
		return new TransformBlock(ETransformBlockType.valueAggregator, null, null, null, prop, aggType, null);
	}

	public static <R> TransformBlock forAggregator(Aggregator<?, ?> aggregator)
	{
		return new TransformBlock(ETransformBlockType.valueAggregator, null, null, null, null, null, aggregator);
	}

	@Override
	public String toString()
	{
		switch (type) {
			case constant:
				return constant.toString();

			case property:
				return property.getName();

			case functor:
				return function.toString();

			case key:
				return "key" + (property == null ? "" : " " + property);

			case value:
				return "value";

			case valueAggregator:
				return aggregatorType != null ? aggregatorType.name() : aggregator.toString();

			default:
				throw new IllegalArgumentException("unknown transform type " + type);
		}

	}

}
