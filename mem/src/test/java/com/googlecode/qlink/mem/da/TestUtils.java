package com.googlecode.qlink.mem.da;

import java.util.ArrayList;
import java.util.List;

public class TestUtils
{

	public static String[] names20 = //
		new String[]{"James","John","Robert","Michael","William","David","Richard","Charles","Joseph","Thomas",
						"Christopher","Daniel","Paul","Mark","Donald","George","Kenneth","Steven","Edward","Brian"};

	public static int startAge = 25;

	public static List<Person> generatePersons(String[] names)
	{
		List<Person> lst = new ArrayList<Person>();
		int i = 0;
		for (String name : names) {
			lst.add(new Person(name, startAge + i++));
		}

		return lst;
	}

}
