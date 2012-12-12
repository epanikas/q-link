package com.googlecode.qlink.core.pruning.order;

import java.util.Arrays;
import java.util.List;

import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.blocks.OrderBlock;
import com.googlecode.qlink.core.context.enums.EOrderBlockType;
import com.googlecode.qlink.core.pruning.IPruningAction;
import com.googlecode.qlink.tuples.Tuples;

public class ToStringOrderPruningAction
	implements IPruningAction<EOrderBlockType, OrderBlock>
{

	@Override
	public List<Pair<EOrderBlockType, OrderBlock>> applyOnStack(List<Pair<EOrderBlockType, OrderBlock>> stackTop)
	{
		String expr = "";
		for (int i = stackTop.size() - 1; i >= 0; --i) {
			OrderBlock ob = stackTop.get(i).getSecond();
			expr += ob.toString() + (i != 0 ? " " : "");
		}

		return Arrays.<Pair<EOrderBlockType, OrderBlock>> asList(Tuples.tie(EOrderBlockType.comparator,
			OrderBlock.forComparator(new ToStringComparator(expr))));
	}

}
