package com.googlecode.qlink.mem.context;

import java.util.List;

import com.googlecode.qlink.core.context.DefaultPipelineContext;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.IPipelineDefinition;
import com.googlecode.qlink.core.context.PipelineElementsFactory;

public class MemPipelineContext
	extends DefaultPipelineContext
	implements IPipelineContext
{

	private final IPipelineDefinition def;
	private Object postProcessor;
	private final List<?> sourceList;

	public MemPipelineContext(List<?> srcList, IPipelineDefinition def, Object postProcessor)
	{
		sourceList = srcList;
		this.def = def;
		this.postProcessor = postProcessor;

		factory = new MemPipelineElementsFactory(this);
	}

	public List<?> getSourceList()
	{
		return sourceList;
	}

	@Override
	public IPipelineDefinition getPipelineDef()
	{
		return def;
	}

	@Override
	public void setPlugin(Object postProcessor)
	{
		this.postProcessor = postProcessor;
	}

	@Override
	public Object getPlugin()
	{
		return postProcessor;
	}

	@Override
	public PipelineElementsFactory getFactory()
	{
		return factory;
	}

}
