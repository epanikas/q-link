package com.googlecode.qlink.api.definition.filter.common;

import java.util.Map;

import com.googlecode.qlink.api.functor.Predicate;

public interface CommonHavingFilterDef<K, V, TConnector>

{
	Condition<Integer, TConnector> count();

	<R> Condition<R, TConnector> sum(Class<R> resCls);

	<R> Condition<R, TConnector> min(Class<R> resCls);

	<R> Condition<R, TConnector> max(Class<R> resCls);

	<R> Condition<R, TConnector> sumOf(String propName, Class<R> propCls);

	<R> Condition<R, TConnector> minOf(String propName, Class<R> propCls);

	<R> Condition<R, TConnector> maxOf(String propName, Class<R> propCls);

	TConnector with(Predicate<Map.Entry<K, V>> p);

}
