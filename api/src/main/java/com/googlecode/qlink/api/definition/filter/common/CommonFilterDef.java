package com.googlecode.qlink.api.definition.filter.common;

import com.googlecode.qlink.api.functor.Predicate;
import com.googlecode.qlink.api.functor.TProperty;

public interface CommonFilterDef<T, TConnector>
{
	<R> Condition<R, TConnector> p(String propName);

	<R> Condition<R, TConnector> p(String propName, Class<R> cls);

	<R> Condition<R, TConnector> p(TProperty<R> tp);

	Condition<T, TConnector> self();

	TConnector with(Predicate<T> p);

}
