package com.googlecode.qlink.api.definition.group.having;

import java.util.List;

import com.googlecode.qlink.api.definition.filter.common.CommonHavingFilterDef;

public interface HavingFilterDef<K, V extends List<?>, TPlugin>
	extends CommonHavingFilterDef<K, V, HavingConnector<K, V, TPlugin>>
{

	HavingFilterDef<K, V, TPlugin> not();

	CompositeHavingFilterDef<K, V, TPlugin, HavingConnector<K, V, TPlugin>> begin();
}
