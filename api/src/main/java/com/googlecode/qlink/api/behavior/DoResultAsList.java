package com.googlecode.qlink.api.behavior;

import java.util.List;

public interface DoResultAsList<T, TPlugin>
{

	List<T> toList();

	T toUniqueResult();

	int size();

	boolean isEmpty();

	TPlugin plugin();

}
