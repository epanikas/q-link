package com.googlecode.qlink.api.definition.visit;

import com.googlecode.qlink.api.behavior.CanAggregate;
import com.googlecode.qlink.api.behavior.CanFilter;
import com.googlecode.qlink.api.behavior.CanIndex;
import com.googlecode.qlink.api.behavior.CanOrder;
import com.googlecode.qlink.api.behavior.CanSample;
import com.googlecode.qlink.api.behavior.CanTransform;
import com.googlecode.qlink.api.behavior.DoResultAsList;
import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.api.functor.Visitor;
import com.googlecode.qlink.api.functor.Visitor2;

public interface VisitDef<T, TPlugin>
	extends CanFilter<T, TPlugin>, //
	CanOrder<T, TPlugin>, //
	CanSample<T, TPlugin>, //
	CanIndex<T, TPlugin>, //
	DoResultAsList<T, TPlugin>, //
	CanTransform<T, TPlugin>, //
	CanAggregate<T, TPlugin>
{

	VisitAssignDef<Object, T, TPlugin> p(String propFrom);

	<R> VisitAssignDef<R, T, TPlugin> p(String propFrom, Class<R> cls);

	<R> VisitAssignDef<R, T, TPlugin> p(TProperty<R> tp);

	VisitDef<T, TPlugin> with(Visitor<T> v);

	VisitDef<T, TPlugin> with(Visitor2<T, Integer> v);

}
