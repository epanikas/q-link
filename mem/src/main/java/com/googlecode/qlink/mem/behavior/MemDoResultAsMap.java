package com.googlecode.qlink.mem.behavior;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import com.googlecode.qlink.api.behavior.DoResultAsList;
import com.googlecode.qlink.api.behavior.DoResultAsMap;
import com.googlecode.qlink.api.functor.Function2;
import com.googlecode.qlink.api.functor.Predicate;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.TypedFactory;
import com.googlecode.qlink.core.context.blocks.GroupByBlock;
import com.googlecode.qlink.core.context.enums.EGroupByBlockType;
import com.googlecode.qlink.core.utils.SimpleAssert;
import com.googlecode.qlink.core.utils.StackPruningUtils;
import com.googlecode.qlink.mem.pruning.MemPruningRules;
import com.googlecode.qlink.tuples.Tuples;

public class MemDoResultAsMap<K, V, TPlugin>
	extends MemDoResultSupport
	implements DoResultAsMap<K, V, TPlugin>
{

	private TypedFactory<List<?>> groupByCollectionFactory = new TypedFactory<List<?>>() {
		@SuppressWarnings("rawtypes")
		@Override
		public List<?> create()
		{
			return new ArrayList();
		}
	};

	public MemDoResultAsMap(IPipelineContext ctxt)
	{
		super(ctxt);
	}

	public void setGroupByListFactory(TypedFactory<List<?>> groupByListFactory)
	{
		this.groupByCollectionFactory = groupByListFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<K, V> toMap()
	{

		DoResultAsList<?, TPlugin> doResultAsList = getCtxt().getFactory().create(DoResultAsList.class);
		List<?> lstRes = doResultAsList.toList();

		Stack<Pair<EGroupByBlockType, GroupByBlock>> groupByStack = getCtxt().getPipelineDef().getGroupByStack();
		Stack<Pair<EGroupByBlockType, GroupByBlock>> indexStack = getCtxt().getPipelineDef().getIndexStack();

		SimpleAssert.isTrue((groupByStack.size() != 0 && indexStack.size() == 0)
			|| (indexStack.size() != 0 && groupByStack.size() == 0));

		boolean isGroupBy = groupByStack.size() != 0;

		Function2<Object, Integer, ?> indexer = null;
		if (isGroupBy) {
			indexer =
				(Function2<Object, Integer, ?>) StackPruningUtils.createGroupByFunction(MemPruningRules.groupByPruner,
					getCtxt().getPipelineDef().getGroupByStack());
		}
		else {
			indexer =
				(Function2<Object, Integer, ?>) StackPruningUtils.createIndexByFunction(MemPruningRules.groupByPruner,
					getCtxt().getPipelineDef().getIndexStack());
		}
		SimpleAssert.notNull(indexer, "indexer cannot be null for " + getCtxt());

		Map<K, V> res = new HashMap<K, V>();
		if (isGroupBy) {

			int i = 0;
			for (Object obj : lstRes) {
				Pair<K, Object> entry = (Pair<K, Object>) indexer.apply(obj, i++);
				V lst = res.get(entry.getFirst());
				if (lst == null) {
					lst = (V) groupByCollectionFactory.create();
					res.put(entry.getFirst(), lst);
				}
				((List<Object>) lst).add(entry.getSecond());
			}

			Function2<Object, Integer, ?> groupSelectFunction =
				(Function2<Object, Integer, ?>) createSelectGroupFunction();

			if (groupSelectFunction != null) {

				int ind = 0;
				for (Map.Entry<K, V> entry : res.entrySet()) {
					Object val = groupSelectFunction.apply(Tuples.tie(entry.getKey(), entry.getValue()), ind++);

					entry.setValue((V) val);
				}
			}
		}
		else {
			int i = 0;
			for (Object obj : lstRes) {
				Pair<K, V> entry = (Pair<K, V>) indexer.apply(obj, i++);
				if (res.containsKey(entry.getFirst())) {
					throw new IllegalArgumentException("unique index, but got double occurence of the key "
						+ entry.getFirst());
				}
				res.put(entry.getFirst(), entry.getSecond());
			}
		}

		Predicate<Object> havingPredicate =
			(Predicate<Object>) StackPruningUtils.createHavingPredicate(MemPruningRules.filterPruner, getCtxt()
				.getPipelineDef().getHavingStack());

		if (havingPredicate != null) {
			Iterator<Map.Entry<K, V>> iter = res.entrySet().iterator();
			for (; iter.hasNext();) {
				Map.Entry<K, V> entry = iter.next();
				if (havingPredicate.evaluate(entry.getValue()) == false) {
					iter.remove();
				}
			}
		}

		if (isGroupBy) {
			@SuppressWarnings("rawtypes")
			Function2 selectGroupTransformFunction = createTransformFunction();

			if (selectGroupTransformFunction != null) {
				Map<K, V> transformedRes = new HashMap<K, V>();
				for (Map.Entry<K, V> e : res.entrySet()) {
					Object transformedLst =
						selectGroupTransformFunction.apply(Tuples.tie(e.getKey(), e.getValue()), /*not used here*/0);
					transformedRes.put(e.getKey(), (V) transformedLst);
				}
				res = transformedRes;
			}

		}

		return res;
	}

	@Override
	public int size()
	{
		return toMap().size();
	}

	@Override
	public boolean isEmpty()
	{
		return size() == 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public TPlugin plugin()
	{
		return (TPlugin) getCtxt().getPlugin();
	}

}
