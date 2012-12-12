package com.googlecode.qlink.api.testcases;

import java.util.List;

import com.googlecode.qlink.api.da.Employee;
import com.googlecode.qlink.api.factory.SimpleCollectionFactory;
import com.googlecode.qlink.api.factory.SqlCollectionFactory;

@SuppressWarnings("unused")
public class NoCompileTestCase
{
	private SqlCollectionFactory sqlF;
	private SimpleCollectionFactory simpleF;

	private List<Employee> employees;

	public void testCase1()
	{
		/*
		 * wrong property type
		 */
		//		sqlF.selectForClass(Employee.class).filter().prop("age", Integer.class).lt().val(30).and()
		//			.prop("bonus", Integer.class).gt().prop("salary", String.class)//
		//			.toList();
	}

	public void testCase2()
	{
		/*
		 * wrong value type
		 */
		//		sqlF.selectForClass(Employee.class)//
		//			.filter().prop("age", Integer.class).lt().val(30).and().prop("salary", Double.class).gt().val(120)//
		//			.toList();
	}

	public void testCase3()
	{
		/*
		 * begin not ended
		 */
		//		sqlF.selectForClass(Employee.class).filter()
		//			//
		//			.begin().begin().p("salary", Double.class).gt().val(120000.).or().p("bonus", Double.class).gt()
		//			.val(120000.).end()//
		//			.and().p("carState", CarState.class).eq().val(CarState.pimpMyRide)//
		//			.toList();
	}

	public void testCase4()
	{
		/*
		 * having condition not ended
		 */
		//		Map<MaritalStatus, List<Employee>> res = //
		//			simpleF.forList(employees).group().by("maritalStatus", MaritalStatus.class).having()
		//				.minOf("age", Integer.class).toMap();
	}
}
