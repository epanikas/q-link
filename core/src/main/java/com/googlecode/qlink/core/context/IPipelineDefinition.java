package com.googlecode.qlink.core.context;

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

public interface IPipelineDefinition
{

	/*
	 * stacks
	 */
	void pushToFilterStack(FilterBlock fb);

	void pushToOrderStack(OrderBlock ob);

	void pushToVisitStack(VisitBlock vb);

	void pushToTransformStack(TransformBlock tb);

	void pushToSelectGroupStack(TransformBlock tb);

	void pushToGroupByStack(GroupByBlock gbb);

	void pushToIndexStack(GroupByBlock gbb);

	void pushToHavingStack(FilterBlock hb);

	Stack<Pair<EFilterBlockType, FilterBlock>> getFilterStack();

	Stack<Pair<EFilterBlockType, FilterBlock>> getHavingStack();

	Stack<Pair<EOrderBlockType, OrderBlock>> getOrderStack();

	Stack<Pair<EGroupByBlockType, GroupByBlock>> getGroupByStack();

	Stack<Pair<EGroupByBlockType, GroupByBlock>> getIndexStack();

	Stack<Pair<ETransformBlockType, TransformBlock>> getTransformStack();

	Stack<Pair<ETransformBlockType, TransformBlock>> getSelectGroupStack();

	Stack<Pair<EVisitBlockType, VisitBlock>> getVisitStack();

	/*
	 * aggregators 
	 */
	void addAggregator(Aggregator<?, ?> aggregator);

	void addPropertyAggregator(EAggregatorType aggType, TProperty<?> prop);

	List<Pair<EAggregatorType, TransformBlock>> getAggregators();

	void setAggregatedResultType(ETransformResultType transformResultType);

	ETransformResultType getAggregatedResultType();

	void setAggregatedResultClass(Class<?> transformResultClass);

	Class<?> getAggregatedResultClass();

	void setFolder(Folder<?, ?> folder, Object init, EFoldSide side);

	Folder<?, ?> getFolder();

	Object getFoldInit();

	EFoldSide getFoldSide();

	void setReducer(Reducer<?> reducer);

	Reducer<?> getReducer();

	/*
	 * transformers
	 */
	void setTransformResultType(ETransformResultType transformResultType);

	ETransformResultType getTransformResultType();

	void setTransformResultClass(Class<?> transformResultClass);

	Class<?> getTransformResultClass();

	void setSelectGroupResultType(ETransformResultType transformResultType);

	ETransformResultType getSelectGroupResultType();

	void setSelectGroupResultClass(Class<?> transformResultClass);

	Class<?> getSelectGroupResultClass();

	/*
	 * sample predicate
	 */
	void setSamplePredicate(SamplePredicate p);

	SamplePredicate getSamplePredicate();

	void setSampleType(ESampleType type);

	void setSampleType(ESampleType type, int param);

	ESampleType getSampleType();

	int getSampleParam();

	/*
	 * classes for map
	 */
	Class<?> getKeyClass();

	Class<?> getValueClass();

}
