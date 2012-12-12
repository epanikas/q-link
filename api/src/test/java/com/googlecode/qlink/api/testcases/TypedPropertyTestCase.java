package com.googlecode.qlink.api.testcases;

import java.util.List;

import com.googlecode.qlink.api.da.Person;
import com.googlecode.qlink.api.da.enums.MaritalStatus;
import com.googlecode.qlink.api.factory.SimpleCollectionFactory;

@SuppressWarnings("unused")
public class TypedPropertyTestCase
{
	private SimpleCollectionFactory simpleF;
	private List<Person> persons;

	public void testCase1()
	{
		List<Person> lst = simpleF.forList(persons).filter()//
			.p(Person.Tp.age).gt().val(18).or().p(Person.Tp.name).eq().val("John").toList();
	}

	public void testCase2()
	{
		simpleF.forList(persons).visit().p(Person.Tp.age).assign().val(40).p(Person.Tp.maritalStatus).assign()
			.val(MaritalStatus.married).toList();
	}

}
