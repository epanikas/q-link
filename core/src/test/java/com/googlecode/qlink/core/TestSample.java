package com.googlecode.qlink.core;

import org.junit.Assert;
import org.junit.Test;

import com.googlecode.qlink.api.functor.SamplePredicate;
import com.googlecode.qlink.core.context.IPipelineDefinition;
import com.googlecode.qlink.core.context.enums.ESampleType;
import com.googlecode.qlink.core.factory.PipelineDefFactory;

public class TestSample
{
	private final PipelineDefFactory simpleFactory = new PipelineDefFactory();

	@Test
	public void testEvenOdd()
	{
		/*
		 * when
		 */
		IPipelineDefinition defEven = //
			simpleFactory.forType(Integer.class).sample().even().plugin().getPipelineDef();
		IPipelineDefinition defOdd = //
			simpleFactory.forType(Integer.class).sample().odd().plugin().getPipelineDef();

		/*
		 * should
		 */
		Assert.assertNull(defEven.getSamplePredicate());
		Assert.assertEquals(ESampleType.even, defEven.getSampleType());
		Assert.assertNull(defOdd.getSamplePredicate());
		Assert.assertEquals(ESampleType.odd, defOdd.getSampleType());
	}

	@Test
	public void testFirstLastMiddle()
	{
		/*
		 * when
		 */
		IPipelineDefinition defFirst = //
			simpleFactory.forType(Integer.class).sample().first().plugin().getPipelineDef();
		IPipelineDefinition defLast = //
			simpleFactory.forType(Integer.class).sample().last().plugin().getPipelineDef();
		IPipelineDefinition defMiddle = //
			simpleFactory.forType(Integer.class).sample().middle().plugin().getPipelineDef();

		/*
		 * should
		 */
		Assert.assertNull(defFirst.getSamplePredicate());
		Assert.assertEquals(ESampleType.first, defFirst.getSampleType());
		Assert.assertNull(defLast.getSamplePredicate());
		Assert.assertEquals(ESampleType.last, defLast.getSampleType());
		Assert.assertNull(defMiddle.getSamplePredicate());
		Assert.assertEquals(ESampleType.middle, defMiddle.getSampleType());
	}

	@Test
	public void testHeadTailNth()
	{
		/*
		 * when
		 */
		IPipelineDefinition defHead = //
			simpleFactory.forType(Integer.class).sample().head(5).plugin().getPipelineDef();
		IPipelineDefinition defTail = //
			simpleFactory.forType(Integer.class).sample().tail(5).plugin().getPipelineDef();
		IPipelineDefinition defNth = //
			simpleFactory.forType(Integer.class).sample().nth(5).plugin().getPipelineDef();

		/*
		 * should
		 */
		Assert.assertNull(defHead.getSamplePredicate());
		Assert.assertEquals(ESampleType.head, defHead.getSampleType());
		Assert.assertEquals(5, defHead.getSampleParam());

		Assert.assertNull(defTail.getSamplePredicate());
		Assert.assertEquals(ESampleType.tail, defTail.getSampleType());
		Assert.assertEquals(5, defTail.getSampleParam());

		Assert.assertNull(defNth.getSamplePredicate());
		Assert.assertEquals(ESampleType.nth, defNth.getSampleType());
		Assert.assertEquals(5, defNth.getSampleParam());
	}

	@Test
	public void testWithPredicate()
	{

		/*
		 * given
		 */
		SamplePredicate p = new SamplePredicate() {

			@Override
			public boolean evaluate(int i, int size)
			{
				throw new UnsupportedOperationException("evaluate not implemented");
			}
		};

		/*
		 * when
		 */
		IPipelineDefinition defPredicate = //
			simpleFactory.forType(Integer.class).sample().with(p).plugin().getPipelineDef();

		/*
		 * should
		 */
		Assert.assertEquals(p, defPredicate.getSamplePredicate());
	}
}
