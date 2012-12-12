package com.googlecode.qlink.core.context.blocks;

import java.util.Comparator;

import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.core.context.TPropertyImpl;
import com.googlecode.qlink.core.context.enums.EOrderBlockType;
import com.googlecode.qlink.core.context.enums.EOrderDirection;
import com.googlecode.qlink.core.functor.Comparators;

public class OrderBlock
{
	private final EOrderBlockType type;

	private final Comparator<?> comparator;
	private final TProperty<?> property;
	private final EOrderDirection direction;

	//	public OrderBlock(EOrderChainType type)
	//	{
	//		super();
	//		this.type = type;
	//		comparator = null;
	//		property = null;
	//		direction = null;
	//	}
	//
	//	public OrderBlock(Comparator<?> comparator)
	//	{
	//		super();
	//		type = EOrderChainType.comparator;
	//		this.comparator = comparator;
	//		property = null;
	//		direction = null;
	//	}
	//
	//	public OrderBlock(TProperty<?> property)
	//	{
	//		super();
	//		type = EOrderChainType.property;
	//		this.property = property;
	//		comparator = null;
	//		direction = null;
	//	}
	//
	//	public OrderBlock(EOrderDirection direction)
	//	{
	//		super();
	//		type = EOrderChainType.ascDesc;
	//		this.direction = direction;
	//		comparator = null;
	//		property = null;
	//
	//	}

	public OrderBlock(EOrderBlockType type, Comparator<?> comparator, TProperty<?> property, EOrderDirection direction)
	{
		this.type = type;
		this.comparator = comparator;
		this.property = property;
		this.direction = direction;
	}

	public EOrderBlockType getType()
	{
		return type;
	}

	public Comparator<?> getComparator()
	{
		return comparator;
	}

	public TProperty<?> getProperty()
	{
		return property;
	}

	public EOrderDirection getDirection()
	{
		return direction;
	}

	@Override
	public String toString()
	{
		switch (type) {

			case comparator:
				return comparator.toString();

			case property:
				return property.getName();

			case ascDesc:
				return direction.name();

			case nullability:
				return "null first";

			default:
				return "";
		}
	}

	public static OrderBlock forProperty(String propName)
	{
		//		return new OrderBlock(TypedPropertyImpl.forName(propName));
		return new OrderBlock(EOrderBlockType.property, null, TPropertyImpl.forName(propName), null);
	}

	public static OrderBlock forProperty(TProperty<?> prop)
	{
		//		return new OrderBlock(prop);
		return new OrderBlock(EOrderBlockType.property, null, prop, null);
	}

	public static OrderBlock forAscDesc(EOrderDirection dir)
	{
		//		return new OrderBlock(dir);
		return new OrderBlock(EOrderBlockType.ascDesc, null, null, dir);
	}

	public static OrderBlock forComparator(Comparator<?> c)
	{
		//		return new OrderBlock(c);
		return new OrderBlock(EOrderBlockType.comparator, c, null, null);
	}

	@SuppressWarnings({"unchecked","rawtypes"})
	public static OrderBlock forNaturalComparator()
	{
		//		return new OrderBlock(c);
		return new OrderBlock(EOrderBlockType.comparator, Comparators.<Comparable> naturalComparator(), null, null);
	}
}
