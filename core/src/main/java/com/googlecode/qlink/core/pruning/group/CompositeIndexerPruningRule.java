package com.googlecode.qlink.core.pruning.group;

import com.googlecode.qlink.core.context.blocks.GroupByBlock;
import com.googlecode.qlink.core.context.enums.EGroupByBlockType;
import com.googlecode.qlink.core.pruning.IPruningAction;
import com.googlecode.qlink.core.pruning.PruningRule;

public class CompositeIndexerPruningRule extends PruningRule<EGroupByBlockType, GroupByBlock> {

	public CompositeIndexerPruningRule(IPruningAction<EGroupByBlockType, GroupByBlock> action) {
		super(action, EGroupByBlockType.indexer, EGroupByBlockType.indexer);
	}

}
