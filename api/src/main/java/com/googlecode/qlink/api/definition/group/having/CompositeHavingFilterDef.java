package com.googlecode.qlink.api.definition.group.having;

import java.util.List;

import com.googlecode.qlink.api.definition.filter.common.CommonHavingFilterDef;

public interface CompositeHavingFilterDef<K, V extends List<?>, TPlugin, TRemainingConnector>
	extends
	CommonHavingFilterDef<K, V, CompositeHavingConnector<K, V, TPlugin, TRemainingConnector>>
{

	CompositeHavingFilterDef<K, V, TPlugin, CompositeHavingConnector<K, V, TPlugin, TRemainingConnector>> not();

	CompositeHavingFilterDef<K, V, TPlugin, CompositeHavingConnector<K, V, TPlugin, TRemainingConnector>> begin();
}
