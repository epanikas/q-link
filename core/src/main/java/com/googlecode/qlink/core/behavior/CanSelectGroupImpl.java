package com.googlecode.qlink.core.behavior;

import java.util.List;

import com.googlecode.qlink.api.behavior.CanSelectGroup;
import com.googlecode.qlink.api.definition.selectgroup.SelectGroupDefinitions.StartSelectGroupDef;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.PipelineContextAwareSupport;
import com.googlecode.qlink.core.definition.selectgroup.SelectGroupDefinitionsImpl;

public class CanSelectGroupImpl<K, V extends List<?>, TPlugin>
	extends PipelineContextAwareSupport
	implements CanSelectGroup<K, V, TPlugin>
{

	public CanSelectGroupImpl(IPipelineContext ctxt)
	{
		super(ctxt);
	}

	@Override
	public StartSelectGroupDef<K, V, TPlugin> selectAs()
	{
		return new SelectGroupDefinitionsImpl.StartSelectGroupDefImpl<K, V, TPlugin>(getCtxt());
	}

}
