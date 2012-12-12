package com.googlecode.qlink.hibernate.pruning;

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
import com.googlecode.qlink.core.pruning.group.PropertyGroupByPruningRule;
import com.googlecode.qlink.core.pruning.having.AggregatorConditionPruningRule;
import com.googlecode.qlink.core.pruning.order.AscDescPruningRule;
import com.googlecode.qlink.core.pruning.order.PropertyOrderPruningRule;
import com.googlecode.qlink.core.pruning.order.TwoComparatorPruningRule;
import com.googlecode.qlink.core.pruning.visit.AssignVisitPruningRule;
import com.googlecode.qlink.core.pruning.visit.TwoVisitorsPruningRule;
import com.googlecode.qlink.hibernate.pruning.filter.AggregatorConditionPruningAction;
import com.googlecode.qlink.hibernate.pruning.filter.BeginEndPruningAction;
import com.googlecode.qlink.hibernate.pruning.filter.JunctionPruningAction;
import com.googlecode.qlink.hibernate.pruning.filter.PropertyConditionPruningActioin;
import com.googlecode.qlink.hibernate.pruning.groupby.CompositeIndexerPruningAction;
import com.googlecode.qlink.hibernate.pruning.groupby.PropertyIndexerPruningAction;
import com.googlecode.qlink.hibernate.pruning.order.AscDescPruningAction;
import com.googlecode.qlink.hibernate.pruning.order.CompositeComparatorPruningAction;
import com.googlecode.qlink.hibernate.pruning.order.PropertyOrderPruningAction;
import com.googlecode.qlink.hibernate.pruning.visit.AssignPropertyPruningAction;
import com.googlecode.qlink.hibernate.pruning.visit.CompositeVisitorPruningAction;

@SuppressWarnings("unchecked")
public class HibernatePruningRules
{

	public static Pruner<EOrderBlockType, OrderBlock> orderPruner = //
		new Pruner<EOrderBlockType, OrderBlock>(HibernatePruningRules.getOrderPruningRulesForTest(),
			EOrderBlockType.endOfStack);

	public static Pruner<EFilterBlockType, FilterBlock> filterPruner = new Pruner<EFilterBlockType, FilterBlock>(
		HibernatePruningRules.getFilterPruningRules(), EFilterBlockType.endOfStack);

	public static Pruner<EGroupByBlockType, GroupByBlock> groupByPruner = new Pruner<EGroupByBlockType, GroupByBlock>(
		HibernatePruningRules.getGroupByPruningRulesForTest(), EGroupByBlockType.endOfStack);

	public static Pruner<EVisitBlockType, VisitBlock> visitorPruner = new Pruner<EVisitBlockType, VisitBlock>(
		HibernatePruningRules.getVisitPruningRules(), EVisitBlockType.endOfStack);

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

	private static List<PruningRule<EOrderBlockType, OrderBlock>> getOrderPruningRulesForTest()
	{
		/*
		 * comparator, comparator ==> composite comparator
		 */
		PruningRule<EOrderBlockType, OrderBlock> r1 = new TwoComparatorPruningRule(new CompositeComparatorPruningAction());

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

		return Arrays.asList(r1, r21, r22, /*r23, */r24, r3);
	}

	private static List<PruningRule<EGroupByBlockType, GroupByBlock>> getGroupByPruningRulesForTest()
	{
		/*
		 * property ==> indexer
		 */
		PruningRule<EGroupByBlockType, GroupByBlock> r1 =
			new PropertyGroupByPruningRule(new PropertyIndexerPruningAction());

		/*
		 * indexer, indexer ==> composite indexer
		 */
		PruningRule<EGroupByBlockType, GroupByBlock> r2 =
			new CompositeIndexerPruningRule(new CompositeIndexerPruningAction());

		return Arrays.asList(r1, r2);
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
