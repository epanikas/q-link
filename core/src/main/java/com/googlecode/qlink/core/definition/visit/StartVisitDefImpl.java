package com.googlecode.qlink.core.definition.visit;

import com.googlecode.qlink.api.definition.visit.StartVisitDef;
import com.googlecode.qlink.api.definition.visit.VisitAssignDef;
import com.googlecode.qlink.api.definition.visit.VisitDef;
import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.api.functor.Visitor;
import com.googlecode.qlink.api.functor.Visitor2;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.PipelineContextAwareSupport;
import com.googlecode.qlink.core.context.TPropertyImpl;
import com.googlecode.qlink.core.context.blocks.VisitBlock;
import com.googlecode.qlink.core.functor.Functions;

public class StartVisitDefImpl<T, TPlugin>
	extends PipelineContextAwareSupport
	implements StartVisitDef<T, TPlugin>
{

	public StartVisitDefImpl(IPipelineContext ctxt)
	{
		super(ctxt);
	}

	@Override
	public VisitAssignDef<Object, T, TPlugin> p(String propName)
	{
		getCtxt().getPipelineDef().pushToVisitStack(VisitBlock.forLhsProperty(TPropertyImpl.forName(propName)));
		return new VisitAssignDefImpl<Object, T, TPlugin>(getCtxt());
	}

	@Override
	public <R> VisitAssignDef<R, T, TPlugin> p(String propName, Class<R> cls)
	{
		getCtxt().getPipelineDef().pushToVisitStack(
			VisitBlock.forLhsProperty(TPropertyImpl.forTypedName(propName, cls)));
		return new VisitAssignDefImpl<R, T, TPlugin>(getCtxt());
	}

	@Override
	public <R> VisitAssignDef<R, T, TPlugin> p(TProperty<R> tp)
	{
		getCtxt().getPipelineDef().pushToVisitStack(VisitBlock.forLhsProperty(tp));
		return new VisitAssignDefImpl<R, T, TPlugin>(getCtxt());
	}

	@Override
	public VisitDef<T, TPlugin> with(Visitor<T> v)
	{
		getCtxt().getPipelineDef().pushToVisitStack(VisitBlock.forVisitor2(Functions.adaptToVisitorWithIndex(v)));
		return new VisitDefImpl<T, TPlugin>(getCtxt());
	}

	@Override
	public VisitDef<T, TPlugin> with(Visitor2<T, Integer> v)
	{
		getCtxt().getPipelineDef().pushToVisitStack(VisitBlock.forVisitor2(v));
		return new VisitDefImpl<T, TPlugin>(getCtxt());
	}
}
