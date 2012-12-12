package com.googlecode.qlink.core.definition.group;

import java.util.Map;

import com.googlecode.qlink.api.behavior.DoResultAsMap;
import com.googlecode.qlink.api.definition.group.IndexWithDef;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.PipelineContextAwareSupport;

public class IndexWithDefImpl<K, V, TPlugin>
	extends PipelineContextAwareSupport
	implements IndexWithDef<K, V, TPlugin>
{
	private final DoResultAsMap<K, V, TPlugin> doResult;

	@SuppressWarnings("unchecked")
	public IndexWithDefImpl(IPipelineContext ctxt)
	{
		super(ctxt);
		doResult = getCtxt().getFactory().create(DoResultAsMap.class);
	}

	@Override
	public Map<K, V> toMap()
	{
		return doResult.toMap();
	}

	@Override
	public int size()
	{
		return doResult.size();
	}

	@Override
	public boolean isEmpty()
	{
		return doResult.isEmpty();
	}

	@Override
	public TPlugin plugin()
	{
		return doResult.plugin();
	}

}
