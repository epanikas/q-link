package com.googlecode.qlink.core.utils;

import java.util.Iterator;
import java.util.List;

import com.googlecode.qlink.api.functor.Folder;
import com.googlecode.qlink.api.functor.Reducer;
import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.blocks.TransformBlock;
import com.googlecode.qlink.core.context.enums.EAggregatorType;
import com.googlecode.qlink.core.context.enums.EFoldSide;
import com.googlecode.qlink.core.context.enums.ETransformResultType;
import com.googlecode.qlink.tuples.Tuples;

public class AggregationUtils
{

	private AggregationUtils()
	{
		// private ctor
	}

	public static <T> T applyReducer(List<T> lst, Reducer<T> r)
	{
		Iterator<T> it = lst.iterator();
		T a = it.next();
		T b = it.next();

		T res = r.reduce(a, b);
		for (; it.hasNext();) {
			res = r.reduce(res, it.next());
		}

		return res;
	}

	public static <T, O> O applyFolder(List<T> lst, Folder<T, O> f, O init, EFoldSide foldSide)
	{
		O res = init;
		switch (foldSide) {
			case left:
				for (T t : lst) {
					res = f.apply(res, t);
				}
				break;

			case right:
				for (int i = lst.size() - 1; i >= 0; --i) {
					res = f.apply(res, lst.get(i));
				}
				break;

			default:
				throw new IllegalArgumentException("unrecognized fold side " + foldSide);
		}

		return res;
	}

	public static Object adaptAggregatedResult(Object[] aggregatedResult,
												List<Pair<EAggregatorType, TransformBlock>> aggregators,
												ETransformResultType resultType, Class<?> resCls)
	{

		/*
		 * coarse the result if necessary
		 * hibernate sometimes invent the types: sum(integer) yeilds Long, so convert the types manually
		 */
		int i = 0;
		for (Object res : aggregatedResult) {
			Pair<EAggregatorType, TransformBlock> r = aggregators.get(i);
			TransformBlock tb = r.getSecond();

			if (tb.getAggregatorType() != null) {
				TProperty<?> prop = tb.getProperty();

				if (prop != null && prop.getPropertyCls().isAssignableFrom(res.getClass()) == false) {
					if (Number.class.isAssignableFrom(res.getClass())) {
						aggregatedResult[i] = TypedNumberUtils.coarseToNumberType(res, prop.getPropertyCls());
					}
					else {
						throw new IllegalArgumentException("can't convert " + res.getClass() + " to "
							+ prop.getPropertyCls());
					}
				}
			}
			++i;
		}

		switch (resultType) {
			case tuple:
				if (aggregatedResult.length == 1) {
					return aggregatedResult[0];
				}
				return Tuples.createTupleFor(aggregatedResult);

			case arrayObject:
				return aggregatedResult;

			case newObject:
				return TypedBeanUtils.createObjectForClass(resCls, aggregatedResult);

			default:
				throw new IllegalArgumentException("unrecognized  result type " + resultType);
		}

	}

}
