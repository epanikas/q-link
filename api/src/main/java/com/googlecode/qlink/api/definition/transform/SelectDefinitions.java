package com.googlecode.qlink.api.definition.transform;

import com.googlecode.qlink.api.functor.Function;
import com.googlecode.qlink.api.functor.Function2;
import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.api.tuple.Tuple3;
import com.googlecode.qlink.api.tuple.Tuple4;
import com.googlecode.qlink.api.tuple.Tuple5;
import com.googlecode.qlink.api.tuple.TupleN;

public class SelectDefinitions
{

	public interface SelectDefSupport<TRes, TPlugin>
		extends FinishSelectDef<TRes, TPlugin>
	{

		<TNewRes> FinishSelectDef<TNewRes, TPlugin> asNew(Class<TNewRes> cls);

		FinishSelectDef<Object[], TPlugin> asArray();
	}

	public interface StartSelectDef<T, TPlugin>
	{
		SelectDef1<T, T, TPlugin> self();

		SelectDef1<Integer, T, TPlugin> elemIndex();

		<R> SelectDef1<R, T, TPlugin> with(Function<T, R> f);

		<R> SelectDef1<R, T, TPlugin> with(Function2<T, Integer, R> f);

		<R> SelectDef1<R, T, TPlugin> p(String propName, Class<R> cls);

		<R> SelectDef1<R, T, TPlugin> p(TProperty<R> tp);

		<R> SelectDef1<R, T, TPlugin> p(String propName);

		<R> SelectDef1<R, T, TPlugin> val(R constant);
	}

	public interface SelectDef1<T1, TOrigElem, TPlugin>
		extends SelectDefSupport<T1, TPlugin>
	{
		SelectDef2<T1, TOrigElem, TOrigElem, TPlugin> self();

		SelectDef2<T1, Integer, TOrigElem, TPlugin> elemIndex();

		<R> SelectDef2<T1, R, TOrigElem, TPlugin> with(Function<TOrigElem, R> f);

		<R> SelectDef2<T1, R, TOrigElem, TPlugin> with(Function2<TOrigElem, Integer, R> f);

		<R> SelectDef2<T1, R, TOrigElem, TPlugin> p(String propName, Class<R> cls);

		<R> SelectDef2<T1, R, TOrigElem, TPlugin> p(TProperty<R> tp);

		<R> SelectDef2<T1, R, TOrigElem, TPlugin> p(String propName);

		<R> SelectDef2<T1, R, TOrigElem, TPlugin> val(R constant);
	}

	public interface SelectDef2<T1, T2, TOrigElem, TPlugin>
		extends SelectDefSupport<Pair<T1, T2>, TPlugin>
	{
		SelectDef3<T1, T2, TOrigElem, TOrigElem, TPlugin> self();

		SelectDef3<T1, T2, Integer, TOrigElem, TPlugin> elemIndex();

		<R> SelectDef3<T1, T2, R, TOrigElem, TPlugin> with(Function<TOrigElem, R> f);

		<R> SelectDef3<T1, T2, R, TOrigElem, TPlugin> with(Function2<TOrigElem, Integer, R> f);

		<R> SelectDef3<T1, T2, R, TOrigElem, TPlugin> p(String propName, Class<R> cls);

		<R> SelectDef3<T1, T2, R, TOrigElem, TPlugin> p(TProperty<R> tp);

		<R> SelectDef3<T1, T2, R, TOrigElem, TPlugin> p(String propName);

		<R> SelectDef3<T1, T2, R, TOrigElem, TPlugin> val(R constant);
	}

	public interface SelectDef3<T1, T2, T3, TOrigElem, TPlugin>
		extends SelectDefSupport<Tuple3<T1, T2, T3>, TPlugin>
	{
		SelectDef4<T1, T2, T3, TOrigElem, TOrigElem, TPlugin> self();

		SelectDef4<T1, T2, T3, Integer, TOrigElem, TPlugin> elemIndex();

		<R> SelectDef4<T1, T2, T3, R, TOrigElem, TPlugin> with(Function<TOrigElem, R> f);

		<R> SelectDef4<T1, T2, T3, R, TOrigElem, TPlugin> with(Function2<TOrigElem, Integer, R> f);

		<R> SelectDef4<T1, T2, T3, R, TOrigElem, TPlugin> p(String propName, Class<R> cls);

		<R> SelectDef4<T1, T2, T3, R, TOrigElem, TPlugin> p(TProperty<R> tp);

		<R> SelectDef4<T1, T2, T3, R, TOrigElem, TPlugin> p(String propName);

		<R> SelectDef4<T1, T2, T3, R, TOrigElem, TPlugin> val(R constant);
	}

	public interface SelectDef4<T1, T2, T3, T4, TOrigElem, TPlugin>
		extends SelectDefSupport<Tuple4<T1, T2, T3, T4>, TPlugin>
	{

		SelectDef5<T1, T2, T3, T4, TOrigElem, TOrigElem, TPlugin> self();

		SelectDef5<T1, T2, T3, T4, Integer, TOrigElem, TPlugin> elemIndex();

		<R> SelectDef5<T1, T2, T3, T4, R, TOrigElem, TPlugin> with(Function<TOrigElem, R> f);

		<R> SelectDef5<T1, T2, T3, T4, R, TOrigElem, TPlugin> with(Function2<TOrigElem, Integer, R> f);

		<R> SelectDef5<T1, T2, T3, T4, R, TOrigElem, TPlugin> p(String propName, Class<R> cls);

		<R> SelectDef5<T1, T2, T3, T4, R, TOrigElem, TPlugin> p(TProperty<R> tp);

		<R> SelectDef5<T1, T2, T3, T4, R, TOrigElem, TPlugin> p(String propName);

		<R> SelectDef5<T1, T2, T3, T4, R, TOrigElem, TPlugin> val(R constant);
	}

	public interface SelectDef5<T1, T2, T3, T4, T5, TOrigElem, TPlugin>
		extends SelectDefSupport<Tuple5<T1, T2, T3, T4, T5>, TPlugin>
	{
		SelectDef1<TupleN<Tuple5<T1, T2, T3, T4, T5>, TOrigElem>, TOrigElem, TPlugin> self();

		SelectDef1<TupleN<Tuple5<T1, T2, T3, T4, T5>, Integer>, TOrigElem, TPlugin> elemIndex();

		<R> SelectDef1<TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, TOrigElem, TPlugin> with(Function<TOrigElem, R> f);

		<R> SelectDef1<TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, TOrigElem, TPlugin> with(	Function2<TOrigElem, Integer, R> f);

		<R> SelectDef1<TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, TOrigElem, TPlugin> p(String propName, Class<R> cls);

		<R> SelectDef1<TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, TOrigElem, TPlugin> p(TProperty<R> tp);

		<R> SelectDef1<TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, TOrigElem, TPlugin> p(String propName);

		<R> SelectDef1<TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, TOrigElem, TPlugin> val(R constant);
	}

}