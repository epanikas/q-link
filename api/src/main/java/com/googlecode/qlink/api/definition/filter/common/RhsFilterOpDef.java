package com.googlecode.qlink.api.definition.filter.common;

import com.googlecode.qlink.api.functor.TProperty;

public interface RhsFilterOpDef<T, TEnd>
{

	TEnd prop(String propName);

	TEnd prop(TProperty<?> prop);

	TEnd val(T val);

}
