package com.googlecode.qlink.mem.behavior;


import com.googlecode.qlink.api.functor.Function2;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.PipelineContextAwareSupport;
import com.googlecode.qlink.mem.utils.MemFunctionUtils;

public class MemDoResultSupport
	extends PipelineContextAwareSupport
{
	protected MemDoResultSupport(IPipelineContext ctxt)
	{
		super(ctxt);
	}

	public Function2<?, Integer, ?> createSelectGroupFunction()
	{
		return MemFunctionUtils.createTransformFunctionInternal(getCtxt().getPipelineDef().getSelectGroupStack(),
			getCtxt().getPipelineDef().getSelectGroupResultType(), getCtxt().getPipelineDef()
				.getSelectGroupResultClass());
	}

	public Function2<?, Integer, ?> createTransformFunction()
	{
		return MemFunctionUtils.createTransformFunctionInternal(getCtxt().getPipelineDef().getTransformStack(),
			getCtxt().getPipelineDef().getTransformResultType(), getCtxt().getPipelineDef().getTransformResultClass());
	}

}
