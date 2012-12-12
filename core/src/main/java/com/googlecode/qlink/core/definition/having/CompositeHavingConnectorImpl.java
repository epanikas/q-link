package com.googlecode.qlink.core.definition.having;

import java.util.List;

import com.googlecode.qlink.api.definition.group.having.CompositeHavingConnector;
import com.googlecode.qlink.api.definition.group.having.CompositeHavingFilterDef;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.blocks.FilterBlock;
import com.googlecode.qlink.core.definition.filter.common.CommonConnectorImpl;

public class CompositeHavingConnectorImpl<K, V extends List<?>, TPlugin, TRemainingConnector>
	extends CommonConnectorImpl<CompositeHavingFilterDef<K, V, TPlugin, TRemainingConnector>>
	implements CompositeHavingConnector<K, V, TPlugin, TRemainingConnector>
{

	private TRemainingConnector remaining;

	protected CompositeHavingConnectorImpl(IPipelineContext ctxt, TRemainingConnector remaining,
					CompositeHavingFilterDefImpl<K, V, TPlugin, TRemainingConnector> filterDef)
	{
		super(ctxt, filterDef, /*isHaving*/true);
		this.remaining = remaining;
	}

	public void setRemaining(TRemainingConnector remaining)
	{
		this.remaining = remaining;
	}

	@Override
	public TRemainingConnector end()
	{
		getCtxt().getPipelineDef().pushToHavingStack(FilterBlock.forEnd());
		return this.remaining;
	}

}
