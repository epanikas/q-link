package com.googlecode.qlink.core.behavior;

import java.util.List;

import com.googlecode.qlink.api.behavior.CanHaving;
import com.googlecode.qlink.api.definition.group.having.HavingFilterDef;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.PipelineContextAwareSupport;
import com.googlecode.qlink.core.definition.having.HavingFilterDefImpl;

public class CanHavingImpl<K, V extends List<?>, TPlugin>
	extends PipelineContextAwareSupport
	implements CanHaving<K, V, TPlugin>
{

	public CanHavingImpl(IPipelineContext ctxt)
	{
		super(ctxt);
	}

	@Override
	public HavingFilterDef<K, V, TPlugin> having()
	{
		return new HavingFilterDefImpl<K, V, TPlugin>(getCtxt());
	}

}
