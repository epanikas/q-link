package com.googlecode.qlink.core.context;

public interface IPipelineContext
{

	IPipelineDefinition getPipelineDef();

	Object getPlugin();

	//	<T> TypedFactory<T> getFactory(Class<T> class1);

	PipelineElementsFactory getFactory();

}
