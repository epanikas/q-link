package com.googlecode.qlink.core.context;


public class PipelineContextAwarePlugin
{
	private IPipelineContext pipelineContext;

	public PipelineContextAwarePlugin()
	{
		// empty
	}

	public PipelineContextAwarePlugin(IPipelineContext pipelineContext)
	{
		this.pipelineContext = pipelineContext;
	}

	public void setPipelineContext(IPipelineContext pipelineContext)
	{
		this.pipelineContext = pipelineContext;
	}

	public IPipelineContext getPipelineContext()
	{
		return pipelineContext;
	}

	public IPipelineDefinition getPipelineDef()
	{
		return pipelineContext.getPipelineDef();
	}
}
