package com.googlecode.qlink.core.definition.filter.common;

import com.googlecode.qlink.api.definition.filter.common.CommonConnector;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.PipelineContextAwareSupport;
import com.googlecode.qlink.core.context.blocks.FilterBlock;
import com.googlecode.qlink.core.context.enums.EFilterJunction;

public class CommonConnectorImpl<TFilterDef>
	extends PipelineContextAwareSupport
	implements CommonConnector<TFilterDef>
{

	private TFilterDef filterDef;

	private final boolean isHaving;

	protected CommonConnectorImpl(IPipelineContext ctxt, TFilterDef filterDef, boolean isHaving)
	{
		super(ctxt);
		this.filterDef = filterDef;
		this.isHaving = isHaving;
	}

	public void setFilterDef(TFilterDef filterDef)
	{
		this.filterDef = filterDef;
	}

	private void pushToStack(FilterBlock fb)
	{
		if (isHaving) {
			getCtxt().getPipelineDef().pushToHavingStack(fb);
		}
		else {
			getCtxt().getPipelineDef().pushToFilterStack(fb);
		}
	}

	@Override
	public TFilterDef and()
	{
		pushToStack(FilterBlock.forJunction(EFilterJunction.and));
		return this.filterDef;
	}

	@Override
	public TFilterDef or()
	{
		pushToStack(FilterBlock.forJunction(EFilterJunction.or));
		return this.filterDef;
	}

}
