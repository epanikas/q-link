package com.googlecode.qlink.tuples;

import com.googlecode.qlink.api.tuple.Tuple;
import com.googlecode.qlink.api.tuple.Tuple3;
import com.googlecode.qlink.api.tuple.Tuple4;
import com.googlecode.qlink.api.tuple.Tuple5;
import com.googlecode.qlink.api.tuple.TupleN;
import com.googlecode.qlink.core.utils.SimpleAssert;

public class Tuples
{
	public static <A, B> PairImpl<A, B> tie(A a, B b)
	{
		return new PairImpl<A, B>(a, b);
	}

	public static <T1, T2, T3> Tuple3<T1, T2, T3> tie(T1 first, T2 second, T3 third)
	{
		return new Tuple3Impl<T1, T2, T3>(first, second, third);
	}

	public static <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> tie(T1 first, T2 second, T3 third, T4 fourth)
	{
		return new Tuple4Impl<T1, T2, T3, T4>(first, second, third, fourth);
	}

	public static <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> tie(T1 first, T2 second, T3 third, T4 fourth, T5 fifth)
	{
		return new Tuple5Impl<T1, T2, T3, T4, T5>(first, second, third, fourth, fifth);
	}

	public static Object createTupleOrObjectFor(Object[] args)
	{
		if (args.length == 1) {
			return args[0];
		}

		return createTupleFor(args);
	}

	public static Tuple createTupleFor(Object[] args)
	{
		switch (args.length) {
			case 2:
				return Tuples.tie(args[0], args[1]);

			case 3:
				return Tuples.tie(args[0], args[1], args[2]);

			case 4:
				return Tuples.tie(args[0], args[1], args[2], args[3]);

			case 5:
				return Tuples.tie(args[0], args[1], args[2], args[3], args[4]);

			default:
				return createCompositeTuple(args);
		}

	}

	private static TupleN<?, ?> createCompositeTuple(Object[] args)
	{
		SimpleAssert.isTrue(args.length > 5);

		TupleN<?, ?> res = tieComposite(tie(args[0], args[1], args[2], args[3], args[4]), args[5]);
		for (int i = 6; i < args.length; ++i) {
			res = tieComposite(res, args[i]);
		}

		return res;
	}

	public static <TTuple extends Tuple, B> TupleN<TTuple, B> tieComposite(TTuple head, B last)
	{
		return new TupleNImpl<TTuple, B>(head, last);
	}
}
