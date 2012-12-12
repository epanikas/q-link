package com.googlecode.qlink.mem.pruning;

import java.util.Arrays;
import java.util.List;


import com.googlecode.qlink.core.context.blocks.FilterBlock;
import com.googlecode.qlink.core.context.blocks.GroupByBlock;
import com.googlecode.qlink.core.context.blocks.OrderBlock;
import com.googlecode.qlink.core.context.blocks.VisitBlock;
import com.googlecode.qlink.core.context.enums.EFilterBlockType;
import com.googlecode.qlink.core.context.enums.EGroupByBlockType;
import com.googlecode.qlink.core.context.enums.EOrderBlockType;
import com.googlecode.qlink.core.context.enums.EVisitBlockType;
import com.googlecode.qlink.core.pruning.Pruner;
import com.googlecode.qlink.core.pruning.PruningRule;
import com.googlecode.qlink.core.pruning.filter.BeginEndPruningRule;
import com.googlecode.qlink.core.pruning.filter.ElemConditionPruningRule;
import com.googlecode.qlink.core.pruning.filter.JunctionPruningRule;
import com.googlecode.qlink.core.pruning.filter.PropertyConditionPruningRule;
import com.googlecode.qlink.core.pruning.group.CompositeIndexerPruningRule;
import com.googlecode.qlink.core.pruning.group.CustomKeyGroupByPruningRule;
import com.googlecode.qlink.core.pruning.group.PropertyGroupByPruningRule;
import com.googlecode.qlink.core.pruning.having.AggregatorConditionPruningRule;
import com.googlecode.qlink.core.pruning.order.AscDescPruningRule;
import com.googlecode.qlink.core.pruning.order.PropertyOrderPruningRule;
import com.googlecode.qlink.core.pruning.order.TwoComparatorPruningRule;
import com.googlecode.qlink.core.pruning.visit.AssignVisitPruningRule;
import com.googlecode.qlink.core.pruning.visit.TwoVisitorsPruningRule;
import com.googlecode.qlink.mem.pruning.filter.AggregatorConditionPruningAction;
import com.googlecode.qlink.mem.pruning.filter.BeginEndPruningAction;
import com.googlecode.qlink.mem.pruning.filter.JunctionPruningAction;
import com.googlecode.qlink.mem.pruning.filter.PropertyConditionPruningActioin;
import com.googlecode.qlink.mem.pruning.groupby.CompositeIndexerPruningAction;
import com.googlecode.qlink.mem.pruning.groupby.IndexByPropertyPruningAction;
import com.googlecode.qlink.mem.pruning.order.AscDescPruningAction;
import com.googlecode.qlink.mem.pruning.order.CompositeComparatorPruningAction;
import com.googlecode.qlink.mem.pruning.order.PropertyOrderPruningAction;
import com.googlecode.qlink.mem.pruning.visit.AssignPropertyPruningAction;
import com.googlecode.qlink.mem.pruning.visit.CompositeVisitorPruningAction;

public class MemPruningRules
{

	public static Pruner<EOrderBlockType, OrderBlock> orderPruner = //
		new Pruner<EOrderBlockType, OrderBlock>(MemPruningRules.getOrderPruningRules(), EOrderBlockType.endOfStack);

	public static Pruner<EFilterBlockType, FilterBlock> filterPruner = new Pruner<EFilterBlockType, FilterBlock>(
		MemPruningRules.getFilterPruningRules(), EFilterBlockType.endOfStack);

	public static Pruner<EGroupByBlockType, GroupByBlock> groupByPruner = new Pruner<EGroupByBlockType, GroupByBlock>(
		MemPruningRules.getGroupByPruningRules(), EGroupByBlockType.endOfStack);

	public static Pruner<EVisitBlockType, VisitBlock> visitorPruner = new Pruner<EVisitBlockType, VisitBlock>(
		MemPruningRules.getVisitPruningRules(), EVisitBlockType.endOfStack);

	private static List<PruningRule<EFilterBlockType, FilterBlock>> getFilterPruningRules()
	{
		PruningRule<EFilterBlockType, FilterBlock> r1 = new BeginEndPruningRule(new BeginEndPruningAction());

		PruningRule<EFilterBlockType, FilterBlock> r21 =
			new PropertyConditionPruningRule(new PropertyConditionPruningActioin());

		PruningRule<EFilterBlockType, FilterBlock> r22 =
			new ElemConditionPruningRule(new PropertyConditionPruningActioin());

		PruningRule<EFilterBlockType, FilterBlock> r3 = new JunctionPruningRule(new JunctionPruningAction());

		PruningRule<EFilterBlockType, FilterBlock> r4 =
			new AggregatorConditionPruningRule(new AggregatorConditionPruningAction());

		return Arrays.asList(r1, r21, r22, r3, r4);
	}

	private static List<PruningRule<EOrderBlockType, OrderBlock>> getOrderPruningRules()
	{
		/*
		 * comparator, comparator ==> composite comparator
		 */
		PruningRule<EOrderBlockType, OrderBlock> r1 =
			new TwoComparatorPruningRule(new CompositeComparatorPruningAction());

		/*
		 * property, [property | comparator | endOfStack] ==> comparator, [property | comparator | endOfStack]
		 */
		PruningRule<EOrderBlockType, OrderBlock> r21 = new PropertyOrderPruningRule(EOrderBlockType.property, //
			new PropertyOrderPruningAction());
		PruningRule<EOrderBlockType, OrderBlock> r22 =
			new PropertyOrderPruningRule(EOrderBlockType.comparator, new PropertyOrderPruningAction());
		PruningRule<EOrderBlockType, OrderBlock> r24 =
			new PropertyOrderPruningRule(EOrderBlockType.endOfStack, new PropertyOrderPruningAction());
		/*
		 * property, ascDesc ==> comparator
		 */
		PruningRule<EOrderBlockType, OrderBlock> r3 = new AscDescPruningRule(new AscDescPruningAction());

		/*
		 * comparator, nullability ==> comparator with nullability
		 */
		//		PruningRule<EOrderChainType, OrderBlock> r4 = new NullFirstLastPruningRule(new NullFirstLastPruningAction());

		return Arrays.asList(r1, r21, r22, r24, r3/*, r4*/);
	}

	private static List<PruningRule<EGroupByBlockType, GroupByBlock>> getGroupByPruningRules()
	{
		return Arrays.asList(
		/*
		 * property ==> indexer
		 */
		new PropertyGroupByPruningRule(new IndexByPropertyPruningAction()),

		/*
		 * custom key indexer ==> indexer
		 */
		new CustomKeyGroupByPruningRule(new IndexByPropertyPruningAction()),

		/*
		 * indexer, indexer ==> composite indexer
		 */
		new CompositeIndexerPruningRule(new CompositeIndexerPruningAction())

		);
	}

	private static List<PruningRule<EVisitBlockType, VisitBlock>> getVisitPruningRules()
	{
		PruningRule<EVisitBlockType, VisitBlock> r11 =
			new AssignVisitPruningRule(EVisitBlockType.rhsValue, new AssignPropertyPruningAction());

		PruningRule<EVisitBlockType, VisitBlock> r12 =
			new AssignVisitPruningRule(EVisitBlockType.rhsProperty, new AssignPropertyPruningAction());

		PruningRule<EVisitBlockType, VisitBlock> r13 =
			new AssignVisitPruningRule(EVisitBlockType.rhsFunction, new AssignPropertyPruningAction());

		PruningRule<EVisitBlockType, VisitBlock> r2 = new TwoVisitorsPruningRule(new CompositeVisitorPruningAction());

		return Arrays.asList(r11, r12, r13, r2);
	}
}
