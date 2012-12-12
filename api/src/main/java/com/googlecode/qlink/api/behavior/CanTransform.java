package com.googlecode.qlink.api.behavior;

import com.googlecode.qlink.api.definition.transform.SelectDefinitions.StartSelectDef;

public interface CanTransform<T, TPlugin> {

	StartSelectDef<T, TPlugin> select();

}
