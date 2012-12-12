package com.googlecode.qlink.core.definition.aggregate;

import com.googlecode.qlink.api.behavior.DoResultAsSingleValue;
import com.googlecode.qlink.api.definition.aggregate.FoldDef;
import com.googlecode.qlink.api.functor.Folder;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.PipelineContextAwareSupport;
import com.googlecode.qlink.core.context.enums.EFoldSide;

public class FoldDefImpl<T, TPlugin>
	extends PipelineContextAwareSupport
	implements FoldDef<T, TPlugin>
{
	private final EFoldSide foldSide;

	private final DoResultAsSingleValue<T, TPlugin> doResult;

	@SuppressWarnings("unchecked")
	public FoldDefImpl(IPipelineContext ctxt, EFoldSide side)
	{
		super(ctxt);
		foldSide = side;
		doResult = getCtxt().getFactory().create(DoResultAsSingleValue.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <R> DoResultAsSingleValue<R, TPlugin> with(R init, Folder<T, R> f)
	{
		getCtxt().getPipelineDef().setFolder(f, init, foldSide);
		return (DoResultAsSingleValue<R, TPlugin>) doResult;
	}

}
