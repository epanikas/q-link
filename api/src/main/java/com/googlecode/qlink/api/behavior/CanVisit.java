package com.googlecode.qlink.api.behavior;

import com.googlecode.qlink.api.definition.visit.StartVisitDef;

public interface CanVisit<T, TPlugin>
{

	StartVisitDef<T, TPlugin> visit();

}
