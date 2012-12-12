package com.googlecode.qlink.hibernate.pruning.filter;

import java.util.Arrays;
import java.util.List;


import com.googlecode.qlink.api.functor.Aggregator;
import com.googlecode.qlink.api.functor.Predicate;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.blocks.FilterBlock;
import com.googlecode.qlink.core.context.enums.EFilterBlockType;
import com.googlecode.qlink.core.pruning.IPruningAction;
import com.googlecode.qlink.hibernate.functor.SqlAwareAggregatorPredicate;
import com.googlecode.qlink.hibernate.utils.SqlAwareAggregationUtils;
import com.googlecode.qlink.tuples.Tuples;

public class AggregatorConditionPruningAction
	implements IPruningAction<EFilterBlockType, FilterBlock>
{

	@Override
	public List<Pair<EFilterBlockType, FilterBlock>> applyOnStack(List<Pair<EFilterBlockType, FilterBlock>> stackTop)
	{
		Object val = stackTop.get(0).getSecond().getValue();
		FilterBlock condition = stackTop.get(1).getSecond();
		FilterBlock fBlock = stackTop.get(2).getSecond();
		Aggregator<?, ?> aggregator = fBlock.getAggregator();

		if (aggregator == null) {
			aggregator = SqlAwareAggregationUtils.chooseAggregator(fBlock.getAggregatorType(), fBlock.getProperty());

		}

		Predicate<?> p = new SqlAwareAggregatorPredicate(aggregator, condition.getCondition(), val);

		Pair<EFilterBlockType, FilterBlock> pair = Tuples.tie(EFilterBlockType.predicate, FilterBlock.forPredicate(p));

		return Arrays.asList(pair);
	}
}
