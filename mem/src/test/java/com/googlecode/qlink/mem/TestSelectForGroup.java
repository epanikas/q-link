package com.googlecode.qlink.mem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.mem.da.Person;
import com.googlecode.qlink.mem.da.TestUtils;
import com.googlecode.qlink.mem.factory.QLinkInMemoryFactory;

public class TestSelectForGroup
{
	private final QLinkInMemoryFactory simpleFactory = new QLinkInMemoryFactory();

	List<Person> persons = new ArrayList<Person>();

	@Before
	public void setUp()
	{
		persons = TestUtils.generatePersons(TestUtils.names20);

		persons.get(2).setName("David");
		persons.get(4).setName("David");
		persons.get(6).setName("David");
		persons.get(1).setName("Bob");
		persons.get(3).setName("Bob");
		persons.get(5).setName("Bob");
	}

	@Test
	public void testSelectMin()
	{
		/*
		 * when
		 */
		Map<String, Integer> res =
			simpleFactory.forList(persons).group().by("name", String.class).selectAs().minOf("age", Integer.class)
				.toMap();

		/*
		 * should
		 */
		Assert.assertEquals(16, res.size());
		int minAge = res.get("David");
		Assert.assertEquals(27, minAge);
	}

	@Test
	public void testSelectMinMax()
	{
		/*
		 * when
		 */
		Map<String, Pair<Integer, Integer>> res =
			simpleFactory.forList(persons).group().by("name", String.class).selectAs().minOf("age", Integer.class)
				.maxOf("age", Integer.class).toMap();

		/*
		 * should
		 */
		Assert.assertEquals(16, res.size());
		int minAge = res.get("David").getFirst();
		int maxAge = res.get("David").getSecond();
		Assert.assertEquals(27, minAge);
		Assert.assertEquals(31, maxAge);
	}

	@Test
	public void testSelectMinMaxAsArray()
	{
		/*
		 * when
		 */
		Map<String, Object[]> res =
			simpleFactory.forList(persons).group().by("name", String.class).selectAs().minOf("age", Integer.class)
				.maxOf("age", Integer.class).asArray().toMap();

		/*
		 * should
		 */
		Assert.assertEquals(16, res.size());
		Object[] minMax = res.get("David");
		int minAge = (Integer) minMax[0];
		int maxAge = (Integer) minMax[1];
		Assert.assertEquals(27, minAge);
		Assert.assertEquals(31, maxAge);
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
		Map<String, MinMax> res = simpleFactory.forList(persons)//
			.group().by("name", String.class)//
			.selectAs().minOf("age", Integer.class).maxOf("age", Integer.class).asNew(MinMax.class)//
			.toMap();

		/*
		 * should
		 */
		Assert.assertEquals(16, res.size());
		MinMax minMax = res.get("David");
		int minAge = minMax.min;
		int maxAge = minMax.max;
		Assert.assertEquals(27, minAge);
		Assert.assertEquals(31, maxAge);
	}

}
