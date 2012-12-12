package com.googlecode.qlink.core.pruning.order;

import com.googlecode.qlink.core.context.blocks.OrderBlock;
import com.googlecode.qlink.core.context.enums.EOrderBlockType;
import com.googlecode.qlink.core.pruning.IPruningAction;
import com.googlecode.qlink.core.pruning.PruningRule;

public class AscDescPruningRule
	extends PruningRule<EOrderBlockType, OrderBlock>
{

	public AscDescPruningRule(IPruningAction<EOrderBlockType, OrderBlock> action)
	{
		super(action, EOrderBlockType.property, EOrderBlockType.ascDesc);
	}
}
