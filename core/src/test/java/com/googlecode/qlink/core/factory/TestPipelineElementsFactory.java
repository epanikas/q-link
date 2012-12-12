package com.googlecode.qlink.core.factory;

import com.googlecode.qlink.api.behavior.DoResultAsList;
import com.googlecode.qlink.api.behavior.DoResultAsMap;
import com.googlecode.qlink.api.behavior.DoResultAsSingleValue;
import com.googlecode.qlink.core.context.DefaultPipelineElementsFactory;
import com.googlecode.qlink.core.context.IPipelineContext;

public class TestPipelineElementsFactory
	extends DefaultPipelineElementsFactory
{

	public TestPipelineElementsFactory(IPipelineContext ctxt)
	{
		super(ctxt);
	}

	@Override
	public <T> T create(Class<T> cls)
	{
		if (cls.isAssignableFrom(DoResultAsList.class)) {
			return (T) DoResults.getDoResultAsList(ctxt);
		}

		if (cls.isAssignableFrom(DoResultAsMap.class)) {
			return (T) DoResults.getDoResultAsMap(ctxt);
		}

		if (cls.isAssignableFrom(DoResultAsSingleValue.class)) {
			return (T) DoResults.getDoResultAsSingleValue(ctxt);
		}

		return super.create(cls);
	}
}
