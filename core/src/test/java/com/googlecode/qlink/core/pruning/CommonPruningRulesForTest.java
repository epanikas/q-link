package com.googlecode.qlink.core.pruning;

import java.util.Arrays;
import java.util.List;


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
import com.googlecode.qlink.core.pruning.PruningRule;
import com.googlecode.qlink.core.pruning.filter.BeginEndPruningRule;
import com.googlecode.qlink.core.pruning.filter.ElemConditionPruningRule;
import com.googlecode.qlink.core.pruning.filter.JunctionPruningRule;
import com.googlecode.qlink.core.pruning.filter.NotPredicatePruningRule;
import com.googlecode.qlink.core.pruning.filter.PropertyConditionPropertyPruningRule;
import com.googlecode.qlink.core.pruning.filter.PropertyConditionPruningRule;
import com.googlecode.qlink.core.pruning.filter.ToStringFilterPruningAction;
import com.googlecode.qlink.core.pruning.group.CompositeIndexerPruningRule;
import com.googlecode.qlink.core.pruning.group.CustomKeyGroupByPruningRule;
import com.googlecode.qlink.core.pruning.group.PropertyGroupByPruningRule;
import com.googlecode.qlink.core.pruning.having.AggregatorConditionPruningRule;
import com.googlecode.qlink.core.pruning.indexer.ToStringGroupByPruningAction;
import com.googlecode.qlink.core.pruning.order.AscDescPruningRule;
import com.googlecode.qlink.core.pruning.order.NullFirstLastPruningRule;
import com.googlecode.qlink.core.pruning.order.PropertyOrderPruningAction;
import com.googlecode.qlink.core.pruning.order.PropertyOrderPruningRule;
import com.googlecode.qlink.core.pruning.order.ToStringOrderPruningAction;
import com.googlecode.qlink.core.pruning.order.TwoComparatorPruningRule;
import com.googlecode.qlink.core.pruning.transform.ToStringTransformPruningAction;
import com.googlecode.qlink.core.pruning.transform.TransformTwoFunctionsPruningRule;
import com.googlecode.qlink.core.pruning.visit.AssignVisitPruningRule;
import com.googlecode.qlink.core.pruning.visit.TwoVisitorsPruningRule;
import com.googlecode.qlink.core.pruning.visitor.ToStringTwoVisitorPruningAction;
import com.googlecode.qlink.core.pruning.visitor.ToStringVisitPruningAction;

public class CommonPruningRulesForTest
{

	public static Pruner<EOrderBlockType, OrderBlock> orderPruner = //
		new Pruner<EOrderBlockType, OrderBlock>(CommonPruningRulesForTest.getOrderPruningRulesForTest(),
			EOrderBlockType.endOfStack);

	public static Pruner<EFilterBlockType, FilterBlock> filterPruner = new Pruner<EFilterBlockType, FilterBlock>(
		CommonPruningRulesForTest.getFilterPruningRulesForTest(), EFilterBlockType.endOfStack);

	public static Pruner<EGroupByBlockType, GroupByBlock> groupByPruner = new Pruner<EGroupByBlockType, GroupByBlock>(
		CommonPruningRulesForTest.getGroupByPruningRulesForTest(), EGroupByBlockType.endOfStack);

	public static Pruner<ETransformBlockType, TransformBlock> transformPruner =
		new Pruner<ETransformBlockType, TransformBlock>(CommonPruningRulesForTest.getTransformPruningRulesForTest(),
			ETransformBlockType.endOfStack);

	public static Pruner<EVisitBlockType, VisitBlock> visitPruner = new Pruner<EVisitBlockType, VisitBlock>(
		CommonPruningRulesForTest.getVisitRulesForTest(), EVisitBlockType.endOfStack);

	private static List<PruningRule<EFilterBlockType, FilterBlock>> getFilterPruningRulesForTest()
	{
		PruningRule<EFilterBlockType, FilterBlock> r1 = new BeginEndPruningRule(new ToStringFilterPruningAction());

		PruningRule<EFilterBlockType, FilterBlock> r21 =
			new PropertyConditionPruningRule(new ToStringFilterPruningAction());

		PruningRule<EFilterBlockType, FilterBlock> r22 =
			new ElemConditionPruningRule(new ToStringFilterPruningAction());

		PruningRule<EFilterBlockType, FilterBlock> r23 = new NotPredicatePruningRule(new ToStringFilterPruningAction());

		PruningRule<EFilterBlockType, FilterBlock> r24 =
			new PropertyConditionPropertyPruningRule(new ToStringFilterPruningAction());

		PruningRule<EFilterBlockType, FilterBlock> r3 = new JunctionPruningRule(new ToStringFilterPruningAction());

		PruningRule<EFilterBlockType, FilterBlock> r4 =
			new AggregatorConditionPruningRule(new ToStringFilterPruningAction());

		return Arrays.asList(r1, r21, r22, r23, r24, r3, r4);
	}

	private static List<PruningRule<EOrderBlockType, OrderBlock>> getOrderPruningRulesForTest()
	{
		/*
		 * comparator, comparator ==> composite comparator
		 */
		PruningRule<EOrderBlockType, OrderBlock> r1 = new TwoComparatorPruningRule(new ToStringOrderPruningAction());

		/*
		 * property, [property | comparator | endOfStack] ==> comparator, [property | comparator | endOfStack]
		 */
		PruningRule<EOrderBlockType, OrderBlock> r21 = new PropertyOrderPruningRule(EOrderBlockType.property, //
			new PropertyOrderPruningAction());
		PruningRule<EOrderBlockType, OrderBlock> r22 =
			new PropertyOrderPruningRule(EOrderBlockType.comparator, new PropertyOrderPruningAction());
		//		PruningRule<EOrderChainType, OrderBlock> r23 =
		//			new PropertyOrderPruningRule(EOrderChainType.nullability,
		//				new PropertyOrderPruningAction());
		PruningRule<EOrderBlockType, OrderBlock> r24 =
			new PropertyOrderPruningRule(EOrderBlockType.endOfStack, new PropertyOrderPruningAction());
		/*
		 * property, ascDesc ==> comparator
		 */
		PruningRule<EOrderBlockType, OrderBlock> r3 = new AscDescPruningRule(new ToStringOrderPruningAction());

		/*
		 * comparator, nullability ==> comparator
		 */
		PruningRule<EOrderBlockType, OrderBlock> r4 = new NullFirstLastPruningRule(new ToStringOrderPruningAction());

		return Arrays.asList(r1, r21, r22, /*r23, */r24, r3, r4);
	}

	private static List<PruningRule<EGroupByBlockType, GroupByBlock>> getGroupByPruningRulesForTest()
	{
		/*
		 * property ==> indexer
		 */
		PruningRule<EGroupByBlockType, GroupByBlock> r1 =
			new PropertyGroupByPruningRule(new ToStringGroupByPruningAction());

		/*
		 * custom key ==> indexer
		 */
		PruningRule<EGroupByBlockType, GroupByBlock> r11 =
			new CustomKeyGroupByPruningRule(new ToStringGroupByPruningAction());

		/*
		 * indexer, indexer ==> composite indexer
		 */
		PruningRule<EGroupByBlockType, GroupByBlock> r2 =
			new CompositeIndexerPruningRule(new ToStringGroupByPruningAction());

		return Arrays.asList(r1, r11, r2);
	}

	private static List<PruningRule<EVisitBlockType, VisitBlock>> getVisitRulesForTest()
	{
		PruningRule<EVisitBlockType, VisitBlock> r11 =
			new AssignVisitPruningRule(EVisitBlockType.rhsValue, new ToStringVisitPruningAction());

		PruningRule<EVisitBlockType, VisitBlock> r12 =
			new AssignVisitPruningRule(EVisitBlockType.rhsProperty, new ToStringVisitPruningAction());

		PruningRule<EVisitBlockType, VisitBlock> r13 =
			new AssignVisitPruningRule(EVisitBlockType.rhsFunction, new ToStringVisitPruningAction());

		PruningRule<EVisitBlockType, VisitBlock> r2 = new TwoVisitorsPruningRule(new ToStringTwoVisitorPruningAction());

		return Arrays.asList(r11, r12, r13, r2);
	}

	private static List<PruningRule<ETransformBlockType, TransformBlock>> getTransformPruningRulesForTest()
	{
		PruningRule<ETransformBlockType, TransformBlock> r1 =
			new PruningRule<ETransformBlockType, TransformBlock>(new ToStringTransformPruningAction(),
				ETransformBlockType.property);

		PruningRule<ETransformBlockType, TransformBlock> r2 =
			new PruningRule<ETransformBlockType, TransformBlock>(new ToStringTransformPruningAction(),
				ETransformBlockType.constant);

		PruningRule<ETransformBlockType, TransformBlock> r3 =
			new PruningRule<ETransformBlockType, TransformBlock>(new ToStringTransformPruningAction(),
				ETransformBlockType.key);

		PruningRule<ETransformBlockType, TransformBlock> r4 =
			new PruningRule<ETransformBlockType, TransformBlock>(new ToStringTransformPruningAction(),
				ETransformBlockType.value);

		PruningRule<ETransformBlockType, TransformBlock> r5 =
			new PruningRule<ETransformBlockType, TransformBlock>(new ToStringTransformPruningAction(),
				ETransformBlockType.valueAggregator);

		PruningRule<ETransformBlockType, TransformBlock> r6 =
			new TransformTwoFunctionsPruningRule(new ToStringTransformPruningAction());

		return Arrays.asList(r1, r2, r3, r4, r5, r6);

	}

}
