package com.googlecode.qlink.api.definition.visit;

public interface VisitAssignDef<TProp, T, TPlugin>
{
	VisitRhsDef<TProp, T, TPlugin> assign();

	VisitDef<T, TPlugin> assign(TProp val);
}
