package com.googlecode.qlink.core.behavior;

import com.googlecode.qlink.api.behavior.CanIndex;
import com.googlecode.qlink.api.definition.group.StartGroupByDef;
import com.googlecode.qlink.api.definition.group.StartIndexByDef;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.PipelineContextAwareSupport;
import com.googlecode.qlink.core.definition.group.StartGroupDefImpl;
import com.googlecode.qlink.core.definition.group.StartIndexDefImpl;

public class CanIndexImpl<T, TPlugin>
	extends PipelineContextAwareSupport
	implements CanIndex<T, TPlugin>
{

	public CanIndexImpl(IPipelineContext ctxt)
	{
		super(ctxt);
	}

	@Override
	public StartIndexByDef<T, TPlugin> index()
	{
		return new StartIndexDefImpl<T, TPlugin>(getCtxt());
	}

	@Override
	public StartGroupByDef<T, TPlugin> group()
	{
		return new StartGroupDefImpl<T, TPlugin>(getCtxt());
	}
}
