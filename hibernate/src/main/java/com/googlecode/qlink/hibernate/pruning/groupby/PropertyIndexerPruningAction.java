package com.googlecode.qlink.hibernate.pruning.groupby;

import java.util.Arrays;
import java.util.List;


import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.blocks.GroupByBlock;
import com.googlecode.qlink.core.context.enums.EGroupByBlockType;
import com.googlecode.qlink.core.pruning.IPruningAction;
import com.googlecode.qlink.hibernate.functor.SqlAwarePropertyIndexer;
import com.googlecode.qlink.tuples.Tuples;

public class PropertyIndexerPruningAction implements IPruningAction<EGroupByBlockType, GroupByBlock> {

	@Override
	public List<Pair<EGroupByBlockType, GroupByBlock>> applyOnStack(List<Pair<EGroupByBlockType, GroupByBlock>> stackTop) {
		GroupByBlock gbb = stackTop.get(0).getSecond();
		TProperty<?> typedProp = gbb.getProperty();

		return Arrays.<Pair<EGroupByBlockType, GroupByBlock>> asList(Tuples.tie(EGroupByBlockType.indexer,
				GroupByBlock.forIndexer(new SqlAwarePropertyIndexer(typedProp))));
	}
}
