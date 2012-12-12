package com.googlecode.qlink.core.definition.transform;

import java.util.List;

import com.googlecode.qlink.api.behavior.CanVisit;
import com.googlecode.qlink.api.behavior.DoResultAsList;
import com.googlecode.qlink.api.definition.aggregate.AggregateDefinitions.StartAggregateDef;
import com.googlecode.qlink.api.definition.aggregate.FoldDef;
import com.googlecode.qlink.api.definition.aggregate.ReduceDef;
import com.googlecode.qlink.api.definition.filter.FilterDef;
import com.googlecode.qlink.api.definition.group.StartGroupByDef;
import com.googlecode.qlink.api.definition.group.StartIndexByDef;
import com.googlecode.qlink.api.definition.order.StartOrderDef;
import com.googlecode.qlink.api.definition.transform.FinishSelectDef;
import com.googlecode.qlink.api.definition.visit.StartVisitDef;
import com.googlecode.qlink.core.behavior.CanAggregateImpl;
import com.googlecode.qlink.core.behavior.CanFilterImpl;
import com.googlecode.qlink.core.behavior.CanIndexImpl;
import com.googlecode.qlink.core.behavior.CanOrderImpl;
import com.googlecode.qlink.core.behavior.CanVisitImpl;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.PipelineContextAwareSupport;

public class FinishSelectDefImpl<T, TPlugin>
	extends PipelineContextAwareSupport
	implements FinishSelectDef<T, TPlugin>
{

	private final DoResultAsList<T, TPlugin> doResult;
	private final CanFilterImpl<T, TPlugin> canFilter;
	private final CanOrderImpl<T, TPlugin> canOrder;
	private final CanIndexImpl<T, TPlugin> canIndex;
	private final CanAggregateImpl<T, TPlugin> canAggregate;
	private final CanVisit<T, TPlugin> canVisit;

	@SuppressWarnings("unchecked")
	public FinishSelectDefImpl(IPipelineContext ctxt)
	{
		super(ctxt);
		doResult = getCtxt().getFactory().create(DoResultAsList.class);
		canFilter = new CanFilterImpl<T, TPlugin>(ctxt);
		canOrder = new CanOrderImpl<T, TPlugin>(ctxt);
		canIndex = new CanIndexImpl<T, TPlugin>(ctxt);
		canAggregate = new CanAggregateImpl<T, TPlugin>(ctxt);
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
	public List<T> toList()
	{
		return doResult.toList();
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
	public StartAggregateDef<T, TPlugin> aggregate()
	{
		return canAggregate.aggregate();
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
	public StartVisitDef<T, TPlugin> visit()
	{
		return canVisit.visit();
	}

}
