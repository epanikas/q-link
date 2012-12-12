package com.googlecode.qlink.core.context;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.googlecode.qlink.core.utils.SimpleAssert;
import com.googlecode.qlink.core.utils.TypedBeanUtils;

public class DefaultPipelineElementsFactory
	implements PipelineElementsFactory
{
	protected final IPipelineContext ctxt;

	public DefaultPipelineElementsFactory(IPipelineContext ctxt)
	{
		this.ctxt = ctxt;
	}

	@Override
	public <T> T create(Class<T> cls)
	{
		Class<?> implemCls = null;
		try {
			String implemClsName = cls.getCanonicalName() + "Impl";
			implemClsName = implemClsName.replace("api", "common");
			implemCls = Class.forName(implemClsName);
		}
		catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("no implementation for " + cls + ": " + e, e);
		}

		@SuppressWarnings("unchecked")
		Constructor<T> con =
			(Constructor<T>) TypedBeanUtils.findConstructor(implemCls, new Class[]{IPipelineContext.class});

		SimpleAssert.notNull(con);

		try {
			return con.newInstance(ctxt);
		}
		catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e);
		}
		catch (InstantiationException e) {
			throw new IllegalArgumentException(e);
		}
		catch (IllegalAccessException e) {
			throw new IllegalArgumentException(e);
		}
		catch (InvocationTargetException e) {
			throw new IllegalArgumentException(e);
		}

	}
}
