package com.googlecode.qlink.mem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.googlecode.qlink.api.functor.Function;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.api.tuple.Tuple4;
import com.googlecode.qlink.api.tuple.Tuple5;
import com.googlecode.qlink.api.tuple.TupleN;
import com.googlecode.qlink.mem.da.Person;
import com.googlecode.qlink.mem.da.TestUtils;
import com.googlecode.qlink.mem.factory.ListSourceFactory;

public class TestTransform
{
	private final ListSourceFactory simpleFactory = new ListSourceFactory();

	List<Person> persons = new ArrayList<Person>();

	@Before
	public void setUp()
	{
		persons = TestUtils.generatePersons(TestUtils.names20);
	}

	@Test
	public void testTransformSingleWith()
	{
		/*
		 * given
		 */
		List<Integer> sourceNumbers = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

		/*
		 * when
		 */
		List<String> lst = simpleFactory.forList(sourceNumbers).select().with(new Function<Integer, String>() {
			@Override
			public String apply(Integer input)
			{
				return "#" + input;
			}
		}).toList();

		/*
		 * should
		 */
		Assert.assertEquals(10, lst.size());
		Assert.assertEquals("#1", lst.get(1));
	}

	@Test
	public void testTransformAsPair()
	{
		/*
		 * when
		 */
		List<Pair<String, Integer>> lst = simpleFactory.forList(persons).select()//
			.p("name", String.class).p("age", Integer.class)//
			.toList();

		/*
		 * should
		 */
		Assert.assertEquals(20, lst.size());
		Pair<String, Integer> p = lst.get(0);
		Assert.assertEquals(Integer.valueOf(25), p.getSecond());

	}

	@Test
	public void testTransformAs4Tuple()
	{
		/*
		 * when
		 */
		List<Tuple4<String, Integer, String, Integer>> lst = simpleFactory.forList(persons).select()//
			.p("name", String.class).p("age", Integer.class)//
			.p("name", String.class).p("age", Integer.class)//
			.toList();

		/*
		 * should
		 */
		Assert.assertEquals(20, lst.size());
		Tuple4<String, Integer, String, Integer> p = lst.get(0);
		Assert.assertEquals(Integer.valueOf(25), p.getFourth());
	}

	@Test
	public void testTransformAsCompositeTuple()
	{
		/*
		 * when
		 */
		List<TupleN<Tuple5<String, Integer, String, Integer, String>, Integer>> lst =
			simpleFactory.forList(persons).select()//
				.p("name", String.class).p("age", Integer.class)//
				.p("name", String.class).p("age", Integer.class)//
				.p("name", String.class).p("age", Integer.class)//
				.toList();

		/*
		 * should
		 */
		Assert.assertEquals(20, lst.size());
		Object[] arr = lst.get(0).toArray();
		Assert.assertEquals(Integer.valueOf(25), arr[1]);
	}

	@Test
	public void testFilterSelect()
	{
		/*
		 * when
		 */
		List<Pair<String, Integer>> lst = simpleFactory.forList(persons)//
			.filter().p("age", Integer.class).gt(35)//
			.select().p("name", String.class).p("age", Integer.class)//
			.toList();

		/*
		 * should
		 */
		Assert.assertEquals(9, lst.size());
		Pair<String, Integer> p = lst.get(4);
		Assert.assertTrue(p.getSecond() > 35);
	}

	@Test
	public void testAsSingleObject()
	{
		/*
		 * when
		 */
		List<String> lst = simpleFactory.forList(persons)//
			.select().p("name", String.class).toList();

		/*
		 * should
		 */
		Assert.assertEquals(20, lst.size());
		String p = lst.get(0);
		Assert.assertEquals("James", p);
	}

	public static final class MyCoolJob
	{
		private final String title;
		private final String employeeName;

		public MyCoolJob(String title, String employeeName)
		{
			this.title = title;
			this.employeeName = employeeName;
		}

		public String getEmployeeName()
		{
			return employeeName;
		}

		public String getTitle()
		{
			return title;
		}
	}

	@Test
	public void testAsNewObject()
	{
		/*
		 * when
		 */
		List<MyCoolJob> lst =
			simpleFactory.forList(persons).select().val("guru").p("name", String.class).asNew(MyCoolJob.class).toList();

		/*
		 * should
		 */
		Assert.assertEquals(20, lst.size());
		MyCoolJob p = lst.get(0);
		Assert.assertEquals("James", p.getEmployeeName());
	}

	@Test
	public void testAsArray()
	{
		/*
		 * when
		 */
		List<Object[]> lst =
			simpleFactory.forList(persons).select().val("Mr").p("name", String.class).asArray().toList();

		/*
		 * should
		 */
		Assert.assertEquals(20, lst.size());
		Object title = lst.get(0)[0];
		Object name = lst.get(0)[1];
		Assert.assertEquals("Mr", title);
		Assert.assertEquals("James", name);
	}
}
