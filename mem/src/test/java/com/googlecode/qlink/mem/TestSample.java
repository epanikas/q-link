package com.googlecode.qlink.mem;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.googlecode.qlink.api.functor.SamplePredicate;
import com.googlecode.qlink.mem.factory.ListSourceFactory;

public class TestSample
{
	private final ListSourceFactory simpleFactory = new ListSourceFactory();

	@Test
	public void testEvenOdd()
	{

		/*
		 * when
		 */
		List<Integer> lstEven = simpleFactory.forFewItems(1, 2, 3, 4, 5, 6, 7, 8, 9).sample().even().toList();
		List<Integer> lstOdd = simpleFactory.forFewItems(1, 2, 3, 4, 5, 6, 7, 8, 9).sample().odd().toList();

		/*
		 * should
		 */
		Assert.assertEquals(5, lstEven.size());
		Assert.assertEquals(Integer.valueOf(3), lstEven.get(1));
		Assert.assertEquals(4, lstOdd.size());
		Assert.assertEquals(Integer.valueOf(4), lstOdd.get(1));
	}

	@Test
	public void testFirstLastMiddleNth()
	{

		/*
		 * when
		 */
		Integer first = simpleFactory.forFewItems(1, 2, 3, 4, 5, 6, 7, 8, 9).sample().first().toValue();
		Integer last = simpleFactory.forFewItems(1, 2, 3, 4, 5, 6, 7, 8, 9).sample().last().toValue();
		Integer middle = simpleFactory.forFewItems(1, 2, 3, 4, 5, 6, 7, 8, 9).sample().middle().toValue();
		Integer nth = simpleFactory.forFewItems(1, 2, 3, 4, 5, 6, 7, 8, 9).sample().nth(2).toValue();

		/*
		 * should
		 */
		Assert.assertEquals(Integer.valueOf(1), first);
		Assert.assertEquals(Integer.valueOf(9), last);
		Assert.assertEquals(Integer.valueOf(5), middle);
		Assert.assertEquals(Integer.valueOf(3), nth);
	}

	@Test
	public void testHeadTail()
	{

		/*
		 * when
		 */
		List<Integer> head = simpleFactory.forFewItems(1, 2, 3, 4, 5, 6, 7, 8, 9).sample().head(3).toList();
		List<Integer> tail = simpleFactory.forFewItems(1, 2, 3, 4, 5, 6, 7, 8, 9).sample().tail(3).toList();

		/*
		 * should
		 */
		Assert.assertEquals(3, head.size());
		Assert.assertEquals(Integer.valueOf(1), head.get(0));
		Assert.assertEquals(3, tail.size());
		Assert.assertEquals(Integer.valueOf(7), tail.get(0));
	}

	@Test
	public void testCustom()
	{

		/*
		 * when
		 */
		List<Integer> custom =
			simpleFactory.forFewItems(1, 2, 3, 4, 5, 6, 7, 8, 9).sample().with(new SamplePredicate() {

				@Override
				public boolean evaluate(int i, int size)
				{
					return i % 3 == 0;
				}

			}).toList();

		/*
		 * should
		 */
		Assert.assertEquals(3, custom.size());
		Assert.assertEquals(Integer.valueOf(4), custom.get(1));
	}
}
