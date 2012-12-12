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

public class TestIndex
{
	private final ListSourceFactory simpleFactory = new ListSourceFactory();

	List<Person> persons = new ArrayList<Person>();

	@Before
	public void setUp()
	{
		persons = TestUtils.generatePersons(TestUtils.names20);
	}

	@Test
	public void testIndexBy()
	{

		/*
		 * when
		 */
		Map<String, Person> res = simpleFactory.forList(persons).index().by("name", String.class).toMap();

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
			simpleFactory.forList(persons).index().by("name", String.class).by("age", Integer.class).toMap();

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
		persons.get(2).setName("David");
		persons.get(4).setName("David");

		/*
		 * when
		 */
		simpleFactory.forList(persons).index().by("name", String.class).toMap();

		/*
		 * should
		 */
		// should throw an exception
	}

	@Test
	public void testIndexByNumber()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		Map<Integer, Person> res = simpleFactory.forList(persons)//
			.index().with(new Function2<Person, Integer, Pair<Integer, Person>>() {
				@Override
				public Pair<Integer, Person> apply(Person p, Integer i)
				{
					return Tuples.tie(i, p);
				}
			}).toMap();

		/*
		 * should
		 */
		Assert.assertEquals(20, res.size());

	}

	@Test
	public void testIndexByCustomKeyProperty()
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
		Map<Pair<String, Integer>, Person> res =
			simpleFactory.forList(persons).index().by(customKey).by(Person.Tp.age).toMap();

		/*
		 * should
		 */
		Assert.assertEquals(20, res.size());
	}
}
