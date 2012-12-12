package com.googlecode.qlink.core;

import java.util.Stack;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.googlecode.qlink.api.functor.Function;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.IPipelineDefinition;
import com.googlecode.qlink.core.context.blocks.GroupByBlock;
import com.googlecode.qlink.core.context.enums.EGroupByBlockType;
import com.googlecode.qlink.core.da.Person;
import com.googlecode.qlink.core.factory.PipelineDefFactory;
import com.googlecode.qlink.core.pruning.CommonPruningRulesForTest;

public class TestIndex
{
	private final PipelineDefFactory simpleFactory = new PipelineDefFactory();

	@Before
	public void setUp()
	{
		// empty
	}

	@Test
	public void testIndexBy()
	{

		/*
		 * when
		 */
		IPipelineDefinition pDef = //
			simpleFactory.forType(Person.class).index().by("name", String.class).plugin().getPipelineDef();

		/*
		 * should
		 */
		Stack<Pair<EGroupByBlockType, GroupByBlock>> indexStack = pDef.getIndexStack();
		Assert.assertEquals(1, indexStack.size());
		StackAssertUtils.assertGroupByResult(CommonPruningRulesForTest.groupByPruner, indexStack, "name");
	}

	@Test
	public void testIndexBySeveral()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		IPipelineDefinition pDef = //
			simpleFactory.forType(Person.class).index().by("name", String.class).by("age", Integer.class).plugin()
				.getPipelineDef();

		/*
		 * should
		 */
		Stack<Pair<EGroupByBlockType, GroupByBlock>> indexStack = pDef.getIndexStack();
		Assert.assertEquals(2, indexStack.size());
		StackAssertUtils.assertGroupByResult(CommonPruningRulesForTest.groupByPruner, indexStack, "name age");
	}

	@Test
	public void testIndexBySeveral2()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		IPipelineDefinition pDef = //
			simpleFactory.forType(Person.class).index().by(Person.Tp.name).by(Person.Tp.age).plugin().getPipelineDef();

		/*
		 * should
		 */
		Stack<Pair<EGroupByBlockType, GroupByBlock>> indexStack = pDef.getIndexStack();
		Assert.assertEquals(2, indexStack.size());
		StackAssertUtils.assertGroupByResult(CommonPruningRulesForTest.groupByPruner, indexStack, "name age");
	}

	@Test
	public void testIndexByCustom()
	{
		/*
		 * given
		 */
		Function<Person, String> f = new Function<Person, String>() {
			@Override
			public String apply(Person input)
			{
				throw new UnsupportedOperationException("apply not implemented");
			}

			@Override
			public String toString()
			{
				return "custom";
			}
		};

		/*
		 * when
		 */
		IPipelineDefinition pDef = //
			simpleFactory.forType(Person.class).index().by(Person.Tp.name).by(f).plugin().getPipelineDef();

		/*
		 * should
		 */
		Stack<Pair<EGroupByBlockType, GroupByBlock>> indexStack = pDef.getIndexStack();
		Assert.assertEquals(2, indexStack.size());
		StackAssertUtils.assertGroupByResult(CommonPruningRulesForTest.groupByPruner, indexStack, "name custom");
	}

}
