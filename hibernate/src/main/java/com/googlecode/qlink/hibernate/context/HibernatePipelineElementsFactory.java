package com.googlecode.qlink.hibernate.context;


import com.googlecode.qlink.api.behavior.DoResultAsList;
import com.googlecode.qlink.api.behavior.DoResultAsMap;
import com.googlecode.qlink.api.behavior.DoResultAsSingleValue;
import com.googlecode.qlink.core.context.DefaultPipelineElementsFactory;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.hibernate.behavior.HIbernateDoResultAsList;
import com.googlecode.qlink.hibernate.behavior.HibernateDoResultAsMap;
import com.googlecode.qlink.hibernate.behavior.HibernateDoResultAsSingleValue;


public class HibernatePipelineElementsFactory
	extends DefaultPipelineElementsFactory
{

	public HibernatePipelineElementsFactory(IPipelineContext ctxt)
	{
		super(ctxt);
	}

	@Override
	public <T> T create(Class<T> cls)
	{
		if (cls.isAssignableFrom(DoResultAsList.class)) {
			return (T) new HIbernateDoResultAsList(ctxt);
		}

		if (cls.isAssignableFrom(DoResultAsMap.class)) {
			return (T) new HibernateDoResultAsMap(ctxt);
		}

		if (cls.isAssignableFrom(DoResultAsSingleValue.class)) {
			return (T) new HibernateDoResultAsSingleValue(ctxt);
		}

		return super.create(cls);
	}

}
