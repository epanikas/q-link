package com.googlecode.qlink.api.factory;

import com.googlecode.qlink.api.definition.EntryPointDef;

@SuppressWarnings("unused")
public class SqlCollectionFactory
{

	public <T> EntryPointDef<T, Void> selectForClass(Class<T> cls)
	{
		return null;
	}

	public <T> EntryPointDef<T, SaveUpdatePlugin> updateForClass(Class<T> cls)
	{
		return null;
	}

}
