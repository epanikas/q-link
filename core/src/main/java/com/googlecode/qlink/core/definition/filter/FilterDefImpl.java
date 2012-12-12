package com.googlecode.qlink.core.definition.filter;

import com.googlecode.qlink.api.definition.filter.CompositeFilterDef;
import com.googlecode.qlink.api.definition.filter.Connector;
import com.googlecode.qlink.api.definition.filter.FilterDef;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.blocks.FilterBlock;
import com.googlecode.qlink.core.definition.filter.common.CommonFilterDefImpl;

public class FilterDefImpl<T, TPlugin>
	extends CommonFilterDefImpl<T, Connector<T, TPlugin>>
	implements FilterDef<T, TPlugin>
{

	public FilterDefImpl(IPipelineContext ctxt)
	{
		// can't use 'this' in super..., thus xxx.setFilter()
		super(ctxt, new ConnectorImpl<T, TPlugin>(ctxt, null));
		((ConnectorImpl<T, TPlugin>) getConnector()).setFilterDef(this);
	}

	@Override
	public FilterDef<T, TPlugin> not()
	{
		getCtxt().getPipelineDef().pushToFilterStack(FilterBlock.forNot());

		return this;
	}

	@Override
	public CompositeFilterDef<T, TPlugin, Connector<T, TPlugin>> begin()
	{
		getCtxt().getPipelineDef().pushToFilterStack(FilterBlock.forBegin());

		return new CompositeFilterDefImpl<T, TPlugin, Connector<T, TPlugin>>(getCtxt(), new ConnectorImpl<T, TPlugin>(
			getCtxt(), this));
	}

}
