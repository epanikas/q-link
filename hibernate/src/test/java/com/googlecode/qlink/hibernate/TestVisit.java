package com.googlecode.qlink.hibernate;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.googlecode.qlink.hibernate.da.Person;
import com.googlecode.qlink.hibernate.da.TestUtils;
import com.googlecode.qlink.hibernate.factory.QLinkHibernateFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/da/test-db-config.xml"})
@TransactionConfiguration(defaultRollback = false)
public class TestVisit
{
	@Autowired
	private QLinkHibernateFactory hibernateFactory;

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Before
	public void setUp()
	{
		TestUtils.prepareDb(hibernateTemplate);
	}

	@Test
	public void testVisitAssignVal()
	{

		/*
		 * when
		 */
		hibernateFactory.modifyForClass(Person.class)//
			.filter().p(Person.Tp.name).eq().val("David")//
			.visit().p("name").assign().val("Smith")//
			.plugin().update();

		/*
		 * should
		 */
		@SuppressWarnings("unchecked")
		List<Person> lst = hibernateTemplate.find("from " + Person.class.getName() + " where name = 'Smith'");
		Assert.assertEquals(1, lst.size());
		Person p = lst.get(0);
		Assert.assertEquals("Smith", p.getName());

	}

	@Test
	public void testVisitAssignVal2()
	{

		/*
		 * when
		 */
		hibernateFactory.modifyForClass(Person.class)//
			.filter().p(Person.Tp.name).eq().val("David")//
			.visit().p(Person.Tp.name).assign("Smith").p(Person.Tp.age).assign(45)//
			.plugin().update();

		/*
		 * should
		 */
		@SuppressWarnings("unchecked")
		List<Person> lst = hibernateTemplate.find("from " + Person.class.getName() + " where name = 'Smith'");
		Assert.assertEquals(1, lst.size());
		Person p = lst.get(0);
		Assert.assertEquals(Integer.valueOf(45), p.getAge());

	}

	@Test
	public void testVisitAssignProp()
	{

		/*
		 * when
		 */
		hibernateFactory.modifyForClass(Person.class)//
			.filter().p(Person.Tp.name).eq().val("David")//
			.visit().p(Person.Tp.name).assign("Smith").p(Person.Tp.age).assign(45)//
			.plugin().update();

		/*
		 * should
		 */
		@SuppressWarnings("unchecked")
		List<Person> lst = hibernateTemplate.find("from " + Person.class.getName() + " where name = 'Smith'");
		Assert.assertEquals(1, lst.size());
		Person p = lst.get(0);
		Assert.assertEquals(Integer.valueOf(45), p.getAge());

	}
}
