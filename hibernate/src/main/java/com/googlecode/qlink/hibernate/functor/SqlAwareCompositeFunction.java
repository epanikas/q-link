package com.googlecode.qlink.hibernate.functor;

import java.util.Collections;
import java.util.List;


import com.googlecode.qlink.api.functor.Function2;
import com.googlecode.qlink.core.functor.CompositeFunctionForTuple;
import com.googlecode.qlink.tuples.PairImpl;
import com.googlecode.qlink.tuples.Tuple3Impl;
import com.googlecode.qlink.tuples.Tuple4Impl;
import com.googlecode.qlink.tuples.Tuple5Impl;

public class SqlAwareCompositeFunction<T, R>
	extends CompositeFunctionForTuple<T, R>
	implements SqlClauseSnippet
{

	public SqlAwareCompositeFunction(List<Function2<T, Integer, Object>> functions, boolean asArray)
	{
		super(functions, asArray);
	}

	public SqlAwareCompositeFunction(List<Function2<T, Integer, Object>> functions, Class<R> newCls)
	{
		super(functions, newCls);
	}

	@Override
	public String getSqlClause()
	{
		String clause = "";
		boolean shouldClose = false;

		if (newCls != null) {
			clause = "new " + newCls.getName() + "(";
			shouldClose = true;
		}
		else if (!asArray) {

			shouldClose = true;
			switch (functions.size()) {
				case 2:
					clause = "new " + PairImpl.class.getName() + "(";
					break;

				case 3:
					clause = "new " + Tuple3Impl.class.getName() + "(";
					break;

				case 4:
					clause = "new " + Tuple4Impl.class.getName() + "(";
					break;

				case 5:
					clause = "new " + Tuple5Impl.class.getName() + "(";
					break;

				default:
					clause = "";
					shouldClose = false;
			}
		}

		int i = 0;
		for (Function2<T, Integer, Object> f : functions) {

			clause += (i++ == 0 ? "" : ", ") + SqlAwareFunctions.getSqlClauseSnippet(f).getSqlClause();
		}
		clause += shouldClose ? ")" : "";

		return clause;
	}

	@Override
	public List<Object> getParams()
	{
		return Collections.emptyList();
	}
}
