package com.googlecode.qlink.core.definition.selectgroup;

import java.util.List;
import java.util.Map;

import com.googlecode.qlink.api.behavior.DoResultAsMap;
import com.googlecode.qlink.api.definition.selectgroup.SelectGroupDefinitions.SelectGroupDef1;
import com.googlecode.qlink.api.definition.selectgroup.SelectGroupDefinitions.SelectGroupDef2;
import com.googlecode.qlink.api.definition.selectgroup.SelectGroupDefinitions.SelectGroupDef3;
import com.googlecode.qlink.api.definition.selectgroup.SelectGroupDefinitions.SelectGroupDef4;
import com.googlecode.qlink.api.definition.selectgroup.SelectGroupDefinitions.SelectGroupDef5;
import com.googlecode.qlink.api.definition.selectgroup.SelectGroupDefinitions.SelectGroupDefSupport;
import com.googlecode.qlink.api.definition.selectgroup.SelectGroupDefinitions.StartSelectGroupDef;
import com.googlecode.qlink.api.functor.Function;
import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.api.tuple.Tuple3;
import com.googlecode.qlink.api.tuple.Tuple4;
import com.googlecode.qlink.api.tuple.Tuple5;
import com.googlecode.qlink.api.tuple.TupleN;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.PipelineContextAwareSupport;
import com.googlecode.qlink.core.context.TPropertyImpl;
import com.googlecode.qlink.core.context.blocks.TransformBlock;
import com.googlecode.qlink.core.context.enums.EAggregatorType;
import com.googlecode.qlink.core.context.enums.ETransformResultType;

public class SelectGroupDefinitionsImpl
{

	public static class SelectGroupDefSupportImpl<K, V, TPlugin>
		extends PipelineContextAwareSupport
		implements SelectGroupDefSupport<K, V, TPlugin>
	{

		private final DoResultAsMap<K, V, TPlugin> doResult;

		@SuppressWarnings("unchecked")
		public SelectGroupDefSupportImpl(IPipelineContext ctxt)
		{
			super(ctxt);
			doResult = getCtxt().getFactory().create(DoResultAsMap.class);
		}

		@Override
		public Map<K, V> toMap()
		{
			return doResult.toMap();
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

		@SuppressWarnings("unchecked")
		@Override
		public <TRes> DoResultAsMap<K, TRes, TPlugin> asNew(Class<TRes> cls)
		{
			getCtxt().getPipelineDef().setSelectGroupResultType(ETransformResultType.newObject);
			getCtxt().getPipelineDef().setSelectGroupResultClass(cls);
			return (DoResultAsMap<K, TRes, TPlugin>) doResult;
		}

		@SuppressWarnings("unchecked")
		@Override
		public DoResultAsMap<K, Object[], TPlugin> asArray()
		{
			getCtxt().getPipelineDef().setSelectGroupResultType(ETransformResultType.arrayObject);
			getCtxt().getPipelineDef().setSelectGroupResultClass(Object[].class);
			return (DoResultAsMap<K, Object[], TPlugin>) doResult;
		}
	}

	public static class StartSelectGroupDefImpl<K, V extends List<?>, TPlugin>
		extends PipelineContextAwareSupport
		implements StartSelectGroupDef<K, V, TPlugin>
	{

		public StartSelectGroupDefImpl(IPipelineContext ctxt)
		{
			super(ctxt);
		}

		@Override
		public SelectGroupDef1<K, Long, V, TPlugin> count()
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(
				TransformBlock.forAggregatorType(EAggregatorType.count, null));
			return new SelectGroupDef1Impl<K, Long, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef1<K, R, V, TPlugin> sumOf(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(
				TransformBlock.forAggregatorType(EAggregatorType.sum, TPropertyImpl.forTypedName(propName, cls)));
			return new SelectGroupDef1Impl<K, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef1<K, R, V, TPlugin> sumOf(TProperty<R> tp)
		{
			getCtxt().getPipelineDef()
				.pushToSelectGroupStack(TransformBlock.forAggregatorType(EAggregatorType.sum, tp));
			return new SelectGroupDef1Impl<K, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef1<K, R, V, TPlugin> minOf(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(
				TransformBlock.forAggregatorType(EAggregatorType.min, TPropertyImpl.forTypedName(propName, cls)));
			return new SelectGroupDef1Impl<K, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef1<K, R, V, TPlugin> minOf(TProperty<R> tp)
		{
			getCtxt().getPipelineDef()
				.pushToSelectGroupStack(TransformBlock.forAggregatorType(EAggregatorType.min, tp));
			return new SelectGroupDef1Impl<K, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef1<K, R, V, TPlugin> maxOf(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(
				TransformBlock.forAggregatorType(EAggregatorType.max, TPropertyImpl.forTypedName(propName, cls)));
			return new SelectGroupDef1Impl<K, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef1<K, R, V, TPlugin> maxOf(TProperty<R> tp)
		{
			getCtxt().getPipelineDef()
				.pushToSelectGroupStack(TransformBlock.forAggregatorType(EAggregatorType.max, tp));
			return new SelectGroupDef1Impl<K, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef1<K, R, V, TPlugin> keyField(String propName, Class<R> propCls)
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(
				TransformBlock.forKeyField(TPropertyImpl.forTypedName(propName, propCls)));
			return new SelectGroupDef1Impl<K, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef1<K, R, V, TPlugin> withTransformer(Function<Pair<K, V>, R> f)
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(TransformBlock.forFunction(f));
			return new SelectGroupDef1Impl<K, R, V, TPlugin>(getCtxt());
		}

		@Override
		public SelectGroupDef1<K, K, V, TPlugin> key()
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(TransformBlock.forKey());
			return new SelectGroupDef1Impl<K, K, V, TPlugin>(getCtxt());
		}

		@Override
		public SelectGroupDef1<K, V, V, TPlugin> value()
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(TransformBlock.forValue());
			return new SelectGroupDef1Impl<K, V, V, TPlugin>(getCtxt());
		}

	}

	public static class SelectGroupDef1Impl<K, T, V extends List<?>, TPlugin>
		extends SelectGroupDefSupportImpl<K, T, TPlugin>
		implements SelectGroupDef1<K, T, V, TPlugin>
	{

		public SelectGroupDef1Impl(IPipelineContext ctxt)
		{
			super(ctxt);
		}

		@Override
		public SelectGroupDef2<K, T, Long, V, TPlugin> count()
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(
				TransformBlock.forAggregatorType(EAggregatorType.count, null));
			return new SelectGroupDef2Impl<K, T, Long, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef2<K, T, R, V, TPlugin> sumOf(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(
				TransformBlock.forAggregatorType(EAggregatorType.sum, TPropertyImpl.forTypedName(propName, cls)));
			return new SelectGroupDef2Impl<K, T, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef2<K, T, R, V, TPlugin> sumOf(TProperty<R> tp)
		{
			getCtxt().getPipelineDef()
				.pushToSelectGroupStack(TransformBlock.forAggregatorType(EAggregatorType.sum, tp));
			return new SelectGroupDef2Impl<K, T, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef2<K, T, R, V, TPlugin> minOf(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(
				TransformBlock.forAggregatorType(EAggregatorType.min, TPropertyImpl.forTypedName(propName, cls)));
			return new SelectGroupDef2Impl<K, T, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef2<K, T, R, V, TPlugin> minOf(TProperty<R> tp)
		{
			getCtxt().getPipelineDef()
				.pushToSelectGroupStack(TransformBlock.forAggregatorType(EAggregatorType.min, tp));
			return new SelectGroupDef2Impl<K, T, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef2<K, T, R, V, TPlugin> maxOf(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(
				TransformBlock.forAggregatorType(EAggregatorType.max, TPropertyImpl.forTypedName(propName, cls)));
			return new SelectGroupDef2Impl<K, T, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef2<K, T, R, V, TPlugin> maxOf(TProperty<R> tp)
		{
			getCtxt().getPipelineDef()
				.pushToSelectGroupStack(TransformBlock.forAggregatorType(EAggregatorType.max, tp));
			return new SelectGroupDef2Impl<K, T, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef2<K, T, R, V, TPlugin> keyField(String propName, Class<R> propCls)
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(
				TransformBlock.forKeyField(TPropertyImpl.forTypedName(propName, propCls)));
			return new SelectGroupDef2Impl<K, T, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef2<K, T, R, V, TPlugin> withTransformer(Function<Pair<K, V>, R> f)
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(TransformBlock.forFunction(f));
			return new SelectGroupDef2Impl<K, T, R, V, TPlugin>(getCtxt());
		}

		@Override
		public SelectGroupDef2<K, T, K, V, TPlugin> key()
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(TransformBlock.forKey());
			return new SelectGroupDef2Impl<K, T, K, V, TPlugin>(getCtxt());
		}

		@Override
		public SelectGroupDef2<K, T, V, V, TPlugin> value()
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(TransformBlock.forValue());
			return new SelectGroupDef2Impl<K, T, V, V, TPlugin>(getCtxt());
		}

	}

	public static class SelectGroupDef2Impl<K, T1, T2, V extends List<?>, TPlugin>
		extends SelectGroupDefSupportImpl<K, Pair<T1, T2>, TPlugin>
		implements SelectGroupDef2<K, T1, T2, V, TPlugin>
	{

		public SelectGroupDef2Impl(IPipelineContext ctxt)
		{
			super(ctxt);
		}

		@Override
		public SelectGroupDef3<K, T1, T2, Long, V, TPlugin> count()
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(
				TransformBlock.forAggregatorType(EAggregatorType.count, null));
			return new SelectGroupDef3Impl<K, T1, T2, Long, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef3<K, T1, T2, R, V, TPlugin> sumOf(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(
				TransformBlock.forAggregatorType(EAggregatorType.sum, TPropertyImpl.forTypedName(propName, cls)));
			return new SelectGroupDef3Impl<K, T1, T2, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef3<K, T1, T2, R, V, TPlugin> sumOf(TProperty<R> tp)
		{
			getCtxt().getPipelineDef()
				.pushToSelectGroupStack(TransformBlock.forAggregatorType(EAggregatorType.sum, tp));
			return new SelectGroupDef3Impl<K, T1, T2, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef3<K, T1, T2, R, V, TPlugin> minOf(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(
				TransformBlock.forAggregatorType(EAggregatorType.min, TPropertyImpl.forTypedName(propName, cls)));
			return new SelectGroupDef3Impl<K, T1, T2, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef3<K, T1, T2, R, V, TPlugin> minOf(TProperty<R> tp)
		{
			getCtxt().getPipelineDef()
				.pushToSelectGroupStack(TransformBlock.forAggregatorType(EAggregatorType.min, tp));
			return new SelectGroupDef3Impl<K, T1, T2, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef3<K, T1, T2, R, V, TPlugin> maxOf(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(
				TransformBlock.forAggregatorType(EAggregatorType.max, TPropertyImpl.forTypedName(propName, cls)));
			return new SelectGroupDef3Impl<K, T1, T2, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef3<K, T1, T2, R, V, TPlugin> maxOf(TProperty<R> tp)
		{
			getCtxt().getPipelineDef()
				.pushToSelectGroupStack(TransformBlock.forAggregatorType(EAggregatorType.min, tp));
			return new SelectGroupDef3Impl<K, T1, T2, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef3<K, T1, T2, R, V, TPlugin> keyField(String propName, Class<R> propCls)
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(
				TransformBlock.forKeyField(TPropertyImpl.forTypedName(propName, propCls)));
			return new SelectGroupDef3Impl<K, T1, T2, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef3<K, T1, T2, R, V, TPlugin> withTransformer(Function<Pair<K, V>, R> f)
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(TransformBlock.forFunction(f));
			return new SelectGroupDef3Impl<K, T1, T2, R, V, TPlugin>(getCtxt());
		}

		@Override
		public SelectGroupDef3<K, T1, T2, K, V, TPlugin> key()
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(TransformBlock.forKey());
			return new SelectGroupDef3Impl<K, T1, T2, K, V, TPlugin>(getCtxt());
		}

		@Override
		public SelectGroupDef3<K, T1, T2, V, V, TPlugin> value()
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(TransformBlock.forValue());
			return new SelectGroupDef3Impl<K, T1, T2, V, V, TPlugin>(getCtxt());
		}

	}

	public static class SelectGroupDef3Impl<K, T1, T2, T3, V extends List<?>, TPlugin>
		extends SelectGroupDefSupportImpl<K, Tuple3<T1, T2, T3>, TPlugin>
		implements SelectGroupDef3<K, T1, T2, T3, V, TPlugin>
	{

		public SelectGroupDef3Impl(IPipelineContext ctxt)
		{
			super(ctxt);
		}

		@Override
		public SelectGroupDef4<K, T1, T2, T3, Long, V, TPlugin> count()
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(
				TransformBlock.forAggregatorType(EAggregatorType.count, null));
			return new SelectGroupDef4Impl<K, T1, T2, T3, Long, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef4<K, T1, T2, T3, R, V, TPlugin> sumOf(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(
				TransformBlock.forAggregatorType(EAggregatorType.sum, TPropertyImpl.forTypedName(propName, cls)));
			return new SelectGroupDef4Impl<K, T1, T2, T3, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef4<K, T1, T2, T3, R, V, TPlugin> sumOf(TProperty<R> tp)
		{
			getCtxt().getPipelineDef()
				.pushToSelectGroupStack(TransformBlock.forAggregatorType(EAggregatorType.sum, tp));
			return new SelectGroupDef4Impl<K, T1, T2, T3, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef4<K, T1, T2, T3, R, V, TPlugin> minOf(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(
				TransformBlock.forAggregatorType(EAggregatorType.min, TPropertyImpl.forTypedName(propName, cls)));
			return new SelectGroupDef4Impl<K, T1, T2, T3, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef4<K, T1, T2, T3, R, V, TPlugin> minOf(TProperty<R> tp)
		{
			getCtxt().getPipelineDef()
				.pushToSelectGroupStack(TransformBlock.forAggregatorType(EAggregatorType.min, tp));
			return new SelectGroupDef4Impl<K, T1, T2, T3, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef4<K, T1, T2, T3, R, V, TPlugin> maxOf(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(
				TransformBlock.forAggregatorType(EAggregatorType.max, TPropertyImpl.forTypedName(propName, cls)));
			return new SelectGroupDef4Impl<K, T1, T2, T3, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef4<K, T1, T2, T3, R, V, TPlugin> maxOf(TProperty<R> tp)
		{
			getCtxt().getPipelineDef()
				.pushToSelectGroupStack(TransformBlock.forAggregatorType(EAggregatorType.max, tp));
			return new SelectGroupDef4Impl<K, T1, T2, T3, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef4<K, T1, T2, T3, R, V, TPlugin> keyField(String propName, Class<R> propCls)
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(
				TransformBlock.forKeyField(TPropertyImpl.forTypedName(propName, propCls)));
			return new SelectGroupDef4Impl<K, T1, T2, T3, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef4<K, T1, T2, T3, R, V, TPlugin> withTransformer(Function<Pair<K, V>, R> f)
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(TransformBlock.forFunction(f));
			return new SelectGroupDef4Impl<K, T1, T2, T3, R, V, TPlugin>(getCtxt());
		}

		@Override
		public SelectGroupDef4<K, T1, T2, T3, K, V, TPlugin> key()
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(TransformBlock.forKey());
			return new SelectGroupDef4Impl<K, T1, T2, T3, K, V, TPlugin>(getCtxt());
		}

		@Override
		public SelectGroupDef4<K, T1, T2, T3, V, V, TPlugin> value()
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(TransformBlock.forValue());
			return new SelectGroupDef4Impl<K, T1, T2, T3, V, V, TPlugin>(getCtxt());
		}

	}

	public static class SelectGroupDef4Impl<K, T1, T2, T3, T4, V extends List<?>, TPlugin>
		extends SelectGroupDefSupportImpl<K, Tuple4<T1, T2, T3, T4>, TPlugin>
		implements SelectGroupDef4<K, T1, T2, T3, T4, V, TPlugin>
	{

		public SelectGroupDef4Impl(IPipelineContext ctxt)
		{
			super(ctxt);
		}

		@Override
		public SelectGroupDef5<K, T1, T2, T3, T4, Long, V, TPlugin> count()
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(
				TransformBlock.forAggregatorType(EAggregatorType.count, null));
			return new SelectGroupDef5Impl<K, T1, T2, T3, T4, Long, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef5<K, T1, T2, T3, T4, R, V, TPlugin> sumOf(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(
				TransformBlock.forAggregatorType(EAggregatorType.sum, TPropertyImpl.forTypedName(propName, cls)));
			return new SelectGroupDef5Impl<K, T1, T2, T3, T4, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef5<K, T1, T2, T3, T4, R, V, TPlugin> sumOf(TProperty<R> tp)
		{
			getCtxt().getPipelineDef()
				.pushToSelectGroupStack(TransformBlock.forAggregatorType(EAggregatorType.sum, tp));
			return new SelectGroupDef5Impl<K, T1, T2, T3, T4, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef5<K, T1, T2, T3, T4, R, V, TPlugin> minOf(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(
				TransformBlock.forAggregatorType(EAggregatorType.min, TPropertyImpl.forTypedName(propName, cls)));
			return new SelectGroupDef5Impl<K, T1, T2, T3, T4, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef5<K, T1, T2, T3, T4, R, V, TPlugin> minOf(TProperty<R> tp)
		{
			getCtxt().getPipelineDef()
				.pushToSelectGroupStack(TransformBlock.forAggregatorType(EAggregatorType.min, tp));
			return new SelectGroupDef5Impl<K, T1, T2, T3, T4, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef5<K, T1, T2, T3, T4, R, V, TPlugin> maxOf(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(
				TransformBlock.forAggregatorType(EAggregatorType.max, TPropertyImpl.forTypedName(propName, cls)));
			return new SelectGroupDef5Impl<K, T1, T2, T3, T4, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef5<K, T1, T2, T3, T4, R, V, TPlugin> maxOf(TProperty<R> tp)
		{
			getCtxt().getPipelineDef()
				.pushToSelectGroupStack(TransformBlock.forAggregatorType(EAggregatorType.max, tp));
			return new SelectGroupDef5Impl<K, T1, T2, T3, T4, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef5<K, T1, T2, T3, T4, R, V, TPlugin> keyField(String propName, Class<R> propCls)
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(
				TransformBlock.forKeyField(TPropertyImpl.forTypedName(propName, propCls)));
			return new SelectGroupDef5Impl<K, T1, T2, T3, T4, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef5<K, T1, T2, T3, T4, R, V, TPlugin> withTransformer(Function<Pair<K, V>, R> f)
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(TransformBlock.forFunction(f));
			return new SelectGroupDef5Impl<K, T1, T2, T3, T4, R, V, TPlugin>(getCtxt());
		}

		@Override
		public SelectGroupDef5<K, T1, T2, T3, T4, K, V, TPlugin> key()
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(TransformBlock.forKey());
			return new SelectGroupDef5Impl<K, T1, T2, T3, T4, K, V, TPlugin>(getCtxt());
		}

		@Override
		public SelectGroupDef5<K, T1, T2, T3, T4, V, V, TPlugin> value()
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(TransformBlock.forValue());
			return new SelectGroupDef5Impl<K, T1, T2, T3, T4, V, V, TPlugin>(getCtxt());
		}

	}

	public static class SelectGroupDef5Impl<K, T1, T2, T3, T4, T5, V extends List<?>, TPlugin>
		extends SelectGroupDefSupportImpl<K, Tuple5<T1, T2, T3, T4, T5>, TPlugin>
		implements SelectGroupDef5<K, T1, T2, T3, T4, T5, V, TPlugin>
	{

		public SelectGroupDef5Impl(IPipelineContext ctxt)
		{
			super(ctxt);
		}

		@Override
		public SelectGroupDef1<K, TupleN<Tuple5<T1, T2, T3, T4, T5>, Long>, V, TPlugin> count()
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(
				TransformBlock.forAggregatorType(EAggregatorType.count, null));
			return new SelectGroupDef1Impl<K, TupleN<Tuple5<T1, T2, T3, T4, T5>, Long>, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef1<K, TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, V, TPlugin> sumOf(String propName,
																								Class<R> cls)
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(
				TransformBlock.forAggregatorType(EAggregatorType.sum, TPropertyImpl.forTypedName(propName, cls)));
			return new SelectGroupDef1Impl<K, TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef1<K, TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, V, TPlugin> sumOf(TProperty<R> tp)
		{
			getCtxt().getPipelineDef()
				.pushToSelectGroupStack(TransformBlock.forAggregatorType(EAggregatorType.sum, tp));
			return new SelectGroupDef1Impl<K, TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef1<K, TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, V, TPlugin> minOf(String propName,
																								Class<R> cls)
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(
				TransformBlock.forAggregatorType(EAggregatorType.min, TPropertyImpl.forTypedName(propName, cls)));
			return new SelectGroupDef1Impl<K, TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef1<K, TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, V, TPlugin> minOf(TProperty<R> tp)
		{
			getCtxt().getPipelineDef()
				.pushToSelectGroupStack(TransformBlock.forAggregatorType(EAggregatorType.min, tp));
			return new SelectGroupDef1Impl<K, TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef1<K, TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, V, TPlugin> maxOf(String propName,
																								Class<R> cls)
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(
				TransformBlock.forAggregatorType(EAggregatorType.max, TPropertyImpl.forTypedName(propName, cls)));
			return new SelectGroupDef1Impl<K, TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef1<K, TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, V, TPlugin> maxOf(TProperty<R> tp)
		{
			getCtxt().getPipelineDef()
				.pushToSelectGroupStack(TransformBlock.forAggregatorType(EAggregatorType.max, tp));
			return new SelectGroupDef1Impl<K, TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef1<K, TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, V, TPlugin> keyField(String propName,
																									Class<R> propCls)
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(
				TransformBlock.forKeyField(TPropertyImpl.forTypedName(propName, propCls)));
			return new SelectGroupDef1Impl<K, TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> SelectGroupDef1<K, TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, V, TPlugin> withTransformer(	Function<Pair<K, V>, R> f)
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(TransformBlock.forFunction(f));
			return new SelectGroupDef1Impl<K, TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, V, TPlugin>(getCtxt());
		}

		@Override
		public SelectGroupDef1<K, TupleN<Tuple5<T1, T2, T3, T4, T5>, K>, V, TPlugin> key()
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(TransformBlock.forKey());
			return new SelectGroupDef1Impl<K, TupleN<Tuple5<T1, T2, T3, T4, T5>, K>, V, TPlugin>(getCtxt());
		}

		@Override
		public SelectGroupDef1<K, TupleN<Tuple5<T1, T2, T3, T4, T5>, V>, V, TPlugin> value()
		{
			getCtxt().getPipelineDef().pushToSelectGroupStack(TransformBlock.forValue());
			return new SelectGroupDef1Impl<K, TupleN<Tuple5<T1, T2, T3, T4, T5>, V>, V, TPlugin>(getCtxt());
		}

	}

}
