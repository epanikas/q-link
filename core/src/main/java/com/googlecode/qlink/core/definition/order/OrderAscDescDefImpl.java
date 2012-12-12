package com.googlecode.qlink.core.definition.order;

import java.util.Comparator;
import java.util.List;

import com.googlecode.qlink.api.behavior.CanVisit;
import com.googlecode.qlink.api.behavior.DoResultAsList;
import com.googlecode.qlink.api.definition.aggregate.AggregateDefinitions.StartAggregateDef;
import com.googlecode.qlink.api.definition.aggregate.FoldDef;
import com.googlecode.qlink.api.definition.aggregate.ReduceDef;
import com.googlecode.qlink.api.definition.group.StartGroupByDef;
import com.googlecode.qlink.api.definition.group.StartIndexByDef;
import com.googlecode.qlink.api.definition.order.OrderAscDescDef;
import com.googlecode.qlink.api.definition.order.OrderDef;
import com.googlecode.qlink.api.definition.transform.SelectDefinitions.StartSelectDef;
import com.googlecode.qlink.api.definition.visit.StartVisitDef;
import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.core.behavior.CanAggregateImpl;
import com.googlecode.qlink.core.behavior.CanIndexImpl;
import com.googlecode.qlink.core.behavior.CanTransformImpl;
import com.googlecode.qlink.core.behavior.CanVisitImpl;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.PipelineContextAwareSupport;
import com.googlecode.qlink.core.context.TPropertyImpl;
import com.googlecode.qlink.core.context.blocks.OrderBlock;

public class OrderAscDescDefImpl<T, TPlugin>
	extends PipelineContextAwareSupport
	implements OrderAscDescDef<T, TPlugin>
{
	private final DoResultAsList<T, TPlugin> doResult;
	private final CanAggregateImpl<T, TPlugin> canAggregate;
	private final CanTransformImpl<T, TPlugin> canTransform;
	private final CanVisit<T, TPlugin> canVisit;
	private final CanIndexImpl<T, TPlugin> canIndex;

	@SuppressWarnings("unchecked")
	public OrderAscDescDefImpl(IPipelineContext def)
	{
		super(def);
		doResult = getCtxt().getFactory().create(DoResultAsList.class);
		canAggregate = new CanAggregateImpl<T, TPlugin>(getCtxt());
		canTransform = new CanTransformImpl<T, TPlugin>(getCtxt());
		canVisit = new CanVisitImpl<T, TPlugin>(getCtxt());
		canIndex = new CanIndexImpl<T, TPlugin>(getCtxt());
	}

	@Override
	public OrderDef<T, TPlugin> by(String propName)
	{
		getCtxt().getPipelineDef().pushToOrderStack(OrderBlock.forProperty(TPropertyImpl.forName(propName)));
		return new OrderDefImpl<T, TPlugin>(getCtxt());
	}

	@Override
	public OrderDef<T, TPlugin> by(TProperty<?> prop)
	{
		getCtxt().getPipelineDef().pushToOrderStack(OrderBlock.forProperty(prop));
		return new OrderDefImpl<T, TPlugin>(getCtxt());
	}

	@Override
	public OrderDef<T, TPlugin> with(Comparator<T> c)
	{
		getCtxt().getPipelineDef().pushToOrderStack(OrderBlock.forComparator(c));
		return new OrderDefImpl<T, TPlugin>(getCtxt());
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
	public StartAggregateDef<T, TPlugin> aggregate()
	{
		return canAggregate.aggregate();
	}

	@Override
	public StartSelectDef<T, TPlugin> select()
	{
		return canTransform.select();
	}

	@Override
	public StartVisitDef<T, TPlugin> visit()
	{
		return canVisit.visit();
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

}
