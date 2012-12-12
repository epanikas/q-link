package com.googlecode.qlink.api.definition.group;

import java.util.List;

import com.googlecode.qlink.api.behavior.CanSelectGroup;
import com.googlecode.qlink.api.behavior.DoResultAsMap;
import com.googlecode.qlink.api.definition.group.having.HavingFilterDef;

public interface GroupWithDef<K, V extends List<?>, TPlugin>
	extends DoResultAsMap<K, V, TPlugin>, //
	CanSelectGroup<K, V, TPlugin>
{

	HavingFilterDef<K, V, TPlugin> having();

}
