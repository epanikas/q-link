package com.googlecode.qlink.api.definition.order;

import java.util.Comparator;

import com.googlecode.qlink.api.functor.TProperty;

public interface StartOrderDef<T, TPlugin>
{
	OrderDef<T, TPlugin> naturally();

	OrderDef<T, TPlugin> by(String propName);

	OrderDef<T, TPlugin> by(TProperty<?> prop);

	OrderWithDef<T, TPlugin> with(Comparator<T> c);

}
