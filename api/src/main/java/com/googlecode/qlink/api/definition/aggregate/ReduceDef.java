package com.googlecode.qlink.api.definition.aggregate;

import com.googlecode.qlink.api.behavior.DoResultAsSingleValue;
import com.googlecode.qlink.api.functor.Reducer;

public interface ReduceDef<T, TPlugin>
{

	DoResultAsSingleValue<T, TPlugin> with(Reducer<T> f);
}
