package com.googlecode.qlink.api.behavior;

import com.googlecode.qlink.api.definition.filter.FilterDef;

public interface CanFilter<T, TPlugin> {

	FilterDef<T, TPlugin> filter();

}
