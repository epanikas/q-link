package com.googlecode.qlink.api.definition.aggregate;

import com.googlecode.qlink.api.behavior.DoResultAsSingleValue;
import com.googlecode.qlink.api.functor.Folder;

public interface FoldDef<T, TPlugin>
{
	<R> DoResultAsSingleValue<R, TPlugin> with(R init, Folder<T, R> f);
}
