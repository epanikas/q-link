package com.googlecode.qlink.core.context;

public interface PipelineElementsFactory
{
	<T> T create(Class<T> cls);
}
