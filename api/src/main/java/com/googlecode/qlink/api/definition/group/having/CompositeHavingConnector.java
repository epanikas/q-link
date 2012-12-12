package com.googlecode.qlink.api.definition.group.having;

import java.util.List;

import com.googlecode.qlink.api.definition.filter.common.CommonConnector;

public interface CompositeHavingConnector<K, V extends List<?>, TPlugin, TRemainingConnector>
	extends CommonConnector<CompositeHavingFilterDef<K, V, TPlugin, TRemainingConnector>>
{

	TRemainingConnector end();

}
