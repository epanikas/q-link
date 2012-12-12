package com.googlecode.qlink.core.definition.filter;

import com.googlecode.qlink.api.definition.filter.CompositeConnector;
import com.googlecode.qlink.api.definition.filter.CompositeFilterDef;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.blocks.FilterBlock;
import com.googlecode.qlink.core.definition.filter.common.CommonConnectorImpl;

public class CompositeConnectorImpl<T, TPlugin, TRemainingConnector>
	extends CommonConnectorImpl<CompositeFilterDef<T, TPlugin, TRemainingConnector>>
	implements CompositeConnector<T, TPlugin, TRemainingConnector>
{

	private TRemainingConnector remaining = null;

	public CompositeConnectorImpl(IPipelineContext ctxt, TRemainingConnector remaining,
					CompositeFilterDefImpl<T, TPlugin, TRemainingConnector> filterDef)
	{
		super(ctxt, filterDef, /*isHaving*/false);
		this.remaining = remaining;
	}

	public void setRemaining(TRemainingConnector remaining)
	{
		this.remaining = remaining;
	}

	@Override
	public TRemainingConnector end()
	{
		getCtxt().getPipelineDef().pushToFilterStack(FilterBlock.forEnd());
		return this.remaining;
	}

}
