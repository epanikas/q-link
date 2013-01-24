package com.googlecode.qlink.mem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.googlecode.qlink.api.functor.Predicate;
import com.googlecode.qlink.mem.da.Person;
import com.googlecode.qlink.mem.da.TestUtils;
import com.googlecode.qlink.mem.factory.QLinkInMemoryFactory;

public class TestFilter
{
	private final QLinkInMemoryFactory simpleFactory = new QLinkInMemoryFactory();

	List<Person> persons = new ArrayList<Person>();

	@Before
	public void setUp()
	{

		persons = TestUtils.generatePersons(TestUtils.names20);
	}

	@Test
	public void testFilterWithPredicate()
	{
		/*
		 * given
		 */
		List<String> strings = Arrays.asList("a", "a", "a", "b", "c", "d", "e", "f", "g");

		/*
		 * when
		 */
		List<String> res = simpleFactory.forList(strings)//
			.filter().with(new Predicate<String>() {

				@Override
				public boolean evaluate(String object)
				{
					return object.equals("a");
				}
			})//
			.toList();

		/*
		 * should
		 */
		Assert.assertEquals(3, res.size());
		Assert.assertEquals("a", res.get(0));
		Assert.assertEquals("a", res.get(1));
		Assert.assertEquals("a", res.get(2));
	}

	@Test
	public void testFilterOr()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		List<Person> res =
			simpleFactory.forList(persons).filter().p("name", String.class).eq("James").or().p("name", String.class)
				.eq("John").toList();

		/*
		 * should
		 */
		Assert.assertEquals(2, res.size());
		Assert.assertEquals("James", res.get(0).getName());
		Assert.assertEquals("John", res.get(1).getName());
	}

	@Test
	public void testFilterAnd()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		List<Person> res =
			simpleFactory.forList(persons).filter().p("name", String.class).eq("James").and().p("age", Integer.class)
				.eq(25).toList();

		/*
		 * should
		 */
		Assert.assertEquals(1, res.size());
		Assert.assertEquals("James", res.get(0).getName());
	}

	@Test
	public void testFilterBeginEnd()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		List<Person> res = simpleFactory.forList(persons).filter()//
			.begin()//
			.p("name", String.class).eq("James")//
			.or()//
			.p("age", Integer.class).eq(11)//
			.end()//
			.toList();

		/*
		 * should
		 */
		Assert.assertEquals(1, res.size());
		Assert.assertEquals("James", res.get(0).getName());
	}

	@Test
	public void testFilterBeginEnd1()
	{
		/*
		 * given
		 */
		persons.get(3).setName("Smith");
		persons.get(5).setName("Smith");
		persons.get(8).setName("Smith");

		/*
		 * when
		 */
		List<Person> res =
			simpleFactory.forList(persons).filter().begin().begin().begin().begin().p("age").eq(28).or().p("age")
				.eq(30).end().and().p("name").eq("Smith").end().end().end().toList();

		/*
		 * should
		 */
		Assert.assertEquals(2, res.size());
		Assert.assertEquals("Smith", res.get(0).getName());
		Assert.assertEquals(Integer.valueOf(28), res.get(0).getAge());
		Assert.assertEquals("Smith", res.get(1).getName());
		Assert.assertEquals(Integer.valueOf(30), res.get(1).getAge());

	}

	@Test
	public void testFilterElem()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		List<Integer> lst = simpleFactory.forFewItems(1, 2, 3, 4, 5, 6, 7, 8, 9).filter().self().gt(5).toList();

		/*
		 * should
		 */
		Assert.assertEquals(4, lst.size());
	}

	@Test
	public void testFilterUniqueResult()
	{
		/*
		 * when
		 */
		Person res = simpleFactory.forList(persons).filter().p("name").eq("Brian").toUniqueResult();

		/*
		 * should
		 */
		Assert.assertEquals("Brian", res.getName());

	}

	@Test(expected = IllegalArgumentException.class)
	public void testFilterUniqueResultExc()
	{
		/*
		 * when
		 */
		simpleFactory.forList(persons).filter().p("age").gt(30).toUniqueResult();

		/*
		 * should
		 */
		// should throw exception

	}

	@Test
	public void testSize()
	{
		/*
		 * when
		 */
		int n = simpleFactory.forList(persons).filter().p("age").gt(30).size();

		/*
		 * should
		 */
		Assert.assertEquals(14, n);

	}

	@Test
	public void testIsEmpty()
	{
		/*
		 * when
		 */
		boolean empty = simpleFactory.forList(persons).filter().p("age").gt(30).isEmpty();

		/*
		 * should
		 */
		Assert.assertEquals(false, empty);

	}
}
