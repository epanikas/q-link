package com.googlecode.qlink.api.definition.order;

import com.googlecode.qlink.api.behavior.CanAggregate;
import com.googlecode.qlink.api.behavior.CanIndex;
import com.googlecode.qlink.api.behavior.CanTransform;
import com.googlecode.qlink.api.behavior.CanVisit;
import com.googlecode.qlink.api.behavior.DoResultAsList;

public interface OrderWithDef<T, TPlugin>
	extends CanIndex<T, TPlugin>, //
	CanAggregate<T, TPlugin>, //
	DoResultAsList<T, TPlugin>, //
	CanTransform<T, TPlugin>, //
	CanVisit<T, TPlugin>
{
	// empty
}
