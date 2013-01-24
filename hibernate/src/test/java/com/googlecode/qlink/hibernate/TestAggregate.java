package com.googlecode.qlink.hibernate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.googlecode.qlink.api.functor.Folder;
import com.googlecode.qlink.api.tuple.Tuple3;
import com.googlecode.qlink.hibernate.da.Person;
import com.googlecode.qlink.hibernate.da.TestUtils;
import com.googlecode.qlink.hibernate.factory.QLinkHibernateFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/da/test-db-config.xml"})
@TransactionConfiguration(defaultRollback = false)
public class TestAggregate
{

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Autowired
	private QLinkHibernateFactory hibernateFactory;

	@Before
	public void setUp()
	{
		TestUtils.prepareDb(hibernateTemplate);
	}

	@Test
	public void testAggregateToSum()
	{

		/*
		 * when
		 */
		int val = hibernateFactory.selectForClass(Person.class)//
			.filter().p("age", Integer.class).gt(22)//
			.select().p("age", Integer.class)//
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
		 * when
		 */
		int val = hibernateFactory.selectForClass(Person.class)//
			.filter().p("age", Integer.class).gt(22)//
			.aggregate().sumOf("age", Integer.class).toValue();

		/*
		 * should
		 */
		Assert.assertEquals(690, val);
	}

	@Test
	public void testAggregateToCount()
	{

		/*
		 * when
		 */
		long val =
			hibernateFactory.selectForClass(Person.class).filter().p("age", Integer.class).gt(40).aggregate().count()
				.toValue();

		/*
		 * should
		 */
		Assert.assertEquals(4, val);
	}

	@Test
	public void testAggregateToMin()
	{

		/*
		 * when
		 */
		int val =
			hibernateFactory.selectForClass(Person.class).filter().p("age", Integer.class).gt(22).select()
				.p("age", Integer.class).aggregate().min().toValue();

		/*
		 * should
		 */
		Assert.assertEquals(25, val);
	}

	@Test
	public void testAggregateToMax()
	{

		/*
		 * when
		 */
		int val =
			hibernateFactory.selectForClass(Person.class).filter().p("age", Integer.class).gt(22).select()
				.p("age", Integer.class).aggregate().max().toValue();

		/*
		 * should
		 */
		Assert.assertEquals(44, val);
	}

	@Test
	public void testAggregateToMinOf()
	{

		/*
		 * when
		 */
		int val =
			hibernateFactory.selectForClass(Person.class).filter().p(Person.Tp.age).gt(22).aggregate()
				.minOf(Person.Tp.age).toValue();

		/*
		 * should
		 */
		Assert.assertEquals(25, val);
	}

	@Test
	public void testAggregateToMaxOf()
	{

		/*
		 * when
		 */
		int val =
			hibernateFactory.selectForClass(Person.class).filter().p(Person.Tp.age).gt(22).aggregate()
				.maxOf(Person.Tp.age).toValue();

		/*
		 * should
		 */
		Assert.assertEquals(44, val);
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
			hibernateFactory.selectForClass(Person.class).filter().p("age", Integer.class).gt(42).select()
				.p("age", Integer.class).aggregate().sum().min().max().toValue();

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
			hibernateFactory.selectForClass(Person.class).filter().p("age", Integer.class).gt(42).foldLeft()
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
			hibernateFactory.selectForClass(Person.class).filter().p("age", Integer.class).gt(42).foldRight()
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
