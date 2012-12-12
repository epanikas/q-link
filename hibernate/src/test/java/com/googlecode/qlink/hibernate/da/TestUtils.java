package com.googlecode.qlink.hibernate.da;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

public class TestUtils
{

	public static String[] names20 = //
		new String[]{"James","John","Robert","Michael","William","David","Richard","Charles","Joseph","Thomas",
						"Christopher","Daniel","Paul","Mark","Donald","George","Kenneth","Steven","Edward","Brian"};

	public static int startAge = 25;

	public static List<Person> generatePersons(String[] names)
	{
		List<Person> lst = new ArrayList<Person>();
		int i = 0;
		for (String name : names) {
			lst.add(new Person(name, startAge + i++));
		}

		return lst;
	}

	public static void clearDb(HibernateTemplate hibernateTemplate)
	{
		List<Person> lst = hibernateTemplate.find("from " + Person.class.getName());
		hibernateTemplate.deleteAll(lst);
	}

	public static List<Person> fillInDb(HibernateTemplate hibernateTemplate)
	{

		List<Person> persons = TestUtils.generatePersons(TestUtils.names20);

		savePersons(hibernateTemplate, persons);

		return persons;
	}

	public static List<Person> prepareDb(HibernateTemplate hibernateTemplate)
	{
		clearDb(hibernateTemplate);
		return fillInDb(hibernateTemplate);
	}

	public static void savePersons(HibernateTemplate hibernateTemplate, List<Person> persons)
	{
		long personId = 1000;

		for (Person p : persons) {
			p.setId(personId++);
			hibernateTemplate.save(p);
		}
	}
}
