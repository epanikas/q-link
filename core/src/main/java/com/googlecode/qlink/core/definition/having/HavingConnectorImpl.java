package com.googlecode.qlink.core.definition.having;

import java.util.List;
import java.util.Map;

import com.googlecode.qlink.api.behavior.DoResultAsMap;
import com.googlecode.qlink.api.definition.group.having.HavingConnector;
import com.googlecode.qlink.api.definition.group.having.HavingFilterDef;
import com.googlecode.qlink.api.definition.selectgroup.SelectGroupDefinitions.StartSelectGroupDef;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.definition.filter.common.CommonConnectorImpl;
import com.googlecode.qlink.core.definition.selectgroup.SelectGroupDefinitionsImpl.StartSelectGroupDefImpl;

public class HavingConnectorImpl<K, V extends List<?>, TPlugin>
	extends CommonConnectorImpl<HavingFilterDef<K, V, TPlugin>>
	implements HavingConnector<K, V, TPlugin>
{

	private final DoResultAsMap<K, V, TPlugin> doResult;

	@SuppressWarnings("unchecked")
	public HavingConnectorImpl(IPipelineContext def, HavingFilterDefImpl<K, V, TPlugin> filterDef)
	{
		super(def, filterDef, /*isHaving*/true);
		doResult = getCtxt().getFactory().create(DoResultAsMap.class);
	}

	@Override
	public StartSelectGroupDef<K, V, TPlugin> selectAs()
	{
		return new StartSelectGroupDefImpl<K, V, TPlugin>(getCtxt());
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
