package com.googlecode.qlink.api.da;


import com.googlecode.qlink.api.da.enums.MaritalStatus;
import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.api.mock.MockProperty;

public class Person
{

	private String name;
	private int age;
	private MaritalStatus maritalStatus;

	public static class Tp
	{
		public static final TProperty<String> name = MockProperty.create("name", String.class);
		public static final TProperty<Integer> age = MockProperty.create("age", Integer.class);
		public static final TProperty<MaritalStatus> maritalStatus = MockProperty.create("maritalStatus",
			MaritalStatus.class);
	}

	public Person(String name, int age, MaritalStatus maritalStatus)
	{
		this.name = name;
		this.age = age;
		this.maritalStatus = maritalStatus;
	}

	public String getName()
	{
		return name;
	}

	public int getAge()
	{
		return age;
	}

	public MaritalStatus getMaritalStatus()
	{
		return maritalStatus;
	}

}
