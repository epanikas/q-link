package com.googlecode.qlink.core.definition.aggregate;

import com.googlecode.qlink.api.behavior.DoResultAsSingleValue;
import com.googlecode.qlink.api.definition.aggregate.ReduceDef;
import com.googlecode.qlink.api.functor.Reducer;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.PipelineContextAwareSupport;

public class ReduceDefImpl<T, TPlugin>
	extends PipelineContextAwareSupport
	implements ReduceDef<T, TPlugin>
{
	private final DoResultAsSingleValue<T, TPlugin> doResult;

	@SuppressWarnings("unchecked")
	public ReduceDefImpl(IPipelineContext ctxt)
	{
		super(ctxt);
		doResult = getCtxt().getFactory().create(DoResultAsSingleValue.class);
	}

	@Override
	public DoResultAsSingleValue<T, TPlugin> with(Reducer<T> f)
	{
		getCtxt().getPipelineDef().setReducer(f);
		return doResult;
	}

}
