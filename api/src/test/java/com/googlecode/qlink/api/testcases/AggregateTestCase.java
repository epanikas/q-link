package com.googlecode.qlink.api.testcases;

import java.util.Collection;


import com.googlecode.qlink.api.da.Employee;
import com.googlecode.qlink.api.factory.SimpleCollectionFactory;
import com.googlecode.qlink.api.factory.SqlCollectionFactory;
import com.googlecode.qlink.api.functor.Aggregator;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.api.tuple.Tuple3;

@SuppressWarnings("unused")
public class AggregateTestCase
{

	private SimpleCollectionFactory simpleF;
	private SqlCollectionFactory sqlF;

	public static class MyMinMaxCountDto
	{
		private final double min, max;
		private final long count;

		public MyMinMaxCountDto(double min, double max, long count)
		{
			this.min = min;
			this.max = max;
			this.count = count;
		}

		public double getMin()
		{
			return min;
		}

		public double getMax()
		{
			return max;
		}

		public long getCount()
		{
			return count;
		}
	}

	public void testCase1()
	{
		int min = simpleF.forFewElements(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).aggregate().min().toValue();
	}

	public void testCase2()
	{
		int sum = simpleF.forFewElements(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).aggregate().sum().toValue();
	}

	public void testCase3()
	{
		Pair<Integer, Integer> minMax =
			simpleF.forFewElements(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).aggregate().min().max().toValue();
	}

	public void testCase4()
	{
		MyMinMaxCountDto minMaxCount =
			simpleF.forFewElements(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).aggregate().min().max().count()
				.asNew(MyMinMaxCountDto.class).toValue();
	}

	public void testCase5()
	{
		Object[] minMaxCount =
			simpleF.forFewElements(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).aggregate().min().max().count().asArray()
				.toValue();
	}

	public void testCase7()
	{
		Long res = sqlF.selectForClass(Employee.class).aggregate().count().toValue();
	}

	public void testCase8()
	{
		Double res = sqlF.selectForClass(Employee.class).aggregate().minOf("salary", Double.class).toValue();
	}

	public void testCase9()
	{
		Pair<Double, Double> res =
			sqlF.selectForClass(Employee.class).aggregate().minOf("salary", Double.class)
				.maxOf("salary", Double.class).toValue();
	}

	public void testCase10()
	{
		Tuple3<Double, Double, Double> minMaxAvgBonus =
			sqlF.selectForClass(Employee.class).aggregate().minOf("bonus", Double.class)
				.maxOf("bonus", Double.class).with(Double.class, new Aggregator<Employee, Double>() {

					@Override
					public Double aggregate(Collection<Employee> lst)
					{
						if (lst.size() == 0) {
							return 0.;
						}

						double sum = 0;
						for (Employee e : lst) {
							sum += e.getBonus();
						}
						return sum / lst.size();
					}

				}).toValue();
	}

}
