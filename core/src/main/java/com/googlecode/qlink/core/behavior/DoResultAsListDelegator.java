package com.googlecode.qlink.core.behavior;

import com.googlecode.qlink.api.behavior.DoResultAsList;

public interface DoResultAsListDelegator<T, TPlugin>
{
	DoResultAsList<T, TPlugin> getDoResultAsListDelegate();
}
