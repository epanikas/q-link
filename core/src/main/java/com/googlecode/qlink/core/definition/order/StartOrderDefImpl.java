package com.googlecode.qlink.core.definition.order;

import java.util.Comparator;

import com.googlecode.qlink.api.definition.order.OrderDef;
import com.googlecode.qlink.api.definition.order.OrderWithDef;
import com.googlecode.qlink.api.definition.order.StartOrderDef;
import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.PipelineContextAwareSupport;
import com.googlecode.qlink.core.context.TPropertyImpl;
import com.googlecode.qlink.core.context.blocks.OrderBlock;

public class StartOrderDefImpl<T, TPlugin>
	extends PipelineContextAwareSupport
	implements StartOrderDef<T, TPlugin>
{

	public StartOrderDefImpl(IPipelineContext ctxt)
	{
		super(ctxt);
	}

	@Override
	public OrderDef<T, TPlugin> naturally()
	{
		getCtxt().getPipelineDef().pushToOrderStack(OrderBlock.forNaturalComparator());
		return new OrderDefImpl<T, TPlugin>(getCtxt());
	}

	@Override
	public OrderDef<T, TPlugin> by(String propName)
	{
		getCtxt().getPipelineDef().pushToOrderStack(OrderBlock.forProperty(TPropertyImpl.forName(propName)));
		return new OrderDefImpl<T, TPlugin>(getCtxt());
	}

	@Override
	public OrderDef<T, TPlugin> by(TProperty<?> prop)
	{
		getCtxt().getPipelineDef().pushToOrderStack(OrderBlock.forProperty(prop));
		return new OrderDefImpl<T, TPlugin>(getCtxt());
	}

	@Override
	public OrderWithDef<T, TPlugin> with(Comparator<T> c)
	{
		getCtxt().getPipelineDef().pushToOrderStack(OrderBlock.forComparator(c));
		return new OrderWithDefImpl<T, TPlugin>(getCtxt());
	}

}
