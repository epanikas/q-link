package com.googlecode.qlink.core.pruning.order;

import java.util.Arrays;
import java.util.List;

import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.blocks.OrderBlock;
import com.googlecode.qlink.core.context.enums.EOrderBlockType;
import com.googlecode.qlink.core.pruning.IPruningAction;
import com.googlecode.qlink.tuples.Tuples;

public class PropertyOrderPruningAction
	implements IPruningAction<EOrderBlockType, OrderBlock>
{
	@Override
	public List<Pair<EOrderBlockType, OrderBlock>> applyOnStack(List<Pair<EOrderBlockType, OrderBlock>> stackTop)
	{

		Pair<EOrderBlockType, OrderBlock> followUp = stackTop.get(0);
		Pair<EOrderBlockType, OrderBlock> property = stackTop.get(1);

		String expr = property.getSecond().getProperty().getName();

		return Arrays.<Pair<EOrderBlockType, OrderBlock>> asList(//
			Tuples.tie(EOrderBlockType.comparator, OrderBlock.forComparator(new ToStringComparator(expr))), followUp);
	}

}
