package com.googlecode.qlink.core.definition.aggregate;

import com.googlecode.qlink.api.behavior.DoResultAsSingleValue;
import com.googlecode.qlink.api.definition.aggregate.AggregateDefinitions.AggregateDef1;
import com.googlecode.qlink.api.definition.aggregate.AggregateDefinitions.AggregateDef2;
import com.googlecode.qlink.api.definition.aggregate.AggregateDefinitions.AggregateDef3;
import com.googlecode.qlink.api.definition.aggregate.AggregateDefinitions.AggregateDef4;
import com.googlecode.qlink.api.definition.aggregate.AggregateDefinitions.AggregateDef5;
import com.googlecode.qlink.api.definition.aggregate.AggregateDefinitions.AggregateDefSupport;
import com.googlecode.qlink.api.definition.aggregate.AggregateDefinitions.StartAggregateDef;
import com.googlecode.qlink.api.functor.Aggregator;
import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.api.tuple.Tuple3;
import com.googlecode.qlink.api.tuple.Tuple4;
import com.googlecode.qlink.api.tuple.Tuple5;
import com.googlecode.qlink.api.tuple.TupleN;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.PipelineContextAwareSupport;
import com.googlecode.qlink.core.context.TPropertyImpl;
import com.googlecode.qlink.core.context.enums.EAggregatorType;
import com.googlecode.qlink.core.context.enums.ETransformResultType;

public class AggregateDefinitionsImpl
{

	public static class AggregateDefSupportImpl<TRes, TPlugin>
		extends PipelineContextAwareSupport
		implements AggregateDefSupport<TRes, TPlugin>
	{

		private final DoResultAsSingleValue<TRes, TPlugin> doResult;

		@SuppressWarnings("unchecked")
		public AggregateDefSupportImpl(IPipelineContext ctxt)
		{
			super(ctxt);
			doResult = getCtxt().getFactory().create(DoResultAsSingleValue.class);
		}

		@Override
		public TRes toValue()
		{
			return doResult.toValue();
		}

		@Override
		public TPlugin plugin()
		{
			return doResult.plugin();
		}

		@SuppressWarnings("unchecked")
		@Override
		public <TNewRes> DoResultAsSingleValue<TNewRes, TPlugin> asNew(Class<TNewRes> cls)
		{
			getCtxt().getPipelineDef().setAggregatedResultType(ETransformResultType.newObject);
			getCtxt().getPipelineDef().setAggregatedResultClass(cls);
			return (DoResultAsSingleValue<TNewRes, TPlugin>) doResult;
		}

		@SuppressWarnings("unchecked")
		@Override
		public DoResultAsSingleValue<Object[], TPlugin> asArray()
		{
			getCtxt().getPipelineDef().setAggregatedResultType(ETransformResultType.arrayObject);
			return (DoResultAsSingleValue<Object[], TPlugin>) doResult;
		}

	}

	public static class StartAggregateDefImpl<TOrigElem, TPlugin>
		extends PipelineContextAwareSupport
		implements StartAggregateDef<TOrigElem, TPlugin>
	{

		public StartAggregateDefImpl(IPipelineContext ctxt)
		{
			super(ctxt);
		}

		@Override
		public AggregateDef1<Long, TOrigElem, TPlugin> count()
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.count,
				TPropertyImpl.forTypedName("", Long.class));
			return new AggregateDef1Impl<Long, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public AggregateDef1<TOrigElem, TOrigElem, TPlugin> sum()
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.sum, null);
			return new AggregateDef1Impl<TOrigElem, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public AggregateDef1<TOrigElem, TOrigElem, TPlugin> min()
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.min, null);
			return new AggregateDef1Impl<TOrigElem, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public AggregateDef1<TOrigElem, TOrigElem, TPlugin> max()
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.max, null);
			return new AggregateDef1Impl<TOrigElem, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef1<R, TOrigElem, TPlugin> sumOf(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.sum,
				TPropertyImpl.forTypedName(propName, cls));
			return new AggregateDef1Impl<R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef1<R, TOrigElem, TPlugin> sumOf(TProperty<R> tp)
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.sum, tp);
			return new AggregateDef1Impl<R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef1<R, TOrigElem, TPlugin> minOf(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.min,
				TPropertyImpl.forTypedName(propName, cls));
			return new AggregateDef1Impl<R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef1<R, TOrigElem, TPlugin> minOf(TProperty<R> tp)
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.min, tp);
			return new AggregateDef1Impl<R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef1<R, TOrigElem, TPlugin> maxOf(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.max,
				TPropertyImpl.forTypedName(propName, cls));
			return new AggregateDef1Impl<R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef1<R, TOrigElem, TPlugin> maxOf(TProperty<R> tp)
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.max, tp);
			return new AggregateDef1Impl<R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef1<R, TOrigElem, TPlugin> with(Class<R> cls, Aggregator<TOrigElem, R> r)
		{
			getCtxt().getPipelineDef().addAggregator(r);
			return new AggregateDef1Impl<R, TOrigElem, TPlugin>(getCtxt());
		}

	}

	public static class AggregateDef1Impl<T1, TOrigElem, TPlugin>
		extends AggregateDefSupportImpl<T1, TPlugin>
		implements AggregateDef1<T1, TOrigElem, TPlugin>
	{

		public AggregateDef1Impl(IPipelineContext ctxt)
		{
			super(ctxt);
		}

		@Override
		public AggregateDef2<T1, Long, TOrigElem, TPlugin> count()
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.count, null);
			return new AggregateDef2Impl<T1, Long, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public AggregateDef2<T1, TOrigElem, TOrigElem, TPlugin> sum()
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.sum, null);
			return new AggregateDef2Impl<T1, TOrigElem, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public AggregateDef2<T1, TOrigElem, TOrigElem, TPlugin> min()
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.min, null);
			return new AggregateDef2Impl<T1, TOrigElem, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public AggregateDef2<T1, TOrigElem, TOrigElem, TPlugin> max()
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.max, null);
			return new AggregateDef2Impl<T1, TOrigElem, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef2<T1, R, TOrigElem, TPlugin> sumOf(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.sum,
				TPropertyImpl.forTypedName(propName, cls));
			return new AggregateDef2Impl<T1, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef2<T1, R, TOrigElem, TPlugin> sumOf(TProperty<R> tp)
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.sum, tp);
			return new AggregateDef2Impl<T1, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef2<T1, R, TOrigElem, TPlugin> minOf(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.min,
				TPropertyImpl.forTypedName(propName, cls));
			return new AggregateDef2Impl<T1, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef2<T1, R, TOrigElem, TPlugin> minOf(TProperty<R> tp)
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.min, tp);
			return new AggregateDef2Impl<T1, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef2<T1, R, TOrigElem, TPlugin> maxOf(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.max,
				TPropertyImpl.forTypedName(propName, cls));
			return new AggregateDef2Impl<T1, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef2<T1, R, TOrigElem, TPlugin> maxOf(TProperty<R> tp)
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.max, tp);
			return new AggregateDef2Impl<T1, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef2<T1, R, TOrigElem, TPlugin> with(Class<R> cls, Aggregator<TOrigElem, R> r)
		{
			getCtxt().getPipelineDef().addAggregator(r);
			return new AggregateDef2Impl<T1, R, TOrigElem, TPlugin>(getCtxt());
		}

	}

	public static class AggregateDef2Impl<T1, T2, TOrigElem, TPlugin>
		extends AggregateDefSupportImpl<Pair<T1, T2>, TPlugin>
		implements AggregateDef2<T1, T2, TOrigElem, TPlugin>
	{

		public AggregateDef2Impl(IPipelineContext ctxt)
		{
			super(ctxt);
		}

		@Override
		public AggregateDef3<T1, T2, Long, TOrigElem, TPlugin> count()
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.count, null);
			return new AggregateDef3Impl<T1, T2, Long, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public AggregateDef3<T1, T2, TOrigElem, TOrigElem, TPlugin> sum()
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.sum, null);
			return new AggregateDef3Impl<T1, T2, TOrigElem, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public AggregateDef3<T1, T2, TOrigElem, TOrigElem, TPlugin> min()
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.min, null);
			return new AggregateDef3Impl<T1, T2, TOrigElem, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public AggregateDef3<T1, T2, TOrigElem, TOrigElem, TPlugin> max()
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.max, null);
			return new AggregateDef3Impl<T1, T2, TOrigElem, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef3<T1, T2, R, TOrigElem, TPlugin> sumOf(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.sum,
				TPropertyImpl.forTypedName(propName, cls));
			return new AggregateDef3Impl<T1, T2, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef3<T1, T2, R, TOrigElem, TPlugin> sumOf(TProperty<R> tp)
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.sum, tp);
			return new AggregateDef3Impl<T1, T2, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef3<T1, T2, R, TOrigElem, TPlugin> minOf(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.min,
				TPropertyImpl.forTypedName(propName, cls));
			return new AggregateDef3Impl<T1, T2, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef3<T1, T2, R, TOrigElem, TPlugin> minOf(TProperty<R> tp)
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.min, tp);
			return new AggregateDef3Impl<T1, T2, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef3<T1, T2, R, TOrigElem, TPlugin> maxOf(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.max,
				TPropertyImpl.forTypedName(propName, cls));
			return new AggregateDef3Impl<T1, T2, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef3<T1, T2, R, TOrigElem, TPlugin> maxOf(TProperty<R> tp)
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.max, tp);
			return new AggregateDef3Impl<T1, T2, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef3<T1, T2, R, TOrigElem, TPlugin> with(Class<R> cls, Aggregator<TOrigElem, R> r)
		{
			getCtxt().getPipelineDef().addAggregator(r);
			return new AggregateDef3Impl<T1, T2, R, TOrigElem, TPlugin>(getCtxt());
		}

	}

	public static class AggregateDef3Impl<T1, T2, T3, TOrigElem, TPlugin>
		extends AggregateDefSupportImpl<Tuple3<T1, T2, T3>, TPlugin>
		implements AggregateDef3<T1, T2, T3, TOrigElem, TPlugin>
	{

		public AggregateDef3Impl(IPipelineContext ctxt)
		{
			super(ctxt);
		}

		@Override
		public AggregateDef4<T1, T2, T3, Long, TOrigElem, TPlugin> count()
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.count, null);
			return new AggregateDef4Impl<T1, T2, T3, Long, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public AggregateDef4<T1, T2, T3, TOrigElem, TOrigElem, TPlugin> sum()
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.sum, null);
			return new AggregateDef4Impl<T1, T2, T3, TOrigElem, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public AggregateDef4<T1, T2, T3, TOrigElem, TOrigElem, TPlugin> min()
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.min, null);
			return new AggregateDef4Impl<T1, T2, T3, TOrigElem, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public AggregateDef4<T1, T2, T3, TOrigElem, TOrigElem, TPlugin> max()
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.max, null);
			return new AggregateDef4Impl<T1, T2, T3, TOrigElem, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef4<T1, T2, T3, R, TOrigElem, TPlugin> sumOf(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.sum,
				TPropertyImpl.forTypedName(propName, cls));
			return new AggregateDef4Impl<T1, T2, T3, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef4<T1, T2, T3, R, TOrigElem, TPlugin> sumOf(TProperty<R> tp)
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.sum, tp);
			return new AggregateDef4Impl<T1, T2, T3, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef4<T1, T2, T3, R, TOrigElem, TPlugin> minOf(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.min,
				TPropertyImpl.forTypedName(propName, cls));
			return new AggregateDef4Impl<T1, T2, T3, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef4<T1, T2, T3, R, TOrigElem, TPlugin> minOf(TProperty<R> tp)
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.min, tp);
			return new AggregateDef4Impl<T1, T2, T3, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef4<T1, T2, T3, R, TOrigElem, TPlugin> maxOf(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.max,
				TPropertyImpl.forTypedName(propName, cls));
			return new AggregateDef4Impl<T1, T2, T3, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef4<T1, T2, T3, R, TOrigElem, TPlugin> maxOf(TProperty<R> tp)
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.max, tp);
			return new AggregateDef4Impl<T1, T2, T3, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef4<T1, T2, T3, R, TOrigElem, TPlugin> with(Class<R> cls, Aggregator<TOrigElem, R> r)
		{
			getCtxt().getPipelineDef().addAggregator(r);
			return new AggregateDef4Impl<T1, T2, T3, R, TOrigElem, TPlugin>(getCtxt());
		}

	}

	public static class AggregateDef4Impl<T1, T2, T3, T4, TOrigElem, TPlugin>
		extends AggregateDefSupportImpl<Tuple4<T1, T2, T3, T4>, TPlugin>
		implements AggregateDef4<T1, T2, T3, T4, TOrigElem, TPlugin>
	{

		public AggregateDef4Impl(IPipelineContext ctxt)
		{
			super(ctxt);
		}

		@Override
		public AggregateDef5<T1, T2, T3, T4, Long, TOrigElem, TPlugin> count()
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.count, null);
			return new AggregateDef5Impl<T1, T2, T3, T4, Long, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public AggregateDef5<T1, T2, T3, T4, TOrigElem, TOrigElem, TPlugin> sum()
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.sum, null);
			return new AggregateDef5Impl<T1, T2, T3, T4, TOrigElem, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public AggregateDef5<T1, T2, T3, T4, TOrigElem, TOrigElem, TPlugin> min()
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.min, null);
			return new AggregateDef5Impl<T1, T2, T3, T4, TOrigElem, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public AggregateDef5<T1, T2, T3, T4, TOrigElem, TOrigElem, TPlugin> max()
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.max, null);
			return new AggregateDef5Impl<T1, T2, T3, T4, TOrigElem, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef5<T1, T2, T3, T4, R, TOrigElem, TPlugin> sumOf(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.sum,
				TPropertyImpl.forTypedName(propName, cls));
			return new AggregateDef5Impl<T1, T2, T3, T4, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef5<T1, T2, T3, T4, R, TOrigElem, TPlugin> sumOf(TProperty<R> tp)
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.sum, tp);
			return new AggregateDef5Impl<T1, T2, T3, T4, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef5<T1, T2, T3, T4, R, TOrigElem, TPlugin> minOf(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.min,
				TPropertyImpl.forTypedName(propName, cls));
			return new AggregateDef5Impl<T1, T2, T3, T4, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef5<T1, T2, T3, T4, R, TOrigElem, TPlugin> minOf(TProperty<R> tp)
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.min, tp);
			return new AggregateDef5Impl<T1, T2, T3, T4, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef5<T1, T2, T3, T4, R, TOrigElem, TPlugin> maxOf(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.max,
				TPropertyImpl.forTypedName(propName, cls));
			return new AggregateDef5Impl<T1, T2, T3, T4, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef5<T1, T2, T3, T4, R, TOrigElem, TPlugin> maxOf(TProperty<R> tp)
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.max, tp);
			return new AggregateDef5Impl<T1, T2, T3, T4, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef5<T1, T2, T3, T4, R, TOrigElem, TPlugin> with(Class<R> cls, Aggregator<TOrigElem, R> r)
		{
			getCtxt().getPipelineDef().addAggregator(r);
			return new AggregateDef5Impl<T1, T2, T3, T4, R, TOrigElem, TPlugin>(getCtxt());
		}

	}

	public static class AggregateDef5Impl<T1, T2, T3, T4, T5, TOrigElem, TPlugin>
		extends AggregateDefSupportImpl<Tuple5<T1, T2, T3, T4, T5>, TPlugin>
		implements AggregateDef5<T1, T2, T3, T4, T5, TOrigElem, TPlugin>
	{

		public AggregateDef5Impl(IPipelineContext ctxt)
		{
			super(ctxt);
		}

		@Override
		public AggregateDef1<TupleN<Tuple5<T1, T2, T3, T4, T5>, Long>, TOrigElem, TPlugin> count()
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.count, null);
			return new AggregateDef1Impl<TupleN<Tuple5<T1, T2, T3, T4, T5>, Long>, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public AggregateDef1<TupleN<Tuple5<T1, T2, T3, T4, T5>, TOrigElem>, TOrigElem, TPlugin> sum()
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.sum, null);
			return new AggregateDef1Impl<TupleN<Tuple5<T1, T2, T3, T4, T5>, TOrigElem>, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public AggregateDef1<TupleN<Tuple5<T1, T2, T3, T4, T5>, TOrigElem>, TOrigElem, TPlugin> min()
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.min, null);
			return new AggregateDef1Impl<TupleN<Tuple5<T1, T2, T3, T4, T5>, TOrigElem>, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public AggregateDef1<TupleN<Tuple5<T1, T2, T3, T4, T5>, TOrigElem>, TOrigElem, TPlugin> max()
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.max, null);
			return new AggregateDef1Impl<TupleN<Tuple5<T1, T2, T3, T4, T5>, TOrigElem>, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef1<TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, TOrigElem, TPlugin> sumOf(String propName,
																									Class<R> cls)
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.sum,
				TPropertyImpl.forTypedName(propName, cls));
			return new AggregateDef1Impl<TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef1<TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, TOrigElem, TPlugin> sumOf(TProperty<R> tp)
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.sum, tp);
			return new AggregateDef1Impl<TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef1<TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, TOrigElem, TPlugin> minOf(String propName,
																									Class<R> cls)
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.min,
				TPropertyImpl.forTypedName(propName, cls));
			return new AggregateDef1Impl<TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef1<TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, TOrigElem, TPlugin> minOf(TProperty<R> tp)
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.min, tp);
			return new AggregateDef1Impl<TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef1<TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, TOrigElem, TPlugin> maxOf(String propName,
																									Class<R> cls)
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.max,
				TPropertyImpl.forTypedName(propName, cls));
			return new AggregateDef1Impl<TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef1<TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, TOrigElem, TPlugin> maxOf(TProperty<R> tp)
		{
			getCtxt().getPipelineDef().addPropertyAggregator(EAggregatorType.max, tp);
			return new AggregateDef1Impl<TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> AggregateDef1<TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, TOrigElem, TPlugin> with(	Class<R> cls,
																									Aggregator<TOrigElem, R> r)
		{
			getCtxt().getPipelineDef().addAggregator(r);
			return new AggregateDef1Impl<TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, TOrigElem, TPlugin>(getCtxt());
		}

	}

}
