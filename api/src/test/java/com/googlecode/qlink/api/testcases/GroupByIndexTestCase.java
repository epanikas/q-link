package com.googlecode.qlink.api.testcases;

import java.util.List;
import java.util.Map;


import com.googlecode.qlink.api.da.Employee;
import com.googlecode.qlink.api.da.enums.MaritalStatus;
import com.googlecode.qlink.api.factory.SimpleCollectionFactory;
import com.googlecode.qlink.api.functor.Function;
import com.googlecode.qlink.api.functor.Function2;
import com.googlecode.qlink.api.functor.Predicate;
import com.googlecode.qlink.api.mock.MockPair;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.api.tuple.Tuple3;

@SuppressWarnings("unused")
public class GroupByIndexTestCase
{
	private SimpleCollectionFactory simpleF;
	private List<Employee> employees;

	public static class MinMaxCountDto
	{
		private final Integer min, max;
		private final Long count;

		public MinMaxCountDto(Integer min, Integer max, Long count)
		{
			this.min = min;
			this.max = max;
			this.count = count;
		}

	}

	public void testCase1()
	{
		Map<String, Employee> res = //
			simpleF.forList(employees).index().by("name", String.class).toMap();
	}

	public void testCase2()
	{
		Map<Pair<String, Integer>, Employee> res = //
			simpleF.forList(employees).index().by("name", String.class).by("age", Integer.class).toMap();
	}

	public void testCase3()
	{
		Map<MaritalStatus, List<Employee>> res = //
			simpleF.forList(employees).group().by("maritalStatus", MaritalStatus.class).toMap();
	}

	public void testCase4()
	{
		Map<MaritalStatus, List<Integer>> res = //
			simpleF.forList(employees).group().with(new Function<Employee, Pair<MaritalStatus, Integer>>() {
				@Override
				public Pair<MaritalStatus, Integer> apply(Employee input)
				{
					return new MockPair<MaritalStatus, Integer>(input.getMaritalStatus(), input.getAge());
				}
			}).toMap();
	}

	public void testCase5()
	{
		Map<String, Employee> res = //
			simpleF.forList(employees).index().with(new Function2<Employee, Integer, Pair<String, Employee>>() {
				@Override
				public Pair<String, Employee> apply(Employee e, Integer i)
				{
					return new MockPair<String, Employee>(i + "" + e.getName(), e);
				}
			}).toMap();
	}

	public void testCase6()
	{
		Map<MaritalStatus, List<Employee>> res = //
			simpleF.forList(employees).group().by("maritalStatus", MaritalStatus.class).having()
				.minOf("age", Integer.class).lt().val(25).toMap();
	}

	public void testCase7()
	{
		Map<MaritalStatus, List<Employee>> res = //
			simpleF.forList(employees).group().by("maritalStatus", MaritalStatus.class).having()
				.minOf("age", Integer.class).lt().val(25).or().count().lt().val(5).toMap();
	}

	public void testCase8()
	{
		Map<MaritalStatus, List<Employee>> res = //
			simpleF.forList(employees).group().by("maritalStatus", MaritalStatus.class).having()
				.minOf("age", Integer.class).lt().val(25).or().count().lt().val(5).toMap();
	}

	public void testCase9()
	{
		Map<MaritalStatus, List<Employee>> res = //
			simpleF.forList(employees).group().by("maritalStatus", MaritalStatus.class).having()
				.with(new Predicate<Map.Entry<MaritalStatus, List<Employee>>>() {

					@Override
					public boolean evaluate(Map.Entry<MaritalStatus, List<Employee>> entry)
					{
						return entry.getKey() == MaritalStatus.married || entry.getValue().size() > 2;
					}

				}).toMap();
	}

	public void testCase10()
	{
		Map<MaritalStatus, Tuple3<Integer, Integer, Long>> res = //
			simpleF.forList(employees).group().by("maritalStatus", MaritalStatus.class).selectAs()
				.minOf("age", Integer.class).maxOf("age", Integer.class).count().toMap();
	}

	public void testCase11()
	{
		Map<MaritalStatus, MinMaxCountDto> res = //
			simpleF.forList(employees).group().by("maritalStatus", MaritalStatus.class).selectAs()
				.minOf("age", Integer.class).maxOf("age", Integer.class).count().asNew(MinMaxCountDto.class).toMap();
	}

	public void testCase12()
	{
		Map<MaritalStatus, Object[]> res = //
			simpleF.forList(employees).group().by("maritalStatus", MaritalStatus.class).selectAs()
				.minOf("age", Integer.class).maxOf("age", Integer.class).count().asArray().toMap();
	}

	public void testCase13()
	{
		Map<MaritalStatus, MinMaxCountDto> res = //
			simpleF.forList(employees).group().by("maritalStatus", MaritalStatus.class).selectAs()
				.withTransformer(new Function<Pair<MaritalStatus, List<Employee>>, MinMaxCountDto>() {
					@Override
					public MinMaxCountDto apply(Pair<MaritalStatus, List<Employee>> input)
					{

						int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
						for (Employee e : input.getSecond()) {
							if (min > e.getAge()) {
								min = e.getAge();
							}
							if (max < e.getAge()) {
								max = e.getAge();
							}
						}

						return new MinMaxCountDto(min, max, (long) input.getSecond().size());
					}

				}).toMap();
	}
}
