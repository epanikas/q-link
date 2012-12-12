package com.googlecode.qlink.api.tuple;

public interface Tuple
{
	int size();

	Object get(int i);

	Object[] toArray();
}
