package com.googlecode.qlink.core.pruning.filter;

import com.googlecode.qlink.core.context.blocks.FilterBlock;
import com.googlecode.qlink.core.context.enums.EFilterBlockType;
import com.googlecode.qlink.core.pruning.IPruningAction;
import com.googlecode.qlink.core.pruning.PruningRule;

public class NotPredicatePruningRule
	extends PruningRule<EFilterBlockType, FilterBlock>
{

	public NotPredicatePruningRule(IPruningAction<EFilterBlockType, FilterBlock> action)
	{
		super(action, EFilterBlockType.not, EFilterBlockType.predicate);
	}
}
