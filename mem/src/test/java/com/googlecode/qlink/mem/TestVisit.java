package com.googlecode.qlink.mem;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.googlecode.qlink.api.functor.Function;
import com.googlecode.qlink.mem.da.Person;
import com.googlecode.qlink.mem.da.TestUtils;
import com.googlecode.qlink.mem.factory.ListSourceFactory;

public class TestVisit
{
	private final ListSourceFactory simpleFactory = new ListSourceFactory();

	private List<Person> persons = new ArrayList<Person>();

	@Before
	public void setUp()
	{
		persons = TestUtils.generatePersons(TestUtils.names20);
	}

	@Test
	public void testAssignValue()
	{

		/*
		 * when
		 */
		List<Person> modifiedList = simpleFactory.forList(persons)//
			.filter().p(Person.Tp.name).eq().val("David")//
			.visit().p("name").assign().val("Smith")//
			.toList();

		/*
		 * should
		 */
		Assert.assertEquals(1, modifiedList.size());
		Person p = modifiedList.get(0);
		Assert.assertEquals("Smith", p.getName());
	}

	@Test
	public void testAssignFromFunction()
	{

		/*
		 * when
		 */
		List<Person> modifiedList = simpleFactory.forList(persons)//
			.filter().p(Person.Tp.name).eq().val("David")//
			.visit().p("name").assign().func(new Function<Person, Object>() {

				@Override
				public Object apply(Person input)
				{
					return "Mr. " + input.getName();
				}
			}).toList();

		/*
		 * should
		 */
		Assert.assertEquals(1, modifiedList.size());
		Person p = modifiedList.get(0);
		Assert.assertEquals("Mr. David", p.getName());
	}

	@Test
	public void testVisit2()
	{

		/*
		 * when
		 */
		List<Person> modifiedList = simpleFactory.forList(persons)//
			.filter().p(Person.Tp.name).eq().val("David")//
			.visit()//
			.p("name").assign().val("Smith")//
			.p(Person.Tp.age).assign().func(new Function<Person, Integer>() {
				@Override
				public Integer apply(Person input)
				{
					return input.getAge() + 5;
				}
			}).toList();

		/*
		 * should
		 */
		Assert.assertEquals(1, modifiedList.size());
		Person p = modifiedList.get(0);
		Assert.assertEquals("Smith", p.getName());
		Assert.assertEquals(Integer.valueOf(35), p.getAge());
	}
}
