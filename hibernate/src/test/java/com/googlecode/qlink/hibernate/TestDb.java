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

import com.googlecode.qlink.hibernate.da.Person;
import com.googlecode.qlink.hibernate.da.TestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/da/test-db-config.xml"})
@TransactionConfiguration(defaultRollback = false)
public class TestDb
{
	Logger log = Logger.getLogger(TestDb.class);

	private long personId = 1000;

	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Before
	public void setUp()
	{
		TestUtils.clearDb(hibernateTemplate);

		Person p1 = new Person("Smith", 42);
		p1.setId(personId++);

		hibernateTemplate.save(p1);
	}

	@Test
	public void testDb()
	{
		/*
		 * when
		 */
		@SuppressWarnings("unchecked")
		List<Person> lst = hibernateTemplate.find("from " + Person.class.getName());

		/*
		 * should
		 */
		log.info(lst);
		Assert.assertEquals(1, lst.size());
	}

}
