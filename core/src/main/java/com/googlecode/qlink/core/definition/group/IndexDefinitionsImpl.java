package com.googlecode.qlink.core.definition.group;

import java.util.Map;

import com.googlecode.qlink.api.behavior.DoResultAsMap;
import com.googlecode.qlink.api.definition.group.IndexDefinitions.IndexByDef;
import com.googlecode.qlink.api.definition.group.IndexDefinitions.IndexByDef2;
import com.googlecode.qlink.api.definition.group.IndexDefinitions.IndexByDef3;
import com.googlecode.qlink.api.definition.group.IndexDefinitions.IndexByDef4;
import com.googlecode.qlink.api.definition.group.IndexDefinitions.IndexByDef5;
import com.googlecode.qlink.api.definition.group.IndexDefinitions.IndexByDefSupport;
import com.googlecode.qlink.api.functor.Function;
import com.googlecode.qlink.api.functor.Function2;
import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.api.tuple.Tuple3;
import com.googlecode.qlink.api.tuple.Tuple4;
import com.googlecode.qlink.api.tuple.Tuple5;
import com.googlecode.qlink.api.tuple.TupleN;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.PipelineContextAwareSupport;
import com.googlecode.qlink.core.context.TPropertyImpl;
import com.googlecode.qlink.core.context.blocks.GroupByBlock;
import com.googlecode.qlink.core.functor.Functions;

public class IndexDefinitionsImpl
{

	private IndexDefinitionsImpl()
	{
		// private ctor
	}

	public static class IndexByDefSupportImpl<K, V, TPlugin>
		extends PipelineContextAwareSupport
		implements IndexByDefSupport<K, V, TPlugin>
	{
		private final DoResultAsMap<K, V, TPlugin> doResult;

		@SuppressWarnings("unchecked")
		public IndexByDefSupportImpl(IPipelineContext ctxt)
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
	}

	public static class IndexByDefImpl<K, V, TPlugin>
		extends IndexByDefSupportImpl<K, V, TPlugin>
		implements IndexByDef<K, V, TPlugin>
	{

		public IndexByDefImpl(IPipelineContext ctxt)
		{
			super(ctxt);
		}

		@Override
		public <R> IndexByDef2<K, R, V, TPlugin> by(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().pushToIndexStack(
				GroupByBlock.forProperty(TPropertyImpl.forTypedName(propName, cls)));
			return new IndexByDef2Impl<K, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> IndexByDef2<K, R, V, TPlugin> by(TProperty<R> tp)
		{
			getCtxt().getPipelineDef().pushToIndexStack(GroupByBlock.forProperty(tp));
			return new IndexByDef2Impl<K, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> IndexByDef2<K, R, V, TPlugin> by(Function<V, R> f)
		{
			getCtxt().getPipelineDef().pushToIndexStack(
				GroupByBlock.forKeyIndexer(Functions.adaptToFunctionWithIndex(f)));
			return new IndexByDef2Impl<K, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> IndexByDef2<K, R, V, TPlugin> by(Function2<V, Integer, R> f)
		{
			getCtxt().getPipelineDef().pushToIndexStack(GroupByBlock.forKeyIndexer(f));
			return new IndexByDef2Impl<K, R, V, TPlugin>(getCtxt());
		}

	}

	public static class IndexByDef2Impl<K1, K2, V, TPlugin>
		extends IndexByDefSupportImpl<Pair<K1, K2>, V, TPlugin>
		implements IndexByDef2<K1, K2, V, TPlugin>
	{

		public IndexByDef2Impl(IPipelineContext ctxt)
		{
			super(ctxt);
		}

		@Override
		public <R> IndexByDef3<K1, K2, R, V, TPlugin> by(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().pushToIndexStack(
				GroupByBlock.forProperty(TPropertyImpl.forTypedName(propName, cls)));
			return new IndexByDef3Impl<K1, K2, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> IndexByDef3<K1, K2, R, V, TPlugin> by(TProperty<R> tp)
		{
			getCtxt().getPipelineDef().pushToIndexStack(GroupByBlock.forProperty(tp));
			return new IndexByDef3Impl<K1, K2, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> IndexByDef3<K1, K2, R, V, TPlugin> by(Function<V, R> f)
		{
			getCtxt().getPipelineDef().pushToIndexStack(
				GroupByBlock.forKeyIndexer(Functions.adaptToFunctionWithIndex(f)));
			return new IndexByDef3Impl<K1, K2, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> IndexByDef3<K1, K2, R, V, TPlugin> by(Function2<V, Integer, R> f)
		{
			getCtxt().getPipelineDef().pushToIndexStack(GroupByBlock.forKeyIndexer(f));
			return new IndexByDef3Impl<K1, K2, R, V, TPlugin>(getCtxt());
		}
	}

	public static class IndexByDef3Impl<K1, K2, K3, V, TPlugin>
		extends IndexByDefSupportImpl<Tuple3<K1, K2, K3>, V, TPlugin>
		implements IndexByDef3<K1, K2, K3, V, TPlugin>
	{

		public IndexByDef3Impl(IPipelineContext ctxt)
		{
			super(ctxt);
		}

		@Override
		public <R> IndexByDef4<K1, K2, K3, R, V, TPlugin> by(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().pushToIndexStack(
				GroupByBlock.forProperty(TPropertyImpl.forTypedName(propName, cls)));
			return new IndexByDef4Impl<K1, K2, K3, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> IndexByDef4<K1, K2, K3, R, V, TPlugin> by(TProperty<R> tp)
		{
			getCtxt().getPipelineDef().pushToIndexStack(GroupByBlock.forProperty(tp));
			return new IndexByDef4Impl<K1, K2, K3, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> IndexByDef4<K1, K2, K3, R, V, TPlugin> by(Function<V, R> f)
		{
			getCtxt().getPipelineDef().pushToIndexStack(
				GroupByBlock.forKeyIndexer(Functions.adaptToFunctionWithIndex(f)));
			return new IndexByDef4Impl<K1, K2, K3, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> IndexByDef4<K1, K2, K3, R, V, TPlugin> by(Function2<V, Integer, R> f)
		{
			getCtxt().getPipelineDef().pushToIndexStack(GroupByBlock.forKeyIndexer(f));
			return new IndexByDef4Impl<K1, K2, K3, R, V, TPlugin>(getCtxt());
		}

	}

	public static class IndexByDef4Impl<K1, K2, K3, K4, V, TPlugin>
		extends IndexByDefSupportImpl<Tuple4<K1, K2, K3, K4>, V, TPlugin>
		implements IndexByDef4<K1, K2, K3, K4, V, TPlugin>
	{

		public IndexByDef4Impl(IPipelineContext ctxt)
		{
			super(ctxt);
		}

		@Override
		public <R> IndexByDef5<K1, K2, K3, K4, R, V, TPlugin> by(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().pushToIndexStack(
				GroupByBlock.forProperty(TPropertyImpl.forTypedName(propName, cls)));
			return new IndexByDef5Impl<K1, K2, K3, K4, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> IndexByDef5<K1, K2, K3, K4, R, V, TPlugin> by(TProperty<R> tp)
		{
			getCtxt().getPipelineDef().pushToIndexStack(GroupByBlock.forProperty(tp));
			return new IndexByDef5Impl<K1, K2, K3, K4, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> IndexByDef5<K1, K2, K3, K4, R, V, TPlugin> by(Function<V, R> f)
		{
			getCtxt().getPipelineDef().pushToIndexStack(
				GroupByBlock.forKeyIndexer(Functions.adaptToFunctionWithIndex(f)));
			return new IndexByDef5Impl<K1, K2, K3, K4, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> IndexByDef5<K1, K2, K3, K4, R, V, TPlugin> by(Function2<V, Integer, R> f)
		{
			getCtxt().getPipelineDef().pushToIndexStack(GroupByBlock.forKeyIndexer(f));
			return new IndexByDef5Impl<K1, K2, K3, K4, R, V, TPlugin>(getCtxt());
		}

	}

	public static class IndexByDef5Impl<K1, K2, K3, K4, K5, V, TPlugin>
		extends IndexByDefSupportImpl<Tuple5<K1, K2, K3, K4, K5>, V, TPlugin>
		implements IndexByDef5<K1, K2, K3, K4, K5, V, TPlugin>
	{

		public IndexByDef5Impl(IPipelineContext ctxt)
		{
			super(ctxt);
		}

		@Override
		public <R> IndexByDef<TupleN<Tuple5<K1, K2, K3, K4, K5>, R>, V, TPlugin> by(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().pushToIndexStack(
				GroupByBlock.forProperty(TPropertyImpl.forTypedName(propName, cls)));
			return new IndexByDefImpl<TupleN<Tuple5<K1, K2, K3, K4, K5>, R>, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> IndexByDef<TupleN<Tuple5<K1, K2, K3, K4, K5>, R>, V, TPlugin> by(TProperty<R> tp)
		{
			getCtxt().getPipelineDef().pushToIndexStack(GroupByBlock.forProperty(tp));
			return new IndexByDefImpl<TupleN<Tuple5<K1, K2, K3, K4, K5>, R>, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> IndexByDef<TupleN<Tuple5<K1, K2, K3, K4, K5>, R>, V, TPlugin> by(Function<V, R> f)
		{
			getCtxt().getPipelineDef().pushToIndexStack(
				GroupByBlock.forKeyIndexer(Functions.adaptToFunctionWithIndex(f)));
			return new IndexByDefImpl<TupleN<Tuple5<K1, K2, K3, K4, K5>, R>, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> IndexByDef<TupleN<Tuple5<K1, K2, K3, K4, K5>, R>, V, TPlugin> by(Function2<V, Integer, R> f)
		{
			getCtxt().getPipelineDef().pushToIndexStack(GroupByBlock.forKeyIndexer(f));
			return new IndexByDefImpl<TupleN<Tuple5<K1, K2, K3, K4, K5>, R>, V, TPlugin>(getCtxt());
		}
	}

}
