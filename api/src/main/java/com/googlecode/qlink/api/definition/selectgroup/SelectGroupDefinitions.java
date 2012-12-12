package com.googlecode.qlink.api.definition.selectgroup;

import java.util.List;

import com.googlecode.qlink.api.behavior.DoResultAsMap;
import com.googlecode.qlink.api.functor.Function;
import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.api.tuple.Tuple3;
import com.googlecode.qlink.api.tuple.Tuple4;
import com.googlecode.qlink.api.tuple.Tuple5;
import com.googlecode.qlink.api.tuple.TupleN;

public class SelectGroupDefinitions
{

	public interface SelectGroupDefSupport<K, V, TPlugin>
		extends DoResultAsMap<K, V, TPlugin>
	{

		<TRes> DoResultAsMap<K, TRes, TPlugin> asNew(Class<TRes> cls);

		DoResultAsMap<K, Object[], TPlugin> asArray();
	}

	public interface StartSelectGroupDef<K, V extends List<?>, TPlugin>
	{

		SelectGroupDef1<K, Long, V, TPlugin> count();

		<R> SelectGroupDef1<K, R, V, TPlugin> sumOf(String propName, Class<R> cls);

		<R> SelectGroupDef1<K, R, V, TPlugin> sumOf(TProperty<R> tp);

		<R> SelectGroupDef1<K, R, V, TPlugin> minOf(String propName, Class<R> cls);

		<R> SelectGroupDef1<K, R, V, TPlugin> minOf(TProperty<R> tp);

		<R> SelectGroupDef1<K, R, V, TPlugin> maxOf(String propName, Class<R> cls);

		<R> SelectGroupDef1<K, R, V, TPlugin> maxOf(TProperty<R> tp);

		<R> SelectGroupDef1<K, R, V, TPlugin> keyField(String propName, Class<R> propCls);

		<R> SelectGroupDef1<K, R, V, TPlugin> withTransformer(Function<Pair<K, V>, R> f);

		SelectGroupDef1<K, K, V, TPlugin> key();

		SelectGroupDef1<K, V, V, TPlugin> value();

	}

	public interface SelectGroupDef1<K, T, V extends List<?>, TPlugin>
		extends SelectGroupDefSupport<K, T, TPlugin>
	{

		SelectGroupDef2<K, T, Long, V, TPlugin> count();

		<R> SelectGroupDef2<K, T, R, V, TPlugin> sumOf(String propName, Class<R> cls);

		<R> SelectGroupDef2<K, T, R, V, TPlugin> sumOf(TProperty<R> tp);

		<R> SelectGroupDef2<K, T, R, V, TPlugin> minOf(String propName, Class<R> cls);

		<R> SelectGroupDef2<K, T, R, V, TPlugin> minOf(TProperty<R> tp);

		<R> SelectGroupDef2<K, T, R, V, TPlugin> maxOf(String propName, Class<R> cls);

		<R> SelectGroupDef2<K, T, R, V, TPlugin> maxOf(TProperty<R> tp);

		<R> SelectGroupDef2<K, T, R, V, TPlugin> keyField(String propName, Class<R> propCls);

		<R> SelectGroupDef2<K, T, R, V, TPlugin> withTransformer(Function<Pair<K, V>, R> f);

		SelectGroupDef2<K, T, K, V, TPlugin> key();

		SelectGroupDef2<K, T, V, V, TPlugin> value();

	}

	public interface SelectGroupDef2<K, T1, T2, V extends List<?>, TPlugin>
		extends SelectGroupDefSupport<K, Pair<T1, T2>, TPlugin>
	{

		SelectGroupDef3<K, T1, T2, Long, V, TPlugin> count();

		<R> SelectGroupDef3<K, T1, T2, R, V, TPlugin> sumOf(String propName, Class<R> cls);

		<R> SelectGroupDef3<K, T1, T2, R, V, TPlugin> sumOf(TProperty<R> tp);

		<R> SelectGroupDef3<K, T1, T2, R, V, TPlugin> minOf(String propName, Class<R> cls);

		<R> SelectGroupDef3<K, T1, T2, R, V, TPlugin> minOf(TProperty<R> tp);

		<R> SelectGroupDef3<K, T1, T2, R, V, TPlugin> maxOf(String propName, Class<R> cls);

		<R> SelectGroupDef3<K, T1, T2, R, V, TPlugin> maxOf(TProperty<R> tp);

		<R> SelectGroupDef3<K, T1, T2, R, V, TPlugin> keyField(String propName, Class<R> propCls);

		<R> SelectGroupDef3<K, T1, T2, R, V, TPlugin> withTransformer(Function<Pair<K, V>, R> f);

		SelectGroupDef3<K, T1, T2, K, V, TPlugin> key();

		SelectGroupDef3<K, T1, T2, V, V, TPlugin> value();

	}

	public interface SelectGroupDef3<K, T1, T2, T3, V extends List<?>, TPlugin>
		extends SelectGroupDefSupport<K, Tuple3<T1, T2, T3>, TPlugin>
	{

		SelectGroupDef4<K, T1, T2, T3, Long, V, TPlugin> count();

		<R> SelectGroupDef4<K, T1, T2, T3, R, V, TPlugin> sumOf(String propName, Class<R> cls);

		<R> SelectGroupDef4<K, T1, T2, T3, R, V, TPlugin> sumOf(TProperty<R> tp);

		<R> SelectGroupDef4<K, T1, T2, T3, R, V, TPlugin> minOf(String propName, Class<R> cls);

		<R> SelectGroupDef4<K, T1, T2, T3, R, V, TPlugin> minOf(TProperty<R> tp);

		<R> SelectGroupDef4<K, T1, T2, T3, R, V, TPlugin> maxOf(String propName, Class<R> cls);

		<R> SelectGroupDef4<K, T1, T2, T3, R, V, TPlugin> maxOf(TProperty<R> tp);

		<R> SelectGroupDef4<K, T1, T2, T3, R, V, TPlugin> keyField(String propName, Class<R> propCls);

		<R> SelectGroupDef4<K, T1, T2, T3, R, V, TPlugin> withTransformer(Function<Pair<K, V>, R> f);

		SelectGroupDef4<K, T1, T2, T3, K, V, TPlugin> key();

		SelectGroupDef4<K, T1, T2, T3, V, V, TPlugin> value();

	}

	public interface SelectGroupDef4<K, T1, T2, T3, T4, V extends List<?>, TPlugin>
		extends SelectGroupDefSupport<K, Tuple4<T1, T2, T3, T4>, TPlugin>
	{

		SelectGroupDef5<K, T1, T2, T3, T4, Long, V, TPlugin> count();

		<R> SelectGroupDef5<K, T1, T2, T3, T4, R, V, TPlugin> sumOf(String propName, Class<R> cls);

		<R> SelectGroupDef5<K, T1, T2, T3, T4, R, V, TPlugin> sumOf(TProperty<R> tp);

		<R> SelectGroupDef5<K, T1, T2, T3, T4, R, V, TPlugin> minOf(String propName, Class<R> cls);

		<R> SelectGroupDef5<K, T1, T2, T3, T4, R, V, TPlugin> minOf(TProperty<R> tp);

		<R> SelectGroupDef5<K, T1, T2, T3, T4, R, V, TPlugin> maxOf(String propName, Class<R> cls);

		<R> SelectGroupDef5<K, T1, T2, T3, T4, R, V, TPlugin> maxOf(TProperty<R> tp);

		<R> SelectGroupDef5<K, T1, T2, T3, T4, R, V, TPlugin> keyField(String propName, Class<R> propCls);

		<R> SelectGroupDef5<K, T1, T2, T3, T4, R, V, TPlugin> withTransformer(Function<Pair<K, V>, R> f);

		SelectGroupDef5<K, T1, T2, T3, T4, K, V, TPlugin> key();

		SelectGroupDef5<K, T1, T2, T3, T4, V, V, TPlugin> value();

	}

	public interface SelectGroupDef5<K, T1, T2, T3, T4, T5, V extends List<?>, TPlugin>
		extends SelectGroupDefSupport<K, Tuple5<T1, T2, T3, T4, T5>, TPlugin>
	{

		SelectGroupDef1<K, TupleN<Tuple5<T1, T2, T3, T4, T5>, Long>, V, TPlugin> count();

		<R> SelectGroupDef1<K, TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, V, TPlugin> sumOf(String propName, Class<R> cls);

		<R> SelectGroupDef1<K, TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, V, TPlugin> sumOf(TProperty<R> tp);

		<R> SelectGroupDef1<K, TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, V, TPlugin> minOf(String propName, Class<R> cls);

		<R> SelectGroupDef1<K, TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, V, TPlugin> minOf(TProperty<R> tp);

		<R> SelectGroupDef1<K, TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, V, TPlugin> maxOf(String propName, Class<R> cls);

		<R> SelectGroupDef1<K, TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, V, TPlugin> maxOf(TProperty<R> tp);

		<R> SelectGroupDef1<K, TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, V, TPlugin> keyField(String propName,
																							Class<R> propCls);

		<R> SelectGroupDef1<K, TupleN<Tuple5<T1, T2, T3, T4, T5>, R>, V, TPlugin> withTransformer(	Function<Pair<K, V>, R> f);

		SelectGroupDef1<K, TupleN<Tuple5<T1, T2, T3, T4, T5>, K>, V, TPlugin> key();

		SelectGroupDef1<K, TupleN<Tuple5<T1, T2, T3, T4, T5>, V>, V, TPlugin> value();

	}

}
