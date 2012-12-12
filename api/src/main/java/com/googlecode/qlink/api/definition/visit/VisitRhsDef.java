package com.googlecode.qlink.api.definition.visit;

import com.googlecode.qlink.api.functor.Function;
import com.googlecode.qlink.api.functor.TProperty;

public interface VisitRhsDef<TProp, T, TPlugin>
{
	VisitDef<T, TPlugin> val(TProp val);

	VisitDef<T, TPlugin> func(Function<T, TProp> f);

	VisitDef<T, TPlugin> prop(String propName);

	<R> VisitDef<T, TPlugin> prop(TProperty<R> tp);
}
