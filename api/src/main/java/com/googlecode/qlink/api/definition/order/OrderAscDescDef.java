package com.googlecode.qlink.api.definition.order;

import java.util.Comparator;

import com.googlecode.qlink.api.behavior.CanAggregate;
import com.googlecode.qlink.api.behavior.CanIndex;
import com.googlecode.qlink.api.behavior.CanTransform;
import com.googlecode.qlink.api.behavior.CanVisit;
import com.googlecode.qlink.api.behavior.DoResultAsList;
import com.googlecode.qlink.api.functor.TProperty;

public interface OrderAscDescDef<T, TPlugin>
	extends CanIndex<T, TPlugin>, //
	CanAggregate<T, TPlugin>, //
	CanTransform<T, TPlugin>, //
	CanVisit<T, TPlugin>, //
	DoResultAsList<T, TPlugin>
{

	OrderDef<T, TPlugin> by(String propName);

	OrderDef<T, TPlugin> by(TProperty<?> prop);

	OrderDef<T, TPlugin> with(Comparator<T> c);

}
