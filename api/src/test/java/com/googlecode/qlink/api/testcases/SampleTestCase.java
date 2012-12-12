package com.googlecode.qlink.api.testcases;

import java.util.List;

import com.googlecode.qlink.api.factory.SimpleCollectionFactory;

public class SampleTestCase
{
	private SimpleCollectionFactory simpleF;

	public void testCase1()
	{
		simpleF.forFewElements(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).sample().even().toList();
	}

	public void testCase2()
	{
		simpleF.forFewElements(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).sample().odd().toList();
	}

	public void testCase3()
	{
		simpleF.forFewElements(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).sample().first().toValue();
	}

	public void testCase4()
	{
		List<Integer> lst = simpleF.forFewElements(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).sample().head(3).toList();
	}

	public void testCase5()
	{
		Integer v = simpleF.forFewElements(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).sample().nth(3).toValue();
	}

}
