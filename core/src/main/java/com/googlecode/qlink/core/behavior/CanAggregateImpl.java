package com.googlecode.qlink.core.behavior;

import com.googlecode.qlink.api.behavior.CanAggregate;
import com.googlecode.qlink.api.definition.aggregate.AggregateDefinitions.StartAggregateDef;
import com.googlecode.qlink.api.definition.aggregate.FoldDef;
import com.googlecode.qlink.api.definition.aggregate.ReduceDef;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.PipelineContextAwareSupport;
import com.googlecode.qlink.core.context.enums.EFoldSide;
import com.googlecode.qlink.core.definition.aggregate.AggregateDefinitionsImpl.StartAggregateDefImpl;
import com.googlecode.qlink.core.definition.aggregate.FoldDefImpl;
import com.googlecode.qlink.core.definition.aggregate.ReduceDefImpl;

public class CanAggregateImpl<T, TPlugin>
	extends PipelineContextAwareSupport
	implements CanAggregate<T, TPlugin>
{

	public CanAggregateImpl(IPipelineContext ctxt)
	{
		super(ctxt);
	}

	@Override
	public StartAggregateDef<T, TPlugin> aggregate()
	{
		return new StartAggregateDefImpl<T, TPlugin>(getCtxt());
	}

	@Override
	public ReduceDef<T, TPlugin> reduce()
	{
		return new ReduceDefImpl<T, TPlugin>(getCtxt());
	}

	@Override
	public FoldDef<T, TPlugin> foldLeft()
	{
		return new FoldDefImpl<T, TPlugin>(getCtxt(), EFoldSide.left);
	}

	@Override
	public FoldDef<T, TPlugin> foldRight()
	{
		return new FoldDefImpl<T, TPlugin>(getCtxt(), EFoldSide.right);
	}
}
