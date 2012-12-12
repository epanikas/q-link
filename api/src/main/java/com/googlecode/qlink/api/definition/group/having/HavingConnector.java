package com.googlecode.qlink.api.definition.group.having;

import java.util.List;

import com.googlecode.qlink.api.behavior.CanSelectGroup;
import com.googlecode.qlink.api.behavior.DoResultAsMap;
import com.googlecode.qlink.api.definition.filter.common.CommonConnector;

public interface HavingConnector<K, V extends List<?>, TPlugin>
	extends CommonConnector<HavingFilterDef<K, V, TPlugin>>, DoResultAsMap<K, V, TPlugin>,
	CanSelectGroup<K, V, TPlugin>
{
	// empty
}
