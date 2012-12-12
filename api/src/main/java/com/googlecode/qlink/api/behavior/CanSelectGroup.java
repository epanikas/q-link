package com.googlecode.qlink.api.behavior;

import java.util.List;

import com.googlecode.qlink.api.definition.selectgroup.SelectGroupDefinitions.StartSelectGroupDef;

public interface CanSelectGroup<K, V extends List<?>, TPlugin>
{
	StartSelectGroupDef<K, V, TPlugin> selectAs();
}
