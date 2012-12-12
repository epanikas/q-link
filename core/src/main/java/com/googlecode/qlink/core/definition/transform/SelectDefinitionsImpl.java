package com.googlecode.qlink.core.definition.transform;

import java.util.List;

import com.googlecode.qlink.api.behavior.CanVisit;
import com.googlecode.qlink.api.behavior.DoResultAsList;
import com.googlecode.qlink.api.definition.aggregate.AggregateDefinitions.StartAggregateDef;
import com.googlecode.qlink.api.definition.aggregate.FoldDef;
import com.googlecode.qlink.api.definition.aggregate.ReduceDef;
import com.googlecode.qlink.api.definition.filter.FilterDef;
import com.googlecode.qlink.api.definition.group.StartGroupByDef;
import com.googlecode.qlink.api.definition.group.StartIndexByDef;
import com.googlecode.qlink.api.definition.order.StartOrderDef;
import com.googlecode.qlink.api.definition.transform.FinishSelectDef;
import com.googlecode.qlink.api.definition.transform.SelectDefinitions.SelectDef1;
import com.googlecode.qlink.api.definition.transform.SelectDefinitions.SelectDef2;
import com.googlecode.qlink.api.definition.transform.SelectDefinitions.SelectDef3;
import com.googlecode.qlink.api.definition.transform.SelectDefinitions.SelectDef4;
import com.googlecode.qlink.api.definition.transform.SelectDefinitions.SelectDef5;
import com.googlecode.qlink.api.definition.transform.SelectDefinitions.SelectDefSupport;
import com.googlecode.qlink.api.definition.transform.SelectDefinitions.StartSelectDef;
import com.googlecode.qlink.api.definition.visit.StartVisitDef;
import com.googlecode.qlink.api.functor.Function;
import com.googlecode.qlink.api.functor.Function2;
import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.api.tuple.Tuple3;
import com.googlecode.qlink.api.tuple.Tuple4;
import com.googlecode.qlink.api.tuple.Tuple5;
import com.googlecode.qlink.api.tuple.TupleN;
import com.googlecode.qlink.core.behavior.CanAggregateImpl;
import com.googlecode.qlink.core.behavior.CanFilterImpl;
import com.googlecode.qlink.core.behavior.CanIndexImpl;
import com.googlecode.qlink.core.behavior.CanOrderImpl;
import com.googlecode.qlink.core.behavior.CanVisitImpl;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.PipelineContextAwareSupport;
import com.googlecode.qlink.core.context.TPropertyImpl;
import com.googlecode.qlink.core.context.blocks.TransformBlock;
import com.googlecode.qlink.core.context.enums.ETransformResultType;
import com.googlecode.qlink.core.definition.aggregate.AggregateDefinitionsImpl.StartAggregateDefImpl;

public class SelectDefinitionsImpl
{

	public static class SelectDefSupportImpl<TRes, TPlugin>
		extends PipelineContextAwareSupport
		implements SelectDefSupport<TRes, TPlugin>
	{

		private final DoResultAsList<TRes, TPlugin> doResult;
		private final CanFilterImpl<TRes, TPlugin> canFilter;
		private final CanOrderImpl<TRes, TPlugin> canOrder;
		private final CanIndexImpl<TRes, TPlugin> canIndex;
		private final CanAggregateImpl<TRes, TPlugin> canAggregate;
		private final CanVisit<TRes, TPlugin> canVisit;

		@SuppressWarnings("unchecked")
		protected SelectDefSupportImpl(IPipelineContext ctxt)
		{
			super(ctxt);
			doResult = getCtxt().getFactory().create(DoResultAsList.class);
			canFilter = new CanFilterImpl<TRes, TPlugin>(ctxt);
			canOrder = new CanOrderImpl<TRes, TPlugin>(ctxt);
			canIndex = new CanIndexImpl<TRes, TPlugin>(ctxt);
			canAggregate = new CanAggregateImpl<TRes, TPlugin>(ctxt);
			canVisit = new CanVisitImpl<TRes, TPlugin>(ctxt);
		}

		@Override
		public StartIndexByDef<TRes, TPlugin> index()
		{
			return canIndex.index();
		}

		@Override
		public StartGroupByDef<TRes, TPlugin> group()
		{
			return canIndex.group();
		}

		@Override
		public <TNewClass> FinishSelectDef<TNewClass, TPlugin> asNew(Class<TNewClass> cls)
		{
			getCtxt().getPipelineDef().setTransformResultType(ETransformResultType.newObject);
			getCtxt().getPipelineDef().setTransformResultClass(cls);
			return new FinishSelectDefImpl<TNewClass, TPlugin>(getCtxt());
		}

		@Override
		public FinishSelectDef<Object[], TPlugin> asArray()
		{
			getCtxt().getPipelineDef().setTransformResultType(ETransformResultType.arrayObject);
			return new FinishSelectDefImpl<Object[], TPlugin>(getCtxt());
		}

		@Override
		public StartAggregateDef<TRes, TPlugin> aggregate()
		{
			return new StartAggregateDefImpl<TRes, TPlugin>(getCtxt());
		}

		@Override
		public ReduceDef<TRes, TPlugin> reduce()
		{
			return canAggregate.reduce();
		}

		@Override
		public FoldDef<TRes, TPlugin> foldLeft()
		{
			return canAggregate.foldLeft();
		}

		@Override
		public FoldDef<TRes, TPlugin> foldRight()
		{
			return canAggregate.foldRight();
		}

		@Override
		public StartVisitDef<TRes, TPlugin> visit()
		{
			return canVisit.visit();
		}

		@Override
		public FilterDef<TRes, TPlugin> filter()
		{
			return canFilter.filter();
		}

		@Override
		public StartOrderDef<TRes, TPlugin> order()
		{
			return canOrder.order();
		}

		@Override
		public List<TRes> toList()
		{
			return doResult.toList();
		}

		@Override
		public TRes toUniqueResult()
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

	public static class StartSelectDefImpl<T, TPlugin>
		extends PipelineContextAwareSupport
		implements StartSelectDef<T, TPlugin>
	{

		public StartSelectDefImpl(IPipelineContext ctxt)
		{
			super(ctxt);
		}

		@Override
		public SelectDef1<T, T, TPlugin> self()
		{
			getCtxt().getPipelineDef().pushToTransformStack(TransformBlock.forSelf());
			return new SelectDef1Impl<T, T, TPlugin>(getCtxt());
		}

		@Override
		public SelectDef1<Integer, T, TPlugin> elemIndex()
		{
			getCtxt().getPipelineDef().pushToTransformStack(TransformBlock.forElemIndex());
			return new SelectDef1Impl<Integer, T, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectDef1<R, T, TPlugin> with(Function<T, R> f)
		{
			getCtxt().getPipelineDef().pushToTransformStack(TransformBlock.forFunction(f));
			return new SelectDef1Impl<R, T, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectDef1<R, T, TPlugin> with(Function2<T, Integer, R> f)
		{
			getCtxt().getPipelineDef().pushToTransformStack(TransformBlock.forFunction2(f));
			return new SelectDef1Impl<R, T, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectDef1<R, T, TPlugin> p(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().pushToTransformStack(
				TransformBlock.forProperty(TPropertyImpl.forTypedName(propName, cls)));
			return new SelectDef1Impl<R, T, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectDef1<R, T, TPlugin> p(TProperty<R> tp)
		{
			getCtxt().getPipelineDef().pushToTransformStack(TransformBlock.forProperty(tp));
			return new SelectDef1Impl<R, T, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectDef1<R, T, TPlugin> p(String propName)
		{
			getCtxt().getPipelineDef()
				.pushToTransformStack(TransformBlock.forProperty(TPropertyImpl.forName(propName)));
			return new SelectDef1Impl<R, T, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectDef1<R, T, TPlugin> val(R constant)
		{
			getCtxt().getPipelineDef().pushToTransformStack(TransformBlock.forConstant(constant));
			return new SelectDef1Impl<R, T, TPlugin>(getCtxt());
		}

	}

	public static class SelectDef1Impl<T1, TOrigElem, TPlugin>
		extends SelectDefSupportImpl<T1, TPlugin>
		implements SelectDef1<T1, TOrigElem, TPlugin>
	{

		public SelectDef1Impl(IPipelineContext ctxt)
		{
			super(ctxt);
		}

		@Override
		public SelectDef2<T1, TOrigElem, TOrigElem, TPlugin> self()
		{
			getCtxt().getPipelineDef().pushToTransformStack(TransformBlock.forSelf());
			return new SelectDef2Impl<T1, TOrigElem, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public SelectDef2<T1, Integer, TOrigElem, TPlugin> elemIndex()
		{
			getCtxt().getPipelineDef().pushToTransformStack(TransformBlock.forElemIndex());
			return new SelectDef2Impl<T1, Integer, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectDef2<T1, R, TOrigElem, TPlugin> with(Function<TOrigElem, R> f)
		{
			getCtxt().getPipelineDef().pushToTransformStack(TransformBlock.forFunction(f));
			return new SelectDef2Impl<T1, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectDef2<T1, R, TOrigElem, TPlugin> with(Function2<TOrigElem, Integer, R> f)
		{
			getCtxt().getPipelineDef().pushToTransformStack(TransformBlock.forFunction2(f));
			return new SelectDef2Impl<T1, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectDef2<T1, R, TOrigElem, TPlugin> p(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().pushToTransformStack(
				TransformBlock.forProperty(TPropertyImpl.forTypedName(propName, cls)));
			return new SelectDef2Impl<T1, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectDef2<T1, R, TOrigElem, TPlugin> p(TProperty<R> tp)
		{
			getCtxt().getPipelineDef().pushToTransformStack(TransformBlock.forProperty(tp));
			return new SelectDef2Impl<T1, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectDef2<T1, R, TOrigElem, TPlugin> p(String propName)
		{
			getCtxt().getPipelineDef()
				.pushToTransformStack(TransformBlock.forProperty(TPropertyImpl.forName(propName)));
			return new SelectDef2Impl<T1, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectDef2<T1, R, TOrigElem, TPlugin> val(R constant)
		{
			getCtxt().getPipelineDef().pushToTransformStack(TransformBlock.forConstant(constant));
			return new SelectDef2Impl<T1, R, TOrigElem, TPlugin>(getCtxt());
		}

	}

	public static class SelectDef2Impl<T1, T2, TOrigElem, TPlugin>
		extends SelectDefSupportImpl<Pair<T1, T2>, TPlugin>
		implements SelectDef2<T1, T2, TOrigElem, TPlugin>
	{

		public SelectDef2Impl(IPipelineContext ctxt)
		{
			super(ctxt);
		}

		@Override
		public SelectDef3<T1, T2, TOrigElem, TOrigElem, TPlugin> self()
		{
			getCtxt().getPipelineDef().pushToTransformStack(TransformBlock.forSelf());
			return new SelectDef3Impl<T1, T2, TOrigElem, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public SelectDef3<T1, T2, Integer, TOrigElem, TPlugin> elemIndex()
		{
			getCtxt().getPipelineDef().pushToTransformStack(TransformBlock.forElemIndex());
			return new SelectDef3Impl<T1, T2, Integer, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectDef3<T1, T2, R, TOrigElem, TPlugin> with(Function<TOrigElem, R> f)
		{
			getCtxt().getPipelineDef().pushToTransformStack(TransformBlock.forFunction(f));
			return new SelectDef3Impl<T1, T2, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectDef3<T1, T2, R, TOrigElem, TPlugin> with(Function2<TOrigElem, Integer, R> f)
		{
			getCtxt().getPipelineDef().pushToTransformStack(TransformBlock.forFunction2(f));
			return new SelectDef3Impl<T1, T2, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectDef3<T1, T2, R, TOrigElem, TPlugin> p(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().pushToTransformStack(
				TransformBlock.forProperty(TPropertyImpl.forTypedName(propName, cls)));
			return new SelectDef3Impl<T1, T2, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectDef3<T1, T2, R, TOrigElem, TPlugin> p(TProperty<R> tp)
		{
			getCtxt().getPipelineDef().pushToTransformStack(TransformBlock.forProperty(tp));
			return new SelectDef3Impl<T1, T2, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectDef3<T1, T2, R, TOrigElem, TPlugin> p(String propName)
		{
			getCtxt().getPipelineDef()
				.pushToTransformStack(TransformBlock.forProperty(TPropertyImpl.forName(propName)));
			return new SelectDef3Impl<T1, T2, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectDef3<T1, T2, R, TOrigElem, TPlugin> val(R constant)
		{
			getCtxt().getPipelineDef().pushToTransformStack(TransformBlock.forConstant(constant));
			return new SelectDef3Impl<T1, T2, R, TOrigElem, TPlugin>(getCtxt());
		}
	}

	public static class SelectDef3Impl<T1, T2, T3, TOrigElem, TPlugin>
		extends SelectDefSupportImpl<Tuple3<T1, T2, T3>, TPlugin>
		implements SelectDef3<T1, T2, T3, TOrigElem, TPlugin>
	{

		public SelectDef3Impl(IPipelineContext ctxt)
		{
			super(ctxt);
		}

		@Override
		public SelectDef4<T1, T2, T3, TOrigElem, TOrigElem, TPlugin> self()
		{
			getCtxt().getPipelineDef().pushToTransformStack(TransformBlock.forSelf());
			return new SelectDef4Impl<T1, T2, T3, TOrigElem, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public SelectDef4<T1, T2, T3, Integer, TOrigElem, TPlugin> elemIndex()
		{
			getCtxt().getPipelineDef().pushToTransformStack(TransformBlock.forElemIndex());
			return new SelectDef4Impl<T1, T2, T3, Integer, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectDef4<T1, T2, T3, R, TOrigElem, TPlugin> with(Function<TOrigElem, R> f)
		{
			getCtxt().getPipelineDef().pushToTransformStack(TransformBlock.forFunction(f));
			return new SelectDef4Impl<T1, T2, T3, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectDef4<T1, T2, T3, R, TOrigElem, TPlugin> with(Function2<TOrigElem, Integer, R> f)
		{
			getCtxt().getPipelineDef().pushToTransformStack(TransformBlock.forFunction2(f));
			return new SelectDef4Impl<T1, T2, T3, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectDef4<T1, T2, T3, R, TOrigElem, TPlugin> p(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().pushToTransformStack(
				TransformBlock.forProperty(TPropertyImpl.forTypedName(propName, cls)));
			return new SelectDef4Impl<T1, T2, T3, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectDef4<T1, T2, T3, R, TOrigElem, TPlugin> p(TProperty<R> tp)
		{
			getCtxt().getPipelineDef().pushToTransformStack(TransformBlock.forProperty(tp));
			return new SelectDef4Impl<T1, T2, T3, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectDef4<T1, T2, T3, R, TOrigElem, TPlugin> p(String propName)
		{
			getCtxt().getPipelineDef()
				.pushToTransformStack(TransformBlock.forProperty(TPropertyImpl.forName(propName)));
			return new SelectDef4Impl<T1, T2, T3, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectDef4<T1, T2, T3, R, TOrigElem, TPlugin> val(R constant)
		{
			getCtxt().getPipelineDef().pushToTransformStack(TransformBlock.forConstant(constant));
			return new SelectDef4Impl<T1, T2, T3, R, TOrigElem, TPlugin>(getCtxt());
		}
	}

	public static class SelectDef4Impl<T1, T2, T3, T4, TOrigElem, TPlugin>
		extends SelectDefSupportImpl<Tuple4<T1, T2, T3, T4>, TPlugin>
		implements SelectDef4<T1, T2, T3, T4, TOrigElem, TPlugin>
	{

		public SelectDef4Impl(IPipelineContext ctxt)
		{
			super(ctxt);
		}

		@Override
		public SelectDef5<T1, T2, T3, T4, TOrigElem, TOrigElem, TPlugin> self()
		{
			getCtxt().getPipelineDef().pushToTransformStack(TransformBlock.forSelf());
			return new SelectDef5Impl<T1, T2, T3, T4, TOrigElem, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public SelectDef5<T1, T2, T3, T4, Integer, TOrigElem, TPlugin> elemIndex()
		{
			getCtxt().getPipelineDef().pushToTransformStack(TransformBlock.forElemIndex());
			return new SelectDef5Impl<T1, T2, T3, T4, Integer, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectDef5<T1, T2, T3, T4, R, TOrigElem, TPlugin> with(Function<TOrigElem, R> f)
		{
			getCtxt().getPipelineDef().pushToTransformStack(TransformBlock.forFunction(f));
			return new SelectDef5Impl<T1, T2, T3, T4, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectDef5<T1, T2, T3, T4, R, TOrigElem, TPlugin> with(Function2<TOrigElem, Integer, R> f)
		{
			getCtxt().getPipelineDef().pushToTransformStack(TransformBlock.forFunction2(f));
			return new SelectDef5Impl<T1, T2, T3, T4, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectDef5<T1, T2, T3, T4, R, TOrigElem, TPlugin> p(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().pushToTransformStack(
				TransformBlock.forProperty(TPropertyImpl.forTypedName(propName, cls)));
			return new SelectDef5Impl<T1, T2, T3, T4, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectDef5<T1, T2, T3, T4, R, TOrigElem, TPlugin> p(TProperty<R> tp)
		{
			getCtxt().getPipelineDef().pushToTransformStack(TransformBlock.forProperty(tp));
			return new SelectDef5Impl<T1, T2, T3, T4, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectDef5<T1, T2, T3, T4, R, TOrigElem, TPlugin> p(String propName)
		{
			getCtxt().getPipelineDef()
				.pushToTransformStack(TransformBlock.forProperty(TPropertyImpl.forName(propName)));
			return new SelectDef5Impl<T1, T2, T3, T4, R, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectDef5<T1, T2, T3, T4, R, TOrigElem, TPlugin> val(R constant)
		{
			getCtxt().getPipelineDef().pushToTransformStack(TransformBlock.forConstant(constant));
			return new SelectDef5Impl<T1, T2, T3, T4, R, TOrigElem, TPlugin>(getCtxt());
		}
	}

	public static class SelectDef5Impl<T1, T2, T3, T4, T5, TOrigElem, TPlugin>
		extends SelectDefSupportImpl<Tuple5<T1, T2, T3, T4, T5>, TPlugin>
		implements SelectDef5<T1, T2, T3, T4, T5, TOrigElem, TPlugin>
	{

		public SelectDef5Impl(IPipelineContext ctxt)
		{
			super(ctxt);
		}

		@Override
		public SelectDef1<TupleN<Tuple5<T1, T2, T3, T4, T5>, TOrigElem>, TOrigElem, TPlugin> self()
		{
			getCtxt().getPipelineDef().pushToTransformStack(TransformBlock.forSelf());
			return new SelectDef1Impl<TupleN<Tuple5<T1, T2, T3, T4, T5>, TOrigElem>, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public SelectDef1<TupleN<Tuple5<T1, T2, T3, T4, T5>, Integer>, TOrigElem, TPlugin> elemIndex()
		{
			getCtxt().getPipelineDef().pushToTransformStack(TransformBlock.forElemIndex());
			return new SelectDef1Impl<TupleN<Tuple5<T1, T2, T3, T4, T5>, Integer>, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectDef1<TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, TOrigElem, TPlugin> with(Function<TOrigElem, R> f)
		{
			getCtxt().getPipelineDef().pushToTransformStack(TransformBlock.forFunction(f));
			return new SelectDef1Impl<TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectDef1<TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, TOrigElem, TPlugin> with(	Function2<TOrigElem, Integer, R> f)
		{
			getCtxt().getPipelineDef().pushToTransformStack(TransformBlock.forFunction2(f));
			return new SelectDef1Impl<TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectDef1<TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, TOrigElem, TPlugin> p(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().pushToTransformStack(
				TransformBlock.forProperty(TPropertyImpl.forTypedName(propName, cls)));
			return new SelectDef1Impl<TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectDef1<TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, TOrigElem, TPlugin> p(TProperty<R> tp)
		{
			getCtxt().getPipelineDef().pushToTransformStack(TransformBlock.forProperty(tp));
			return new SelectDef1Impl<TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectDef1<TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, TOrigElem, TPlugin> p(String propName)
		{
			getCtxt().getPipelineDef()
				.pushToTransformStack(TransformBlock.forProperty(TPropertyImpl.forName(propName)));
			return new SelectDef1Impl<TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, TOrigElem, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectDef1<TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, TOrigElem, TPlugin> val(R constant)
		{
			getCtxt().getPipelineDef().pushToTransformStack(TransformBlock.forConstant(constant));
			return new SelectDef1Impl<TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, TOrigElem, TPlugin>(getCtxt());
		}
	}

}