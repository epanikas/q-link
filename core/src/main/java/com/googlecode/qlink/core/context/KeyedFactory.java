package com.googlecode.qlink.core.context;

public interface KeyedFactory<K, V> {

	V create(K key);

}
