package com.googlecode.qlink.core.utils;

import java.util.Comparator;
import java.util.Stack;

import com.googlecode.qlink.api.functor.Function2;
import com.googlecode.qlink.api.functor.Predicate;
import com.googlecode.qlink.api.functor.Visitor2;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.blocks.FilterBlock;
import com.googlecode.qlink.core.context.blocks.GroupByBlock;
import com.googlecode.qlink.core.context.blocks.OrderBlock;
import com.googlecode.qlink.core.context.blocks.VisitBlock;
import com.googlecode.qlink.core.context.enums.EFilterBlockType;
import com.googlecode.qlink.core.context.enums.EGroupByBlockType;
import com.googlecode.qlink.core.context.enums.EOrderBlockType;
import com.googlecode.qlink.core.context.enums.EVisitBlockType;
import com.googlecode.qlink.core.pruning.Pruner;

public class StackPruningUtils
{

	private static <TChainType extends Enum<?>, TBlockType> void pruneStack(Pruner<TChainType, TBlockType> pruner,
																			Stack<Pair<TChainType, TBlockType>> stack)
	{
		if (stack.size() == 0) {
			return;
		}

		pruner.prune(stack);

		SimpleAssert.isTrue(stack.size() == 1, "stack is not empty for " + stack);
	}

	public static <TChainType extends Enum<?>, TBlockType> Predicate<?> createFilterPredicate(	Pruner<EFilterBlockType, FilterBlock> pruner,
																								Stack<Pair<EFilterBlockType, FilterBlock>> filterStack)
	{
		if (filterStack.size() == 0) {
			return null;
		}

		pruneStack(pruner, filterStack);

		return filterStack.peek().getSecond().getPredicate();
	}

	public static Comparator<?> createComparator(Pruner<EOrderBlockType, OrderBlock> pruner,
													Stack<Pair<EOrderBlockType, OrderBlock>> orderStack)
	{
		if (orderStack.size() == 0) {
			return null;
		}

		pruneStack(pruner, orderStack);

		return orderStack.peek().getSecond().getComparator();
	}

	public static Function2<?, ?, Pair<?, ?>> createGroupByFunction(Pruner<EGroupByBlockType, GroupByBlock> pruner,
																	Stack<Pair<EGroupByBlockType, GroupByBlock>> groupByStack)
	{
		if (groupByStack.size() == 0) {
			return null;
		}

		pruneStack(pruner, groupByStack);

		return groupByStack.peek().getSecond().getKeyValueIndexer();

	}

	public static Function2<?, Integer, Pair<?, ?>> createIndexByFunction(	Pruner<EGroupByBlockType, GroupByBlock> pruner,
																			Stack<Pair<EGroupByBlockType, GroupByBlock>> indexStack)
	{
		if (indexStack.size() == 0) {
			return null;
		}

		pruneStack(pruner, indexStack);

		return indexStack.peek().getSecond().getKeyValueIndexer();

	}

	public static Predicate<?> createHavingPredicate(Pruner<EFilterBlockType, FilterBlock> pruner,
														Stack<Pair<EFilterBlockType, FilterBlock>> havingStack)
	{
		if (havingStack.size() == 0) {
			return null;
		}

		pruneStack(pruner, havingStack);

		return havingStack.peek().getSecond().getPredicate();
	}

	public static Visitor2<?, Integer> createVisitor(Pruner<EVisitBlockType, VisitBlock> pruner,
														Stack<Pair<EVisitBlockType, VisitBlock>> visitStack)
	{
		if (visitStack.size() == 0) {
			return null;
		}

		pruneStack(pruner, visitStack);

		return visitStack.peek().getSecond().getVisitor();
	}

}
