package com.googlecode.qlink.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.hibernate.da.Person;
import com.googlecode.qlink.hibernate.da.TestUtils;
import com.googlecode.qlink.hibernate.factory.HibernateFactory;
import com.googlecode.qlink.tuples.Tuples;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/da/test-db-config.xml"})
@TransactionConfiguration(defaultRollback = false)
public class TestIndex
{
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Autowired
	private HibernateFactory hibernateFactory;

	List<Person> persons = new ArrayList<Person>();

	@Before
	public void setUp()
	{
		persons = TestUtils.prepareDb(hibernateTemplate);
	}

	@Test
	public void testIndexBy()
	{

		/*
		 * when
		 */
		Map<String, Person> res = hibernateFactory.selectForClass(Person.class).index().by("name", String.class).toMap();

		/*
		 * should
		 */
		Assert.assertEquals(20, res.size());
		Person person1 = res.get("James");
		Assert.assertEquals("James", person1.getName());
	}

	@Test
	public void testIndexBySeveral()
	{

		/*
		 * when
		 */
		Map<Pair<String, Integer>, Person> res =
			hibernateFactory.selectForClass(Person.class).index().by("name", String.class).by("age", Integer.class).toMap();

		/*
		 * should
		 */
		Assert.assertEquals(20, res.size());
		Person person1 = res.get(Tuples.tie("James", 25));
		Assert.assertEquals("James", person1.getName());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNonUniqueIndex()
	{
		/*
		 * given
		 */
		persons.get(2).setName("James");
		persons.get(4).setName("James");

		for (Person p : persons) {
			hibernateTemplate.update(p);
		}

		/*
		 * when
		 */
		hibernateFactory.selectForClass(Person.class).index().by("name", String.class).toMap();

		/*
		 * should
		 */
		// should throw an exception
	}
}
