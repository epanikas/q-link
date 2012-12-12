package com.googlecode.qlink.core.pruning;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import com.googlecode.qlink.api.tuple.Pair;

public class PruningRule<TChainType, TBlock>
{
	private final List<TChainType> pattern;

	private final IPruningAction<TChainType, TBlock> action;

	public PruningRule(IPruningAction<TChainType, TBlock> action, TChainType... pattern)
	{
		this.pattern = pattern == null ? null : Arrays.asList(pattern);
		this.action = action;
	}

	public PruningRule(IPruningAction<TChainType, TBlock> action, List<TChainType> pattern)
	{
		this.pattern = pattern;
		this.action = action;
	}

	public boolean doesMatch(Stack<Pair<TChainType, TBlock>> stack)
	{
		int l = stack.size() - pattern.size();
		if (l < 0) {
			/*
			 * the stack length is smaller than required
			 */
			return false;
		}

		for (int i = l, k = 0; i < stack.size(); ++i, ++k) {
			TChainType stackElemType = stack.get(i).getFirst();
			TChainType patternElemType = pattern.get(k);
			if (stackElemType != patternElemType) {
				return false;
			}
		}

		return true;
	}

	public List<TChainType> getPattern()
	{
		return pattern;
	}

	public boolean prune(Stack<Pair<TChainType, TBlock>> stack)
	{
		List<Pair<TChainType, TBlock>> topStack = new ArrayList<Pair<TChainType, TBlock>>();
		for (int i = 0; i < pattern.size(); i++) {
			topStack.add(stack.pop());
		}

		List<Pair<TChainType, TBlock>> actionRes = action.applyOnStack(topStack);

		if (actionRes.size() != 0) {
			for (Pair<TChainType, TBlock> p : actionRes) {
				stack.push(p);
			}
		}

		return true;

	}

	@Override
	public String toString()
	{
		return pattern.toString();
	}
}
