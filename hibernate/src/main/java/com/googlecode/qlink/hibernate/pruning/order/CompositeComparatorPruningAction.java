package com.googlecode.qlink.hibernate.pruning.order;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;


import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.blocks.OrderBlock;
import com.googlecode.qlink.core.context.enums.EOrderBlockType;
import com.googlecode.qlink.core.pruning.IPruningAction;
import com.googlecode.qlink.hibernate.functor.SqlAwareCompositeComparator;
import com.googlecode.qlink.hibernate.functor.SqlAwarePropertyComparator;
import com.googlecode.qlink.tuples.Tuples;

public class CompositeComparatorPruningAction
	implements IPruningAction<EOrderBlockType, OrderBlock>
{

	@Override
	public List<Pair<EOrderBlockType, OrderBlock>> applyOnStack(List<Pair<EOrderBlockType, OrderBlock>> stackTop)
	{
		SqlAwarePropertyComparator<?, ?> c2 = (SqlAwarePropertyComparator) stackTop.get(0).getSecond().getComparator();
		SqlAwarePropertyComparator<?, ?> c1 = (SqlAwarePropertyComparator) stackTop.get(1).getSecond().getComparator();

		Comparator c = new SqlAwareCompositeComparator(c1, c2);

		return Arrays.<Pair<EOrderBlockType, OrderBlock>> asList(Tuples.tie(EOrderBlockType.comparator,
			OrderBlock.forComparator(c)));
	}
}
