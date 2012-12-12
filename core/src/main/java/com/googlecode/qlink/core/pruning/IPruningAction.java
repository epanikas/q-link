package com.googlecode.qlink.core.pruning;

import java.util.List;

import com.googlecode.qlink.api.tuple.Pair;

public interface IPruningAction<TChainType, TBlock>
{

	/**
	 * @param stackTop - NOTE: the topmost element will come first !!!
	 * @return
	 */
	List<Pair<TChainType, TBlock>> applyOnStack(List<Pair<TChainType, TBlock>> stackTop);

}
