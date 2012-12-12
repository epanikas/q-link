package com.googlecode.qlink.core.definition.visit;

import com.googlecode.qlink.api.definition.visit.VisitDef;
import com.googlecode.qlink.api.definition.visit.VisitRhsDef;
import com.googlecode.qlink.api.functor.Function;
import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.PipelineContextAwareSupport;
import com.googlecode.qlink.core.context.TPropertyImpl;
import com.googlecode.qlink.core.context.blocks.VisitBlock;
import com.googlecode.qlink.core.functor.Functions;

public class VisitRhsDefImpl<TProp, T, TPlugin>
	extends PipelineContextAwareSupport
	implements VisitRhsDef<TProp, T, TPlugin>
{

	public VisitRhsDefImpl(IPipelineContext ctxt)
	{
		super(ctxt);
	}

	@Override
	public VisitDef<T, TPlugin> val(TProp val)
	{
		getCtxt().getPipelineDef().pushToVisitStack(VisitBlock.forRhsValue(val));
		return new VisitDefImpl<T, TPlugin>(getCtxt());
	}

	@Override
	public VisitDef<T, TPlugin> prop(String propName)
	{
		getCtxt().getPipelineDef().pushToVisitStack(VisitBlock.forRhsProperty(TPropertyImpl.forName(propName)));
		return new VisitDefImpl<T, TPlugin>(getCtxt());
	}

	@Override
	public <R> VisitDef<T, TPlugin> prop(TProperty<R> tp)
	{
		getCtxt().getPipelineDef().pushToVisitStack(VisitBlock.forRhsProperty(tp));
		return new VisitDefImpl<T, TPlugin>(getCtxt());
	}

	@Override
	public VisitDef<T, TPlugin> func(Function<T, TProp> f)
	{
		getCtxt().getPipelineDef().pushToVisitStack(VisitBlock.forRhsFunction(Functions.adaptToFunctionWithIndex(f)));
		return new VisitDefImpl<T, TPlugin>(getCtxt());
	}
}
