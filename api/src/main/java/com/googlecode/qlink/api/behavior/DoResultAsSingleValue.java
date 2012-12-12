package com.googlecode.qlink.api.behavior;

public interface DoResultAsSingleValue<T, TPlugin>
{

	T toValue();

	TPlugin plugin();

}
