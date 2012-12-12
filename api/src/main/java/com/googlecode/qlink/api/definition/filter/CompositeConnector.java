package com.googlecode.qlink.api.definition.filter;

import com.googlecode.qlink.api.definition.filter.common.CommonConnector;

public interface CompositeConnector<T, TPlugin, TRemainingConnector>
	extends CommonConnector<CompositeFilterDef<T, TPlugin, TRemainingConnector>>
{

	TRemainingConnector end();
}
