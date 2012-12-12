package com.googlecode.qlink.api.definition.filter;

import com.googlecode.qlink.api.definition.filter.common.CommonFilterDef;

public interface CompositeFilterDef<T, TPlugin, TRemainingConnector>
	extends CommonFilterDef<T, CompositeConnector<T, TPlugin, TRemainingConnector>>
{

	CompositeFilterDef<T, TPlugin, CompositeConnector<T, TPlugin, TRemainingConnector>> not();

	CompositeFilterDef<T, TPlugin, CompositeConnector<T, TPlugin, TRemainingConnector>> begin();
}
