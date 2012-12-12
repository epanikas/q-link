package com.googlecode.qlink.core.pruning.visit;

import com.googlecode.qlink.core.context.blocks.VisitBlock;
import com.googlecode.qlink.core.context.enums.EVisitBlockType;
import com.googlecode.qlink.core.pruning.IPruningAction;
import com.googlecode.qlink.core.pruning.PruningRule;

public class AssignVisitPruningRule
	extends PruningRule<EVisitBlockType, VisitBlock>
{

	public AssignVisitPruningRule(EVisitBlockType followUp, IPruningAction<EVisitBlockType, VisitBlock> action)
	{
		super(action, EVisitBlockType.lhsProperty, EVisitBlockType.op, followUp);
	}

}
