package com.googlecode.qlink.api.definition.transform;

import com.googlecode.qlink.api.behavior.CanAggregate;
import com.googlecode.qlink.api.behavior.CanFilter;
import com.googlecode.qlink.api.behavior.CanIndex;
import com.googlecode.qlink.api.behavior.CanOrder;
import com.googlecode.qlink.api.behavior.CanVisit;
import com.googlecode.qlink.api.behavior.DoResultAsList;

public interface FinishSelectDef<T, TPlugin>
	extends CanFilter<T, TPlugin>, //
	CanOrder<T, TPlugin>, //
	CanIndex<T, TPlugin>, //
	DoResultAsList<T, TPlugin>, //
	CanAggregate<T, TPlugin>, //
	CanVisit<T, TPlugin>
{
	//
}
