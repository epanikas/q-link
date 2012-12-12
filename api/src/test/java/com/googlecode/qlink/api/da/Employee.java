package com.googlecode.qlink.api.da;

import com.googlecode.qlink.api.da.enums.CarState;
import com.googlecode.qlink.api.da.enums.MaritalStatus;
import com.googlecode.qlink.api.da.enums.Occupation;

public class Employee
{
	private String name;
	private Integer age;

	private Occupation occupation;
	private CarState car;
	private MaritalStatus maritalStatus;
	private double salary;
	private double bonus;

	public Employee(String name, Integer age)
	{
		this.name = name;
		this.age = age;
	}

	public Employee(String name, Integer age, Occupation occupation, CarState car, MaritalStatus maritalStatus,
					double salary, double bonus)
	{
		this.name = name;
		this.age = age;
		this.occupation = occupation;
		this.car = car;
		this.maritalStatus = maritalStatus;
		this.salary = salary;
		this.bonus = bonus;
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

	public Occupation getOccupation()
	{
		return occupation;
	}

	public void setOccupation(Occupation occupation)
	{
		this.occupation = occupation;
	}

	public double getSalary()
	{
		return salary;
	}

	public void setSalary(double salary)
	{
		this.salary = salary;
	}

	public double getBonus()
	{
		return bonus;
	}

	public void setBonus(double bonus)
	{
		this.bonus = bonus;
	}

	public CarState getCar()
	{
		return car;
	}

	public void setCar(CarState car)
	{
		this.car = car;
	}

	public MaritalStatus getMaritalStatus()
	{
		return maritalStatus;
	}
}
