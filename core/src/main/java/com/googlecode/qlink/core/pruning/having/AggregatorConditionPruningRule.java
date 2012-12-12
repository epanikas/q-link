package com.googlecode.qlink.core.pruning.having;

import com.googlecode.qlink.core.context.blocks.FilterBlock;
import com.googlecode.qlink.core.context.enums.EFilterBlockType;
import com.googlecode.qlink.core.pruning.IPruningAction;
import com.googlecode.qlink.core.pruning.PruningRule;

public class AggregatorConditionPruningRule extends PruningRule<EFilterBlockType, FilterBlock> {
	public AggregatorConditionPruningRule(IPruningAction<EFilterBlockType, FilterBlock> action) {
		super(action, EFilterBlockType.aggregator, EFilterBlockType.condition, EFilterBlockType.val);
	}

}
