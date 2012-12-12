package com.googlecode.qlink.hibernate.pruning.filter;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.googlecode.qlink.api.functor.Predicate;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.blocks.FilterBlock;
import com.googlecode.qlink.core.context.enums.EFilterBlockType;
import com.googlecode.qlink.core.pruning.IPruningAction;
import com.googlecode.qlink.core.utils.SimpleAssert;
import com.googlecode.qlink.hibernate.functor.SqlAwarePredicates;
import com.googlecode.qlink.tuples.Tuples;

public class BeginEndPruningAction implements IPruningAction<EFilterBlockType, FilterBlock> {

	@Override
	public List<Pair<EFilterBlockType, FilterBlock>> applyOnStack(List<Pair<EFilterBlockType, FilterBlock>> stackTop) {
		SimpleAssert.isTrue(stackTop.size() == 3);

		/*
		 * begin - predicate - end ==> predicate
		 */
		Predicate where = stackTop.get(1).getSecond().getPredicate();

		Predicate newWhere = SqlAwarePredicates.beginEndPredicate(where);

		return Arrays.<Pair<EFilterBlockType, FilterBlock>> asList(Tuples.tie(EFilterBlockType.predicate, FilterBlock.forPredicate(newWhere)));
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("prune: begin - predicate - end ==> predicate").toString();
	}

}
