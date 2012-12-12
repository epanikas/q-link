package com.googlecode.qlink.hibernate.pruning.filter;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.googlecode.qlink.api.functor.Predicate;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.blocks.FilterBlock;
import com.googlecode.qlink.core.context.enums.EFilterBlockType;
import com.googlecode.qlink.core.context.enums.EFilterJunction;
import com.googlecode.qlink.core.pruning.IPruningAction;
import com.googlecode.qlink.hibernate.functor.SqlAwarePredicates;
import com.googlecode.qlink.tuples.Tuples;

public class JunctionPruningAction implements IPruningAction<EFilterBlockType, FilterBlock> {

	@Override
	public List<Pair<EFilterBlockType, FilterBlock>> applyOnStack(List<Pair<EFilterBlockType, FilterBlock>> stackTop) {
		Predicate p1 = stackTop.get(0).getSecond().getPredicate();
		EFilterJunction junction = stackTop.get(1).getSecond().getJunction();
		Predicate p2 = stackTop.get(2).getSecond().getPredicate();

		Predicate p = SqlAwarePredicates.junctionPredicate(p1, junction, p2);

		return Arrays.<Pair<EFilterBlockType, FilterBlock>> asList(Tuples.tie(EFilterBlockType.predicate, FilterBlock.forPredicate(p)));

	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("prune: p1 && p2 ==> predicate").toString();
	}

}
