package com.googlecode.qlink.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.googlecode.qlink.api.functor.Folder;
import com.googlecode.qlink.api.functor.Reducer;
import com.googlecode.qlink.core.context.IPipelineDefinition;
import com.googlecode.qlink.core.context.enums.EFoldSide;
import com.googlecode.qlink.core.da.Person;
import com.googlecode.qlink.core.factory.PipelineDefFactory;

public class TestAggregate
{

	private final PipelineDefFactory simpleFactory = new PipelineDefFactory();

	@Before
	public void setUp()
	{
		// empty
	}

	@Test
	public void testAggregateSum()
	{

		/*
		 * when
		 */
		IPipelineDefinition pDef = //
			simpleFactory.forType(Integer.class).aggregate().sum().plugin().getPipelineDef();

		/*
		 * should
		 */
		Assert.assertEquals(1, pDef.getAggregators().size());
	}

	@Test
	public void testFilterAggregate()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		IPipelineDefinition pDef = //
			simpleFactory.forType(Integer.class).filter().self().gt(7).aggregate().sum().plugin().getPipelineDef();

		/*
		 * should
		 */
		Assert.assertEquals(1, pDef.getAggregators().size());
	}

	@Test
	public void testFold()
	{
		/*
		 * given
		 */
		Folder<Person, String> myFolder = new Folder<Person, String>() {
			@Override
			public String apply(String a, Person b)
			{
				return a + ", " + b.getName();
			}
		};

		/*
		 * when
		 */
		IPipelineDefinition foldLeft = //
			simpleFactory.forType(Person.class).filter().p("age", Integer.class).gt(22).foldLeft().with("", myFolder)
				.plugin().getPipelineDef();
		IPipelineDefinition foldRight = //
			simpleFactory.forType(Person.class).filter().p("age", Integer.class).gt(22).foldRight().with("", myFolder)
				.plugin().getPipelineDef();

		/*
		 * should
		 */
		Assert.assertEquals(0, foldLeft.getAggregators().size());
		Assert.assertEquals(myFolder, foldLeft.getFolder());
		Assert.assertEquals("", foldLeft.getFoldInit());
		Assert.assertEquals("", foldRight.getFoldInit());
		Assert.assertEquals(EFoldSide.left, foldLeft.getFoldSide());
		Assert.assertEquals(EFoldSide.right, foldRight.getFoldSide());
	}

	@Test
	public void testReduce()
	{
		/*
		 * given
		 */
		Reducer<Integer> sum = new Reducer<Integer>() {
			@Override
			public Integer reduce(Integer a, Integer b)
			{
				return a + b;
			}
		};

		/*
		 * when
		 */
		IPipelineDefinition reduce = //
			simpleFactory.forType(Person.class)//
				.filter().p(Person.Tp.age).gt(22)//
				.select().p(Person.Tp.age)//
				.reduce().with(sum).plugin().getPipelineDef();

		/*
		 * should
		 */
		Assert.assertEquals(0, reduce.getAggregators().size());
		Assert.assertEquals(sum, reduce.getReducer());

	}
}
