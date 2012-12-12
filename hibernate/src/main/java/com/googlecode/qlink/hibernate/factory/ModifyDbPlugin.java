package com.googlecode.qlink.hibernate.factory;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.googlecode.qlink.api.behavior.DoResultAsList;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.PipelineContextAwarePlugin;


public class ModifyDbPlugin
	extends PipelineContextAwarePlugin
{
	Logger logger = Logger.getLogger(ModifyDbPlugin.class);

	HibernateTemplate hibernateTemplate;

	public ModifyDbPlugin(IPipelineContext pipelineContext, HibernateTemplate hibernateTemplate)
	{
		super(pipelineContext);
		this.hibernateTemplate = hibernateTemplate;
	}

	private List<?> getResult()
	{
		DoResultAsList<?, ?> doResultAsList = getPipelineContext().getFactory().create(DoResultAsList.class);

		return doResultAsList.toList();
	}

	public void save()
	{
		List<?> res = getResult();

		logger.info("saving " + res);
		for (Object o : res) {
			hibernateTemplate.save(o);
		}

	}

	public void update()
	{
		List<?> res = getResult();

		logger.info("updating " + res);
		for (Object o : res) {
			hibernateTemplate.update(o);
		}
	}
}
