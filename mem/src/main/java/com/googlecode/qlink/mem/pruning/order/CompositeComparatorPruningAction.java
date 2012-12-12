package com.googlecode.qlink.mem.pruning.order;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.blocks.OrderBlock;
import com.googlecode.qlink.core.context.enums.EOrderBlockType;
import com.googlecode.qlink.core.functor.CompositeComparator;
import com.googlecode.qlink.core.pruning.IPruningAction;
import com.googlecode.qlink.tuples.Tuples;

public class CompositeComparatorPruningAction
	implements IPruningAction<EOrderBlockType, OrderBlock>
{

	@Override
	public List<Pair<EOrderBlockType, OrderBlock>> applyOnStack(List<Pair<EOrderBlockType, OrderBlock>> stackTop)
	{
		Comparator<?> c2 = stackTop.get(0).getSecond().getComparator();
		Comparator<?> c1 = stackTop.get(1).getSecond().getComparator();

		return Arrays.<Pair<EOrderBlockType, OrderBlock>> asList(Tuples.tie(EOrderBlockType.comparator,
			OrderBlock.forComparator(new CompositeComparator(c1, c2))));
	}
}
