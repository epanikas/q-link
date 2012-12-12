package com.googlecode.qlink.api.definition.sample;

import com.googlecode.qlink.api.behavior.DoResultAsSingleValue;
import com.googlecode.qlink.api.functor.SamplePredicate;

public interface SampleDef<T, TPlugin>
{

	SampleEndDef<T, TPlugin> with(SamplePredicate p);

	SampleEndDef<T, TPlugin> even();

	SampleEndDef<T, TPlugin> odd();

	DoResultAsSingleValue<T, TPlugin> nth(int nth);

	DoResultAsSingleValue<T, TPlugin> first();

	DoResultAsSingleValue<T, TPlugin> last();

	DoResultAsSingleValue<T, TPlugin> middle();

	SampleEndDef<T, TPlugin> head(int l);

	SampleEndDef<T, TPlugin> tail(int l);

}
