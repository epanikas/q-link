package com.googlecode.qlink.mem.pruning.filter;

import java.util.Arrays;
import java.util.List;


import com.googlecode.qlink.api.functor.Aggregator;
import com.googlecode.qlink.api.functor.Predicate;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.blocks.FilterBlock;
import com.googlecode.qlink.core.context.enums.EFilterBlockType;
import com.googlecode.qlink.core.functor.AggregatorPredicate;
import com.googlecode.qlink.core.pruning.IPruningAction;
import com.googlecode.qlink.core.utils.SimpleAssert;
import com.googlecode.qlink.mem.utils.MemAggregationUtils;
import com.googlecode.qlink.tuples.Tuples;

public class AggregatorConditionPruningAction
	implements IPruningAction<EFilterBlockType, FilterBlock>
{

	@Override
	public List<Pair<EFilterBlockType, FilterBlock>> applyOnStack(List<Pair<EFilterBlockType, FilterBlock>> stackTop)
	{
		FilterBlock val = stackTop.get(0).getSecond();
		FilterBlock condition = stackTop.get(1).getSecond();
		FilterBlock aggregatorBlock = stackTop.get(2).getSecond();
		Aggregator<?, ?> aggregator = aggregatorBlock.getAggregator();

		if (aggregator == null) {
			/*
			 * create the aggregator
			 */
			SimpleAssert.notNull(aggregatorBlock.getAggregatorType());

			aggregator =
				MemAggregationUtils
					.chooseAggregator(aggregatorBlock.getAggregatorType(), aggregatorBlock.getProperty());
		}

		Predicate p = new AggregatorPredicate(aggregator, condition.getCondition(), val.getValue());

		return Arrays.<Pair<EFilterBlockType, FilterBlock>> asList(Tuples.tie(EFilterBlockType.predicate,
			FilterBlock.forPredicate(p)));
	}
}
