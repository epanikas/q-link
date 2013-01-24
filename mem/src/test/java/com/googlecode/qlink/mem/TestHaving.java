package com.googlecode.qlink.mem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.googlecode.qlink.mem.da.Person;
import com.googlecode.qlink.mem.da.TestUtils;
import com.googlecode.qlink.mem.factory.QLinkInMemoryFactory;

public class TestHaving
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
		persons.get(1).setAge(16);
		persons.get(3).setName("Bob");
		persons.get(3).setAge(16);
		persons.get(5).setName("Bob");
		persons.get(5).setAge(16);
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
			simpleFactory.forList(persons).group().by("name", String.class).having().count().eq(3).toMap();

		/*
		 * should
		 */
		Assert.assertEquals(2, res.size());
		Assert.assertEquals(3, res.get("Bob").size());
		Assert.assertEquals(3, res.get("David").size());
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
			simpleFactory.forList(persons).group().by("age", Integer.class).having().count().gt(1).toMap();

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
		Map<String, List<Person>> res = simpleFactory.forList(persons)//
			.group().by("name", String.class).having()//
			.begin().count().eq(3).or().count().eq(2).end().and().sumOf("age", Integer.class).gt(0)//
			.toMap();

		/*
		 * should
		 */
		Assert.assertEquals(2, res.size());
		Assert.assertEquals(3, res.get("Bob").size());
		Assert.assertEquals(3, res.get("David").size());
	}

	@Test
	public void testHavingComposite2()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		Map<String, List<Person>> res = simpleFactory.forList(persons)//
			.group().by("name", String.class)//
			.having()//
			.begin().count().eq(3).or().count().eq(2).end().and()//
			.begin().sumOf("age", Integer.class).gt(15).and()//
			.sumOf("age", Integer.class).lt(50).end()//
			.toMap();

		/*
		 * should
		 */
		Assert.assertEquals(1, res.size());
		Assert.assertEquals(3, res.get("Bob").size());
	}

}
