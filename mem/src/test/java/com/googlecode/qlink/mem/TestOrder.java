package com.googlecode.qlink.mem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.googlecode.qlink.mem.da.Person;
import com.googlecode.qlink.mem.da.TestUtils;
import com.googlecode.qlink.mem.factory.ListSourceFactory;

public class TestOrder
{
	private final ListSourceFactory simpleFactory = new ListSourceFactory();

	List<Person> persons = new ArrayList<Person>();
	List<ComparablePerson> comparablePersons = new ArrayList<TestOrder.ComparablePerson>();

	public static class ComparablePerson
		extends Person
		implements Comparable<Person>
	{
		public ComparablePerson(String name, Integer age)
		{
			super(name, age);
		}

		@Override
		public int compareTo(Person o)
		{
			return getAge().compareTo(o.getAge());
		}
	}

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

		for (Person p : persons) {
			comparablePersons.add(new ComparablePerson(p.getName(), 100 - p.getAge()));
		}
	}

	@Test
	public void testOrderComparator()
	{
		/*
		 * given
		 */
		List<String> sourceStrings = Arrays.asList("g", "f", "c", "a", "a", "a", "b", "c", "d");

		/*
		 * when
		 */
		List<String> res = simpleFactory.forList(sourceStrings)//
			.order().with(new Comparator<String>() {

				@Override
				public int compare(String o1, String o2)
				{
					return o1.compareTo(o2);
				}
			}).toList();

		/*
		 * should
		 */
		Assert.assertEquals(9, res.size());
		String prev = res.get(0);
		for (String elem : res) {
			Assert.assertTrue(elem.compareTo(prev) >= 0);
		}
	}

	@Test
	public void testPropertyComparator()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		List<Person> res = simpleFactory.forList(persons)//
			.order().by("name").asc().by("age").desc().toList();

		/*
		 * should
		 */
		Assert.assertEquals(20, res.size());
		Assert.assertEquals("Bob", res.get(0).getName());
		Assert.assertEquals(Integer.valueOf(30), res.get(0).getAge());
		Assert.assertEquals("Bob", res.get(1).getName());
		Assert.assertEquals(Integer.valueOf(28), res.get(1).getAge());
		Assert.assertEquals("Bob", res.get(2).getName());
		Assert.assertEquals(Integer.valueOf(26), res.get(2).getAge());
		Assert.assertEquals("David", res.get(7).getName());
		Assert.assertEquals("David", res.get(8).getName());
		Assert.assertEquals("David", res.get(9).getName());
	}

	@Test
	public void testPropertyComparator2()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		List<Person> res = simpleFactory.forList(persons)//
			.order().by("name").asc().by("age").toList();

		/*
		 * should
		 */
		Assert.assertEquals(20, res.size());
	}

	@Test
	public void testFilterAndOrder()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		List<Person> res =
			simpleFactory.forList(persons).filter().p("name").eq("Bob").order().by("age").desc().toList();

		/*
		 * should
		 */
		Assert.assertEquals(3, res.size());
		Assert.assertEquals(Integer.valueOf(30), res.get(0).getAge());
		Assert.assertEquals(Integer.valueOf(26), res.get(2).getAge());

	}

	@Test
	public void testNaturalOrder()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		List<ComparablePerson> res = simpleFactory.forList(comparablePersons).order().naturally().toList();

		/*
		 * should
		 */
		Assert.assertEquals(20, res.size());
		Assert.assertEquals(56, (int) res.get(0).getAge());
		Assert.assertEquals(57, (int) res.get(1).getAge());
	}

	/*
	 * sholdn't be able to cast Person to Comparable 
	 */
	@Test(expected = ClassCastException.class)
	public void testNaturalOrderException()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		simpleFactory.forList(persons).order().naturally().toList();

		/*
		 * should
		 */

	}
}
