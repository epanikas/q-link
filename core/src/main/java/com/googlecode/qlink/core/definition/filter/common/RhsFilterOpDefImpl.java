package com.googlecode.qlink.core.definition.filter.common;

import com.googlecode.qlink.api.definition.filter.common.RhsFilterOpDef;
import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.PipelineContextAwareSupport;
import com.googlecode.qlink.core.context.TPropertyImpl;
import com.googlecode.qlink.core.context.blocks.FilterBlock;

public class RhsFilterOpDefImpl<T, TEnd>
	extends PipelineContextAwareSupport
	implements RhsFilterOpDef<T, TEnd>
{

	private final TEnd end;
	private final boolean isHavingCondition;

	public RhsFilterOpDefImpl(IPipelineContext ctxt, TEnd end, boolean havingCondition)
	{
		super(ctxt);
		this.end = end;
		this.isHavingCondition = havingCondition;
	}

	private void pushToStack(FilterBlock fb)
	{
		if (isHavingCondition) {
			getCtxt().getPipelineDef().pushToHavingStack(fb);
		}
		else {
			getCtxt().getPipelineDef().pushToFilterStack(fb);
		}

	}

	@Override
	public TEnd prop(String propName)
	{
		pushToStack(FilterBlock.forProperty(TPropertyImpl.forName(propName)));
		return end;
	}

	@Override
	public TEnd prop(TProperty<?> prop)
	{
		pushToStack(FilterBlock.forProperty(prop));
		return end;
	}

	@Override
	public TEnd val(T val)
	{
		pushToStack(FilterBlock.forValue(val));
		return end;
	}

}
