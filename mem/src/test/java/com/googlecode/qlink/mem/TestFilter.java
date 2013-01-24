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
	private final QLinkInMemoryFactory sf = new QLinkInMemoryFactory();

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
		List<String> res = sf.forList(strings)//
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
			sf.forList(persons).filter().p("name", String.class).eq("James").or().p("name", String.class).eq("John")
				.toList();

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
			sf.forList(persons).filter().p("name", String.class).eq("James").and().p("age", Integer.class).eq(25)
				.toList();

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
		List<Person> res = sf.forList(persons).filter()//
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
			sf.forList(persons).filter().begin().begin().begin().begin().p("age").eq(28).or().p("age").eq(30).end()
				.and().p("name").eq("Smith").end().end().end().toList();

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
		List<Integer> lst = sf.forFewItems(1, 2, 3, 4, 5, 6, 7, 8, 9).filter().self().gt(5).toList();

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
		Person res = sf.forList(persons).filter().p("name").eq("Brian").toUniqueResult();

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
		sf.forList(persons).filter().p("age").gt(30).toUniqueResult();

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
		int n = sf.forList(persons).filter().p("age").gt(30).size();

		/*
		 * should
		 */
		Assert.assertEquals(14, n);

	}

	@Test
	public void testGreaterOrEqual()
	{
		/*
		 * when
		 */
		int n = sf.forList(persons).filter().p("age").ge(30).size();

		/*
		 * should
		 */
		Assert.assertEquals(15, n);

	}

	@Test
	public void testBetween()
	{
		/*
		 * when
		 */
		int n = sf.forList(persons).filter().p("age").between(25, 28).size();

		/*
		 * should
		 */
		Assert.assertEquals(4, n);

	}

	@Test
	public void testIn()
	{
		/*
		 * when
		 */
		List<Person> lst = sf.forList(persons).filter().p("age").in(25, 28, 52).toList();

		/*
		 * should
		 */
		Assert.assertEquals(2, lst.size());

	}

	@Test
	public void testInWithInnerQuery()
	{
		/*
		 * when
		 */
		int n =
			sf.forList(persons).filter().p("age", Integer.class)
				.in(sf.forList(persons).filter().p("age").lt(28).select().p("age", Integer.class)).size();

		/*
		 * should
		 */
		Assert.assertEquals(3, n);

	}

	@Test
	public void testLessOrEqual()
	{
		/*
		 * when
		 */
		int n = sf.forList(persons).filter().p("age").le(30).size();

		/*
		 * should
		 */
		Assert.assertEquals(6, n);

	}

	@Test
	public void testIsEmpty()
	{
		/*
		 * when
		 */
		boolean empty = sf.forList(persons).filter().p("age").gt(30).isEmpty();

		/*
		 * should
		 */
		Assert.assertEquals(false, empty);

	}

	@Test
	public void testIsNull()
	{
		/*
		 * given
		 */
		List<Person> persons1 = new ArrayList<Person>(persons);
		Person nulled = new Person();
		nulled.setName(null);
		persons1.add(nulled);

		/*
		 * when
		 */
		int n1 = sf.forList(persons1).filter().p("name").isNull().size();
		int n2 = sf.forList(persons1).filter().p("name").notNull().size();

		/*
		 * should
		 */
		Assert.assertEquals(1, n1);
		Assert.assertEquals(20, n2);
	}

}
