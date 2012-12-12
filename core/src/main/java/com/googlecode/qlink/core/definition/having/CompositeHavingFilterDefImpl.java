package com.googlecode.qlink.core.definition.having;

import java.util.List;

import com.googlecode.qlink.api.definition.group.having.CompositeHavingConnector;
import com.googlecode.qlink.api.definition.group.having.CompositeHavingFilterDef;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.blocks.FilterBlock;
import com.googlecode.qlink.core.definition.filter.common.CommonHavingFilterDefImpl;

public class CompositeHavingFilterDefImpl<K, V extends List<?>, TPlugin, TRemainingConnector>
	extends CommonHavingFilterDefImpl<K, V, CompositeHavingConnector<K, V, TPlugin, TRemainingConnector>>
	implements CompositeHavingFilterDef<K, V, TPlugin, TRemainingConnector>
{

	private final TRemainingConnector remaining;

	public CompositeHavingFilterDefImpl(IPipelineContext ctxt, TRemainingConnector remaining)
	{
		super(ctxt, new CompositeHavingConnectorImpl<K, V, TPlugin, TRemainingConnector>(ctxt, remaining, null));
		((CompositeHavingConnectorImpl<K, V, TPlugin, TRemainingConnector>) this.getConnector()).setFilterDef(this);
		this.remaining = remaining;
	}

	@Override
	public CompositeHavingFilterDef<K, V, TPlugin, CompositeHavingConnector<K, V, TPlugin, TRemainingConnector>> not()
	{
		getCtxt().getPipelineDef().pushToFilterStack(FilterBlock.forNot());

		return new CompositeHavingFilterDefImpl<K, V, TPlugin, CompositeHavingConnector<K, V, TPlugin, TRemainingConnector>>(
			getCtxt(), new CompositeHavingConnectorImpl<K, V, TPlugin, TRemainingConnector>(getCtxt(), this.remaining,
				this));
	}

	@Override
	public CompositeHavingFilterDef<K, V, TPlugin, CompositeHavingConnector<K, V, TPlugin, TRemainingConnector>> begin()
	{
		getCtxt().getPipelineDef().pushToFilterStack(FilterBlock.forBegin());

		return new CompositeHavingFilterDefImpl<K, V, TPlugin, CompositeHavingConnector<K, V, TPlugin, TRemainingConnector>>(
			getCtxt(), new CompositeHavingConnectorImpl<K, V, TPlugin, TRemainingConnector>(getCtxt(), this.remaining,
				this));
	}

}
