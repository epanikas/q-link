package com.googlecode.qlink.core.behavior;

import com.googlecode.qlink.api.behavior.CanTransform;
import com.googlecode.qlink.api.definition.transform.SelectDefinitions.StartSelectDef;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.PipelineContextAwareSupport;
import com.googlecode.qlink.core.definition.transform.SelectDefinitionsImpl.StartSelectDefImpl;

public class CanTransformImpl<T, TPlugin>
	extends PipelineContextAwareSupport
	implements CanTransform<T, TPlugin>
{

	public CanTransformImpl(IPipelineContext ctxt)
	{
		super(ctxt);
	}

	@Override
	public StartSelectDef<T, TPlugin> select()
	{
		return new StartSelectDefImpl<T, TPlugin>(getCtxt());
	}

}
