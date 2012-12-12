package com.googlecode.qlink.core.pruning;

import java.util.List;
import java.util.Stack;

import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.tuples.Tuples;

public class Pruner<TChainType extends Enum<?>, TBlock>
{
	private final List<PruningRule<TChainType, TBlock>> rules;
	private final TChainType endOfStack;

	public Pruner(List<PruningRule<TChainType, TBlock>> rules, TChainType endOfStack)
	{
		this.rules = rules;
		this.endOfStack = endOfStack;
	}

	public void prune(Stack<Pair<TChainType, TBlock>> stack)
	{

		if (stack.size() == 0) {
			/*
			 * nothing to do here
			 */
			return;
		}

		Stack<Pair<TChainType, TBlock>> internalStack = new Stack<Pair<TChainType, TBlock>>();
		for (int i = 0; i < stack.size() + 1; ++i) {
			if (i < stack.size()) {
				internalStack.push(stack.get(i));
			}
			else {
				internalStack.push(Tuples.tie(endOfStack, (TBlock) null));
			}

			boolean pruning = false;
			do {
				pruning = false;
				for (PruningRule<TChainType, TBlock> pr : rules) {
					if (pr.doesMatch(internalStack)) {
						pruning = pr.prune(internalStack);
					}
				}
				if (internalStack.peek().getFirst() == endOfStack) {
					internalStack.pop();
				}
			}
			while (pruning);

		}

		/*
		 * remove the endOfStack cap from the resulting stack
		 */
		if (internalStack.peek().getFirst() == endOfStack) {
			internalStack.pop();
		}

		/*
		 * finally copy the content of the pruned stack to the initial one
		 */
		stack.clear();
		for (int i = 0; i < internalStack.size(); ++i) {
			stack.push(internalStack.get(i));
		}
	}

}
