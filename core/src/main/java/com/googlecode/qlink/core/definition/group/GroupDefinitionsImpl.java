package com.googlecode.qlink.core.definition.group;

import java.util.List;
import java.util.Map;

import com.googlecode.qlink.api.behavior.CanHaving;
import com.googlecode.qlink.api.behavior.DoResultAsMap;
import com.googlecode.qlink.api.definition.group.GroupDefinitions.GroupByDef;
import com.googlecode.qlink.api.definition.group.GroupDefinitions.GroupByDef2;
import com.googlecode.qlink.api.definition.group.GroupDefinitions.GroupByDef3;
import com.googlecode.qlink.api.definition.group.GroupDefinitions.GroupByDef4;
import com.googlecode.qlink.api.definition.group.GroupDefinitions.GroupByDef5;
import com.googlecode.qlink.api.definition.group.GroupDefinitions.GroupByDefSuport;
import com.googlecode.qlink.api.definition.group.having.HavingFilterDef;
import com.googlecode.qlink.api.definition.selectgroup.SelectGroupDefinitions.StartSelectGroupDef;
import com.googlecode.qlink.api.functor.Function;
import com.googlecode.qlink.api.functor.Function2;
import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.api.tuple.Tuple3;
import com.googlecode.qlink.api.tuple.Tuple4;
import com.googlecode.qlink.api.tuple.Tuple5;
import com.googlecode.qlink.api.tuple.TupleN;
import com.googlecode.qlink.core.behavior.CanHavingImpl;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.PipelineContextAwareSupport;
import com.googlecode.qlink.core.context.TPropertyImpl;
import com.googlecode.qlink.core.context.blocks.GroupByBlock;
import com.googlecode.qlink.core.definition.selectgroup.SelectGroupDefinitionsImpl.StartSelectGroupDefImpl;
import com.googlecode.qlink.core.functor.Functions;

public class GroupDefinitionsImpl
{
	static class GroupByDefSuportImpl<K, V extends List<?>, TPlugin>
		extends PipelineContextAwareSupport
		implements GroupByDefSuport<K, V, TPlugin>
	{
		private final DoResultAsMap<K, V, TPlugin> doResult;
		private final CanHaving<K, V, TPlugin> canHaving;

		@SuppressWarnings("unchecked")
		public GroupByDefSuportImpl(IPipelineContext ctxt)
		{
			super(ctxt);
			doResult = getCtxt().getFactory().create(DoResultAsMap.class);
			canHaving = new CanHavingImpl<K, V, TPlugin>(getCtxt());
		}

		@Override
		public HavingFilterDef<K, V, TPlugin> having()
		{
			return canHaving.having();
		}

		@Override
		public StartSelectGroupDef<K, V, TPlugin> selectAs()
		{
			return new StartSelectGroupDefImpl<K, V, TPlugin>(getCtxt());
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

	public static class GroupByDefImpl<K, V extends List<?>, TPlugin>
		extends GroupByDefSuportImpl<K, V, TPlugin>
		implements GroupByDef<K, V, TPlugin>
	{

		public GroupByDefImpl(IPipelineContext ctxt)
		{
			super(ctxt);
		}

		@Override
		public <R> GroupByDef2<K, R, V, TPlugin> by(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().pushToGroupByStack(
				GroupByBlock.forProperty(TPropertyImpl.forTypedName(propName, cls)));
			return new GroupByDef2Impl<K, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> GroupByDef2<K, R, V, TPlugin> by(TProperty<R> tp)
		{
			getCtxt().getPipelineDef().pushToGroupByStack(GroupByBlock.forProperty(tp));
			return new GroupByDef2Impl<K, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> GroupByDef2<K, R, V, TPlugin> by(Function<V, R> f)
		{
			getCtxt().getPipelineDef().pushToGroupByStack(
				GroupByBlock.forKeyIndexer(Functions.adaptToFunctionWithIndex(f)));
			return new GroupByDef2Impl<K, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> GroupByDef2<K, R, V, TPlugin> by(Function2<V, Integer, R> f)
		{
			getCtxt().getPipelineDef().pushToGroupByStack(GroupByBlock.forKeyIndexer(f));
			return new GroupByDef2Impl<K, R, V, TPlugin>(getCtxt());
		}
	}

	public static class GroupByDef2Impl<K1, K2, V extends List<?>, TPlugin>
		extends GroupByDefSuportImpl<Pair<K1, K2>, V, TPlugin>
		implements GroupByDef2<K1, K2, V, TPlugin>
	{

		public GroupByDef2Impl(IPipelineContext ctxt)
		{
			super(ctxt);
		}

		@Override
		public <R> GroupByDef3<K1, K2, R, V, TPlugin> by(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().pushToGroupByStack(
				GroupByBlock.forProperty(TPropertyImpl.forTypedName(propName, cls)));
			return new GroupByDef3Impl<K1, K2, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> GroupByDef3<K1, K2, R, V, TPlugin> by(TProperty<R> tp)
		{
			getCtxt().getPipelineDef().pushToGroupByStack(GroupByBlock.forProperty(tp));
			return new GroupByDef3Impl<K1, K2, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> GroupByDef3<K1, K2, R, V, TPlugin> by(Function<V, R> f)
		{
			getCtxt().getPipelineDef().pushToGroupByStack(
				GroupByBlock.forKeyIndexer(Functions.adaptToFunctionWithIndex(f)));
			return new GroupByDef3Impl<K1, K2, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> GroupByDef3<K1, K2, R, V, TPlugin> by(Function2<V, Integer, R> f)
		{
			getCtxt().getPipelineDef().pushToGroupByStack(GroupByBlock.forKeyIndexer(f));
			return new GroupByDef3Impl<K1, K2, R, V, TPlugin>(getCtxt());
		}
	}

	public static class GroupByDef3Impl<K1, K2, K3, V extends List<?>, TPlugin>
		extends GroupByDefSuportImpl<Tuple3<K1, K2, K3>, V, TPlugin>
		implements GroupByDef3<K1, K2, K3, V, TPlugin>
	{

		public GroupByDef3Impl(IPipelineContext ctxt)
		{
			super(ctxt);
		}

		@Override
		public <R> GroupByDef4<K1, K2, K3, R, V, TPlugin> by(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().pushToGroupByStack(
				GroupByBlock.forProperty(TPropertyImpl.forTypedName(propName, cls)));
			return new GroupByDef4Impl<K1, K2, K3, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> GroupByDef4<K1, K2, K3, R, V, TPlugin> by(TProperty<R> tp)
		{
			getCtxt().getPipelineDef().pushToGroupByStack(GroupByBlock.forProperty(tp));
			return new GroupByDef4Impl<K1, K2, K3, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> GroupByDef4<K1, K2, K3, R, V, TPlugin> by(Function<V, R> f)
		{
			getCtxt().getPipelineDef().pushToGroupByStack(
				GroupByBlock.forKeyIndexer(Functions.adaptToFunctionWithIndex(f)));
			return new GroupByDef4Impl<K1, K2, K3, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> GroupByDef4<K1, K2, K3, R, V, TPlugin> by(Function2<V, Integer, R> f)
		{
			getCtxt().getPipelineDef().pushToGroupByStack(GroupByBlock.forKeyIndexer(f));
			return new GroupByDef4Impl<K1, K2, K3, R, V, TPlugin>(getCtxt());
		}

	}

	public static class GroupByDef4Impl<K1, K2, K3, K4, V extends List<?>, TPlugin>
		extends GroupByDefSuportImpl<Tuple4<K1, K2, K3, K4>, V, TPlugin>
		implements GroupByDef4<K1, K2, K3, K4, V, TPlugin>
	{

		public GroupByDef4Impl(IPipelineContext ctxt)
		{
			super(ctxt);
		}

		@Override
		public <R> GroupByDef5<K1, K2, K3, K4, R, V, TPlugin> by(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().pushToGroupByStack(
				GroupByBlock.forProperty(TPropertyImpl.forTypedName(propName, cls)));
			return new GroupByDef5Impl<K1, K2, K3, K4, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> GroupByDef5<K1, K2, K3, K4, R, V, TPlugin> by(TProperty<R> tp)
		{
			getCtxt().getPipelineDef().pushToGroupByStack(GroupByBlock.forProperty(tp));
			return new GroupByDef5Impl<K1, K2, K3, K4, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> GroupByDef5<K1, K2, K3, K4, R, V, TPlugin> by(Function<V, R> f)
		{
			getCtxt().getPipelineDef().pushToGroupByStack(
				GroupByBlock.forKeyIndexer(Functions.adaptToFunctionWithIndex(f)));
			return new GroupByDef5Impl<K1, K2, K3, K4, R, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> GroupByDef5<K1, K2, K3, K4, R, V, TPlugin> by(Function2<V, Integer, R> f)
		{
			getCtxt().getPipelineDef().pushToGroupByStack(GroupByBlock.forKeyIndexer(f));
			return new GroupByDef5Impl<K1, K2, K3, K4, R, V, TPlugin>(getCtxt());
		}
	}

	public static class GroupByDef5Impl<K1, K2, K3, K4, K5, V extends List<?>, TPlugin>
		extends GroupByDefSuportImpl<Tuple5<K1, K2, K3, K4, K5>, V, TPlugin>
		implements GroupByDef5<K1, K2, K3, K4, K5, V, TPlugin>
	{
		public GroupByDef5Impl(IPipelineContext ctxt)
		{
			super(ctxt);
		}

		@Override
		public <R> GroupByDef<TupleN<Tuple5<K1, K2, K3, K4, K5>, R>, V, TPlugin> by(String propName, Class<R> cls)
		{
			getCtxt().getPipelineDef().pushToGroupByStack(
				GroupByBlock.forProperty(TPropertyImpl.forTypedName(propName, cls)));
			return new GroupByDefImpl<TupleN<Tuple5<K1, K2, K3, K4, K5>, R>, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> GroupByDef<TupleN<Tuple5<K1, K2, K3, K4, K5>, R>, V, TPlugin> by(TProperty<R> tp)
		{
			getCtxt().getPipelineDef().pushToGroupByStack(GroupByBlock.forProperty(tp));
			return new GroupByDefImpl<TupleN<Tuple5<K1, K2, K3, K4, K5>, R>, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> GroupByDef<TupleN<Tuple5<K1, K2, K3, K4, K5>, R>, V, TPlugin> by(Function<V, R> f)
		{
			getCtxt().getPipelineDef().pushToGroupByStack(
				GroupByBlock.forKeyIndexer(Functions.adaptToFunctionWithIndex(f)));
			return new GroupByDefImpl<TupleN<Tuple5<K1, K2, K3, K4, K5>, R>, V, TPlugin>(getCtxt());
		}

		@Override
		public <R> GroupByDef<TupleN<Tuple5<K1, K2, K3, K4, K5>, R>, V, TPlugin> by(Function2<V, Integer, R> f)
		{
			getCtxt().getPipelineDef().pushToGroupByStack(GroupByBlock.forKeyIndexer(f));
			return new GroupByDefImpl<TupleN<Tuple5<K1, K2, K3, K4, K5>, R>, V, TPlugin>(getCtxt());
		}

	}

}
