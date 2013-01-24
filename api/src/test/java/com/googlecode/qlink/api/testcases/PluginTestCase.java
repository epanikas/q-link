package com.googlecode.qlink.api.testcases;

import java.util.List;


import com.googlecode.qlink.api.da.Employee;
import com.googlecode.qlink.api.da.enums.CarState;
import com.googlecode.qlink.api.da.enums.Occupation;
import com.googlecode.qlink.api.definition.EntryPointDef;

@SuppressWarnings("unused")
public class PluginTestCase
{
	private List<Employee> employees;

	public static class CustomPlugin
	{
		void save()
		{
			// do some saving here
		}

		void update()
		{
			// do some updating here
		}

		void delete()
		{
			// do some deleting here
		}

		void sendByMail(String emailAddres)
		{
			// do some sending here
		}
	}

	public static class CustomFactory
	{
		public <T> EntryPointDef<T, CustomPlugin> forList(List<T> lst)
		{
			return null;
		}
	}

	private CustomFactory customFactory;

	public void testCase1()
	{
		employees.add(new Employee("John", 23));
		employees.add(new Employee("Bob", 23));

		customFactory.forList(employees).visit().p("occupation", Occupation.class).assign().val(Occupation.worker)
			.plugin().save();
	}

	public void testCase2()
	{
		customFactory.forList(employees)//
			.filter().p("occupation", Occupation.class).eq().val(Occupation.manager)//
			.visit().p("carState", CarState.class).assign().val(CarState.veryGood)//
			.plugin().update();
	}

	public void testCase3()
	{
		customFactory.forList(employees).filter().p("occupation", Occupation.class).eq().val(Occupation.boss).and()
			.p("carState", CarState.class).eq().val(CarState.pimpMyRide).plugin().sendByMail("hr@enterprise.com");
	}

	public void testCase4()
	{
		customFactory.forList(employees).filter().p("occupation", Occupation.class).ne().val(Occupation.bigBoss).and()
			.p("carState", CarState.class).eq().val(CarState.pimpMyRide).plugin().delete();
	}
}
