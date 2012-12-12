package com.googlecode.qlink.mem.pruning.groupby;

import java.util.Arrays;
import java.util.List;


import com.googlecode.qlink.api.functor.Function2;
import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.blocks.GroupByBlock;
import com.googlecode.qlink.core.context.enums.EGroupByBlockType;
import com.googlecode.qlink.core.functor.CustomKeyIndexer;
import com.googlecode.qlink.core.functor.PropertyIndexer;
import com.googlecode.qlink.core.pruning.IPruningAction;
import com.googlecode.qlink.tuples.Tuples;

public class IndexByPropertyPruningAction
	implements IPruningAction<EGroupByBlockType, GroupByBlock>
{

	@Override
	public List<Pair<EGroupByBlockType, GroupByBlock>> applyOnStack(List<Pair<EGroupByBlockType, GroupByBlock>> stackTop)
	{
		GroupByBlock gbb = stackTop.get(0).getSecond();

		Function2<?, Integer, Pair<?, ?>> indexer = null;
		if (gbb.getType() == EGroupByBlockType.customKey) {
			indexer = new CustomKeyIndexer(gbb.getKeyIndexer());
		}
		else {
			TProperty<?> typedProp = gbb.getProperty();
			indexer = new PropertyIndexer(typedProp);
		}

		return Arrays.<Pair<EGroupByBlockType, GroupByBlock>> asList(Tuples.tie(EGroupByBlockType.indexer,
			GroupByBlock.forIndexer(indexer)));
	}
}
