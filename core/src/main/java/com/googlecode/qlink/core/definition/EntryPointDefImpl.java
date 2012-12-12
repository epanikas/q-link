package com.googlecode.qlink.core.definition;

import java.util.List;

import com.googlecode.qlink.api.behavior.CanVisit;
import com.googlecode.qlink.api.behavior.DoResultAsList;
import com.googlecode.qlink.api.definition.EntryPointDef;
import com.googlecode.qlink.api.definition.aggregate.AggregateDefinitions.StartAggregateDef;
import com.googlecode.qlink.api.definition.aggregate.FoldDef;
import com.googlecode.qlink.api.definition.aggregate.ReduceDef;
import com.googlecode.qlink.api.definition.filter.FilterDef;
import com.googlecode.qlink.api.definition.group.StartGroupByDef;
import com.googlecode.qlink.api.definition.group.StartIndexByDef;
import com.googlecode.qlink.api.definition.order.StartOrderDef;
import com.googlecode.qlink.api.definition.sample.SampleDef;
import com.googlecode.qlink.api.definition.transform.SelectDefinitions.StartSelectDef;
import com.googlecode.qlink.api.definition.visit.StartVisitDef;
import com.googlecode.qlink.core.behavior.CanAggregateImpl;
import com.googlecode.qlink.core.behavior.CanFilterImpl;
import com.googlecode.qlink.core.behavior.CanIndexImpl;
import com.googlecode.qlink.core.behavior.CanOrderImpl;
import com.googlecode.qlink.core.behavior.CanSampleImpl;
import com.googlecode.qlink.core.behavior.CanTransformImpl;
import com.googlecode.qlink.core.behavior.CanVisitImpl;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.PipelineContextAwareSupport;

public class EntryPointDefImpl<T, TPlugin>
	extends PipelineContextAwareSupport
	implements EntryPointDef<T, TPlugin>
{
	private final DoResultAsList<T, TPlugin> doResult;
	private final CanFilterImpl<T, TPlugin> canFilter;
	private final CanOrderImpl<T, TPlugin> canOrder;
	private final CanIndexImpl<T, TPlugin> canIndex;
	private final CanSampleImpl<T, TPlugin> canSample;
	private final CanAggregateImpl<T, TPlugin> canAggregate;
	private final CanTransformImpl<T, TPlugin> canTransform;
	private final CanVisit<T, TPlugin> canVisit;

	@SuppressWarnings("unchecked")
	public EntryPointDefImpl(IPipelineContext ctxt)
	{
		super(ctxt);
		doResult = getCtxt().getFactory().create(DoResultAsList.class);
		canFilter = new CanFilterImpl<T, TPlugin>(ctxt);
		canOrder = new CanOrderImpl<T, TPlugin>(ctxt);
		canIndex = new CanIndexImpl<T, TPlugin>(ctxt);
		canSample = new CanSampleImpl<T, TPlugin>(ctxt);
		canAggregate = new CanAggregateImpl<T, TPlugin>(ctxt);
		canTransform = new CanTransformImpl<T, TPlugin>(ctxt);
		canVisit = new CanVisitImpl<T, TPlugin>(ctxt);
	}

	@Override
	public FilterDef<T, TPlugin> filter()
	{
		return canFilter.filter();
	}

	@Override
	public StartOrderDef<T, TPlugin> order()
	{
		return canOrder.order();
	}

	@Override
	public StartIndexByDef<T, TPlugin> index()
	{
		return canIndex.index();
	}

	@Override
	public StartGroupByDef<T, TPlugin> group()
	{
		return canIndex.group();
	}

	@Override
	public SampleDef<T, TPlugin> sample()
	{
		return canSample.sample();
	}

	@Override
	public ReduceDef<T, TPlugin> reduce()
	{
		return canAggregate.reduce();
	}

	@Override
	public FoldDef<T, TPlugin> foldLeft()
	{
		return canAggregate.foldLeft();
	}

	@Override
	public FoldDef<T, TPlugin> foldRight()
	{
		return canAggregate.foldRight();
	}

	@Override
	public StartAggregateDef<T, TPlugin> aggregate()
	{
		return canAggregate.aggregate();
	}

	@Override
	public List<T> toList()
	{
		return doResult.toList();
	}

	@Override
	public StartSelectDef<T, TPlugin> select()
	{
		return canTransform.select();
	}

	@Override
	public T toUniqueResult()
	{
		return doResult.toUniqueResult();
	}

	@Override
	public int size()
	{
		return doResult.size();
	}

	@Override
	public boolean isEmpty()
	{
		return doResult.isEmpty();
	}

	@Override
	public TPlugin plugin()
	{
		return doResult.plugin();
	}

	@Override
	public StartVisitDef<T, TPlugin> visit()
	{
		return canVisit.visit();
	}

}
