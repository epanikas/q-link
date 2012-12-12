package com.googlecode.qlink.mem.pruning.groupby;

import java.util.Arrays;
import java.util.List;


import com.googlecode.qlink.api.functor.Function2;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.blocks.GroupByBlock;
import com.googlecode.qlink.core.context.enums.EGroupByBlockType;
import com.googlecode.qlink.core.functor.CompositeIndexer;
import com.googlecode.qlink.core.pruning.IPruningAction;
import com.googlecode.qlink.tuples.Tuples;

public class CompositeIndexerPruningAction
	implements IPruningAction<EGroupByBlockType, GroupByBlock>
{

	@Override
	public List<Pair<EGroupByBlockType, GroupByBlock>> applyOnStack(List<Pair<EGroupByBlockType, GroupByBlock>> stackTop)
	{
		Function2 ind2 = stackTop.get(0).getSecond().getKeyValueIndexer();
		Function2 ind1 = stackTop.get(1).getSecond().getKeyValueIndexer();

		return Arrays.<Pair<EGroupByBlockType, GroupByBlock>> asList(Tuples.tie(EGroupByBlockType.indexer,
			GroupByBlock.forKeyValueIndexer(new CompositeIndexer(ind1, ind2))));
	}
}
