package com.googlecode.qlink.core.behavior;

import com.googlecode.qlink.api.behavior.CanSample;
import com.googlecode.qlink.api.definition.sample.SampleDef;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.PipelineContextAwareSupport;
import com.googlecode.qlink.core.definition.sample.SampleDefImpl;

public class CanSampleImpl<T, TPlugin>
	extends PipelineContextAwareSupport
	implements CanSample<T, TPlugin>
{

	public CanSampleImpl(IPipelineContext ctxt)
	{
		super(ctxt);
	}

	@Override
	public SampleDef<T, TPlugin> sample()
	{
		return new SampleDefImpl<T, TPlugin>(getCtxt());
	}
}
