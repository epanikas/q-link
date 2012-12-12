package com.googlecode.qlink.api.definition.visit;

import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.api.functor.Visitor;
import com.googlecode.qlink.api.functor.Visitor2;

public interface StartVisitDef<T, TPlugin>
{

	VisitAssignDef<Object, T, TPlugin> p(String propFrom);

	<R> VisitAssignDef<R, T, TPlugin> p(String propFrom, Class<R> cls);

	<R> VisitAssignDef<R, T, TPlugin> p(TProperty<R> tp);

	VisitDef<T, TPlugin> with(Visitor<T> v);

	VisitDef<T, TPlugin> with(Visitor2<T, Integer> v);

}
