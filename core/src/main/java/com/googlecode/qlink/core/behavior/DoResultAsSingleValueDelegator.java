package com.googlecode.qlink.core.behavior;

import com.googlecode.qlink.api.behavior.DoResultAsSingleValue;

public interface DoResultAsSingleValueDelegator<T, TPlugin>
{
	DoResultAsSingleValue<T, TPlugin> getDoResultAsSingleValueDelegate();
}
