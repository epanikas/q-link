package com.googlecode.qlink.core.pruning.indexer;

import java.util.Arrays;
import java.util.List;

import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.blocks.GroupByBlock;
import com.googlecode.qlink.core.context.enums.EGroupByBlockType;
import com.googlecode.qlink.core.pruning.IPruningAction;
import com.googlecode.qlink.tuples.Tuples;

public class ToStringGroupByPruningAction
	implements IPruningAction<EGroupByBlockType, GroupByBlock>
{
	@Override
	public List<Pair<EGroupByBlockType, GroupByBlock>> applyOnStack(List<Pair<EGroupByBlockType, GroupByBlock>> stackTop)
	{
		String expr = "";

		for (int i = stackTop.size() - 1; i >= 0; i--) {
			expr += (i == stackTop.size() - 1 ? "" : " ") + stackTop.get(i).getSecond().toString();
		}

		return Arrays.<Pair<EGroupByBlockType, GroupByBlock>> asList(Tuples.tie(
			EGroupByBlockType./*indexIndexer*/indexer, GroupByBlock.forIndexer(new ToStringIndexer(expr))));
	}
}
