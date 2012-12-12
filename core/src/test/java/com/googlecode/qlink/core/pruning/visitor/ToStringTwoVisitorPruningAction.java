package com.googlecode.qlink.core.pruning.visitor;

import java.util.Arrays;
import java.util.List;

import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.blocks.VisitBlock;
import com.googlecode.qlink.core.context.enums.EVisitBlockType;
import com.googlecode.qlink.core.pruning.IPruningAction;
import com.googlecode.qlink.tuples.Tuples;

public class ToStringTwoVisitorPruningAction
	implements IPruningAction<EVisitBlockType, VisitBlock>
{

	@Override
	public List<Pair<EVisitBlockType, VisitBlock>> applyOnStack(List<Pair<EVisitBlockType, VisitBlock>> stackTop)
	{
		String expr = "";
		for (int i = stackTop.size() - 1; i >= 0; --i) {
			expr += stackTop.get(i).getSecond().toString() + (i != 0 ? "; " : "");
		}

		return Arrays.<Pair<EVisitBlockType, VisitBlock>> asList(Tuples.tie(EVisitBlockType.visitor,
			VisitBlock.forVisitor2(new ToStringVisitor2(expr))));
	}
}
