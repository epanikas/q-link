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
import com.googlecode.qlink.tuples.Tuples;

public class TestGroupBy
{

	private final PipelineDefFactory simpleFactory = new PipelineDefFactory();

	@Before
	public void setUp()
	{
		// empty
	}

	@Test
	public void testGroupBy()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		IPipelineDefinition pDef = //
			simpleFactory.forType(Person.class).group().by("name", String.class).plugin().getPipelineDef();

		/*
		 * should
		 */
		Stack<Pair<EGroupByBlockType, GroupByBlock>> groupByStack = pDef.getGroupByStack();
		Assert.assertEquals(1, groupByStack.size());
		StackAssertUtils.assertGroupByResult(CommonPruningRulesForTest.groupByPruner, groupByStack, "name");
	}

	@Test
	public void testGroupBySeveral()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		IPipelineDefinition pDef = //
			simpleFactory.forType(Person.class).group().by("name", String.class).by("age", Integer.class).plugin()
				.getPipelineDef();

		/*
		 * should
		 */
		Stack<Pair<EGroupByBlockType, GroupByBlock>> groupByStack = pDef.getGroupByStack();
		Assert.assertEquals(2, groupByStack.size());
		StackAssertUtils.assertGroupByResult(CommonPruningRulesForTest.groupByPruner, groupByStack, "name age");
	}

	@Test
	public void testGroupBySeveral2()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		IPipelineDefinition pDef = //
			simpleFactory.forType(Person.class).group().by(Person.Tp.name).by(Person.Tp.age).plugin().getPipelineDef();

		/*
		 * should
		 */
		Stack<Pair<EGroupByBlockType, GroupByBlock>> groupByStack = pDef.getGroupByStack();
		Assert.assertEquals(2, groupByStack.size());
		StackAssertUtils.assertGroupByResult(CommonPruningRulesForTest.groupByPruner, groupByStack, "name age");
	}

	@Test
	public void testGroupByWith()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		IPipelineDefinition pDef = //
			simpleFactory.forType(Person.class).group().with(new Function<Person, Pair<String, Integer>>() {

				@Override
				public Pair<String, Integer> apply(Person t)
				{
					return Tuples.tie(t.getName(), t.getAge());
				}

				@Override
				public String toString()
				{
					return "groupBy name age";
				}

			}).plugin().getPipelineDef();

		/*
		 * should
		 */
		Stack<Pair<EGroupByBlockType, GroupByBlock>> groupByStack = pDef.getGroupByStack();
		Assert.assertEquals(1, groupByStack.size());
		StackAssertUtils.assertGroupByResult(CommonPruningRulesForTest.groupByPruner, groupByStack, "groupBy name age");
	}
}
