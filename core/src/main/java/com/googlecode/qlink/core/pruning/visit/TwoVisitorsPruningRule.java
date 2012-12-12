package com.googlecode.qlink.core.pruning.visit;

import com.googlecode.qlink.core.context.blocks.VisitBlock;
import com.googlecode.qlink.core.context.enums.EVisitBlockType;
import com.googlecode.qlink.core.pruning.IPruningAction;
import com.googlecode.qlink.core.pruning.PruningRule;

public class TwoVisitorsPruningRule
	extends PruningRule<EVisitBlockType, VisitBlock>
{

	public TwoVisitorsPruningRule(IPruningAction<EVisitBlockType, VisitBlock> action)
	{
		super(action, EVisitBlockType.visitor, EVisitBlockType.visitor);
	}

}
