package com.googlecode.qlink.api.definition;

import com.googlecode.qlink.api.behavior.CanAggregate;
import com.googlecode.qlink.api.behavior.CanFilter;
import com.googlecode.qlink.api.behavior.CanIndex;
import com.googlecode.qlink.api.behavior.CanOrder;
import com.googlecode.qlink.api.behavior.CanSample;
import com.googlecode.qlink.api.behavior.CanTransform;
import com.googlecode.qlink.api.behavior.CanVisit;
import com.googlecode.qlink.api.behavior.DoResultAsList;

// start --> visit --> filter --> order --> transform --> sample -->
// reduce --> toValue
// index --> toMap
// group --> having --> selectAs --> toMap

public interface EntryPointDef<T, TPlugin>
	extends CanFilter<T, TPlugin>, //
	CanOrder<T, TPlugin>, //
	CanSample<T, TPlugin>, //
	CanIndex<T, TPlugin>, //
	DoResultAsList<T, TPlugin>, //
	CanTransform<T, TPlugin>, //
	CanAggregate<T, TPlugin>, //
	CanVisit<T, TPlugin>
{
	// empty
}
