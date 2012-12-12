package com.googlecode.qlink.core.pruning.transform;

import com.googlecode.qlink.core.context.blocks.TransformBlock;
import com.googlecode.qlink.core.context.enums.ETransformBlockType;
import com.googlecode.qlink.core.pruning.IPruningAction;
import com.googlecode.qlink.core.pruning.PruningRule;

public class TransformPropertyPruningRule
	extends PruningRule<ETransformBlockType, TransformBlock>
{

	public TransformPropertyPruningRule(IPruningAction<ETransformBlockType, TransformBlock> action)
	{
		super(action, ETransformBlockType.property);
	}

}
