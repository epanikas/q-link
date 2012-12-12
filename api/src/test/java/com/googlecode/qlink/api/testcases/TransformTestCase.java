package com.googlecode.qlink.api.testcases;

import java.util.List;


import com.googlecode.qlink.api.da.Employee;
import com.googlecode.qlink.api.factory.SimpleCollectionFactory;
import com.googlecode.qlink.api.functor.Function;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.api.tuple.Tuple3;

@SuppressWarnings("unused")
public class TransformTestCase
{
	private SimpleCollectionFactory simpleF;
	private List<Employee> employees;

	public static class MyEmployeeDto
	{
		private final String name;
		private final Double monthSalary;

		public MyEmployeeDto(String name, Double monthSalary)
		{
			this.name = name;
			this.monthSalary = monthSalary;
		}

		public String getName()
		{
			return name;
		}

		public Double getMonthSalary()
		{
			return monthSalary;
		}
	}

	public void testCase1()
	{
		List<Tuple3<Integer, String, Integer>> res =
			simpleF.forList(employees).select().elemIndex().p("name", String.class).p("age", Integer.class).toList();
	}

	public void testCase2()
	{
		List<Pair<String, Double>> res = simpleF.forList(employees).select().p("name", String.class)//
			.with(new Function<Employee, Double>() {
				@Override
				public Double apply(Employee input)
				{
					return input.getSalary() / 12;
				}
			}).toList();
	}

	public void testCase3()
	{
		List<MyEmployeeDto> res = simpleF.forList(employees).select().p("name", String.class)//
			.with(new Function<Employee, Double>() {
				@Override
				public Double apply(Employee input)
				{
					return input.getSalary() / 12;
				}
			}).asNew(MyEmployeeDto.class).toList();
	}

	public void testCase4()
	{
		List<Object[]> res =
			simpleF.forList(employees).select().elemIndex().p("name", String.class).p("age", Integer.class).asArray()
				.toList();
	}

}
