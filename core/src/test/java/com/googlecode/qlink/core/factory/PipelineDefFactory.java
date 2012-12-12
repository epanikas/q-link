package com.googlecode.qlink.core.factory;

import com.googlecode.qlink.api.definition.EntryPointDef;
import com.googlecode.qlink.core.context.DefaultPipelineContext;
import com.googlecode.qlink.core.context.PipelineContextAwarePlugin;
import com.googlecode.qlink.core.definition.EntryPointDefImpl;

public class PipelineDefFactory
{

	public final <T> EntryPointDef<T, PipelineContextAwarePlugin> forType(@SuppressWarnings("unused") Class<T> cls)
	{
		DefaultPipelineContext ctxt = new DefaultPipelineContext();
		ctxt.setFactory(new TestPipelineElementsFactory(ctxt));
		ctxt.setPlugin(new PipelineContextAwarePlugin(ctxt));

		return new EntryPointDefImpl<T, PipelineContextAwarePlugin>(ctxt);
	}
}
