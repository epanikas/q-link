package com.googlecode.qlink.core.pruning.filter;

import com.googlecode.qlink.core.context.blocks.FilterBlock;
import com.googlecode.qlink.core.context.enums.EFilterBlockType;
import com.googlecode.qlink.core.pruning.IPruningAction;
import com.googlecode.qlink.core.pruning.PruningRule;

public class PropertyConditionPruningRule
	extends PruningRule<EFilterBlockType, FilterBlock>
{

	public PropertyConditionPruningRule(IPruningAction<EFilterBlockType, FilterBlock> action)
	{
		super(action, EFilterBlockType.property, EFilterBlockType.condition, EFilterBlockType.val);
	}
}
