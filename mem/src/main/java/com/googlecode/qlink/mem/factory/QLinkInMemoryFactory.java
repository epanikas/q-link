package com.googlecode.qlink.mem.factory;

import java.util.Arrays;
import java.util.List;


import com.googlecode.qlink.api.definition.EntryPointDef;
import com.googlecode.qlink.core.context.DefaultPipelineDefinition;
import com.googlecode.qlink.core.context.PipelineContextAwarePlugin;
import com.googlecode.qlink.core.definition.EntryPointDefImpl;
import com.googlecode.qlink.mem.context.MemPipelineContext;

public class QLinkInMemoryFactory
{
	public <T> EntryPointDef<T, PipelineContextAwarePlugin> forFewItems(T... lst)
	{
		return this.createEntryPoint(Arrays.asList(lst), null);
	}

	public <T> EntryPointDef<T, PipelineContextAwarePlugin> forFewItems(PipelineContextAwarePlugin postprocessor, T... lst)
	{
		return this.createEntryPoint(Arrays.asList(lst), postprocessor);
	}

	public <T> EntryPointDef<T, PipelineContextAwarePlugin> forList(List<T> lst)
	{
		return this.createEntryPoint(lst, null);
	}

	public <T> EntryPointDef<T, PipelineContextAwarePlugin> forList(PipelineContextAwarePlugin postprocessor,
																	List<T> lst)
	{
		return this.createEntryPoint(lst, postprocessor);
	}

	private final <T> EntryPointDef<T, PipelineContextAwarePlugin> createEntryPoint(List<T> lst,
																					PipelineContextAwarePlugin postprocessor)
	{
		MemPipelineContext ctxt = new MemPipelineContext(lst, new DefaultPipelineDefinition(), postprocessor);

		return new EntryPointDefImpl<T, PipelineContextAwarePlugin>(ctxt);

	}
}
