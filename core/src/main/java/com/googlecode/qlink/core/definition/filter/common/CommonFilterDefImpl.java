package com.googlecode.qlink.core.definition.filter.common;

import com.googlecode.qlink.api.definition.filter.common.CommonFilterDef;
import com.googlecode.qlink.api.definition.filter.common.Condition;
import com.googlecode.qlink.api.functor.Predicate;
import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.PipelineContextAwareSupport;
import com.googlecode.qlink.core.context.TPropertyImpl;
import com.googlecode.qlink.core.context.blocks.FilterBlock;

public class CommonFilterDefImpl<T, TConnector>
	extends PipelineContextAwareSupport
	implements CommonFilterDef<T, TConnector>
{

	private final TConnector connector;

	protected CommonFilterDefImpl(IPipelineContext ctxt, TConnector connector)
	{
		super(ctxt);
		this.connector = connector;
	}

	public TConnector getConnector()
	{
		return this.connector;
	}

	@Override
	public <R> Condition<R, TConnector> p(String propName)
	{
		getCtxt().getPipelineDef().pushToFilterStack(FilterBlock.forProperty(TPropertyImpl.forName(propName)));
		return new ConditionImpl<R, TConnector>(getCtxt(), this.connector, /*isHaving*/false);
	}

	@Override
	public <R> Condition<R, TConnector> p(String propName, Class<R> cls)
	{
		getCtxt().getPipelineDef()
			.pushToFilterStack(FilterBlock.forProperty(TPropertyImpl.forTypedName(propName, cls)));
		return new ConditionImpl<R, TConnector>(getCtxt(), this.connector, /*isHaving*/false);
	}

	@Override
	public <R> Condition<R, TConnector> p(TProperty<R> tp)
	{
		getCtxt().getPipelineDef().pushToFilterStack(FilterBlock.forProperty(tp));
		return new ConditionImpl<R, TConnector>(getCtxt(), this.connector, /*isHaving*/false);
	}

	@Override
	public TConnector with(Predicate<T> p)
	{
		getCtxt().getPipelineDef().pushToFilterStack(FilterBlock.forPredicate(p));
		return this.connector;
	}

	@Override
	public Condition<T, TConnector> self()
	{
		getCtxt().getPipelineDef().pushToFilterStack(FilterBlock.<T> forSelf());
		return new ConditionImpl<T, TConnector>(getCtxt(), this.connector, /*isHaving*/false);
	}

	//	@Override
	//	public <R> Condition<R, TConnector> expr(String expr, Class<R> cls)
	//	{
	//		getCtxt().getPipelineDef().pushToFilterStack(FilterBlock.forExpression(expr, cls));
	//		return new ConditionImpl<R, TConnector>(getCtxt(), this.connector, /*isHaving*/false);
	//	}
}
