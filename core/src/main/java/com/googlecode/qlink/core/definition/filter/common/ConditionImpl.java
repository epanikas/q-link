package com.googlecode.qlink.core.definition.filter.common;

import com.googlecode.qlink.api.behavior.DoResultAsList;
import com.googlecode.qlink.api.definition.filter.common.Condition;
import com.googlecode.qlink.api.definition.filter.common.RhsFilterOpDef;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.PipelineContextAwareSupport;
import com.googlecode.qlink.core.context.blocks.FilterBlock;
import com.googlecode.qlink.core.context.enums.EFilterCondition;
import com.googlecode.qlink.tuples.Tuples;

public class ConditionImpl<R, TEnd>
	extends PipelineContextAwareSupport
	implements Condition<R, TEnd>
{
	private final TEnd end;

	private final boolean isHaving;

	public ConditionImpl(IPipelineContext ctxt, TEnd end, boolean isHaving)
	{
		super(ctxt);
		this.end = end;
		this.isHaving = isHaving;
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
	public TEnd eq(R val)
	{
		pushToStack(FilterBlock.forCondition(EFilterCondition.eq));
		pushToStack(FilterBlock.forValue(val));
		return this.end;
	}

	@Override
	public TEnd ne(R val)
	{
		pushToStack(FilterBlock.forCondition(EFilterCondition.neq));
		pushToStack(FilterBlock.forValue(val));
		return this.end;
	}

	@Override
	public TEnd gt(R val)
	{
		pushToStack(FilterBlock.forCondition(EFilterCondition.gt));
		pushToStack(FilterBlock.forValue(val));
		return this.end;
	}

	@Override
	public TEnd lt(R val)
	{
		pushToStack(FilterBlock.forCondition(EFilterCondition.lt));
		pushToStack(FilterBlock.forValue(val));

		return this.end;
	}

	@Override
	public TEnd ge(R val)
	{
		pushToStack(FilterBlock.forCondition(EFilterCondition.ge));
		pushToStack(FilterBlock.forValue(val));
		return this.end;
	}

	@Override
	public TEnd le(R val)
	{
		pushToStack(FilterBlock.forCondition(EFilterCondition.le));
		pushToStack(FilterBlock.forValue(val));
		return this.end;
	}

	@Override
	public TEnd between(R val1, R val2)
	{
		pushToStack(FilterBlock.forCondition(EFilterCondition.between));
		pushToStack(FilterBlock.forValue(Tuples.tie(val1, val2)));
		return this.end;
	}

	@Override
	public TEnd in(DoResultAsList<R, ?> proc)
	{
		pushToStack(FilterBlock.forCondition(EFilterCondition.in));
		pushToStack(FilterBlock.forValue(proc));
		return this.end;
	}

	@Override
	public TEnd in(R... lst)
	{
		pushToStack(FilterBlock.forCondition(EFilterCondition.in));
		pushToStack(FilterBlock.forValue(lst));
		return this.end;
	}

	@Override
	public RhsFilterOpDef<R, TEnd> eq()
	{
		pushToStack(FilterBlock.forCondition(EFilterCondition.eq));
		return new RhsFilterOpDefImpl<R, TEnd>(getCtxt(), end, isHaving);
	}

	@Override
	public RhsFilterOpDef<R, TEnd> ne()
	{
		pushToStack(FilterBlock.forCondition(EFilterCondition.neq));
		return new RhsFilterOpDefImpl<R, TEnd>(getCtxt(), end, isHaving);
	}

	@Override
	public RhsFilterOpDef<R, TEnd> lt()
	{
		pushToStack(FilterBlock.forCondition(EFilterCondition.lt));
		return new RhsFilterOpDefImpl<R, TEnd>(getCtxt(), end, isHaving);
	}

	@Override
	public RhsFilterOpDef<R, TEnd> le()
	{
		pushToStack(FilterBlock.forCondition(EFilterCondition.le));
		return new RhsFilterOpDefImpl<R, TEnd>(getCtxt(), end, isHaving);
	}

	@Override
	public RhsFilterOpDef<R, TEnd> gt()
	{
		pushToStack(FilterBlock.forCondition(EFilterCondition.gt));
		return new RhsFilterOpDefImpl<R, TEnd>(getCtxt(), end, isHaving);
	}

	@Override
	public RhsFilterOpDef<R, TEnd> ge()
	{
		pushToStack(FilterBlock.forCondition(EFilterCondition.ge));
		return new RhsFilterOpDefImpl<R, TEnd>(getCtxt(), end, isHaving);
	}

}
