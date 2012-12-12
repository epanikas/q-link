package com.googlecode.qlink.core.context;

public class PipelineContextAwareSupport
{
	private final IPipelineContext ctxt;

	protected PipelineContextAwareSupport(IPipelineContext ctxt)
	{
		this.ctxt = ctxt;
	}

	protected IPipelineContext getCtxt()
	{
		return ctxt;
	}
}
