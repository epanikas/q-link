package com.googlecode.qlink.core.pruning.group;

import com.googlecode.qlink.core.context.blocks.GroupByBlock;
import com.googlecode.qlink.core.context.enums.EGroupByBlockType;
import com.googlecode.qlink.core.pruning.IPruningAction;
import com.googlecode.qlink.core.pruning.PruningRule;

public class PropertyGroupByPruningRule
	extends PruningRule<EGroupByBlockType, GroupByBlock>
{

	public PropertyGroupByPruningRule(IPruningAction<EGroupByBlockType, GroupByBlock> action)
	{
		super(action, EGroupByBlockType.property);
	}

}
