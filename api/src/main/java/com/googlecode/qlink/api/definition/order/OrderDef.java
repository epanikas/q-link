package com.googlecode.qlink.api.definition.order;

import com.googlecode.qlink.api.behavior.CanAggregate;
import com.googlecode.qlink.api.behavior.CanIndex;
import com.googlecode.qlink.api.behavior.CanTransform;
import com.googlecode.qlink.api.behavior.CanVisit;
import com.googlecode.qlink.api.behavior.DoResultAsList;
import com.googlecode.qlink.api.functor.TProperty;

public interface OrderDef<T, TPlugin>
	extends CanIndex<T, TPlugin>, //
	CanAggregate<T, TPlugin>, //
	CanTransform<T, TPlugin>, //
	CanVisit<T, TPlugin>, //
	DoResultAsList<T, TPlugin>
{
	OrderAscDescDef<T, TPlugin> asc();

	OrderAscDescDef<T, TPlugin> desc();

	OrderDef<T, TPlugin> by(String propName);

	OrderDef<T, TPlugin> by(TProperty<?> prop);
}
