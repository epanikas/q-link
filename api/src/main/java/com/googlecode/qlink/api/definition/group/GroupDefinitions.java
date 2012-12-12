package com.googlecode.qlink.api.definition.group;

import java.util.List;

import com.googlecode.qlink.api.behavior.CanHaving;
import com.googlecode.qlink.api.behavior.CanSelectGroup;
import com.googlecode.qlink.api.behavior.DoResultAsMap;
import com.googlecode.qlink.api.functor.Function;
import com.googlecode.qlink.api.functor.Function2;
import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.api.tuple.Tuple3;
import com.googlecode.qlink.api.tuple.Tuple4;
import com.googlecode.qlink.api.tuple.Tuple5;
import com.googlecode.qlink.api.tuple.TupleN;

public class GroupDefinitions
{

	public interface GroupByDefSuport<K, V extends List<?>, TPlugin>
		extends CanHaving<K, V, TPlugin>, DoResultAsMap<K, V, TPlugin>, CanSelectGroup<K, V, TPlugin>
	{
		// empty
	}

	public interface GroupByDef<K, V extends List<?>, TPlugin>
		extends GroupByDefSuport<K, V, TPlugin>
	{

		<R> GroupByDef2<K, R, V, TPlugin> by(String propName, Class<R> cls);

		<R> GroupByDef2<K, R, V, TPlugin> by(TProperty<R> tp);

		<R> GroupByDef2<K, R, V, TPlugin> by(Function<V, R> f);

		<R> GroupByDef2<K, R, V, TPlugin> by(Function2<V, Integer, R> f);
	}

	public interface GroupByDef2<K1, K2, V extends List<?>, TPlugin>
		extends GroupByDefSuport<Pair<K1, K2>, V, TPlugin>
	{

		<R> GroupByDef3<K1, K2, R, V, TPlugin> by(String propName, Class<R> cls);

		<R> GroupByDef3<K1, K2, R, V, TPlugin> by(TProperty<R> tp);

		<R> GroupByDef3<K1, K2, R, V, TPlugin> by(Function<V, R> f);

		<R> GroupByDef3<K1, K2, R, V, TPlugin> by(Function2<V, Integer, R> f);
	}

	public interface GroupByDef3<K1, K2, K3, V extends List<?>, TPlugin>
		extends GroupByDefSuport<Tuple3<K1, K2, K3>, V, TPlugin>
	{

		<R> GroupByDef4<K1, K2, K3, R, V, TPlugin> by(String propName, Class<R> cls);

		<R> GroupByDef4<K1, K2, K3, R, V, TPlugin> by(TProperty<R> tp);

		<R> GroupByDef4<K1, K2, K3, R, V, TPlugin> by(Function<V, R> f);

		<R> GroupByDef4<K1, K2, K3, R, V, TPlugin> by(Function2<V, Integer, R> f);
	}

	public interface GroupByDef4<K1, K2, K3, K4, V extends List<?>, TPlugin>
		extends GroupByDefSuport<Tuple4<K1, K2, K3, K4>, V, TPlugin>
	{

		<R> GroupByDef5<K1, K2, K3, K4, R, V, TPlugin> by(String propName, Class<R> cls);

		<R> GroupByDef5<K1, K2, K3, K4, R, V, TPlugin> by(TProperty<R> tp);

		<R> GroupByDef5<K1, K2, K3, K4, R, V, TPlugin> by(Function<V, R> f);

		<R> GroupByDef5<K1, K2, K3, K4, R, V, TPlugin> by(Function2<V, Integer, R> f);
	}

	public interface GroupByDef5<K1, K2, K3, K4, K5, V extends List<?>, TPlugin>
		extends GroupByDefSuport<Tuple5<K1, K2, K3, K4, K5>, V, TPlugin>
	{

		<R> GroupByDef<TupleN<Tuple5<K1, K2, K3, K4, K5>, R>, V, TPlugin> by(String propName, Class<R> cls);

		<R> GroupByDef<TupleN<Tuple5<K1, K2, K3, K4, K5>, R>, V, TPlugin> by(TProperty<R> tp);

		<R> GroupByDef<TupleN<Tuple5<K1, K2, K3, K4, K5>, R>, V, TPlugin> by(Function<V, R> f);

		<R> GroupByDef<TupleN<Tuple5<K1, K2, K3, K4, K5>, R>, V, TPlugin> by(Function2<V, Integer, R> f);
	}

}
