package com.googlecode.qlink.core.context;

public class DefaultPipelineContext
	implements IPipelineContext
{

	private final IPipelineDefinition def;
	private Object plugin;

	protected PipelineElementsFactory factory;

	//	private final ConcurrentHashMap<Class<?>, TypedFactory<?>> factoryCache =
	//		new ConcurrentHashMap<Class<?>, TypedFactory<?>>();

	public DefaultPipelineContext()
	{
		def = new DefaultPipelineDefinition();
		factory = new DefaultPipelineElementsFactory(this);
	}

	public DefaultPipelineContext(Object plugin)
	{
		def = new DefaultPipelineDefinition();
		this.plugin = plugin;
	}

	public DefaultPipelineContext(IPipelineDefinition def, Object plugin)
	{
		this.def = def;
		this.plugin = plugin;
	}

	public void setFactory(PipelineElementsFactory factory)
	{
		this.factory = factory;
	}

	@Override
	public PipelineElementsFactory getFactory()
	{
		return factory;
	}

	@Override
	public IPipelineDefinition getPipelineDef()
	{
		return def;
	}

	public void setPlugin(Object postProcessor)
	{
		this.plugin = postProcessor;
	}

	@Override
	public Object getPlugin()
	{
		return plugin;
	}

	//	private <T> TypedFactory<T> getFactory(Class<T> cls)
	//	{
	//		@SuppressWarnings("unchecked")
	//		TypedFactory<T> f = (TypedFactory<T>) factoryCache.get(cls);
	//		if (f == null) {
	//			f = new DefaultPipelineElementsFactory<T>(cls, this);
	//			factoryCache.putIfAbsent(cls, f);
	//		}
	//
	//		return f;
	//	}

}
