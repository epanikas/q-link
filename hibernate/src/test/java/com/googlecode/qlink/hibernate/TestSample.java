package com.googlecode.qlink.hibernate;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.googlecode.qlink.api.functor.SamplePredicate;
import com.googlecode.qlink.hibernate.da.Person;
import com.googlecode.qlink.hibernate.da.TestUtils;
import com.googlecode.qlink.hibernate.factory.QLinkHibernateFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/da/test-db-config.xml"})
@TransactionConfiguration(defaultRollback = false)
public class TestSample
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

	//	@Test
	//	public void testFilterEven()
	//	{
	//
	//		/*
	//		 * when
	//		 */
	//		List<Person> lst = //
	//			hibernateFactory.selectForClass(Person.class).sample().even().toList();
	//
	//		/*
	//		 * should
	//		 */
	//	}

	@Test
	public void testEvenOdd()
	{

		/*
		 * when
		 */
		List<Person> lstEven = hibernateFactory.selectForClass(Person.class).sample().even().toList();
		List<Person> lstOdd = hibernateFactory.selectForClass(Person.class).sample().odd().toList();

		/*
		 * should
		 */
		Assert.assertEquals(10, lstEven.size());
		Assert.assertEquals("Robert", lstEven.get(1).getName());
		Assert.assertEquals(10, lstOdd.size());
		Assert.assertEquals("Michael", lstOdd.get(1).getName());
	}

	@Test
	public void testFirstLastMiddleNth()
	{

		/*
		 * when
		 */
		Person first = hibernateFactory.selectForClass(Person.class).sample().first().toValue();
		Person last = hibernateFactory.selectForClass(Person.class).sample().last().toValue();
		Person middle = hibernateFactory.selectForClass(Person.class).sample().middle().toValue();
		Person nth = hibernateFactory.selectForClass(Person.class).sample().nth(2).toValue();

		/*
		 * should
		 */
		Assert.assertEquals("James", first.getName());
		Assert.assertEquals("Brian", last.getName());
		Assert.assertEquals("Christopher", middle.getName());
		Assert.assertEquals("Robert", nth.getName());
	}

	@Test
	public void testHeadTail()
	{

		/*
		 * when
		 */
		List<Person> head = hibernateFactory.selectForClass(Person.class).sample().head(3).toList();
		List<Person> tail = hibernateFactory.selectForClass(Person.class).sample().tail(3).toList();

		/*
		 * should
		 */
		Assert.assertEquals(3, head.size());
		Assert.assertEquals("James", head.get(0).getName());
		Assert.assertEquals(3, tail.size());
		Assert.assertEquals("Steven", tail.get(0).getName());
	}

	@Test
	public void testCustom()
	{

		/*
		 * when
		 */
		List<Person> custom = hibernateFactory.selectForClass(Person.class).sample().with(new SamplePredicate() {

			@Override
			public boolean evaluate(int i, int size)
			{
				return i % 3 == 0;
			}

		}).toList();

		/*
		 * should
		 */
		Assert.assertEquals(7, custom.size());
		Assert.assertEquals("Michael", custom.get(1).getName());
	}
}
