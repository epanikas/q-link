package com.googlecode.qlink.hibernate;

import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.googlecode.qlink.api.functor.Predicate;
import com.googlecode.qlink.hibernate.da.Person;
import com.googlecode.qlink.hibernate.da.TestUtils;
import com.googlecode.qlink.hibernate.factory.QLinkHibernateFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/da/test-db-config.xml"})
@TransactionConfiguration(defaultRollback = false)
public class TestFilter
{
	Logger log = Logger.getLogger(TestFilter.class);

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Autowired
	private QLinkHibernateFactory hf;

	@Before
	public void setUp()
	{
		TestUtils.prepareDb(hibernateTemplate);
	}

	@Test
	public void testFilterOr()
	{

		/*
		 * when
		 */
		List<Person> res =
			hf.selectForClass(Person.class).filter().p("name", String.class).eq("James").or().p("name", String.class)
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
		 * when
		 */
		List<Person> res =
			hf.selectForClass(Person.class).filter().p("name", String.class).eq("James").and().p("age", Integer.class)
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
		 * when
		 */
		List<Person> res = hf.selectForClass(Person.class).filter()//
			.begin()//
			.p("name", String.class).eq("James")//
			.or()//
			.p("age", Integer.class).eq(11)//
			.end()//
			//
			.toList();

		/*
		 * should
		 */
		Assert.assertTrue(res.size() == 1);
		Assert.assertEquals("James", res.get(0).getName());
	}

	@Test
	public void testFilterBeginEnd1()
	{
		/*
		 * given
		 */
		@SuppressWarnings("unchecked")
		List<Person> persons = hibernateTemplate.find("from " + Person.class.getName());

		persons.get(3).setName("Smith");
		hibernateTemplate.update(persons.get(3));
		persons.get(5).setName("Smith");
		hibernateTemplate.update(persons.get(5));
		persons.get(8).setName("Smith");
		hibernateTemplate.update(persons.get(8));

		/*
		 * when
		 */
		List<Person> res =
			hf.selectForClass(Person.class).filter()
				//
				.begin().begin().begin().begin().p("age").eq(28).or().p("age").eq(30).end().and().p("name").eq("Smith")
				.end().end().end().toList();

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
	public void testFilterWithPredicate()
	{
		/*
		 * given
		 */

		/*
		 * when
		 */
		List<Person> res = hf.selectForClass(Person.class)//
			.filter().with(new Predicate<Person>() {

				@Override
				public boolean evaluate(Person p)
				{
					return p.getAge() > 43;
				}

			}).toList();

		/*
		 * should
		 */
		Assert.assertEquals(1, res.size());
		Assert.assertEquals("Brian", res.get(0).getName());
	}

	@Test
	public void testLessThan()
	{
		/*
		 * when
		 */
		List<Person> youngest = hf.selectForClass(Person.class).filter().p(Person.Tp.age).lt(26).toList();

		/*
		 * should
		 */
		Assert.assertEquals(1, youngest.size());
		Assert.assertEquals(25, youngest.get(0).getAge().intValue());
	}

	@Test
	public void testLessThanOrEqual()
	{
		/*
		 * when
		 */
		List<Person> youngest = hf.selectForClass(Person.class).filter().p(Person.Tp.age).le(26).toList();

		/*
		 * should
		 */
		Assert.assertEquals(2, youngest.size());
		Assert.assertEquals(25, youngest.get(0).getAge().intValue());
	}

	@Test
	public void testGreaterThan()
	{
		/*
		 * when
		 */
		List<Person> youngest = hf.selectForClass(Person.class).filter().p(Person.Tp.age).gt(26).toList();

		/*
		 * should
		 */
		Assert.assertEquals(18, youngest.size());
		Assert.assertEquals(27, youngest.get(0).getAge().intValue());
	}

	@Test
	public void testGreaterThanOrEqual()
	{
		/*
		 * when
		 */
		List<Person> youngest = hf.selectForClass(Person.class).filter().p(Person.Tp.age).ge(26).toList();

		/*
		 * should
		 */
		Assert.assertEquals(19, youngest.size());
		Assert.assertEquals(26, youngest.get(0).getAge().intValue());
	}

	@Test
	public void testBetween()
	{
		/*
		 * when
		 */
		List<Person> youngest = hf.selectForClass(Person.class).filter().p(Person.Tp.age).between(25, 28).toList();

		/*
		 * should
		 */
		Assert.assertEquals(4, youngest.size());
		Assert.assertEquals(25, youngest.get(0).getAge().intValue());
	}

	@Test
	public void testIn()
	{
		/*
		 * when
		 */
		List<Person> youngest = hf.selectForClass(Person.class).filter().p(Person.Tp.age).in(25, 18).toList();

		/*
		 * should
		 */
		Assert.assertEquals(1, youngest.size());
		Assert.assertEquals(25, youngest.get(0).getAge().intValue());
	}

	@Test
	public void testInWithSql()
	{
		/*
		 * when
		 */
		List<Person> youngest =
			hf.selectForClass(Person.class).filter().p(Person.Tp.age)
				.in(hf.selectForClass(Person.class).filter().p(Person.Tp.age).lt(30).select().p(Person.Tp.age))
				.toList();

		/*
		 * should
		 */
		Assert.assertEquals(5, youngest.size());
		Assert.assertEquals(25, youngest.get(0).getAge().intValue());
	}

	@Test
	public void testIsNotNull()
	{
		/*
		 * when
		 */
		List<Person> notNulls = hf.selectForClass(Person.class).filter().p(Person.Tp.age).ne(null).toList();

		/*
		 * should
		 */
		Assert.assertEquals(20, notNulls.size());
	}

	@Test
	public void testIsNull()
	{
		/*
		 * when
		 */
		List<Person> nulls = hf.selectForClass(Person.class).filter().p(Person.Tp.age).eq(null).toList();

		/*
		 * should
		 */
		Assert.assertEquals(0, nulls.size());
	}

}
