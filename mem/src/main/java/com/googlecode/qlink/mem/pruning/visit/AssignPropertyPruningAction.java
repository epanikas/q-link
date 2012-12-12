package com.googlecode.qlink.mem.pruning.visit;

import java.util.Arrays;
import java.util.List;


import com.googlecode.qlink.api.functor.Visitor2;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.blocks.VisitBlock;
import com.googlecode.qlink.core.context.enums.EVisitBlockType;
import com.googlecode.qlink.core.functor.Visitors;
import com.googlecode.qlink.core.pruning.IPruningAction;
import com.googlecode.qlink.core.utils.SimpleAssert;
import com.googlecode.qlink.tuples.Tuples;

public class AssignPropertyPruningAction
	implements IPruningAction<EVisitBlockType, VisitBlock>
{

	@Override
	public List<Pair<EVisitBlockType, VisitBlock>> applyOnStack(List<Pair<EVisitBlockType, VisitBlock>> stackTop)
	{

		VisitBlock lhs = stackTop.get(2).getSecond();
		VisitBlock op = stackTop.get(1).getSecond();
		VisitBlock rhs = stackTop.get(0).getSecond();

		SimpleAssert.isTrue(lhs.getType() == EVisitBlockType.lhsProperty);
		SimpleAssert.isTrue(op.getType() == EVisitBlockType.op);

		Visitor2<?, Integer> assignVisitor;
		switch (rhs.getType()) {
			case rhsFunction:
				assignVisitor = new Visitors.AssignPropertyFromFunctionVisitor(lhs.getProperty(), rhs.getFunction());
				break;

			case rhsProperty:
				assignVisitor = new Visitors.AssignPropertyFromPropertyVisitor(lhs.getProperty(), rhs.getProperty());
				break;

			case rhsValue:
				assignVisitor = new Visitors.AssignPropertyFromValueVisitor(lhs.getProperty(), rhs.getValue());
				break;

			default:
				throw new IllegalArgumentException("inappropriate type: " + rhs.getType() + " shouldn't be here");
		}

		return Arrays.<Pair<EVisitBlockType, VisitBlock>> asList(Tuples.tie(EVisitBlockType.visitor,
			VisitBlock.forVisitor2(assignVisitor)));
	}
}
