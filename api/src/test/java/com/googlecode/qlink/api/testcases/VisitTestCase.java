package com.googlecode.qlink.api.testcases;

import java.util.List;


import com.googlecode.qlink.api.da.Employee;
import com.googlecode.qlink.api.da.enums.CarState;
import com.googlecode.qlink.api.factory.SimpleCollectionFactory;
import com.googlecode.qlink.api.functor.Function;
import com.googlecode.qlink.api.functor.Visitor;

public class VisitTestCase
{

	private SimpleCollectionFactory simpleF;
	private List<Employee> employees;

	public void testCase1()
	{
		simpleF.forList(employees).visit().p("bonus", Double.class).assign().val(51000.).toList();
	}

	public void testCase2()
	{
		simpleF.forList(employees).visit().p("bonus", Double.class).assign().val(51000.).p("carState", CarState.class)
			.assign().val(CarState.veryGood).toList();
	}

	public void testCase3()
	{
		simpleF.forList(employees).visit().p("bonus", Double.class).assign().func(new Function<Employee, Double>() {

			@Override
			public Double apply(Employee input)
			{
				return 1.1 * input.getBonus();
			}

		}).toList();
	}

	public void testCase4()
	{
		simpleF.forList(employees).visit().with(new Visitor<Employee>() {

			@Override
			public void apply(Employee t)
			{
				t.setBonus(1.1 * t.getBonus());
			}
		}).toList();
	}
}
