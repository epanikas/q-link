package com.googlecode.qlink.core.pruning.transform;

import java.util.Arrays;
import java.util.List;

import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.blocks.TransformBlock;
import com.googlecode.qlink.core.context.enums.ETransformBlockType;
import com.googlecode.qlink.core.pruning.IPruningAction;
import com.googlecode.qlink.core.pruning.ToStringFunction;
import com.googlecode.qlink.tuples.Tuples;

public class ToStringTransformPruningAction
	implements IPruningAction<ETransformBlockType, TransformBlock>
{

	@Override
	public List<Pair<ETransformBlockType, TransformBlock>> applyOnStack(List<Pair<ETransformBlockType, TransformBlock>> stackTop)
	{
		String expr = "";

		for (int i = stackTop.size() - 1; i >= 0; --i) {
			Pair<ETransformBlockType, TransformBlock> p = stackTop.get(i);
			expr += p.getSecond().toString() + (i > 0 ? " " : "");
		}

		return Arrays.<Pair<ETransformBlockType, TransformBlock>> asList(Tuples.tie(ETransformBlockType.functor,
			TransformBlock.forFunction(new ToStringFunction(expr))));

	}
}
