package com.googlecode.qlink.api.definition.sample;

import com.googlecode.qlink.api.behavior.CanAggregate;
import com.googlecode.qlink.api.behavior.CanIndex;
import com.googlecode.qlink.api.behavior.CanTransform;
import com.googlecode.qlink.api.behavior.CanVisit;
import com.googlecode.qlink.api.behavior.DoResultAsList;

public interface SampleEndDef<T, TPlugin>
	extends CanTransform<T, TPlugin>, //
	CanIndex<T, TPlugin>, //
	CanAggregate<T, TPlugin>, //
	DoResultAsList<T, TPlugin>, //
	CanVisit<T, TPlugin>
{
	// empty
}
