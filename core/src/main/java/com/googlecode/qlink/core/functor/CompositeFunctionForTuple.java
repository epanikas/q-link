package com.googlecode.qlink.core.functor;

import java.util.List;

import com.googlecode.qlink.api.functor.Function2;
import com.googlecode.qlink.core.utils.TypedBeanUtils;
import com.googlecode.qlink.tuples.Tuples;

public class CompositeFunctionForTuple<T, R>
	implements Function2<T, Integer, R>
{

	protected final List<Function2<T, Integer, Object>> functions;
	protected final boolean asArray;
	protected final Class<R> newCls;

	public CompositeFunctionForTuple(List<Function2<T, Integer, Object>> functions, boolean asArray)
	{
		this.functions = functions;
		this.asArray = asArray;
		newCls = null;
	}

	public CompositeFunctionForTuple(List<Function2<T, Integer, Object>> functions, Class<R> newCls)
	{
		this.functions = functions;
		this.newCls = newCls;
		asArray = false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public R apply(T input, Integer index)
	{

		Object[] objarr = new Object[functions.size()];
		int i = 0;
		for (Function2<T, Integer, Object> f : functions) {
			objarr[i++] = f.apply(input, index);
		}

		if (newCls != null) {
			return TypedBeanUtils.createObjectForClass(newCls, objarr);
		}

		if (asArray) {
			return (R) objarr;
		}

		return (R) Tuples.createTupleFor(objarr);
	}

}
