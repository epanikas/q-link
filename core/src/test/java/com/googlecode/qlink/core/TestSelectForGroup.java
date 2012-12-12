package com.googlecode.qlink.core;

import java.util.Map;
import java.util.Stack;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.IPipelineDefinition;
import com.googlecode.qlink.core.context.blocks.TransformBlock;
import com.googlecode.qlink.core.context.enums.ETransformBlockType;
import com.googlecode.qlink.core.context.enums.ETransformResultType;
import com.googlecode.qlink.core.da.Person;
import com.googlecode.qlink.core.factory.PipelineDefFactory;
import com.googlecode.qlink.core.pruning.CommonPruningRulesForTest;

public class TestSelectForGroup
{
	private final PipelineDefFactory simpleFactory = new PipelineDefFactory();

	@Before
	public void setUp()
	{
		//
	}

	@Test
	public void testSelectMinToTuple()
	{
		/*
		 * when
		 */
		IPipelineDefinition pDef = //
			simpleFactory.forType(Person.class).group().by("name", String.class).selectAs().key()
				.minOf("age", Integer.class).plugin().getPipelineDef();

		/*
		 * should
		 */
		Assert.assertEquals(ETransformResultType.tuple, pDef.getSelectGroupResultType());
		Stack<Pair<ETransformBlockType, TransformBlock>> selectGroupStack = pDef.getSelectGroupStack();
		Assert.assertEquals(2, selectGroupStack.size());
		StackAssertUtils.assertTransformResult(CommonPruningRulesForTest.transformPruner, selectGroupStack, "key min");
	}

	public static class MyDto
	{
		private String name;
		private Integer minAge;
		private Integer maxAge;

		public MyDto(String name, Integer minAge, Integer maxAge)
		{
			this.name = name;
			this.minAge = minAge;
			this.maxAge = maxAge;
		}

		public String getName()
		{
			return name;
		}

		public void setName(String name)
		{
			this.name = name;
		}

		public Integer getMinAge()
		{
			return minAge;
		}

		public void setMinAge(Integer minAge)
		{
			this.minAge = minAge;
		}

		public Integer getMaxAge()
		{
			return maxAge;
		}

		public void setMaxAge(Integer maxAge)
		{
			this.maxAge = maxAge;
		}

	}

	@Test
	public void testSelectGroupAsNew()
	{
		/*
		 * when
		 */
		IPipelineDefinition pDef = //
			simpleFactory.forType(Person.class).group().by("name", String.class).selectAs().key()
				.minOf("age", Integer.class).maxOf("age", Integer.class).asNew(MyDto.class).plugin().getPipelineDef();

		try {
			/*
			 * should just compile
			 */
			@SuppressWarnings("unused")
			Map<String, MyDto> res =
				simpleFactory.forType(Person.class).group().by("name", String.class).selectAs().key()
					.minOf("age", Integer.class).maxOf("age", Integer.class).asNew(MyDto.class).toMap();
		}
		catch (Exception e) {
			// silence...
		}

		/*
		 * should
		 */
		Assert.assertEquals(ETransformResultType.newObject, pDef.getSelectGroupResultType());
		Assert.assertEquals(MyDto.class, pDef.getSelectGroupResultClass());
		Stack<Pair<ETransformBlockType, TransformBlock>> selectGroupStack = pDef.getSelectGroupStack();
		Assert.assertEquals(3, selectGroupStack.size());
		StackAssertUtils.assertTransformResult(CommonPruningRulesForTest.transformPruner, selectGroupStack,
			"key min max");
	}
}
