package com.googlecode.qlink.api.behavior;

import com.googlecode.qlink.api.definition.group.StartGroupByDef;
import com.googlecode.qlink.api.definition.group.StartIndexByDef;

public interface CanIndex<T, TPlugin>
{

	StartIndexByDef<T, TPlugin> index();

	StartGroupByDef<T, TPlugin> group();
}
