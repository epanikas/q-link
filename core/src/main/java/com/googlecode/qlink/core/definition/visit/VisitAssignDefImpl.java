package com.googlecode.qlink.core.definition.visit;

import com.googlecode.qlink.api.definition.visit.VisitAssignDef;
import com.googlecode.qlink.api.definition.visit.VisitDef;
import com.googlecode.qlink.api.definition.visit.VisitRhsDef;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.PipelineContextAwareSupport;
import com.googlecode.qlink.core.context.blocks.VisitBlock;

public class VisitAssignDefImpl<TProp, T, TPlugin>
	extends PipelineContextAwareSupport
	implements VisitAssignDef<TProp, T, TPlugin>

{

	public VisitAssignDefImpl(IPipelineContext ctxt)
	{
		super(ctxt);
	}

	@Override
	public VisitRhsDef<TProp, T, TPlugin> assign()
	{
		getCtxt().getPipelineDef().pushToVisitStack(VisitBlock.forOp());
		return new VisitRhsDefImpl<TProp, T, TPlugin>(getCtxt());
	}

	@Override
	public VisitDef<T, TPlugin> assign(TProp val)
	{
		getCtxt().getPipelineDef().pushToVisitStack(VisitBlock.forOp());
		getCtxt().getPipelineDef().pushToVisitStack(VisitBlock.forRhsValue(val));
		return new VisitDefImpl<T, TPlugin>(getCtxt());
	};
}
