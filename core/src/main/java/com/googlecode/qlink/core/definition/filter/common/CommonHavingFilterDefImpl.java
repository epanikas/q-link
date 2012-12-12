package com.googlecode.qlink.core.definition.filter.common;

import java.util.Map.Entry;

import com.googlecode.qlink.api.definition.filter.common.CommonHavingFilterDef;
import com.googlecode.qlink.api.definition.filter.common.Condition;
import com.googlecode.qlink.api.functor.Predicate;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.PipelineContextAwareSupport;
import com.googlecode.qlink.core.context.TPropertyImpl;
import com.googlecode.qlink.core.context.blocks.FilterBlock;
import com.googlecode.qlink.core.context.enums.EAggregatorType;

public class CommonHavingFilterDefImpl<K, V, TConnector>
	extends PipelineContextAwareSupport
	implements CommonHavingFilterDef<K, V, TConnector>

{

	private final TConnector connector;

	public CommonHavingFilterDefImpl(IPipelineContext ctxt, TConnector connector)
	{
		super(ctxt);
		this.connector = connector;
	}

	public TConnector getConnector()
	{
		return connector;
	}

	@Override
	public Condition<Integer, TConnector> count()
	{
		getCtxt().getPipelineDef().pushToHavingStack(FilterBlock.forAggregator(EAggregatorType.count, null));
		return new ConditionImpl<Integer, TConnector>(getCtxt(), connector, /*isHaving*/true);
	}

	@Override
	public <R> Condition<R, TConnector> sum(Class<R> cls)
	{
		getCtxt().getPipelineDef().pushToHavingStack(FilterBlock.forAggregator(EAggregatorType.sum, null));
		return new ConditionImpl<R, TConnector>(getCtxt(), connector, /*isHaving*/true);
	}

	@Override
	public <R> Condition<R, TConnector> min(Class<R> cls)
	{
		getCtxt().getPipelineDef().pushToHavingStack(FilterBlock.forAggregator(EAggregatorType.min, null));
		return new ConditionImpl<R, TConnector>(getCtxt(), connector, /*isHaving*/true);
	}

	@Override
	public <R> Condition<R, TConnector> max(Class<R> cls)
	{
		getCtxt().getPipelineDef().pushToHavingStack(FilterBlock.forAggregator(EAggregatorType.max, null));
		return new ConditionImpl<R, TConnector>(getCtxt(), connector, /*isHaving*/true);
	}

	@Override
	public <R> Condition<R, TConnector> sumOf(String propName, Class<R> propCls)
	{
		getCtxt().getPipelineDef().pushToHavingStack(
			FilterBlock.forAggregator(EAggregatorType.sum, TPropertyImpl.forTypedName(propName, propCls)));
		return new ConditionImpl<R, TConnector>(getCtxt(), connector, /*isHaving*/true);
	}

	@Override
	public <R> Condition<R, TConnector> minOf(String propName, Class<R> propCls)
	{
		getCtxt().getPipelineDef().pushToHavingStack(
			FilterBlock.forAggregator(EAggregatorType.min, TPropertyImpl.forTypedName(propName, propCls)));
		return new ConditionImpl<R, TConnector>(getCtxt(), connector, /*isHaving*/true);
	}

	@Override
	public <R> Condition<R, TConnector> maxOf(String propName, Class<R> propCls)
	{
		getCtxt().getPipelineDef().pushToHavingStack(
			FilterBlock.forAggregator(EAggregatorType.max, TPropertyImpl.forTypedName(propName, propCls)));
		return new ConditionImpl<R, TConnector>(getCtxt(), connector, /*isHaving*/true);
	}

	@Override
	public TConnector with(Predicate<Entry<K, V>> p)
	{
		getCtxt().getPipelineDef().pushToHavingStack(FilterBlock.forPredicate(p));
		return connector;
	}
}
