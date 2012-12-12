package com.googlecode.qlink.hibernate;

import java.util.Comparator;
import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.googlecode.qlink.hibernate.da.Person;
import com.googlecode.qlink.hibernate.da.TestUtils;
import com.googlecode.qlink.hibernate.factory.HibernateFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/da/test-db-config.xml"})
@TransactionConfiguration(defaultRollback = false)
public class TestOrder
{
	Logger logger = Logger.getLogger(TestOrder.class);

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Autowired
	private HibernateFactory hibernateFactory;

	@Before
	public void setUp()
	{

		TestUtils.clearDb(hibernateTemplate);

		List<Person> persons = TestUtils.generatePersons(TestUtils.names20);

		persons.get(1).setName("Alain");
		persons.get(3).setName("Alain");
		persons.get(5).setName("Alain");
		persons.get(2).setName("Bob");
		persons.get(4).setName("Bob");
		persons.get(6).setName("Bob");

		TestUtils.savePersons(hibernateTemplate, persons);
	}

	@Test
	public void testOrderComparator()
	{
		/*
		 * given
		 */
		Comparator<Person> cp = new Comparator<Person>() {
			@Override
			public int compare(Person o1, Person o2)
			{
				return -o1.getAge().compareTo(o2.getAge());
			}

		};

		/*
		 * when
		 */
		List<Person> res = hibernateFactory.selectForClass(Person.class)//
			.order().with(cp).toList();

		/*
		 * should
		 */
		Assert.assertEquals(20, res.size());
		Person prev = res.get(0);
		for (Person elem : res) {
			Assert.assertTrue(prev.getAge() >= elem.getAge());
			prev = elem;
		}
	}

	@Test
	public void testPropertyComparator()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		List<Person> res = hibernateFactory.selectForClass(Person.class)//
			.order().by("name").asc().by("age").desc().toList();

		/*
		 * should
		 */
		Assert.assertEquals(20, res.size());
		Assert.assertEquals("Alain", res.get(0).getName());
		Assert.assertEquals(Integer.valueOf(30), res.get(0).getAge());
		Assert.assertEquals("Alain", res.get(1).getName());
		Assert.assertEquals(Integer.valueOf(28), res.get(1).getAge());
		Assert.assertEquals("Alain", res.get(2).getName());
		Assert.assertEquals(Integer.valueOf(26), res.get(2).getAge());
		Assert.assertEquals("Bob", res.get(3).getName());
		Assert.assertEquals("Bob", res.get(4).getName());
		Assert.assertEquals("Bob", res.get(5).getName());
	}

	@Test
	public void testPropertyComparator2()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		List<Person> res = hibernateFactory.selectForClass(Person.class)//
			.order().by("name").asc().by("age").toList();

		/*
		 * should
		 */
		Assert.assertEquals(20, res.size());
	}

	@Test
	public void testFilterAndOrder()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		List<Person> res =
			hibernateFactory.selectForClass(Person.class).filter().p("name").eq("Bob").order().by("age").desc()
				.toList();

		/*
		 * should
		 */
		Assert.assertEquals(3, res.size());
		Assert.assertEquals(Integer.valueOf(31), res.get(0).getAge());
		Assert.assertEquals(Integer.valueOf(27), res.get(2).getAge());

	}
}
