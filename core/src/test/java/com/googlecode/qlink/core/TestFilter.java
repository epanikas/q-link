package com.googlecode.qlink.core;

import java.util.Stack;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.googlecode.qlink.api.functor.Predicate;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.IPipelineDefinition;
import com.googlecode.qlink.core.context.blocks.FilterBlock;
import com.googlecode.qlink.core.context.enums.EFilterBlockType;
import com.googlecode.qlink.core.da.Person;
import com.googlecode.qlink.core.factory.PipelineDefFactory;
import com.googlecode.qlink.core.pruning.CommonPruningRulesForTest;

public class TestFilter
{
	private final PipelineDefFactory simpleFactory = new PipelineDefFactory();

	@Before
	public void setUp()
	{
		// empty
	}

	@Test
	public void testFilterWithPredicate()
	{

		/*
		 * when
		 */
		IPipelineDefinition pDef = simpleFactory.forType(String.class)//
			.filter().with(new Predicate<String>() {

				@Override
				public boolean evaluate(String object)
				{
					return object.equals("a");
				}

				@Override
				public String toString()
				{
					return "x = 'a'";
				}
			})//
			.plugin().getPipelineDef();

		Stack<Pair<EFilterBlockType, FilterBlock>> filterStack = pDef.getFilterStack();

		/*
		 * should
		 */
		Assert.assertEquals(1, filterStack.size());
		StackAssertUtils.assertFilterResult(CommonPruningRulesForTest.filterPruner, filterStack, "x = 'a'");
	}

	@Test
	public void testFilterOr()
	{

		/*
		 * when
		 */
		IPipelineDefinition pDef = simpleFactory.forType(Person.class).filter()//
			.p("age", Integer.class).ne(12).or()//
			.p("name", String.class).eq("bob")//
			.plugin().getPipelineDef();

		/*
		 * should
		 */
		Stack<Pair<EFilterBlockType, FilterBlock>> filterStack = pDef.getFilterStack();
		StackAssertUtils.assertFilterResult(CommonPruningRulesForTest.filterPruner, filterStack,
			"age neq 12 or name eq bob");
	}

	@Test
	public void testFilterAnd()
	{

		/*
		 * when
		 */
		IPipelineDefinition pDef = simpleFactory.forType(Person.class).filter()//
			.p("name", String.class).eq("bob").and().p("age", Integer.class).eq(11)//
			.plugin().getPipelineDef();

		/*
		 * should
		 */
		Stack<Pair<EFilterBlockType, FilterBlock>> filterStack = pDef.getFilterStack();
		StackAssertUtils.assertFilterResult(CommonPruningRulesForTest.filterPruner, filterStack,
			"name eq bob and age eq 11");
	}

	@Test
	public void testFilterBeginEnd()
	{

		/*
		 * when
		 */
		IPipelineDefinition pDef = //
			simpleFactory.forType(Person.class).filter()//
				.begin()//
				.p("name", String.class).eq("bob")//
				.or()//
				.p("age", Integer.class).eq(11)//
				.end()//
				.plugin().getPipelineDef();

		/*
		 * should
		 */
		Stack<Pair<EFilterBlockType, FilterBlock>> filterStack = pDef.getFilterStack();
		StackAssertUtils.assertFilterResult(CommonPruningRulesForTest.filterPruner, filterStack,
			"( name eq bob or age eq 11 )");
	}

	@Test
	public void testFilterBeginEnd1()
	{

		/*
		 * when
		 */
		IPipelineDefinition pDef = //
			simpleFactory.forType(Person.class).filter()//
				.begin().begin().begin().begin()//
				.p("age").eq(15)//
				.or()//
				.p("age").eq(18).end()//
				.and().p("name").eq("smith")//
				.end().end().end()//
				.plugin().getPipelineDef();

		/*
		 * should
		 */
		Stack<Pair<EFilterBlockType, FilterBlock>> filterStack = pDef.getFilterStack();
		Assert.assertEquals(19, filterStack.size());
		StackAssertUtils.assertFilterResult(CommonPruningRulesForTest.filterPruner, filterStack,
			"( ( ( ( age eq 15 or age eq 18 ) and name eq smith ) ) )");

	}

	@Test
	public void testFilterSelf()
	{

		/*
		 * when
		 */
		IPipelineDefinition pDef = //
			simpleFactory.forType(Integer.class).filter().self().gt(5)//
				.plugin().getPipelineDef();

		/*
		 * should
		 */
		Stack<Pair<EFilterBlockType, FilterBlock>> filterStack = pDef.getFilterStack();
		Assert.assertEquals(3, filterStack.size());
		StackAssertUtils.assertFilterResult(CommonPruningRulesForTest.filterPruner, filterStack, "elem gt 5");
	}

	//	@Test
	//	public void testFilterExpressoin()
	//	{
	//		/*
	//		 * when
	//		 */
	//		IPipelineDefinition pDef = //
	//			simpleFactory.forType(Person.class).filter().expr("blocked + originalQty", Integer.class).geVal(45)//
	//				.plugin().getPipelineDef();
	//
	//		/*
	//		 * should
	//		 */
	//		Stack<Pair<EFilterChainType, FilterBlock>> filterStack = pDef.getFilterStack();
	//		Assert.assertEquals(3, filterStack.size());
	//		StackAssertUtils.assertFilterResult(CommonPruningRulesForTest.filterPruner, filterStack,
	//			"blocked + originalQty ge 45");
	//	}

	@Test
	public void testEq()
	{
		/*
		 * when
		 */
		IPipelineDefinition pDef = //
			simpleFactory.forType(Person.class).filter().p("name").eq().val("smith")//
				.plugin().getPipelineDef();

		/*
		 * should
		 */
		Stack<Pair<EFilterBlockType, FilterBlock>> filterStack = pDef.getFilterStack();
		Assert.assertEquals(3, filterStack.size());
		StackAssertUtils.assertFilterResult(CommonPruningRulesForTest.filterPruner, filterStack, "name eq smith");
	}

	@Test
	public void testNotEq()
	{
		/*
		 * when
		 */
		IPipelineDefinition pDef = //
			simpleFactory.forType(Person.class).filter().not().p("name").eq().val("smith")//
				.plugin().getPipelineDef();

		/*
		 * should
		 */
		Stack<Pair<EFilterBlockType, FilterBlock>> filterStack = pDef.getFilterStack();
		Assert.assertEquals(4, filterStack.size());
		StackAssertUtils.assertFilterResult(CommonPruningRulesForTest.filterPruner, filterStack, "not name eq smith");
	}

	@Test
	public void testNotEqBegEnd()
	{
		/*
		 * when
		 */
		IPipelineDefinition pDef = //
			simpleFactory.forType(Person.class).filter().not().begin().p("name").eq().val("smith").end()//
				.plugin().getPipelineDef();

		/*
		 * should
		 */
		Stack<Pair<EFilterBlockType, FilterBlock>> filterStack = pDef.getFilterStack();
		Assert.assertEquals(6, filterStack.size());
		StackAssertUtils.assertFilterResult(CommonPruningRulesForTest.filterPruner, filterStack,
			"not ( name eq smith )");

	}
}
