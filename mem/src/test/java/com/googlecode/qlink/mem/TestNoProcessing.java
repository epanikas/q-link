package com.googlecode.qlink.mem;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.googlecode.qlink.mem.factory.ListSourceFactory;

public class TestNoProcessing {

	private final ListSourceFactory simpleFactory = new ListSourceFactory();

	@Test
	public void testForFew() {
		/*
		 * given
		 */

		/*
		 * when
		 */
		List<String> res = simpleFactory.forFewItems("a", "b", "c").toList();

		/*
		 * should
		 */
		Assert.assertTrue(res.size() == 3);
		Assert.assertEquals("a", res.get(0));
		Assert.assertEquals("b", res.get(1));
		Assert.assertEquals("c", res.get(2));
	}

	@Test
	public void testForList() {
		/*
		 * given
		 */
		List<String> lst = Arrays.asList("a", "b", "c");

		/*
		 * when
		 */

		List<String> res = simpleFactory.forList(lst).toList();

		/*
		 * should
		 */
		Assert.assertTrue(res.size() == 3);
		Assert.assertTrue(res != lst); // we don't wont the original list to be impacted by the processing
		Assert.assertEquals("a", res.get(0));
		Assert.assertEquals("b", res.get(1));
		Assert.assertEquals("c", res.get(2));
	}

}
