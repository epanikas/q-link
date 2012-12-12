package com.googlecode.qlink.api.behavior;

import com.googlecode.qlink.api.definition.aggregate.FoldDef;
import com.googlecode.qlink.api.definition.aggregate.ReduceDef;
import com.googlecode.qlink.api.definition.aggregate.AggregateDefinitions.StartAggregateDef;

public interface CanAggregate<T, TPlugin> {

	StartAggregateDef<T, TPlugin> aggregate();

	ReduceDef<T, TPlugin> reduce();

	FoldDef<T, TPlugin> foldLeft();

	FoldDef<T, TPlugin> foldRight();

}
