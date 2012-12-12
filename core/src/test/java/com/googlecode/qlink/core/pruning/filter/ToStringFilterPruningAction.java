package com.googlecode.qlink.core.pruning.filter;

import java.util.Arrays;
import java.util.List;

import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.blocks.FilterBlock;
import com.googlecode.qlink.core.context.enums.EFilterBlockType;
import com.googlecode.qlink.core.pruning.IPruningAction;
import com.googlecode.qlink.tuples.Tuples;

public class ToStringFilterPruningAction
	implements IPruningAction<EFilterBlockType, FilterBlock>
{

	@Override
	public List<Pair<EFilterBlockType, FilterBlock>> applyOnStack(List<Pair<EFilterBlockType, FilterBlock>> stackTop)
	{
		String expr = "";
		for (int i = stackTop.size() - 1; i >= 0; --i) {
			expr += stackTop.get(i).getSecond().toString() + (i != 0 ? " " : "");
		}

		return Arrays.<Pair<EFilterBlockType, FilterBlock>> asList(Tuples.tie(EFilterBlockType.predicate,
			FilterBlock.forPredicate(new ToStringPredicate(expr))));
	}
}
