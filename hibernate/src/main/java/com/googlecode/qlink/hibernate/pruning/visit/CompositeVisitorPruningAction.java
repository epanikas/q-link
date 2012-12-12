package com.googlecode.qlink.hibernate.pruning.visit;

import java.util.Arrays;
import java.util.List;


import com.googlecode.qlink.api.functor.Visitor2;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.blocks.VisitBlock;
import com.googlecode.qlink.core.context.enums.EVisitBlockType;
import com.googlecode.qlink.core.functor.CompositeVisitor;
import com.googlecode.qlink.core.pruning.IPruningAction;
import com.googlecode.qlink.tuples.Tuples;

public class CompositeVisitorPruningAction
	implements IPruningAction<EVisitBlockType, VisitBlock>
{

	@Override
	public List<Pair<EVisitBlockType, VisitBlock>> applyOnStack(List<Pair<EVisitBlockType, VisitBlock>> stackTop)
	{
		Visitor2<?, Integer> v2 = stackTop.get(0).getSecond().getVisitor();
		Visitor2<?, Integer> v1 = stackTop.get(1).getSecond().getVisitor();

		return Arrays.<Pair<EVisitBlockType, VisitBlock>> asList(//
			Tuples.tie(EVisitBlockType.visitor, VisitBlock.forVisitor2(new CompositeVisitor(v1, v2))));
	}
}
