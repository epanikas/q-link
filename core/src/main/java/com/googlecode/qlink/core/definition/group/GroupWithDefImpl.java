package com.googlecode.qlink.core.definition.group;

import java.util.List;
import java.util.Map;

import com.googlecode.qlink.api.behavior.CanHaving;
import com.googlecode.qlink.api.behavior.CanSelectGroup;
import com.googlecode.qlink.api.behavior.DoResultAsMap;
import com.googlecode.qlink.api.definition.group.GroupWithDef;
import com.googlecode.qlink.api.definition.group.having.HavingFilterDef;
import com.googlecode.qlink.api.definition.selectgroup.SelectGroupDefinitions.StartSelectGroupDef;
import com.googlecode.qlink.core.behavior.CanHavingImpl;
import com.googlecode.qlink.core.behavior.CanSelectGroupImpl;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.PipelineContextAwareSupport;

public class GroupWithDefImpl<K, V extends List<?>, TPlugin>
	extends PipelineContextAwareSupport
	implements GroupWithDef<K, V, TPlugin>
{
	private final DoResultAsMap<K, V, TPlugin> doResult;
	private final CanSelectGroup<K, V, TPlugin> canSelectGroup;
	private final CanHaving<K, V, TPlugin> canHaving;

	@SuppressWarnings("unchecked")
	public GroupWithDefImpl(IPipelineContext ctxt)
	{
		super(ctxt);
		doResult = getCtxt().getFactory().create(DoResultAsMap.class);
		canSelectGroup = new CanSelectGroupImpl<K, V, TPlugin>(getCtxt());
		canHaving = new CanHavingImpl<K, V, TPlugin>(getCtxt());

	}

	@Override
	public StartSelectGroupDef<K, V, TPlugin> selectAs()
	{
		return canSelectGroup.selectAs();
	}

	@Override
	public HavingFilterDef<K, V, TPlugin> having()
	{
		return canHaving.having();
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
