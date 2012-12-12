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
public class TestSelectForGroup
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

		persons.get(1).setName("Alain");
		persons.get(3).setName("Alain");
		persons.get(5).setName("Alain");
		persons.get(2).setName("Bob");
		persons.get(4).setName("Bob");
		persons.get(6).setName("Bob");

		TestUtils.savePersons(hibernateTemplate, persons);
	}

	@Test
	public void testSelectMin()
	{
		/*
		 * when
		 */
		Map<String, Integer> res =
			hibernateFactory.selectForClass(Person.class).group().by("name", String.class).selectAs()
				.minOf("age", Integer.class).toMap();

		/*
		 * should
		 */
		Assert.assertEquals(16, res.size());
		Assert.assertEquals(Integer.valueOf(27), res.get("Bob"));
		Assert.assertEquals(Integer.valueOf(26), res.get("Alain"));
	}

	@Test
	public void testSelectMinMax()
	{
		/*
		 * when
		 */
		Map<String, Pair<Integer, Integer>> res =
			hibernateFactory.selectForClass(Person.class).group().by("name", String.class)//
				.selectAs().minOf("age", Integer.class).maxOf("age", Integer.class).toMap();

		/*
		 * should
		 */
		Assert.assertEquals(16, res.size());
		Assert.assertEquals(Integer.valueOf(27), res.get("Bob").getFirst());
		Assert.assertEquals(Integer.valueOf(31), res.get("Bob").getSecond());
		Assert.assertEquals(Integer.valueOf(26), res.get("Alain").getFirst());
		Assert.assertEquals(Integer.valueOf(30), res.get("Alain").getSecond());
	}

	@Test
	public void testSelectMinMaxHaving()
	{
		/*
		 * when
		 */
		Map<String, Pair<Integer, Integer>> res = hibernateFactory.selectForClass(Person.class)//
			.group().by("name", String.class)//
			.having().minOf("age", Integer.class).eq(26)//
			.selectAs().minOf("age", Integer.class).maxOf("age", Integer.class).toMap();

		/*
		 * should
		 */
		Assert.assertEquals(1, res.size());
		Map.Entry<String, Pair<Integer, Integer>> firstEntry = res.entrySet().iterator().next();
		Assert.assertEquals("Alain", firstEntry.getKey());
		Assert.assertEquals(Integer.valueOf(26), firstEntry.getValue().getFirst());
		Assert.assertEquals(Integer.valueOf(30), firstEntry.getValue().getSecond());
	}

	@Test
	public void testGroupByPair()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		Map<Pair<String, Integer>, Integer> res =
			hibernateFactory.selectForClass(Person.class).group().by("name", String.class).by("age", Integer.class)//
				.selectAs().minOf("age", Integer.class).toMap();

		/*
		 * should
		 */
		Assert.assertEquals(20, res.size());
		Assert.assertNotNull(res.get(Tuples.tie("Alain", 26)));
		Assert.assertEquals(Integer.valueOf(26), res.get(Tuples.tie("Alain", 26)));
	}

	@Test
	public void testSelectMinMaxAsArray()
	{
		/*
		 * when
		 */
		Map<String, Object[]> res =
			hibernateFactory.selectForClass(Person.class).group().by(Person.Tp.name).selectAs()
				.minOf("age", Integer.class).maxOf("age", Integer.class).asArray().toMap();

		/*
		 * should
		 */
		Assert.assertEquals(16, res.size());
		Object[] minMax = res.get("Alain");
		int minAge = (Integer) minMax[0];
		int maxAge = (Integer) minMax[1];
		Assert.assertEquals(26, minAge);
		Assert.assertEquals(30, maxAge);
	}

	public static class MinMax
	{
		int min, max;

		public MinMax(Integer min, Integer max)
		{
			this.min = min;
			this.max = max;
		}

	}

	@Test
	public void testSelectMinMaxAsNew()
	{
		/*
		 * when
		 */
		Map<String, MinMax> res = hibernateFactory.selectForClass(Person.class)//
			.group().by("name", String.class)//
			.selectAs().minOf("age", Integer.class).maxOf("age", Integer.class).asNew(MinMax.class)//
			.toMap();

		/*
		 * should
		 */
		Assert.assertEquals(16, res.size());
		MinMax minMax = res.get("Alain");
		int minAge = minMax.min;
		int maxAge = minMax.max;
		Assert.assertEquals(26, minAge);
		Assert.assertEquals(30, maxAge);
	}

}
