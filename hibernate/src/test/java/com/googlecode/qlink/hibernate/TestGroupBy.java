package com.googlecode.qlink.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import com.googlecode.qlink.api.functor.Function;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.hibernate.da.Person;
import com.googlecode.qlink.hibernate.da.TestUtils;
import com.googlecode.qlink.hibernate.factory.HibernateFactory;
import com.googlecode.qlink.tuples.Tuples;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/da/test-db-config.xml"})
@TransactionConfiguration(defaultRollback = false)
public class TestGroupBy
{
	Logger logger = Logger.getLogger(TestGroupBy.class);

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Autowired
	private HibernateFactory hibernateFactory;

	List<Person> persons = new ArrayList<Person>();

	@Before
	public void setUp()
	{
		TestUtils.clearDb(hibernateTemplate);

		persons = TestUtils.generatePersons(TestUtils.names20);

		persons.get(1).setName("Bob");
		persons.get(3).setName("Bob");
		persons.get(5).setName("Bob");
		persons.get(2).setName("Smith");
		persons.get(4).setName("Smith");
		persons.get(6).setName("Smith");

		TestUtils.savePersons(hibernateTemplate, persons);
	}

	@Test
	public void testGroupBy()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		Map<String, List<Person>> res =
			hibernateFactory.selectForClass(Person.class).group().by("name", String.class).toMap();

		/*
		 * should
		 */
		Assert.assertEquals(16, res.size());
		List<Person> bobs = res.get("Bob");
		Assert.assertEquals(3, bobs.size());
		List<Person> smiths = res.get("Smith");
		Assert.assertEquals(3, smiths.size());
	}

	@Test
	public void testGroupBySeveral()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		Map<Pair<String, Integer>, List<Person>> res =
			hibernateFactory.selectForClass(Person.class).group().by("name", String.class).by("age", Integer.class)
				.toMap();

		/*
		 * should
		 */
		Assert.assertEquals(20, res.size());
		List<Person> smith = res.get(Tuples.tie("Smith", 27));
		Assert.assertEquals(1, smith.size());
		Assert.assertEquals("Smith", smith.get(0).getName());
	}

	@Test
	public void testGroupByWith()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		Map<String, List<Integer>> res =
			hibernateFactory.selectForClass(Person.class).group().with(new Function<Person, Pair<String, Integer>>() {

				@Override
				public Pair<String, Integer> apply(Person t)
				{
					return Tuples.tie(t.getName(), t.getAge());
				}

			}).toMap();

		/*
		 * should
		 */
		Assert.assertEquals(16, res.size());
		List<Integer> Bobs = res.get("Bob");
		Assert.assertEquals(3, Bobs.size());
		List<Integer> davids = res.get("Smith");
		Assert.assertEquals(3, davids.size());
	}

	@Test
	public void testGroupbyWithSamplePredicate()
	{
		/*
		 * when
		 */
		Map<String, List<Person>> res = hibernateFactory.selectForClass(Person.class)//
			.sample().even().group().by(Person.Tp.name).toMap();

		/*
		 * should
		 */
		Assert.assertEquals(8, res.size());
		Assert.assertNull(res.get("Bob"));
		Assert.assertEquals(3, res.get("Smith").size());
	}

	@Test
	public void testGroupbyWithSamplePredicate1()
	{
		/*
		 * when
		 */
		Map<String, Long> res = hibernateFactory.selectForClass(Person.class)//
			.sample().even().group().by(Person.Tp.name).selectAs().count().toMap();

		/*
		 * should
		 */
		Assert.assertEquals(8, res.size());
		Assert.assertNull(res.get("Bob"));
		Assert.assertEquals(Long.valueOf(3), res.get("Smith"));
	}
}
