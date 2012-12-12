package com.googlecode.qlink.core;

import java.util.Stack;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.IPipelineDefinition;
import com.googlecode.qlink.core.context.blocks.FilterBlock;
import com.googlecode.qlink.core.context.enums.EFilterBlockType;
import com.googlecode.qlink.core.da.Person;
import com.googlecode.qlink.core.factory.PipelineDefFactory;
import com.googlecode.qlink.core.pruning.CommonPruningRulesForTest;

public class TestHaving
{

	private final PipelineDefFactory simpleFactory = new PipelineDefFactory();

	@Before
	public void setUp()
	{
		// empty
	}

	@Test
	public void testHavingCount()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		IPipelineDefinition pDef = //
			simpleFactory.forType(Person.class).group().by("name", String.class).having().count().eq(3).plugin()
				.getPipelineDef();

		/*
		 * should
		 */
		Assert.assertEquals(0, pDef.getFilterStack().size());
		Stack<Pair<EFilterBlockType, FilterBlock>> havingStack = pDef.getHavingStack();
		Assert.assertEquals(3, havingStack.size());
		StackAssertUtils.assertHavingResult(CommonPruningRulesForTest.filterPruner, havingStack, "count eq 3");
	}

	@Test
	public void testHavingCountAndSum()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		IPipelineDefinition pDef = //
			simpleFactory.forType(Person.class).group().by("age", Integer.class).having().count().gt(1).plugin()
				.getPipelineDef();

		/*
		 * should
		 */
		Assert.assertEquals(0, pDef.getFilterStack().size());
		Stack<Pair<EFilterBlockType, FilterBlock>> havingStack = pDef.getHavingStack();
		Assert.assertEquals(3, havingStack.size());
		StackAssertUtils.assertHavingResult(CommonPruningRulesForTest.filterPruner, havingStack, "count gt 1");
	}

	@Test
	public void testHavingComposite()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		IPipelineDefinition pDef = //
			simpleFactory.forType(Person.class)
			//
				.group().by("name", String.class).having()
				//
				.begin().count().eq(3).or().count().eq(2).end().and().sumOf("age", Integer.class).gt(0)//
				.plugin().getPipelineDef();

		/*
		 * should
		 */
		Assert.assertEquals(0, pDef.getFilterStack().size());
		Stack<Pair<EFilterBlockType, FilterBlock>> havingStack = pDef.getHavingStack();
		Assert.assertEquals(13, havingStack.size());
		StackAssertUtils.assertHavingResult(CommonPruningRulesForTest.filterPruner, havingStack,
			"( count eq 3 or count eq 2 ) and sum age gt 0");
	}

	@Test
	public void testHavingComposite2()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		IPipelineDefinition pDef = //
			simpleFactory.forType(Person.class)//
				.group().by("name", String.class).having()//
				.begin().count().eq(3).or().count().eq(2).end().and()//
				.begin().sumOf("age", Integer.class).gt(30).and()//
				.sumOf("age", Integer.class).lt(45).end()//
				.plugin().getPipelineDef();

		/*
		 * should
		 */
		Assert.assertEquals(0, pDef.getFilterStack().size());
		Stack<Pair<EFilterBlockType, FilterBlock>> havingStack = pDef.getHavingStack();
		Assert.assertEquals(19, havingStack.size());
		StackAssertUtils.assertHavingResult(CommonPruningRulesForTest.filterPruner, havingStack,
			"( count eq 3 or count eq 2 ) and ( sum age gt 30 and sum age lt 45 )");
	}

}
