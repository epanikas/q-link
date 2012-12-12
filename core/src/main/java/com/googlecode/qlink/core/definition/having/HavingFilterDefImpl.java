package com.googlecode.qlink.core.definition.having;

import java.util.List;

import com.googlecode.qlink.api.definition.group.having.CompositeHavingFilterDef;
import com.googlecode.qlink.api.definition.group.having.HavingConnector;
import com.googlecode.qlink.api.definition.group.having.HavingFilterDef;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.blocks.FilterBlock;
import com.googlecode.qlink.core.definition.filter.common.CommonHavingFilterDefImpl;

public class HavingFilterDefImpl<K, V extends List<?>, TPlugin>
	extends CommonHavingFilterDefImpl<K, V, HavingConnector<K, V, TPlugin>>
	implements HavingFilterDef<K, V, TPlugin>
{

	public HavingFilterDefImpl(IPipelineContext ctxt)
	{
		super(ctxt, new HavingConnectorImpl<K, V, TPlugin>(ctxt, null));
		((HavingConnectorImpl<K, V, TPlugin>) this.getConnector()).setFilterDef(this);
	}

	@Override
	public HavingFilterDef<K, V, TPlugin> not()
	{
		getCtxt().getPipelineDef().pushToHavingStack(FilterBlock.forNot());
		return this;
	}

	@Override
	public CompositeHavingFilterDef<K, V, TPlugin, HavingConnector<K, V, TPlugin>> begin()
	{
		getCtxt().getPipelineDef().pushToHavingStack(FilterBlock.forBegin());

		return new CompositeHavingFilterDefImpl<K, V, TPlugin, HavingConnector<K, V, TPlugin>>(getCtxt(),
			new HavingConnectorImpl<K, V, TPlugin>(getCtxt(), this));
	}

}
