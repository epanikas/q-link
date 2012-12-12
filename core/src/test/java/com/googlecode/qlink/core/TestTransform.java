package com.googlecode.qlink.core;

import java.util.Stack;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.googlecode.qlink.api.functor.Function;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.IPipelineDefinition;
import com.googlecode.qlink.core.context.blocks.TransformBlock;
import com.googlecode.qlink.core.context.enums.ETransformBlockType;
import com.googlecode.qlink.core.context.enums.ETransformResultType;
import com.googlecode.qlink.core.da.Person;
import com.googlecode.qlink.core.factory.PipelineDefFactory;
import com.googlecode.qlink.core.pruning.CommonPruningRulesForTest;

public class TestTransform
{
	private final PipelineDefFactory simpleFactory = new PipelineDefFactory();

	@Before
	public void setUp()
	{
		// empty
	}

	@Test
	public void testTransformSingleWith()
	{

		/*
		 * when
		 */
		IPipelineDefinition pDef = //
			simpleFactory.forType(Integer.class).select().with(new Function<Integer, String>() {
				@Override
				public String apply(Integer input)
				{
					return "#" + input;
				}

				@Override
				public String toString()
				{
					return "enumerator";
				}

			}).plugin().getPipelineDef();

		/*
		 * should
		 */

		Assert.assertEquals(ETransformResultType.tuple, pDef.getTransformResultType());
		Stack<Pair<ETransformBlockType, TransformBlock>> transformStack = pDef.getTransformStack();
		Assert.assertTrue(transformStack.size() == 1);
		StackAssertUtils.assertTransformResult(CommonPruningRulesForTest.transformPruner, transformStack, "enumerator");
	}

	@Test
	public void testTransformAsPair()
	{
		/*
		 * when
		 */
		IPipelineDefinition pDef = //
			simpleFactory.forType(Person.class).select()//
				.p("name", String.class).p("age", Integer.class)//
				.plugin().getPipelineDef();

		/*
		 * should
		 */
		Assert.assertEquals(ETransformResultType.tuple, pDef.getTransformResultType());
		Stack<Pair<ETransformBlockType, TransformBlock>> transformStack = pDef.getTransformStack();
		Assert.assertTrue(transformStack.size() == 2);
		StackAssertUtils.assertTransformResult(CommonPruningRulesForTest.transformPruner, transformStack, "name age");

	}

	@Test
	public void testTransformAs4Tuple()
	{
		/*
		 * when
		 */
		IPipelineDefinition pDef = //
			simpleFactory.forType(Person.class).select()//
				.p("name", String.class).p("age", Integer.class)//
				.p("name", String.class).p("age", Integer.class)//
				.plugin().getPipelineDef();

		/*
		 * should
		 */
		Assert.assertEquals(ETransformResultType.tuple, pDef.getTransformResultType());
		Stack<Pair<ETransformBlockType, TransformBlock>> transformStack = pDef.getTransformStack();
		Assert.assertTrue(transformStack.size() == 4);
		StackAssertUtils.assertTransformResult(CommonPruningRulesForTest.transformPruner, transformStack,
			"name age name age");
	}

	@Test
	public void testTransformAsCompositeTuple()
	{
		/*
		 * when
		 */
		IPipelineDefinition pDef = //
			simpleFactory.forType(Person.class).select()//
				.p("name", String.class).p("age", Integer.class)//
				.p("name", String.class).p("age", Integer.class)//
				.p("name", String.class).p("age", Integer.class)//
				.plugin().getPipelineDef();

		/*
		 * should
		 */
		Assert.assertEquals(ETransformResultType.tuple, pDef.getTransformResultType());
		Stack<Pair<ETransformBlockType, TransformBlock>> transformStack = pDef.getTransformStack();
		Assert.assertTrue(transformStack.size() == 6);
		StackAssertUtils.assertTransformResult(CommonPruningRulesForTest.transformPruner, transformStack,
			"name age name age name age");
	}

	@Test
	public void testFilterSelect()
	{
		/*
		 * when
		 */
		IPipelineDefinition pDef = //
			simpleFactory.forType(Person.class)//
				.filter().p("age", Integer.class).gt(12)//
				.select().p("name", String.class).p("age", Integer.class)//
				.plugin().getPipelineDef();

		/*
		 * should
		 */
		Assert.assertEquals(ETransformResultType.tuple, pDef.getTransformResultType());
		Stack<Pair<ETransformBlockType, TransformBlock>> transformStack = pDef.getTransformStack();
		Assert.assertTrue(transformStack.size() == 2);
		StackAssertUtils.assertTransformResult(CommonPruningRulesForTest.transformPruner, transformStack, "name age");
	}

	@Test
	public void testAsSingleObject()
	{
		/*
		 * when
		 */
		IPipelineDefinition pDef = //
			simpleFactory.forType(Person.class).select().p("name", String.class).plugin().getPipelineDef();

		/*
		 * should
		 */
		Assert.assertEquals(ETransformResultType.tuple, pDef.getTransformResultType());
		Stack<Pair<ETransformBlockType, TransformBlock>> transformStack = pDef.getTransformStack();
		Assert.assertTrue(transformStack.size() == 1);
		StackAssertUtils.assertTransformResult(CommonPruningRulesForTest.transformPruner, transformStack, "name");
	}

	public void testAsSingleObjectShouldntCompile()
	{
		//		IPipelineDefinition pDef = //
		//			simpleFactory.forPipelineDef(Person.class).select().asSingleObject()
		//				.prop("name", String.class).prop("age").toPostProcessor().getPipelineDef();
	}

	public static final class MyCoolJob
	{
		private final String title;
		private final String employeeName;

		public MyCoolJob(String title, String employeeName)
		{
			this.title = title;
			this.employeeName = employeeName;
		}

		public String getEmployeeName()
		{
			return employeeName;
		}

		public String getTitle()
		{
			return title;
		}
	}

	@Test
	public void testAsNewObject()
	{
		/*
		 * when
		 */
		IPipelineDefinition pDef = //
			simpleFactory.forType(Person.class).select().val("guru").p("name", String.class).asNew(MyCoolJob.class)
				.plugin().getPipelineDef();

		/*
		 * should
		 */
		Assert.assertEquals(ETransformResultType.newObject, pDef.getTransformResultType());
		Stack<Pair<ETransformBlockType, TransformBlock>> transformStack = pDef.getTransformStack();
		Assert.assertTrue(transformStack.size() == 2);
		StackAssertUtils.assertTransformResult(CommonPruningRulesForTest.transformPruner, transformStack, "guru name");
	}

	@Test
	public void testAsArray()
	{
		/*
		 * when
		 */
		IPipelineDefinition pDef = //
			simpleFactory.forType(Person.class).select().val("guru").p("name", String.class).asArray().plugin()
				.getPipelineDef();

		/*
		 * should
		 */
		Assert.assertEquals(ETransformResultType.arrayObject, pDef.getTransformResultType());
		Stack<Pair<ETransformBlockType, TransformBlock>> transformStack = pDef.getTransformStack();
		Assert.assertTrue(transformStack.size() == 2);
		StackAssertUtils.assertTransformResult(CommonPruningRulesForTest.transformPruner, transformStack, "guru name");
	}
}
