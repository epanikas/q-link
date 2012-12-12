package com.googlecode.qlink.core.behavior;

import com.googlecode.qlink.api.behavior.CanOrder;
import com.googlecode.qlink.api.definition.order.StartOrderDef;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.PipelineContextAwareSupport;
import com.googlecode.qlink.core.definition.order.StartOrderDefImpl;

public class CanOrderImpl<T, TPlugin>
	extends PipelineContextAwareSupport
	implements CanOrder<T, TPlugin>
{

	public CanOrderImpl(IPipelineContext ctxt)
	{
		super(ctxt);
	}

	@Override
	public StartOrderDef<T, TPlugin> order()
	{
		return new StartOrderDefImpl<T, TPlugin>(getCtxt());
	}
}
