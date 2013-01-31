package com.googlecode.qlink.mem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.googlecode.qlink.api.functor.Folder;
import com.googlecode.qlink.api.tuple.Tuple3;
import com.googlecode.qlink.mem.da.Person;
import com.googlecode.qlink.mem.da.TestUtils;
import com.googlecode.qlink.mem.factory.QLinkInMemoryFactory;

public class TestAggregate
{

	private final QLinkInMemoryFactory simpleFactory = new QLinkInMemoryFactory();

	List<Person> persons = new ArrayList<Person>();
	List<Integer> sourceNumbers = new ArrayList<Integer>();

	@Before
	public void setUp()
	{
		persons = TestUtils.generatePersons(TestUtils.names20);
		sourceNumbers = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
	}

	@Test
	public void testAggregateCount()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		long val = simpleFactory.forList(sourceNumbers).aggregate().count().toValue();

		/*
		 * should
		 */
		Assert.assertEquals(10, val);
	}

	@Test
	public void testAggregateSum()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		int val = simpleFactory.forList(sourceNumbers).aggregate().sum().toValue();

		/*
		 * should
		 */
		Assert.assertEquals(45, val);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAggregateSumOnEmptyList()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		simpleFactory.forList(Collections.<Integer> emptyList()).aggregate().sum().toValue();

		/*
		 * should
		 */
		// should throw an exception
	}

	@Test
	public void testAggregateSumOnListOfOne()
	{
		/*
		 * given
		 */
		List<Integer> listOne = Arrays.asList(2);

		/*
		 * when
		 */
		int val = simpleFactory.forList(listOne).aggregate().sum().toValue();

		/*
		 * should
		 */
		Assert.assertEquals(2, val);
	}

	@Test
	public void testFilterAggregateSum()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		int val = simpleFactory.forList(sourceNumbers).filter().self().gt(7).aggregate().sum().toValue();

		/*
		 * should
		 */
		Assert.assertEquals(17, val);
	}

	@Test
	public void testSelectAggregateToSum()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		int val =
			simpleFactory.forList(persons).filter().p("age", Integer.class).gt(22).select().p("age", Integer.class)
				.aggregate().sum().toValue();

		/*
		 * should
		 */
		Assert.assertEquals(690, val);
	}

	@Test
	public void testAggregateToSumOf()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		int val = simpleFactory.forList(persons).filter().p("age", Integer.class).gt(22)//
			.aggregate().sumOf("age", Integer.class).toValue();

		/*
		 * should
		 */
		Assert.assertEquals(690, val);
	}

	@Test
	public void testAggregateToSumMinMax()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		Tuple3<Integer, Integer, Integer> val =
			simpleFactory.forList(persons).filter().p("age", Integer.class).gt(42).select().p("age", Integer.class)
				.aggregate().sum().min().max().toValue();

		/*
		 * should
		 */
		Assert.assertEquals(87, val.getFirst().intValue());
		Assert.assertEquals(43, val.getSecond().intValue());
		Assert.assertEquals(44, val.getThird().intValue());
	}

	@Test
	public void testFoldLeft()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		String val =
			simpleFactory.forList(persons).filter().p("age", Integer.class).gt(42).foldLeft()
				.with("<==", new Folder<Person, String>() {
					@Override
					public String apply(String a, Person b)
					{
						return a + ", " + b.getName();
					}
				}).toValue();

		/*
		 * should
		 */
		Assert.assertEquals("<==, Edward, Brian", val);
	}

	@Test
	public void testFoldRight()
	{

		/*
		 * when
		 */
		String val =
			simpleFactory.forList(persons).filter().p("age", Integer.class).gt(42).foldRight()
				.with("==>", new Folder<Person, String>() {
					@Override
					public String apply(String a, Person b)
					{
						return a + ", " + b.getName();
					}
				}).toValue();

		/*
		 * should
		 */
		Assert.assertEquals("==>, Brian, Edward", val);
	}

}
