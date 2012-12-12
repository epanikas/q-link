package com.googlecode.qlink.api.testcases;

import java.util.Comparator;
import java.util.List;

import com.googlecode.qlink.api.da.Employee;
import com.googlecode.qlink.api.factory.SimpleCollectionFactory;

@SuppressWarnings("unused")
public class OrderTestCase
{
	private SimpleCollectionFactory simpleF;
	private List<Employee> employees;

	public void testCase1()
	{
		List<Integer> orderedAsc = simpleF.forFewElements(1, 9, 5, 7, 2, 4, 0, 3, 6, 8).order().naturally().toList();
	}

	public void testCase2()
	{
		List<Integer> orderedDesc =
			simpleF.forFewElements(1, 9, 5, 7, 2, 4, 0, 3, 6, 8).order().naturally().desc().toList();
	}

	public void testCase3()
	{
		List<Employee> ordered = simpleF.forList(employees).order().by("maritalStatus").by("age").desc().toList();
	}

	public void testCase4()
	{
		List<Employee> ordered = simpleF.forList(employees).order().with(new Comparator<Employee>() {

			@Override
			public int compare(Employee o1, Employee o2)
			{
				return o1.getAge().compareTo(o2.getAge());
			}

		}).toList();
	}
}
