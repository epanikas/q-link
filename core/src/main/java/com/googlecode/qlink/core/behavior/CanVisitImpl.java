package com.googlecode.qlink.core.behavior;

import com.googlecode.qlink.api.behavior.CanVisit;
import com.googlecode.qlink.api.definition.visit.StartVisitDef;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.PipelineContextAwareSupport;
import com.googlecode.qlink.core.definition.visit.StartVisitDefImpl;

public class CanVisitImpl<T, TPlugin>
	extends PipelineContextAwareSupport
	implements CanVisit<T, TPlugin>
{

	public CanVisitImpl(IPipelineContext ctxt)
	{
		super(ctxt);
	}

	@Override
	public StartVisitDef<T, TPlugin> visit()
	{
		return new StartVisitDefImpl<T, TPlugin>(getCtxt());
	}

}
