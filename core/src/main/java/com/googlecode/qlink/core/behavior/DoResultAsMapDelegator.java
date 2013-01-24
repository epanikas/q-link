package com.googlecode.qlink.core.behavior;

import com.googlecode.qlink.api.behavior.DoResultAsMap;

public interface DoResultAsMapDelegator<K, V, TPlugin>
{
	DoResultAsMap<K, V, TPlugin> getDoResultAsMapDelegate();
}
