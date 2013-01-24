package com.googlecode.qlink.hibernate;

import java.util.List;

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
import com.googlecode.qlink.api.tuple.Tuple4;
import com.googlecode.qlink.api.tuple.Tuple5;
import com.googlecode.qlink.api.tuple.TupleN;
import com.googlecode.qlink.hibernate.da.Person;
import com.googlecode.qlink.hibernate.da.TestUtils;
import com.googlecode.qlink.hibernate.factory.QLinkHibernateFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/da/test-db-config.xml"})
@TransactionConfiguration(defaultRollback = false)
public class TestTransform
{
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Autowired
	private QLinkHibernateFactory hibernateFactory;

	private long personId = 1000;

	@Before
	public void setUp()
	{
		TestUtils.clearDb(hibernateTemplate);

		List<Person> persons = TestUtils.generatePersons(TestUtils.names20);

		persons.get(2).setName("Smith");
		persons.get(4).setName("Smith");
		persons.get(6).setName("Smith");
		persons.get(1).setName("Bob");
		persons.get(3).setName("Bob");
		persons.get(5).setName("Bob");

		for (Person p : persons) {
			p.setId(personId++);
			hibernateTemplate.save(p);
		}
	}

	//	@Test
	//	public void testTransformSingleWith()
	//	{
	//
	//		/*
	//		 * when
	//		 */
	//		List<String> lst =
	//			hibernateFactory.forClass(Person.class).select().asTuple()
	//				.with(new Function<Person, String>() {
	//					@Override
	//					public String apply(Person input)
	//					{
	//						return "#" + input;
	//					}
	//				}).toList();
	//
	//		/*
	//		 * should
	//		 */
	//		Assert.assertEquals(10, lst.size());
	//		Assert.assertEquals("#1", lst.get(1));
	//	}

	@Test
	public void testTransformAsPair()
	{
		/*
		 * when
		 */
		List<Pair<String, Integer>> lst = hibernateFactory.selectForClass(Person.class)//
			.select().p("name", String.class).p("age", Integer.class)//
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
		List<Tuple4<String, Integer, String, Integer>> lst = //
			hibernateFactory.selectForClass(Person.class).select()//
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
			hibernateFactory.selectForClass(Person.class).select()//
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
		List<Pair<String, Integer>> lst = //
			hibernateFactory.selectForClass(Person.class)//
				.filter().p("age", Integer.class).gt(30)//
				.select().p("name", String.class).p("age", Integer.class)//
				.toList();

		/*
		 * should
		 */
		Assert.assertEquals(14, lst.size());
		Pair<String, Integer> p = lst.get(0);
		Assert.assertEquals(Integer.valueOf(31), p.getSecond());
	}

	@Test
	public void testAsSingleObject()
	{
		/*
		 * when
		 */
		List<String> lst = hibernateFactory.selectForClass(Person.class)//
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
		List<MyCoolJob> lst = //
			hibernateFactory.selectForClass(Person.class)//
				.select().val("Mr").p("name", String.class).asNew(MyCoolJob.class).toList();

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
		List<Object[]> lst = hibernateFactory.selectForClass(Person.class)//
			.select().val("Mr").p("name", String.class).asArray().toList();

		/*
		 * should
		 */
		Assert.assertEquals(20, lst.size());
		Object name = lst.get(0)[1];
		Object title = lst.get(0)[0];
		Assert.assertEquals("James", name);
		Assert.assertEquals("Mr", title);
	}
}
