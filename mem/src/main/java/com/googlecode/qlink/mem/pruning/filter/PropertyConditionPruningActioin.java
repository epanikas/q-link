package com.googlecode.qlink.mem.pruning.filter;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.googlecode.qlink.api.functor.Predicate;
import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.blocks.FilterBlock;
import com.googlecode.qlink.core.context.enums.EFilterBlockType;
import com.googlecode.qlink.core.context.enums.EFilterCondition;
import com.googlecode.qlink.core.functor.Predicates;
import com.googlecode.qlink.core.pruning.IPruningAction;
import com.googlecode.qlink.core.utils.SimpleAssert;
import com.googlecode.qlink.tuples.Tuples;

public class PropertyConditionPruningActioin
	implements IPruningAction<EFilterBlockType, FilterBlock>
{

	@Override
	public List<Pair<EFilterBlockType, FilterBlock>> applyOnStack(List<Pair<EFilterBlockType, FilterBlock>> stackTop)
	{
		SimpleAssert.isTrue(stackTop.size() == 2 || stackTop.size() == 3);

		TProperty prop = null;
		EFilterCondition cond = null;
		Object value = null;

		if (stackTop.size() == 2) {
			value = stackTop.get(0).getSecond().getValue();
			cond = stackTop.get(0).getSecond().getCondition();
			prop = stackTop.get(1).getSecond().getProperty();
		}
		else {
			value = stackTop.get(0).getSecond().getValue();
			cond = stackTop.get(1).getSecond().getCondition();
			prop = stackTop.get(2).getSecond().getProperty();

		}

		/*
		 * prop can be null, if elem was specified
		 */
		Predicate p = Predicates.propertyPredicate(prop == null ? null : prop.getName(), cond, value);

		return Arrays.<Pair<EFilterBlockType, FilterBlock>> asList(Tuples.tie(EFilterBlockType.predicate,
			FilterBlock.forPredicate(p)));
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this).append("action: x > y ==> predicate").toString();
	}
}
