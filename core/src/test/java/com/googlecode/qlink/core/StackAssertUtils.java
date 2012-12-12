package com.googlecode.qlink.core;

import java.util.Comparator;
import java.util.Stack;

import org.junit.Assert;

import com.googlecode.qlink.api.functor.Function;
import com.googlecode.qlink.api.functor.Function2;
import com.googlecode.qlink.api.functor.Predicate;
import com.googlecode.qlink.api.functor.Visitor2;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.blocks.FilterBlock;
import com.googlecode.qlink.core.context.blocks.GroupByBlock;
import com.googlecode.qlink.core.context.blocks.OrderBlock;
import com.googlecode.qlink.core.context.blocks.TransformBlock;
import com.googlecode.qlink.core.context.blocks.VisitBlock;
import com.googlecode.qlink.core.context.enums.EFilterBlockType;
import com.googlecode.qlink.core.context.enums.EGroupByBlockType;
import com.googlecode.qlink.core.context.enums.EOrderBlockType;
import com.googlecode.qlink.core.context.enums.ETransformBlockType;
import com.googlecode.qlink.core.context.enums.EVisitBlockType;
import com.googlecode.qlink.core.pruning.Pruner;

public class StackAssertUtils
{

	public static void assertFilterResult(Pruner<EFilterBlockType, FilterBlock> pruner,
											Stack<Pair<EFilterBlockType, FilterBlock>> filterStack, String filterRes)
	{
		pruner.prune(filterStack);
		Assert.assertEquals(1, filterStack.size());
		FilterBlock f = filterStack.pop().getSecond();
		Assert.assertEquals(EFilterBlockType.predicate, f.getType());
		Predicate<?> p = f.getPredicate();
		Assert.assertEquals(filterRes, p.toString());
	}

	public static void assertOrderResult(Pruner<EOrderBlockType, OrderBlock> pruner,
											Stack<Pair<EOrderBlockType, OrderBlock>> orderStack, String orderRes)
	{
		pruner.prune(orderStack);
		Assert.assertTrue(orderStack.size() == 1);
		OrderBlock f = orderStack.pop().getSecond();
		Assert.assertEquals(EOrderBlockType.comparator, f.getType());
		Comparator<?> p = f.getComparator();
		Assert.assertEquals(orderRes, p.toString());
	}

	public static void assertGroupByResult(Pruner<EGroupByBlockType, GroupByBlock> pruner,
											Stack<Pair<EGroupByBlockType, GroupByBlock>> indexStack, String indexRes)
	{
		pruner.prune(indexStack);
		Assert.assertTrue(indexStack.size() == 1);
		GroupByBlock f = indexStack.pop().getSecond();
		Assert.assertEquals(EGroupByBlockType.indexer, f.getType());
		Function2<?, Integer, ?> p = f.getKeyValueIndexer();
		Assert.assertEquals(indexRes, p.toString());
	}

	public static void assertHavingResult(Pruner<EFilterBlockType, FilterBlock> pruner,
											Stack<Pair<EFilterBlockType, FilterBlock>> havingStack, String havingRes)
	{
		pruner.prune(havingStack);
		Assert.assertTrue(havingStack.size() == 1);
		FilterBlock f = havingStack.pop().getSecond();
		Assert.assertEquals(EFilterBlockType.predicate, f.getType());
		Predicate<?> p = f.getPredicate();
		Assert.assertEquals(havingRes, p.toString());
	}

	public static void assertTransformResult(Pruner<ETransformBlockType, TransformBlock> pruner,
												Stack<Pair<ETransformBlockType, TransformBlock>> transformStack,
												String transformRes)
	{
		pruner.prune(transformStack);
		Assert.assertTrue(transformStack.size() == 1);
		TransformBlock tb = transformStack.pop().getSecond();
		Assert.assertEquals(ETransformBlockType.functor, tb.getType());
		Function<?, ?> f = tb.getFunction();
		Assert.assertEquals(transformRes, f.toString());
	}

	public static void assertVisitResult(Pruner<EVisitBlockType, VisitBlock> pruner,
											Stack<Pair<EVisitBlockType, VisitBlock>> visitStack, String visitRes)
	{
		pruner.prune(visitStack);
		Assert.assertTrue(visitStack.size() == 1);
		VisitBlock tb = visitStack.pop().getSecond();
		Assert.assertEquals(EVisitBlockType.visitor, tb.getType());
		Visitor2<?, Integer> f = tb.getVisitor();
		Assert.assertEquals(visitRes, f.toString());
	}

}
