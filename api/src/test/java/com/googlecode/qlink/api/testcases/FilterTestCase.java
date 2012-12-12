package com.googlecode.qlink.api.testcases;

import java.util.List;


import com.googlecode.qlink.api.da.Employee;
import com.googlecode.qlink.api.da.enums.CarState;
import com.googlecode.qlink.api.da.enums.Occupation;
import com.googlecode.qlink.api.factory.SimpleCollectionFactory;
import com.googlecode.qlink.api.functor.Predicate;

@SuppressWarnings("unused")
public class FilterTestCase
{
	private SimpleCollectionFactory simpleF;
	private List<Employee> employees;

	public static class YoungEmployee
		implements Predicate<Employee>
	{
		@Override
		public boolean evaluate(Employee e)
		{
			return e.getAge() < 30;
		}

		public static Predicate<Employee> instance = new YoungEmployee();
	}

	public void testCase1()
	{
		List<Integer> res = simpleF.forFewElements(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).filter().self().gt().val(5).toList();
	}

	public void testCase2()
	{
		List<Integer> res =
			simpleF.forFewElements(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).filter().self().gt().val(5).and().self().le().val(10)
				.toList();
	}

	public void testCase3()
	{
		List<Integer> res = simpleF.forFewElements(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)//
			.filter().begin().self().gt().val(5).and().self().le().val(10).end().or().self().eq().val(0).toList();
	}

	public void testCase4()
	{
		List<Integer> res = simpleF.forFewElements(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)//
			.filter().with(new Predicate<Integer>() {

				@Override
				public boolean evaluate(Integer i)
				{
					return i.intValue() % 2 == 0;
				}
			}).toList();
	}

	public void testCase5()
	{
		List<Employee> lst = simpleF.forList(employees)//
			.filter().p("age", Integer.class).lt().val(30).and().p("bonus").gt().prop("salary")//
			.toList();
	}

	public void testCase6()
	{
		List<Employee> lst = simpleF.forList(employees).filter()//
			.begin().p("salary", Double.class).gt().val(120000.).or().p("bonus", Double.class).gt().val(120000.).end()//
			.and().p("carState", CarState.class).eq().val(CarState.pimpMyRide)//
			.toList();
	}

	public void testCase7()
	{
		List<Employee> lst =
			simpleF.forList(employees).filter().not().begin().p("salary", Double.class).gt().val(120000.).or()
				.p("bonus", Double.class).gt().val(120000.).end()//
				.and().p("carState", CarState.class).eq().val(CarState.veryGood)//
				.toList();
	}

	public void testCase8()
	{
		List<Employee> lst =
			simpleF.forList(employees).filter().with(YoungEmployee.instance).and().p("occupation", Occupation.class)
				.eq().val(Occupation.bigBoss).toList();
	}
}
