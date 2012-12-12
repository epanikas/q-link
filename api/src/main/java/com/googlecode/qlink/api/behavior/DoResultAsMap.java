package com.googlecode.qlink.api.behavior;

import java.util.Map;

public interface DoResultAsMap<K, V, TPlugin>
{
	Map<K, V> toMap();

	int size();

	boolean isEmpty();

	TPlugin plugin();
}
