package com.googlecode.qlink.mem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.googlecode.qlink.api.functor.Function;
import com.googlecode.qlink.api.functor.Function2;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.mem.da.Person;
import com.googlecode.qlink.mem.da.TestUtils;
import com.googlecode.qlink.mem.factory.ListSourceFactory;
import com.googlecode.qlink.tuples.Tuples;

public class TestGroupBy
{

	private final ListSourceFactory simpleFactory = new ListSourceFactory();

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
	public void testGroupBy()
	{

		/*
		 * when
		 */
		Map<String, List<Person>> res = simpleFactory.forList(persons).group().by(Person.Tp.name).toMap();

		/*
		 * should
		 */
		Assert.assertEquals(16, res.size());
		List<Person> bobs = res.get("Bob");
		Assert.assertEquals(3, bobs.size());
		List<Person> davids = res.get("David");
		Assert.assertEquals(3, davids.size());
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
			simpleFactory.forList(persons).group().by("name", String.class).by("age", Integer.class).toMap();

		/*
		 * should
		 */
		Assert.assertEquals(20, res.size());
		List<Person> p1 = res.get(Tuples.tie("David", 27));
		Assert.assertEquals(1, p1.size());
		Assert.assertEquals("David", p1.get(0).getName());
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
			simpleFactory.forList(persons).group().with(new Function<Person, Pair<String, Integer>>() {

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
		List<Integer> lst1 = res.get("Bob");
		Assert.assertEquals(3, lst1.size());
		List<Integer> lst2 = res.get("David");
		Assert.assertEquals(3, lst2.size());
	}

	@Test
	public void testGroupByCustomKey()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		Map<String, List<Person>> res = simpleFactory.forList(persons).group().by(new Function<Person, String>() {

			@Override
			public String apply(Person t)
			{
				return t.getName();
			}

		}).toMap();

		/*
		 * should
		 */
		Assert.assertEquals(16, res.size());
		List<Person> lst1 = res.get("Bob");
		Assert.assertEquals(3, lst1.size());
		List<Person> lst2 = res.get("David");
		Assert.assertEquals(3, lst2.size());
	}

	@Test
	public void testGroupByCustomKeyProperty()
	{
		/*
		 * given
		 */
		Function<Person, String> customKey = new Function<Person, String>() {

			@Override
			public String apply(Person t)
			{
				return t.getName();
			}

		};

		/*
		 * when
		 */
		Map<Pair<String, Integer>, List<Person>> res =
			simpleFactory.forList(persons).group().by(customKey).by(Person.Tp.age).toMap();

		/*
		 * should
		 */
		Assert.assertEquals(20, res.size());
	}

	@Test
	public void testGroupByEvenOdd()
	{

		/*
		 * when
		 */
		Map<Integer, List<Person>> res = simpleFactory.forList(persons)//
			.group().with(new Function2<Person, Integer, Pair<Integer, Person>>() {
				@Override
				public Pair<Integer, Person> apply(Person p, Integer i)
				{
					return Tuples.tie(i % 2, p);
				}
			}).toMap();

		/*
		 * should
		 */
		Assert.assertEquals(2, res.size());
		Assert.assertEquals(10, res.get(0).size());
		Assert.assertEquals(10, res.get(1).size());

	}

	@Test
	public void testGroupBySize()
	{

		/*
		 * when
		 */
		int n = simpleFactory.forList(persons)//
			.group().by(Person.Tp.name).size();

		/*
		 * should
		 */
		Assert.assertEquals(16, n);

	}

	@Test
	public void testGroupByIsEmpty()
	{

		/*
		 * when
		 */
		boolean empty = simpleFactory.forList(persons)//
			.filter().p("age").gt(60).group().by(Person.Tp.name).isEmpty();

		/*
		 * should
		 */
		Assert.assertEquals(true, empty);

	}

}
