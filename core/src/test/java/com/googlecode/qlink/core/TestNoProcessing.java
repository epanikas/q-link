package com.googlecode.qlink.core;

import org.junit.Assert;
import org.junit.Test;

import com.googlecode.qlink.core.context.IPipelineDefinition;
import com.googlecode.qlink.core.factory.PipelineDefFactory;

public class TestNoProcessing
{

	private final PipelineDefFactory simpleFactory = new PipelineDefFactory();

	@Test
	public void testForFew()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		IPipelineDefinition pDef = //
			simpleFactory.forType(String.class).plugin().getPipelineDef();

		/*
		 * should
		 */
		Assert.assertEquals(0, pDef.getHavingStack().size());
		Assert.assertEquals(0, pDef.getOrderStack().size());
		Assert.assertEquals(0, pDef.getGroupByStack().size());
		Assert.assertEquals(0, pDef.getTransformStack().size());
		Assert.assertEquals(0, pDef.getVisitStack().size());
	}

}
