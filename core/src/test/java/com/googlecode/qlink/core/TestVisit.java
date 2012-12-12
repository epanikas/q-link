package com.googlecode.qlink.core;

import java.util.Stack;

import org.junit.Test;

import com.googlecode.qlink.api.functor.Visitor2;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.IPipelineDefinition;
import com.googlecode.qlink.core.context.blocks.VisitBlock;
import com.googlecode.qlink.core.context.enums.EVisitBlockType;
import com.googlecode.qlink.core.da.Person;
import com.googlecode.qlink.core.factory.PipelineDefFactory;
import com.googlecode.qlink.core.pruning.CommonPruningRulesForTest;

public class TestVisit
{

	private final PipelineDefFactory simpleFactory = new PipelineDefFactory();

	@Test
	public void testAssignVal()
	{

		/*
		 * when
		 */
		IPipelineDefinition pDef =
			simpleFactory.forType(Person.class).visit().p(Person.Tp.name).assign().val("Smith").plugin()
				.getPipelineDef();

		/*
		 * should
		 */
		Stack<Pair<EVisitBlockType, VisitBlock>> visitStack = pDef.getVisitStack();
		StackAssertUtils.assertVisitResult(CommonPruningRulesForTest.visitPruner, visitStack, "name = Smith");
	}

	@Test
	public void testAssignVal2()
	{

		/*
		 * when
		 */
		IPipelineDefinition pDef = simpleFactory.forType(Person.class).visit()//
			.p(Person.Tp.name).assign().val("Smith")//
			.p(Person.Tp.age).assign().val(12)//
			.plugin().getPipelineDef();

		/*
		 * should
		 */
		Stack<Pair<EVisitBlockType, VisitBlock>> visitStack = pDef.getVisitStack();
		StackAssertUtils.assertVisitResult(CommonPruningRulesForTest.visitPruner, visitStack, "name = Smith; age = 12");
	}

	@Test
	public void testAssignProp()
	{

		/*
		 * when
		 */
		IPipelineDefinition pDef = simpleFactory.forType(Person.class).visit()//
			.p(Person.Tp.name).assign().prop("secondName")//
			.plugin().getPipelineDef();

		/*
		 * should
		 */
		Stack<Pair<EVisitBlockType, VisitBlock>> visitStack = pDef.getVisitStack();
		StackAssertUtils.assertVisitResult(CommonPruningRulesForTest.visitPruner, visitStack, "name = secondName");
	}

	@Test
	public void testAssignProp2()
	{

		/*
		 * when
		 */
		IPipelineDefinition pDef = simpleFactory.forType(Person.class).visit()//
			.p(Person.Tp.name).assign().prop("secondName")//
			.p(Person.Tp.age).assign().val(12)//
			.plugin().getPipelineDef();

		/*
		 * should
		 */
		Stack<Pair<EVisitBlockType, VisitBlock>> visitStack = pDef.getVisitStack();
		StackAssertUtils.assertVisitResult(CommonPruningRulesForTest.visitPruner, visitStack,
			"name = secondName; age = 12");
	}

	public static class MyVisitor
		implements Visitor2<Person, Integer>
	{

		@Override
		public void apply(Person a, Integer b)
		{
			// emtpy
		}

		@Override
		public String toString()
		{
			return "my visitor";
		}
	}

	@Test
	public void testCustomVisitor()
	{
		/*
		 * given
		 */

		IPipelineDefinition pDef = simpleFactory.forType(Person.class).visit()//
			.with(new MyVisitor())//
			.p(Person.Tp.age).assign().val(12)//
			.plugin().getPipelineDef();

		/*
		 * should
		 */
		Stack<Pair<EVisitBlockType, VisitBlock>> visitStack = pDef.getVisitStack();
		StackAssertUtils.assertVisitResult(CommonPruningRulesForTest.visitPruner, visitStack, "my visitor; age = 12");

	}
}
