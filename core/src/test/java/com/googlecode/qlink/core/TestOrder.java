package com.googlecode.qlink.core;

import java.util.Comparator;
import java.util.Stack;

import junit.framework.Assert;

import org.junit.Test;

import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.IPipelineDefinition;
import com.googlecode.qlink.core.context.blocks.FilterBlock;
import com.googlecode.qlink.core.context.blocks.OrderBlock;
import com.googlecode.qlink.core.context.enums.EFilterBlockType;
import com.googlecode.qlink.core.context.enums.EOrderBlockType;
import com.googlecode.qlink.core.da.Person;
import com.googlecode.qlink.core.factory.PipelineDefFactory;
import com.googlecode.qlink.core.pruning.CommonPruningRulesForTest;

public class TestOrder
{
	private final PipelineDefFactory simpleFactory = new PipelineDefFactory();

	@Test
	public void testOrderComparator()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		IPipelineDefinition pDef = //
			simpleFactory.forType(String.class)//
				.order().with(new Comparator<String>() {

					@Override
					public int compare(String o1, String o2)
					{
						return o1.compareTo(o2);
					}

					@Override
					public String toString()
					{
						return "string comparator";
					}

				}).plugin().getPipelineDef();

		/*
		 * should
		 */
		Stack<Pair<EOrderBlockType, OrderBlock>> orderStack = pDef.getOrderStack();
		Assert.assertTrue(orderStack.size() == 1);
		StackAssertUtils.assertOrderResult(CommonPruningRulesForTest.orderPruner, orderStack, "string comparator");
	}

	@Test
	public void testPropertyComparator()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		IPipelineDefinition pDef = //
			simpleFactory.forType(Person.class).order().by("name").asc().by("age").desc().plugin().getPipelineDef();

		/*
		 * should
		 */
		Stack<Pair<EOrderBlockType, OrderBlock>> orderStack = pDef.getOrderStack();
		Assert.assertTrue(orderStack.size() == 4);
		StackAssertUtils.assertOrderResult(CommonPruningRulesForTest.orderPruner, orderStack, "name asc age desc");
	}

	@Test
	public void testPropertyComparator2()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		IPipelineDefinition pDef =
			simpleFactory.forType(Person.class).order().by("name").asc().by("age").plugin().getPipelineDef();

		/*
		 * should
		 */
		Stack<Pair<EOrderBlockType, OrderBlock>> orderStack = pDef.getOrderStack();
		Assert.assertTrue(orderStack.size() == 3);
		StackAssertUtils.assertOrderResult(CommonPruningRulesForTest.orderPruner, orderStack, "name asc age");
	}

	@Test
	public void testFilterAndOrder()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		IPipelineDefinition pDef =
			simpleFactory.forType(Person.class).filter().p("name").eq("bob").order().by("name").desc().by("age")
				.plugin().getPipelineDef();

		/*
		 * should
		 */
		Stack<Pair<EOrderBlockType, OrderBlock>> orderStack = pDef.getOrderStack();
		Assert.assertTrue(orderStack.size() == 3);
		StackAssertUtils.assertOrderResult(CommonPruningRulesForTest.orderPruner, orderStack, "name desc age");

		Stack<Pair<EFilterBlockType, FilterBlock>> filterStack = pDef.getFilterStack();
		Assert.assertEquals(3, filterStack.size());
		StackAssertUtils.assertFilterResult(CommonPruningRulesForTest.filterPruner, filterStack, "name eq bob");
	}
}
