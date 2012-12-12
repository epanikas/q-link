package com.googlecode.qlink.api.definition.filter;

import com.googlecode.qlink.api.behavior.CanAggregate;
import com.googlecode.qlink.api.behavior.CanIndex;
import com.googlecode.qlink.api.behavior.CanOrder;
import com.googlecode.qlink.api.behavior.CanSample;
import com.googlecode.qlink.api.behavior.CanTransform;
import com.googlecode.qlink.api.behavior.CanVisit;
import com.googlecode.qlink.api.behavior.DoResultAsList;
import com.googlecode.qlink.api.definition.filter.common.CommonConnector;

public interface Connector<T, TPlugin>
	extends CommonConnector<FilterDef<T, TPlugin>>,//
	CanTransform<T, TPlugin>, //
	CanOrder<T, TPlugin>, //
	CanSample<T, TPlugin>, //
	CanIndex<T, TPlugin>, //
	CanAggregate<T, TPlugin>, //
	DoResultAsList<T, TPlugin>, //
	CanVisit<T, TPlugin>
{
	// empty
}
