package com.googlecode.qlink.hibernate;

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
public class TestHaving
{

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Autowired
	private HibernateFactory hibernateFactory;

	@Before
	public void setUp()
	{
		TestUtils.clearDb(hibernateTemplate);

		List<Person> persons = TestUtils.generatePersons(TestUtils.names20);

		persons.get(2).setName("Smith");
		persons.get(4).setName("Smith");
		persons.get(6).setName("Smith");
		persons.get(1).setName("Bob");
		persons.get(1).setAge(16);
		persons.get(3).setName("Bob");
		persons.get(3).setAge(16);
		persons.get(5).setName("Bob");
		persons.get(5).setAge(16);

		TestUtils.savePersons(hibernateTemplate, persons);

	}

	@Test
	public void testHavingCount()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		Map<String, List<Person>> res =
			hibernateFactory.selectForClass(Person.class).group().by("name", String.class).having().count().eq(3)
				.toMap();

		/*
		 * should
		 */
		Assert.assertEquals(2, res.size());
		Assert.assertEquals(3, res.get("Bob").size());
		Assert.assertEquals(3, res.get("Smith").size());
	}

	@Test
	public void testHavingNameAge()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		Map<Pair<String, Integer>, List<Person>> res =
			hibernateFactory.selectForClass(Person.class).group().by("name", String.class).by("age", Integer.class)
				.having().count().eq(3).toMap();

		/*
		 * should
		 */
		Assert.assertEquals(1, res.size());
		Assert.assertEquals(3, res.get(Tuples.tie("Bob", 16)).size());
	}

	@Test
	public void testHavingCountAndSum()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		Map<Integer, List<Person>> res =
			hibernateFactory.selectForClass(Person.class).group().by("age", Integer.class).having().count().gt(1)
				.toMap();

		/*
		 * should
		 */
		Assert.assertEquals(1, res.size());
		Assert.assertEquals(3, res.get(16).size());
	}

	@Test
	public void testHavingComposite()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		Map<String, List<Person>> res = hibernateFactory.selectForClass(Person.class)
		//
			.group().by("name", String.class).having()
			//
			.begin().count().eq(3).or().count().eq(2).end().and().sumOf("age", Integer.class).gt(0)//
			.toMap();

		/*
		 * should
		 */
		Assert.assertEquals(2, res.size());
		Assert.assertEquals(3, res.get("Bob").size());
		Assert.assertEquals(3, res.get("Smith").size());
	}

	@Test
	public void testHavingComposite2()
	{

		/*
		 * when
		 */
		Map<String, List<Person>> res = //
			hibernateFactory.selectForClass(Person.class)//
				.group().by("name", String.class).having()//
				.begin().count().eq(3).or().count().eq(2).end().and()//
				.begin().sumOf("age", Integer.class).gt(30).and()//
				.sumOf("age", Integer.class).lt(145).end()//
				.toMap();

		/*
		 * should
		 */
		Assert.assertEquals(2, res.size());
		Assert.assertEquals(3, res.get("Smith").size());
	}

}
