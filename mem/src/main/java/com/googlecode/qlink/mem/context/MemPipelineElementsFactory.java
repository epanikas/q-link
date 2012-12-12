package com.googlecode.qlink.mem.context;


import com.googlecode.qlink.api.behavior.DoResultAsList;
import com.googlecode.qlink.api.behavior.DoResultAsMap;
import com.googlecode.qlink.api.behavior.DoResultAsSingleValue;
import com.googlecode.qlink.core.context.DefaultPipelineElementsFactory;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.mem.behavior.MemDoResultAsList;
import com.googlecode.qlink.mem.behavior.MemDoResultAsMap;
import com.googlecode.qlink.mem.behavior.MemDoResultAsSingleValue;


public class MemPipelineElementsFactory
	extends DefaultPipelineElementsFactory
{

	public MemPipelineElementsFactory(IPipelineContext ctxt)
	{
		super(ctxt);
	}

	@Override
	public <T> T create(Class<T> cls)
	{
		if (cls.isAssignableFrom(DoResultAsList.class)) {
			return (T) new MemDoResultAsList(ctxt);
		}

		if (cls.isAssignableFrom(DoResultAsMap.class)) {
			return (T) new MemDoResultAsMap(ctxt);
		}

		if (cls.isAssignableFrom(DoResultAsSingleValue.class)) {
			return (T) new MemDoResultAsSingleValue(ctxt);
		}

		return super.create(cls);

	}
}
