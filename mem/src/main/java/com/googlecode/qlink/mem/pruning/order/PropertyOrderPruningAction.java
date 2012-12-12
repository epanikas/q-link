package com.googlecode.qlink.mem.pruning.order;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.blocks.OrderBlock;
import com.googlecode.qlink.core.context.enums.EOrderBlockType;
import com.googlecode.qlink.core.context.enums.EOrderDirection;
import com.googlecode.qlink.core.functor.PropertyComparator;
import com.googlecode.qlink.core.pruning.IPruningAction;
import com.googlecode.qlink.tuples.Tuples;

public class PropertyOrderPruningAction
	implements IPruningAction<EOrderBlockType, OrderBlock>
{

	@Override
	public List<Pair<EOrderBlockType, OrderBlock>> applyOnStack(List<Pair<EOrderBlockType, OrderBlock>> stackTop)
	{
		OrderBlock ob = stackTop.get(1).getSecond();

		Comparator c = new PropertyComparator(ob.getProperty(), EOrderDirection.asc);

		return Arrays.<Pair<EOrderBlockType, OrderBlock>> asList(Tuples.tie(EOrderBlockType.comparator,
			OrderBlock.forComparator(c)));
	}
}
