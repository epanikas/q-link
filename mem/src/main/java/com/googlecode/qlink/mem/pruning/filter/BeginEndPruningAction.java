package com.googlecode.qlink.mem.pruning.filter;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.blocks.FilterBlock;
import com.googlecode.qlink.core.context.enums.EFilterBlockType;
import com.googlecode.qlink.core.pruning.IPruningAction;
import com.googlecode.qlink.core.utils.SimpleAssert;

public class BeginEndPruningAction implements IPruningAction<EFilterBlockType, FilterBlock> {

	@Override
	public List<Pair<EFilterBlockType, FilterBlock>> applyOnStack(List<Pair<EFilterBlockType, FilterBlock>> stackTop) {
		SimpleAssert.isTrue(stackTop.size() == 3);

		/*
		 * begin - predicate - end ==> predicate
		 */
		return Arrays.asList(stackTop.get(1));
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("prune: begin - predicate - end ==> predicate").toString();
	}

}
