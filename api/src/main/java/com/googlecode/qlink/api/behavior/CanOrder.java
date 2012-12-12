package com.googlecode.qlink.api.behavior;

import com.googlecode.qlink.api.definition.order.StartOrderDef;

public interface CanOrder<T, TPlugin>
{
	StartOrderDef<T, TPlugin> order();
}
