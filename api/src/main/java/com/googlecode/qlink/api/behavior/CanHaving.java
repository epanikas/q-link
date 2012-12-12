package com.googlecode.qlink.api.behavior;

import java.util.List;

import com.googlecode.qlink.api.definition.group.having.HavingFilterDef;

public interface CanHaving<K, V extends List<?>, TPlugin>
{
	HavingFilterDef<K, V, TPlugin> having();
}
