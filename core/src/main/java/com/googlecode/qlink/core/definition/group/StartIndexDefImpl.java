package com.googlecode.qlink.core.definition.group;

import com.googlecode.qlink.api.definition.group.IndexDefinitions.IndexByDef;
import com.googlecode.qlink.api.definition.group.IndexWithDef;
import com.googlecode.qlink.api.definition.group.StartIndexByDef;
import com.googlecode.qlink.api.functor.Function;
import com.googlecode.qlink.api.functor.Function2;
import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.PipelineContextAwareSupport;
import com.googlecode.qlink.core.context.TPropertyImpl;
import com.googlecode.qlink.core.context.blocks.GroupByBlock;
import com.googlecode.qlink.core.functor.Functions;

public class StartIndexDefImpl<T, TPlugin>
	extends PipelineContextAwareSupport
	implements StartIndexByDef<T, TPlugin>
{

	public StartIndexDefImpl(IPipelineContext ctxt)
	{
		super(ctxt);
	}

	@Override
	public <K> IndexByDef<K, T, TPlugin> by(String propName, Class<K> cls)
	{
		getCtxt().getPipelineDef()
			.pushToIndexStack(GroupByBlock.forProperty(TPropertyImpl.forTypedName(propName, cls)));
		return new IndexDefinitionsImpl.IndexByDefImpl<K, T, TPlugin>(getCtxt());
	}

	@Override
	public <K> IndexByDef<K, T, TPlugin> by(TProperty<K> tProp)
	{
		getCtxt().getPipelineDef().pushToIndexStack(GroupByBlock.forProperty(tProp));
		return new IndexDefinitionsImpl.IndexByDefImpl<K, T, TPlugin>(getCtxt());
	}

	@Override
	public <K> IndexByDef<K, T, TPlugin> by(Function<T, K> f)
	{
		getCtxt().getPipelineDef().pushToIndexStack(GroupByBlock.forKeyIndexer(Functions.adaptToFunctionWithIndex(f)));
		return new IndexDefinitionsImpl.IndexByDefImpl<K, T, TPlugin>(getCtxt());
	}

	@Override
	public <K> IndexByDef<K, T, TPlugin> by(Function2<T, Integer, K> f)
	{
		getCtxt().getPipelineDef().pushToIndexStack(GroupByBlock.forKeyIndexer(f));
		return new IndexDefinitionsImpl.IndexByDefImpl<K, T, TPlugin>(getCtxt());
	}

	@SuppressWarnings({"rawtypes","unchecked"})
	@Override
	public <K, V> IndexWithDef<K, V, TPlugin> with(Function<T, Pair<K, V>> ind)
	{
		getCtxt().getPipelineDef().pushToIndexStack(GroupByBlock.forIndexer((Function) ind));
		return new IndexWithDefImpl<K, V, TPlugin>(getCtxt());
	}

	@SuppressWarnings({"rawtypes","unchecked"})
	@Override
	public <K, V> IndexWithDef<K, V, TPlugin> with(Function2<T, Integer, Pair<K, V>> ind)
	{
		getCtxt().getPipelineDef().pushToIndexStack(GroupByBlock.forKeyValueIndexer((Function2) ind));
		return new IndexWithDefImpl<K, V, TPlugin>(getCtxt());
	}

}
