package com.googlecode.qlink.core.context.blocks;

import com.googlecode.qlink.api.functor.Function2;
import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.api.functor.Visitor2;
import com.googlecode.qlink.core.context.enums.EVisitBlockType;

public class VisitBlock
{
	private final EVisitBlockType type;
	private final Visitor2<?, Integer> visitor;
	private final TProperty<?> property;
	private final Object value;
	private final Function2<?, Integer, ?> function;

	private VisitBlock(EVisitBlockType type, Visitor2<?, Integer> visitor, TProperty<?> property, Object value,
					Function2<?, Integer, ?> function)
	{
		super();
		this.type = type;
		this.visitor = visitor;
		this.property = property;
		this.value = value;
		this.function = function;
	}

	public EVisitBlockType getType()
	{
		return type;
	}

	public Visitor2<?, Integer> getVisitor()
	{
		return visitor;
	}

	public TProperty<?> getProperty()
	{
		return property;
	}

	public Object getValue()
	{
		return value;
	}

	public Function2<?, Integer, ?> getFunction()
	{
		return function;
	}

	@Override
	public String toString()
	{
		switch (type) {
			case lhsProperty:
			case rhsProperty:
				return property.getName().toString();

			case op:
				return "=";

			case rhsFunction:
				return function.toString();

			case rhsValue:
				return value.toString();

			case visitor:
				return visitor.toString();

			default:
				throw new IllegalArgumentException("unknown visit type " + type);
		}
	}

	public static VisitBlock forOp()
	{
		return new VisitBlock(EVisitBlockType.op, null, null, null, null);

	}

	public static VisitBlock forVisitor2(Visitor2<?, Integer> v)
	{
		return new VisitBlock(EVisitBlockType.visitor, v, null, null, null);
	}

	public static VisitBlock forLhsProperty(TProperty<?> prop)
	{
		return new VisitBlock(EVisitBlockType.lhsProperty, null, prop, null, null);

	}

	public static VisitBlock forRhsProperty(TProperty<?> prop)
	{
		return new VisitBlock(EVisitBlockType.rhsProperty, null, prop, null, null);
	}

	public static VisitBlock forRhsValue(Object val)
	{
		return new VisitBlock(EVisitBlockType.rhsValue, null, null, val, null);
	}

	public static VisitBlock forRhsFunction(Function2<?, Integer, ?> f)
	{
		return new VisitBlock(EVisitBlockType.rhsFunction, null, null, null, f);
	}

}
