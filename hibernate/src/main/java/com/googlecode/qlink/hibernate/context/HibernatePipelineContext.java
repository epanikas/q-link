package com.googlecode.qlink.hibernate.context;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.googlecode.qlink.core.context.DefaultPipelineContext;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.IPipelineDefinition;
import com.googlecode.qlink.core.context.PipelineElementsFactory;

public class HibernatePipelineContext
	extends DefaultPipelineContext
	implements IPipelineContext
{

	private final IPipelineDefinition def;
	private Object plugin;
	private final Class<?> sourceCls;

	private final HibernateTemplate hibernateTemplate;

	public HibernatePipelineContext(HibernateTemplate template, Class<?> srcCls, IPipelineDefinition def, Object plugin)
	{
		sourceCls = srcCls;
		this.def = def;
		this.plugin = plugin;
		hibernateTemplate = template;

		factory = new HibernatePipelineElementsFactory(this);
	}

	@Override
	public IPipelineDefinition getPipelineDef()
	{
		return def;
	}

	@Override
	public void setPlugin(Object plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public Object getPlugin()
	{
		return plugin;
	}

	public Class<?> getSourceCls()
	{
		return sourceCls;
	}

	public HibernateTemplate getHibernateTemplate()
	{
		return hibernateTemplate;
	}

	@Override
	public PipelineElementsFactory getFactory()
	{
		return factory;
	}

}
