package com.googlecode.qlink.core.definition.sample;

import com.googlecode.qlink.api.behavior.DoResultAsSingleValue;
import com.googlecode.qlink.api.definition.sample.SampleDef;
import com.googlecode.qlink.api.definition.sample.SampleEndDef;
import com.googlecode.qlink.api.functor.SamplePredicate;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.PipelineContextAwareSupport;
import com.googlecode.qlink.core.context.enums.ESampleType;

public class SampleDefImpl<T, TPlugin>
	extends PipelineContextAwareSupport
	implements SampleDef<T, TPlugin>
{

	private final DoResultAsSingleValue<T, TPlugin> doResult;

	@SuppressWarnings("unchecked")
	public SampleDefImpl(IPipelineContext ctxt)
	{
		super(ctxt);
		doResult = getCtxt().getFactory().create(DoResultAsSingleValue.class);
	}

	@Override
	public DoResultAsSingleValue<T, TPlugin> first()
	{
		getCtxt().getPipelineDef().setSampleType(ESampleType.first);
		return doResult;
	}

	@Override
	public DoResultAsSingleValue<T, TPlugin> last()
	{
		getCtxt().getPipelineDef().setSampleType(ESampleType.last);
		return doResult;
	}

	@Override
	public DoResultAsSingleValue<T, TPlugin> middle()
	{
		getCtxt().getPipelineDef().setSampleType(ESampleType.middle);
		return doResult;
	}

	@Override
	public DoResultAsSingleValue<T, TPlugin> nth(int nth)
	{
		getCtxt().getPipelineDef().setSampleType(ESampleType.nth, nth);
		return doResult;
	}

	@Override
	public SampleEndDef<T, TPlugin> with(SamplePredicate p)
	{
		getCtxt().getPipelineDef().setSamplePredicate(p);
		return new SampleEndDefImpl<T, TPlugin>(getCtxt());
	}

	@Override
	public SampleEndDef<T, TPlugin> even()
	{
		getCtxt().getPipelineDef().setSampleType(ESampleType.even);
		return new SampleEndDefImpl<T, TPlugin>(getCtxt());
	}

	@Override
	public SampleEndDef<T, TPlugin> odd()
	{
		getCtxt().getPipelineDef().setSampleType(ESampleType.odd);
		return new SampleEndDefImpl<T, TPlugin>(getCtxt());
	}

	@Override
	public SampleEndDef<T, TPlugin> head(int l)
	{
		getCtxt().getPipelineDef().setSampleType(ESampleType.head, l);
		return new SampleEndDefImpl<T, TPlugin>(getCtxt());
	}

	@Override
	public SampleEndDef<T, TPlugin> tail(int l)
	{
		getCtxt().getPipelineDef().setSampleType(ESampleType.tail, l);
		return new SampleEndDefImpl<T, TPlugin>(getCtxt());
	}

}
