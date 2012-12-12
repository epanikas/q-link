package com.googlecode.qlink.core.context.blocks;

import com.googlecode.qlink.api.functor.Aggregator;
import com.googlecode.qlink.api.functor.Predicate;
import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.core.context.enums.EAggregatorType;
import com.googlecode.qlink.core.context.enums.EFilterBlockType;
import com.googlecode.qlink.core.context.enums.EFilterCondition;
import com.googlecode.qlink.core.context.enums.EFilterJunction;

public class FilterBlock
{

	private final EFilterBlockType type;
	private final Predicate<?> predicate;
	private final EFilterJunction junction;
	private final TProperty<?> property;
	private final EFilterCondition condition;
	private final Object value;
	private final EAggregatorType aggregatorType;
	private final Aggregator<?, ?> aggregator;

	public FilterBlock(EFilterBlockType type, Predicate<?> predicate, EFilterJunction junction, TProperty<?> property,
					EFilterCondition condition, Object value, EAggregatorType aggregatorType,
					Aggregator<?, ?> aggregator)
	{
		this.type = type;
		this.predicate = predicate;
		this.junction = junction;
		this.property = property;
		this.condition = condition;
		this.value = value;
		this.aggregatorType = aggregatorType;
		this.aggregator = aggregator;
	}

	public EFilterBlockType getType()
	{
		return type;
	}

	public Predicate<?> getPredicate()
	{
		return predicate;
	}

	public EFilterJunction getJunction()
	{
		return junction;
	}

	public TProperty<?> getProperty()
	{
		return property;
	}

	public EFilterCondition getCondition()
	{
		return condition;
	}

	public Object getValue()
	{
		return value;
	}

	public EAggregatorType getAggregatorType()
	{
		return aggregatorType;
	}

	public Aggregator<?, ?> getAggregator()
	{
		return aggregator;
	}

	public static FilterBlock forCondition(EFilterCondition cond)
	{
		return new FilterBlock(EFilterBlockType.condition, null, null, null, cond, null, null, null);
	}

	public static FilterBlock forProperty(TProperty<?> typedProp)
	{
		return new FilterBlock(EFilterBlockType.property, null, null, typedProp, null, null, null, null);
	}

	public static FilterBlock forValue(Object val)
	{
		return new FilterBlock(EFilterBlockType.val, null, null, null, null, val, null, null);
	}

	//	public static <R> FilterBlock forExpression(String expr, Class<R> cls)
	//	{
	//		return new FilterBlock(EFilterChainType.property, null, null, TypedPropertyImpl.forTypedName(expr, cls), null,
	//			null, null, null);
	//	}

	public static FilterBlock forPredicate(Predicate<?> p)
	{
		return new FilterBlock(EFilterBlockType.predicate, p, null, null, null, null, null, null);
	}

	public static <R> FilterBlock forSelf()
	{
		return new FilterBlock(EFilterBlockType.elem, null, null, null, null, null, null, null);
	}

	public static FilterBlock forJunction(EFilterJunction junction)
	{
		return new FilterBlock(EFilterBlockType.junction, null, junction, null, null, null, null, null);
	}

	public static FilterBlock forAggregator(EAggregatorType at, TProperty<?> typedProp)
	{
		return new FilterBlock(EFilterBlockType.aggregator, null, null, typedProp, null, null, at, null);
	}

	public static FilterBlock forBegin()
	{
		return new FilterBlock(EFilterBlockType.begin, null, null, null, null, null, null, null);
	}

	public static FilterBlock forEnd()
	{
		return new FilterBlock(EFilterBlockType.end, null, null, null, null, null, null, null);
	}

	public static FilterBlock forNot()
	{
		return new FilterBlock(EFilterBlockType.not, null, null, null, null, null, null, null);
	}

	@Override
	public String toString()
	{
		switch (type) {
			case predicate:
				return predicate.toString();

			case aggregator:
				return aggregatorType.name() + (property == null ? "" : " " + property.getName());

			case begin:
				return "(";

			case end:
				return ")";

			case condition:
				return condition.name();

			case not:
				return "not";

			case junction:
				return junction.name();

			case property:
				return property.getName();

			case elem:
				return "elem";

			case val:
				return value.toString();

			case endOfStack:
				return type.name();

			default:
				return "unknown";
		}
	}
}
