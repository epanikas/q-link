package com.googlecode.qlink.api.testcases;


import com.googlecode.qlink.api.da.Employee;
import com.googlecode.qlink.api.factory.SqlCollectionFactory;
import com.googlecode.qlink.api.tuple.Tuple3;

@SuppressWarnings("unused")
public class CompositeTestCase
{
	private SqlCollectionFactory sqlF;

	public void testCase11()
	{

		Tuple3<Long, Double, Double> res = //
			sqlF.selectForClass(Employee.class)//
				.filter().p("age", Integer.class).lt().val(30)//
				.aggregate().count().minOf("salary", Double.class).maxOf("salary", Double.class)//
				.toValue();

	}

}
