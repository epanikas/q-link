package com.googlecode.qlink.api.definition.aggregate;

import com.googlecode.qlink.api.behavior.DoResultAsSingleValue;
import com.googlecode.qlink.api.functor.Aggregator;
import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.api.tuple.Tuple3;
import com.googlecode.qlink.api.tuple.Tuple4;
import com.googlecode.qlink.api.tuple.Tuple5;
import com.googlecode.qlink.api.tuple.TupleN;

public class AggregateDefinitions
{

	public interface AggregateDefSupport<TRes, TPlugin>
		extends DoResultAsSingleValue<TRes, TPlugin>
	{

		<TNewRes> DoResultAsSingleValue<TNewRes, TPlugin> asNew(Class<TNewRes> cls);

		DoResultAsSingleValue<Object[], TPlugin> asArray();
	}

	public interface StartAggregateDef<TOrigElem, TPlugin>
	{

		AggregateDef1<Long, TOrigElem, TPlugin> count();

		AggregateDef1<TOrigElem, TOrigElem, TPlugin> sum();

		AggregateDef1<TOrigElem, TOrigElem, TPlugin> min();

		AggregateDef1<TOrigElem, TOrigElem, TPlugin> max();

		<R> AggregateDef1<R, TOrigElem, TPlugin> sumOf(String propName, Class<R> cls);

		<R> AggregateDef1<R, TOrigElem, TPlugin> sumOf(TProperty<R> tp);

		<R> AggregateDef1<R, TOrigElem, TPlugin> minOf(String propName, Class<R> cls);

		<R> AggregateDef1<R, TOrigElem, TPlugin> minOf(TProperty<R> tp);

		<R> AggregateDef1<R, TOrigElem, TPlugin> maxOf(String propName, Class<R> cls);

		<R> AggregateDef1<R, TOrigElem, TPlugin> maxOf(TProperty<R> tp);

		<R> AggregateDef1<R, TOrigElem, TPlugin> with(Class<R> cls, Aggregator<TOrigElem, R> r);

	}

	public interface AggregateDef1<T1, TOrigElem, TPlugin>
		extends AggregateDefSupport<T1, TPlugin>, DoResultAsSingleValue<T1, TPlugin>
	{

		AggregateDef2<T1, Long, TOrigElem, TPlugin> count();

		AggregateDef2<T1, TOrigElem, TOrigElem, TPlugin> sum();

		AggregateDef2<T1, TOrigElem, TOrigElem, TPlugin> min();

		AggregateDef2<T1, TOrigElem, TOrigElem, TPlugin> max();

		<R> AggregateDef2<T1, R, TOrigElem, TPlugin> sumOf(String propName, Class<R> cls);

		<R> AggregateDef2<T1, R, TOrigElem, TPlugin> sumOf(TProperty<R> tp);

		<R> AggregateDef2<T1, R, TOrigElem, TPlugin> minOf(String propName, Class<R> cls);

		<R> AggregateDef2<T1, R, TOrigElem, TPlugin> minOf(TProperty<R> tp);

		<R> AggregateDef2<T1, R, TOrigElem, TPlugin> maxOf(String propName, Class<R> cls);

		<R> AggregateDef2<T1, R, TOrigElem, TPlugin> maxOf(TProperty<R> tp);

		<R> AggregateDef2<T1, R, TOrigElem, TPlugin> with(Class<R> cls, Aggregator<TOrigElem, R> r);

	}

	public interface AggregateDef2<T1, T2, TOrigElem, TPlugin>
		extends AggregateDefSupport<Pair<T1, T2>, TPlugin>
	{

		AggregateDef3<T1, T2, Long, TOrigElem, TPlugin> count();

		AggregateDef3<T1, T2, TOrigElem, TOrigElem, TPlugin> sum();

		AggregateDef3<T1, T2, TOrigElem, TOrigElem, TPlugin> min();

		AggregateDef3<T1, T2, TOrigElem, TOrigElem, TPlugin> max();

		<R> AggregateDef3<T1, T2, R, TOrigElem, TPlugin> sumOf(String propName, Class<R> cls);

		<R> AggregateDef3<T1, T2, R, TOrigElem, TPlugin> sumOf(TProperty<R> tp);

		<R> AggregateDef3<T1, T2, R, TOrigElem, TPlugin> minOf(String propName, Class<R> cls);

		<R> AggregateDef3<T1, T2, R, TOrigElem, TPlugin> minOf(TProperty<R> tp);

		<R> AggregateDef3<T1, T2, R, TOrigElem, TPlugin> maxOf(String propName, Class<R> cls);

		<R> AggregateDef3<T1, T2, R, TOrigElem, TPlugin> maxOf(TProperty<R> tp);

		<R> AggregateDef3<T1, T2, R, TOrigElem, TPlugin> with(Class<R> cls, Aggregator<TOrigElem, R> r);

	}

	public interface AggregateDef3<T1, T2, T3, TOrigElem, TPlugin>
		extends AggregateDefSupport<Tuple3<T1, T2, T3>, TPlugin>
	{

		AggregateDef4<T1, T2, T3, Long, TOrigElem, TPlugin> count();

		AggregateDef4<T1, T2, T3, TOrigElem, TOrigElem, TPlugin> sum();

		AggregateDef4<T1, T2, T3, TOrigElem, TOrigElem, TPlugin> min();

		AggregateDef4<T1, T2, T3, TOrigElem, TOrigElem, TPlugin> max();

		<R> AggregateDef4<T1, T2, T3, R, TOrigElem, TPlugin> sumOf(String propName, Class<R> cls);

		<R> AggregateDef4<T1, T2, T3, R, TOrigElem, TPlugin> sumOf(TProperty<R> tp);

		<R> AggregateDef4<T1, T2, T3, R, TOrigElem, TPlugin> minOf(String propName, Class<R> cls);

		<R> AggregateDef4<T1, T2, T3, R, TOrigElem, TPlugin> minOf(TProperty<R> tp);

		<R> AggregateDef4<T1, T2, T3, R, TOrigElem, TPlugin> maxOf(String propName, Class<R> cls);

		<R> AggregateDef4<T1, T2, T3, R, TOrigElem, TPlugin> maxOf(TProperty<R> tp);

		<R> AggregateDef4<T1, T2, T3, R, TOrigElem, TPlugin> with(Class<R> cls, Aggregator<TOrigElem, R> r);

	}

	public interface AggregateDef4<T1, T2, T3, T4, TOrigElem, TPlugin>
		extends AggregateDefSupport<Tuple4<T1, T2, T3, T4>, TPlugin>
	{

		AggregateDef5<T1, T2, T3, T4, Long, TOrigElem, TPlugin> count();

		AggregateDef5<T1, T2, T3, T4, TOrigElem, TOrigElem, TPlugin> sum();

		AggregateDef5<T1, T2, T3, T4, TOrigElem, TOrigElem, TPlugin> min();

		AggregateDef5<T1, T2, T3, T4, TOrigElem, TOrigElem, TPlugin> max();

		<R> AggregateDef5<T1, T2, T3, T4, R, TOrigElem, TPlugin> sumOf(String propName, Class<R> cls);

		<R> AggregateDef5<T1, T2, T3, T4, R, TOrigElem, TPlugin> sumOf(TProperty<R> tp);

		<R> AggregateDef5<T1, T2, T3, T4, R, TOrigElem, TPlugin> minOf(String propName, Class<R> cls);

		<R> AggregateDef5<T1, T2, T3, T4, R, TOrigElem, TPlugin> minOf(TProperty<R> tp);

		<R> AggregateDef5<T1, T2, T3, T4, R, TOrigElem, TPlugin> maxOf(String propName, Class<R> cls);

		<R> AggregateDef5<T1, T2, T3, T4, R, TOrigElem, TPlugin> maxOf(TProperty<R> tp);

		<R> AggregateDef5<T1, T2, T3, T4, R, TOrigElem, TPlugin> with(Class<R> cls, Aggregator<TOrigElem, R> r);

	}

	public interface AggregateDef5<T1, T2, T3, T4, T5, TOrigElem, TPlugin>
		extends AggregateDefSupport<Tuple5<T1, T2, T3, T4, T5>, TPlugin>
	{

		AggregateDef1<TupleN<Tuple5<T1, T2, T3, T4, T5>, Long>, TOrigElem, TPlugin> count();

		AggregateDef1<TupleN<Tuple5<T1, T2, T3, T4, T5>, TOrigElem>, TOrigElem, TPlugin> sum();

		AggregateDef1<TupleN<Tuple5<T1, T2, T3, T4, T5>, TOrigElem>, TOrigElem, TPlugin> min();

		AggregateDef1<TupleN<Tuple5<T1, T2, T3, T4, T5>, TOrigElem>, TOrigElem, TPlugin> max();

		<R> AggregateDef1<TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, TOrigElem, TPlugin> sumOf(String propName,
																								Class<R> cls);

		<R> AggregateDef1<TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, TOrigElem, TPlugin> sumOf(TProperty<R> tp);

		<R> AggregateDef1<TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, TOrigElem, TPlugin> minOf(String propName,
																								Class<R> cls);

		<R> AggregateDef1<TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, TOrigElem, TPlugin> minOf(TProperty<R> tp);

		<R> AggregateDef1<TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, TOrigElem, TPlugin> maxOf(String propName,
																								Class<R> cls);

		<R> AggregateDef1<TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, TOrigElem, TPlugin> maxOf(TProperty<R> tp);

		<R> AggregateDef1<TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, TOrigElem, TPlugin> with(Class<R> cls,
																							Aggregator<TOrigElem, R> r);

	}

}