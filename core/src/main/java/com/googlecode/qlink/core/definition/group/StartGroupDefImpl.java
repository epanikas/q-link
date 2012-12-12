package com.googlecode.qlink.core.definition.group;

import java.util.List;

import com.googlecode.qlink.api.definition.group.GroupDefinitions.GroupByDef;
import com.googlecode.qlink.api.definition.group.GroupWithDef;
import com.googlecode.qlink.api.definition.group.StartGroupByDef;
import com.googlecode.qlink.api.functor.Function;
import com.googlecode.qlink.api.functor.Function2;
import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.PipelineContextAwareSupport;
import com.googlecode.qlink.core.context.TPropertyImpl;
import com.googlecode.qlink.core.context.blocks.GroupByBlock;
import com.googlecode.qlink.core.definition.group.GroupDefinitionsImpl.GroupByDefImpl;
import com.googlecode.qlink.core.functor.Functions;

public class StartGroupDefImpl<T, TPlugin>
	extends PipelineContextAwareSupport
	implements StartGroupByDef<T, TPlugin>
{

	public StartGroupDefImpl(IPipelineContext ctxt)
	{
		super(ctxt);
	}

	@Override
	public <K> GroupByDef<K, List<T>, TPlugin> by(String propName, Class<K> cls)
	{
		getCtxt().getPipelineDef().pushToGroupByStack(
			GroupByBlock.forProperty(TPropertyImpl.forTypedName(propName, cls)));
		return new GroupByDefImpl<K, List<T>, TPlugin>(getCtxt());
	}

	@Override
	public <K> GroupByDef<K, List<T>, TPlugin> by(TProperty<K> tProp)
	{
		getCtxt().getPipelineDef().pushToGroupByStack(GroupByBlock.forProperty(tProp));
		return new GroupByDefImpl<K, List<T>, TPlugin>(getCtxt());
	}

	@Override
	public <K> GroupByDef<K, List<T>, TPlugin> by(Function<T, K> f)
	{
		getCtxt().getPipelineDef()
			.pushToGroupByStack(GroupByBlock.forKeyIndexer(Functions.adaptToFunctionWithIndex(f)));
		return new GroupByDefImpl<K, List<T>, TPlugin>(getCtxt());
	}

	@Override
	public <K> GroupByDef<K, List<T>, TPlugin> by(Function2<T, Integer, K> f)
	{
		getCtxt().getPipelineDef().pushToGroupByStack(GroupByBlock.forKeyIndexer(f));
		return new GroupByDefImpl<K, List<T>, TPlugin>(getCtxt());
	}

	@SuppressWarnings({"unchecked","rawtypes"})
	@Override
	public <K, V> GroupWithDef<K, List<V>, TPlugin> with(Function<T, Pair<K, V>> ind)
	{
		getCtxt().getPipelineDef().pushToGroupByStack(GroupByBlock.forIndexer((Function) ind));
		return new GroupWithDefImpl<K, List<V>, TPlugin>(getCtxt());
	}

	@SuppressWarnings({"unchecked","rawtypes"})
	@Override
	public <K, V> GroupWithDef<K, List<V>, TPlugin> with(Function2<T, Integer, Pair<K, V>> ind)
	{
		getCtxt().getPipelineDef().pushToGroupByStack(GroupByBlock.forKeyValueIndexer((Function2) ind));
		return new GroupWithDefImpl<K, List<V>, TPlugin>(getCtxt());
	}

}
