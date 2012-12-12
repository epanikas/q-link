package com.googlecode.qlink.core.da;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.core.context.TPropertyImpl;

public class Person
{

	private String name;
	private Integer age;

	public static class Tp
	{
		public static final TProperty<String> name = TPropertyImpl.forTypedName("name", String.class);
		public static final TProperty<Integer> age = TPropertyImpl.forTypedName("age", Integer.class);
	}

	public Person(String name, Integer age)
	{
		super();
		this.name = name;
		this.age = age;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Integer getAge()
	{
		return age;
	}

	public void setAge(Integer age)
	{
		this.age = age;
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("name", name).append("age", age)
			.toString();
	}

}
