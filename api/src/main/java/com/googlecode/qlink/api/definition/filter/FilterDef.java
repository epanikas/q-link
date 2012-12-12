package com.googlecode.qlink.api.definition.filter;

import com.googlecode.qlink.api.definition.filter.common.CommonFilterDef;

public interface FilterDef<T, TPlugin>
	extends CommonFilterDef<T, Connector<T, TPlugin>>
{

	FilterDef<T, TPlugin> not();

	CompositeFilterDef<T, TPlugin, Connector<T, TPlugin>> begin();
}
