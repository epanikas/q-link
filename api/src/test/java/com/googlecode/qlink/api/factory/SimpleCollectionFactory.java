package com.googlecode.qlink.api.factory;

import java.util.List;

import com.googlecode.qlink.api.definition.EntryPointDef;

@SuppressWarnings("unused")
public class SimpleCollectionFactory
{

	public <T> EntryPointDef<T, Void> forFewElements(T... lst)
	{
		return null;
	}

	public <T> EntryPointDef<T, Void> forList(List<T> lst)
	{
		return null;
	}

}
