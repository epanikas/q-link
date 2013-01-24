package com.googlecode.qlink.api.definition.filter.common;

import com.googlecode.qlink.api.behavior.DoResultAsList;

public interface Condition<T, TEnd>
{

	RhsFilterOpDef<T, TEnd> eq();

	RhsFilterOpDef<T, TEnd> ne();

	RhsFilterOpDef<T, TEnd> lt();

	RhsFilterOpDef<T, TEnd> le();

	RhsFilterOpDef<T, TEnd> gt();

	RhsFilterOpDef<T, TEnd> ge();

	TEnd eq(T val);

	TEnd ne(T val);

	TEnd gt(T val);

	TEnd lt(T val);

	TEnd ge(T val);

	TEnd le(T val);

	TEnd isNull();

	TEnd notNull();

	TEnd between(T val1, T val2);

	TEnd in(DoResultAsList<T, ?> val);

	TEnd in(T... lst);

}
