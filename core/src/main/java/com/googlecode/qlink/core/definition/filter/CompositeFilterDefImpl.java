package com.googlecode.qlink.core.definition.filter;

import com.googlecode.qlink.api.definition.filter.CompositeConnector;
import com.googlecode.qlink.api.definition.filter.CompositeFilterDef;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.blocks.FilterBlock;
import com.googlecode.qlink.core.definition.filter.common.CommonFilterDefImpl;

public class CompositeFilterDefImpl<T, TPlugin, TRemainingConnector>
	extends CommonFilterDefImpl<T, CompositeConnector<T, TPlugin, TRemainingConnector>>
	implements CompositeFilterDef<T, TPlugin, TRemainingConnector>
{
	private final TRemainingConnector remaining;

	public CompositeFilterDefImpl(IPipelineContext ctxt, TRemainingConnector remaining)
	{

		super(ctxt, new CompositeConnectorImpl<T, TPlugin, TRemainingConnector>(ctxt, remaining, null));
		((CompositeConnectorImpl<T, TPlugin, TRemainingConnector>) getConnector()).setFilterDef(this);
		this.remaining = remaining;
	}

	@Override
	public CompositeFilterDef<T, TPlugin, CompositeConnector<T, TPlugin, TRemainingConnector>> begin()
	{
		getCtxt().getPipelineDef().pushToFilterStack(FilterBlock.forBegin());

		return new CompositeFilterDefImpl<T, TPlugin, CompositeConnector<T, TPlugin, TRemainingConnector>>(getCtxt(),
			new CompositeConnectorImpl<T, TPlugin, TRemainingConnector>(getCtxt(), this.remaining, this));
	}

	@Override
	public CompositeFilterDef<T, TPlugin, CompositeConnector<T, TPlugin, TRemainingConnector>> not()
	{
		getCtxt().getPipelineDef().pushToFilterStack(FilterBlock.forNot());

		return new CompositeFilterDefImpl<T, TPlugin, CompositeConnector<T, TPlugin, TRemainingConnector>>(getCtxt(),
			new CompositeConnectorImpl<T, TPlugin, TRemainingConnector>(getCtxt(), this.remaining, this));
	}
}
