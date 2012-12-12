package com.googlecode.qlink.hibernate.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.googlecode.qlink.api.definition.EntryPointDef;
import com.googlecode.qlink.core.context.DefaultPipelineDefinition;
import com.googlecode.qlink.core.context.PipelineContextAwarePlugin;
import com.googlecode.qlink.core.definition.EntryPointDefImpl;
import com.googlecode.qlink.hibernate.context.HibernatePipelineContext;

public class HibernateFactory
{
	@Autowired
	private HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate)
	{
		this.hibernateTemplate = hibernateTemplate;
	}

	public <T> EntryPointDef<T, PipelineContextAwarePlugin> selectForClass(Class<T> cls)
	{
		return createEntryPoint(cls, null);
	}

	public <T> EntryPointDef<T, ModifyDbPlugin> modifyForClass(Class<T> cls)
	{
		DefaultPipelineDefinition definition = new DefaultPipelineDefinition();

		HibernatePipelineContext ctxt = new HibernatePipelineContext(hibernateTemplate, cls, definition, null);
		ModifyDbPlugin plugin = new ModifyDbPlugin(ctxt, hibernateTemplate);
		ctxt.setPlugin(plugin);

		return new EntryPointDefImpl<T, ModifyDbPlugin>(ctxt);
	}

	private final <T> EntryPointDef<T, PipelineContextAwarePlugin> createEntryPoint(Class<T> cls,
																					PipelineContextAwarePlugin postprocessor)
	{
		HibernatePipelineContext ctxt =
			new HibernatePipelineContext(hibernateTemplate, cls, new DefaultPipelineDefinition(), postprocessor);

		return new EntryPointDefImpl<T, PipelineContextAwarePlugin>(ctxt);

	}
}
