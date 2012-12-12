package com.googlecode.qlink.core.context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import com.googlecode.qlink.api.functor.Aggregator;
import com.googlecode.qlink.api.functor.Folder;
import com.googlecode.qlink.api.functor.Reducer;
import com.googlecode.qlink.api.functor.SamplePredicate;
import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.blocks.FilterBlock;
import com.googlecode.qlink.core.context.blocks.GroupByBlock;
import com.googlecode.qlink.core.context.blocks.OrderBlock;
import com.googlecode.qlink.core.context.blocks.TransformBlock;
import com.googlecode.qlink.core.context.blocks.VisitBlock;
import com.googlecode.qlink.core.context.enums.EAggregatorType;
import com.googlecode.qlink.core.context.enums.EFilterBlockType;
import com.googlecode.qlink.core.context.enums.EFoldSide;
import com.googlecode.qlink.core.context.enums.EGroupByBlockType;
import com.googlecode.qlink.core.context.enums.EOrderBlockType;
import com.googlecode.qlink.core.context.enums.ESampleType;
import com.googlecode.qlink.core.context.enums.ETransformBlockType;
import com.googlecode.qlink.core.context.enums.ETransformResultType;
import com.googlecode.qlink.core.context.enums.EVisitBlockType;
import com.googlecode.qlink.tuples.Tuples;

public class DefaultPipelineDefinition
	implements IPipelineDefinition
{
	private final Stack<Pair<EFilterBlockType, FilterBlock>> filterStack =
		new Stack<Pair<EFilterBlockType, FilterBlock>>();

	private final Stack<Pair<EOrderBlockType, OrderBlock>> orderStack = new Stack<Pair<EOrderBlockType, OrderBlock>>();

	private final Stack<Pair<EGroupByBlockType, GroupByBlock>> indexByStack =
		new Stack<Pair<EGroupByBlockType, GroupByBlock>>();

	private final Stack<Pair<EGroupByBlockType, GroupByBlock>> groupByStack =
		new Stack<Pair<EGroupByBlockType, GroupByBlock>>();

	private final Stack<Pair<EFilterBlockType, FilterBlock>> havingStack =
		new Stack<Pair<EFilterBlockType, FilterBlock>>();

	private final Stack<Pair<ETransformBlockType, TransformBlock>> transformStack =
		new Stack<Pair<ETransformBlockType, TransformBlock>>();

	private final Stack<Pair<ETransformBlockType, TransformBlock>> selectGroupStack =
		new Stack<Pair<ETransformBlockType, TransformBlock>>();

	private final Stack<Pair<EVisitBlockType, VisitBlock>> visitStack = new Stack<Pair<EVisitBlockType, VisitBlock>>();

	private ETransformResultType transformResultType = ETransformResultType.tuple;
	private Class<?> transformResultClass;

	private ETransformResultType selectGroupResultType = ETransformResultType.tuple;
	private Class<?> selectGroupResultClass;

	private final List<Pair<EAggregatorType, TransformBlock>> aggregators =
		new ArrayList<Pair<EAggregatorType, TransformBlock>>();
	private ETransformResultType aggregatorResultType = ETransformResultType.tuple;
	private Class<?> aggregatorResultClass;

	private Reducer<?> reducer;

	private Folder<?, ?> folder;
	private Object foldInit;
	private EFoldSide foldSide;

	private SamplePredicate samplePredicate;
	private ESampleType sampleType;
	private int sampleParam;

	@Override
	public void pushToFilterStack(FilterBlock fb)
	{
		filterStack.push(Tuples.tie(fb.getType(), fb));
	}

	@Override
	public void pushToOrderStack(OrderBlock ob)
	{
		orderStack.push(Tuples.tie(ob.getType(), ob));
	}

	@Override
	public void pushToVisitStack(VisitBlock vb)
	{
		visitStack.push(Tuples.tie(vb.getType(), vb));
	}

	@Override
	public void pushToTransformStack(TransformBlock tb)
	{
		transformStack.push(Tuples.tie(tb.getType(), tb));
	}

	@Override
	public void pushToSelectGroupStack(TransformBlock tb)
	{
		selectGroupStack.push(Tuples.tie(tb.getType(), tb));
	}

	@Override
	public void pushToGroupByStack(GroupByBlock gbb)
	{
		groupByStack.push(Tuples.tie(gbb.getType(), gbb));
	}

	@Override
	public void pushToIndexStack(GroupByBlock gbb)
	{
		indexByStack.push(Tuples.tie(gbb.getType(), gbb));
	}

	@Override
	public void pushToHavingStack(FilterBlock hb)
	{
		havingStack.push(Tuples.tie(hb.getType(), hb));
	}

	@Override
	public void setSamplePredicate(SamplePredicate p)
	{
		samplePredicate = p;
	}

	@Override
	public SamplePredicate getSamplePredicate()
	{
		return samplePredicate;
	}

	@Override
	public void setSampleType(ESampleType type, int param)
	{
		sampleType = type;
		sampleParam = param;
	}

	@Override
	public void setSampleType(ESampleType type)
	{
		sampleType = type;
	}

	@Override
	public ESampleType getSampleType()
	{
		return sampleType;
	}

	@Override
	public int getSampleParam()
	{
		return sampleParam;
	}

	@Override
	public void addAggregator(Aggregator<?, ?> agg)
	{
		aggregators.add(Tuples.tie(EAggregatorType.custom, TransformBlock.forAggregator(agg)));
	}

	@Override
	public void addPropertyAggregator(EAggregatorType aggType, TProperty<?> prop)
	{
		aggregators.add(Tuples.tie(aggType, TransformBlock.forAggregatorType(aggType, prop)));
	}

	@Override
	public List<Pair<EAggregatorType, TransformBlock>> getAggregators()
	{
		return Collections.unmodifiableList(aggregators);
	}

	@Override
	public void setReducer(Reducer<?> reducer)
	{
		this.reducer = reducer;
	}

	@Override
	public Reducer<?> getReducer()
	{
		return reducer;
	}

	@Override
	public void setFolder(Folder<?, ?> folder, Object init, EFoldSide side)
	{
		this.folder = folder;
		foldInit = init;
		foldSide = side;
	}

	@Override
	public Folder<?, ?> getFolder()
	{
		return folder;
	}

	@Override
	public Object getFoldInit()
	{
		return foldInit;
	}

	@Override
	public EFoldSide getFoldSide()
	{
		return foldSide;
	}

	@Override
	public void setTransformResultType(ETransformResultType transformResultType)
	{
		this.transformResultType = transformResultType;
	}

	@Override
	public ETransformResultType getTransformResultType()
	{
		return transformResultType;
	}

	@Override
	public void setTransformResultClass(Class<?> transformResultClass)
	{
		this.transformResultClass = transformResultClass;
	}

	@Override
	public Class<?> getTransformResultClass()
	{
		return transformResultClass;
	}

	@Override
	public void setSelectGroupResultType(ETransformResultType transformResultType)
	{
		selectGroupResultType = transformResultType;
	}

	@Override
	public ETransformResultType getSelectGroupResultType()
	{
		return selectGroupResultType;
	}

	@Override
	public void setSelectGroupResultClass(Class<?> transformResultClass)
	{
		selectGroupResultClass = transformResultClass;
	}

	@Override
	public Class<?> getSelectGroupResultClass()
	{
		return selectGroupResultClass;
	}

	@Override
	public void setAggregatedResultType(ETransformResultType transformResultType)
	{
		aggregatorResultType = transformResultType;
	}

	@Override
	public ETransformResultType getAggregatedResultType()
	{
		return aggregatorResultType;
	}

	@Override
	public void setAggregatedResultClass(Class<?> transformResultClass)
	{
		aggregatorResultClass = transformResultClass;
	}

	@Override
	public Class<?> getAggregatedResultClass()
	{
		return aggregatorResultClass;
	}

	@Override
	public Stack<Pair<EFilterBlockType, FilterBlock>> getFilterStack()
	{
		return filterStack;
	}

	@Override
	public Stack<Pair<EOrderBlockType, OrderBlock>> getOrderStack()
	{
		return orderStack;
	}

	@Override
	public Stack<Pair<EGroupByBlockType, GroupByBlock>> getGroupByStack()
	{
		return groupByStack;
	}

	@Override
	public Stack<Pair<EGroupByBlockType, GroupByBlock>> getIndexStack()
	{
		return indexByStack;
	}

	@Override
	public Stack<Pair<EFilterBlockType, FilterBlock>> getHavingStack()
	{
		return havingStack;
	}

	@Override
	public Stack<Pair<ETransformBlockType, TransformBlock>> getTransformStack()
	{
		return transformStack;
	}

	@Override
	public Stack<Pair<ETransformBlockType, TransformBlock>> getSelectGroupStack()
	{
		return selectGroupStack;
	}

	@Override
	public Stack<Pair<EVisitBlockType, VisitBlock>> getVisitStack()
	{
		return visitStack;
	}

	@Override
	public Class<?> getKeyClass()
	{
		return null;
	}

	@Override
	public Class<?> getValueClass()
	{
		return null;
	}
}
