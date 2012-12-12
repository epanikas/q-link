package com.googlecode.qlink.core.behavior;

import com.googlecode.qlink.api.behavior.CanFilter;
import com.googlecode.qlink.api.definition.filter.FilterDef;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.PipelineContextAwareSupport;
import com.googlecode.qlink.core.definition.filter.FilterDefImpl;

public class CanFilterImpl<T, TPlugin>
	extends PipelineContextAwareSupport
	implements CanFilter<T, TPlugin>
{

	public CanFilterImpl(IPipelineContext ctxt)
	{
		super(ctxt);
	}

	@Override
	public FilterDef<T, TPlugin> filter()
	{
		return new FilterDefImpl<T, TPlugin>(getCtxt());
	}

}
