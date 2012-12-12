package com.googlecode.qlink.hibernate.utils;

import java.util.Arrays;
import java.util.Stack;


import com.googlecode.qlink.api.functor.Aggregator;
import com.googlecode.qlink.api.functor.Function2;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.blocks.TransformBlock;
import com.googlecode.qlink.core.context.enums.ETransformBlockType;
import com.googlecode.qlink.core.context.enums.ETransformResultType;
import com.googlecode.qlink.core.functor.Functions;
import com.googlecode.qlink.core.utils.SimpleAssert;
import com.googlecode.qlink.hibernate.functor.SqlAwareAggregators;
import com.googlecode.qlink.hibernate.functor.SqlAwareCompositeFunction;
import com.googlecode.qlink.hibernate.functor.SqlAwareFunctions;

public class SqlAwareFunctionUtils
{
	private SqlAwareFunctionUtils()
	{
		// private ctor
	}

	public static Function2<?, Integer, ?>[] getTransformFunctions(	Stack<Pair<ETransformBlockType, TransformBlock>> transformStack)
	{

		@SuppressWarnings("unchecked")
		Function2<?, Integer, ?>[] functions = new Function2[transformStack.size()];
		if (transformStack.size() == 0) {
			return functions;
		}

		int i = 0;
		for (Pair<ETransformBlockType, TransformBlock> pair : transformStack) {
			TransformBlock ts = pair.getSecond();
			switch (ts.getType()) {
				case constant:
					functions[i++] = Functions.adaptToFunctionWithIndex(SqlAwareFunctions.constant(ts.getConstant()));
					break;

				case property:
					functions[i++] =
						Functions.adaptToFunctionWithIndex(SqlAwareFunctions.propertyAccessor(ts.getProperty()
							.getName(), ts.getProperty().getPropertyCls()));
					break;

				case valueAggregator:
					Aggregator<?, ?> agg =
						SqlAwareAggregationUtils.chooseAggregator(ts.getAggregatorType(), ts.getProperty());
					functions[i++] = SqlAwareAggregators.asFunctionWithIndex(agg);
					break;

				case functor:
					functions[i++] = Functions.adaptToFunctionWithIndex(ts.getFunction());
					break;

				default:
					throw new IllegalArgumentException("unrecognized type " + ts.getType() + ": " + ts);
			}
		}

		return functions;
	}

	@SuppressWarnings({"unchecked","rawtypes"})
	public static Function2<?, Integer, ?> createTransformFunctionInternal(	Stack<Pair<ETransformBlockType, TransformBlock>> transformStack,
																			ETransformResultType resType,
																			Class<?> resCls)
	{

		if (transformStack.size() == 0) {
			return null;
		}

		SimpleAssert.notNull(resType);

		Function2<?, Integer, ?>[] functions = getTransformFunctions(transformStack);

		switch (resType) {

			case arrayObject:
				return new SqlAwareCompositeFunction(Arrays.asList(functions), /*asArray*/true);

			case newObject:
				return new SqlAwareCompositeFunction(Arrays.asList(functions), resCls);

			case tuple:
				if (transformStack.size() == 1) {
					return functions[0];
				}
				return new SqlAwareCompositeFunction(Arrays.asList(functions), /*asArray*/false);

			default:
				throw new IllegalArgumentException("unrecognized transformation: " + resType + ", " + resCls);
		}
	}

}
