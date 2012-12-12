package com.googlecode.qlink.api.definition.group;

import com.googlecode.qlink.api.behavior.DoResultAsMap;
import com.googlecode.qlink.api.functor.Function;
import com.googlecode.qlink.api.functor.Function2;
import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.api.tuple.Tuple3;
import com.googlecode.qlink.api.tuple.Tuple4;
import com.googlecode.qlink.api.tuple.Tuple5;
import com.googlecode.qlink.api.tuple.TupleN;

public class IndexDefinitions
{

	public interface IndexByDefSupport<K, V, TPlugin>
		extends DoResultAsMap<K, V, TPlugin>
	{
		// empty
	}
	public interface IndexByDef<K, V, TPlugin>
		extends IndexByDefSupport<K, V, TPlugin>
	{

		<R> IndexByDef2<K, R, V, TPlugin> by(String propName, Class<R> cls);

		<R> IndexByDef2<K, R, V, TPlugin> by(TProperty<R> tp);

		<R> IndexByDef2<K, R, V, TPlugin> by(Function<V, R> f);

		<R> IndexByDef2<K, R, V, TPlugin> by(Function2<V, Integer, R> f);

	}
	public interface IndexByDef2<K1, K2, V, TPlugin>
		extends IndexByDefSupport<Pair<K1, K2>, V, TPlugin>
	{

		<R> IndexByDef3<K1, K2, R, V, TPlugin> by(String propName, Class<R> cls);

		<R> IndexByDef3<K1, K2, R, V, TPlugin> by(TProperty<R> tp);

		<R> IndexByDef3<K1, K2, R, V, TPlugin> by(Function<V, R> f);

		<R> IndexByDef3<K1, K2, R, V, TPlugin> by(Function2<V, Integer, R> f);
	}

	public interface IndexByDef3<K1, K2, K3, V, TPlugin>
		extends IndexByDefSupport<Tuple3<K1, K2, K3>, V, TPlugin>
	{

		<R> IndexByDef4<K1, K2, K3, R, V, TPlugin> by(String propName, Class<R> cls);

		<R> IndexByDef4<K1, K2, K3, R, V, TPlugin> by(TProperty<R> tp);

		<R> IndexByDef4<K1, K2, K3, R, V, TPlugin> by(Function<V, R> f);

		<R> IndexByDef4<K1, K2, K3, R, V, TPlugin> by(Function2<V, Integer, R> f);
	}

	public interface IndexByDef4<K1, K2, K3, K4, V, TPlugin>
		extends IndexByDefSupport<Tuple4<K1, K2, K3, K4>, V, TPlugin>
	{

		<R> IndexByDef5<K1, K2, K3, K4, R, V, TPlugin> by(String propName, Class<R> cls);

		<R> IndexByDef5<K1, K2, K3, K4, R, V, TPlugin> by(TProperty<R> tp);

		<R> IndexByDef5<K1, K2, K3, K4, R, V, TPlugin> by(Function<V, R> f);

		<R> IndexByDef5<K1, K2, K3, K4, R, V, TPlugin> by(Function2<V, Integer, R> f);
	}

	public interface IndexByDef5<K1, K2, K3, K4, K5, V, TPlugin>
		extends IndexByDefSupport<Tuple5<K1, K2, K3, K4, K5>, V, TPlugin>
	{

		<R> IndexByDef<TupleN<Tuple5<K1, K2, K3, K4, K5>, R>, V, TPlugin> by(String propName, Class<R> cls);

		<R> IndexByDef<TupleN<Tuple5<K1, K2, K3, K4, K5>, R>, V, TPlugin> by(TProperty<R> tp);

		<R> IndexByDef<TupleN<Tuple5<K1, K2, K3, K4, K5>, R>, V, TPlugin> by(Function<V, R> f);

		<R> IndexByDef<TupleN<Tuple5<K1, K2, K3, K4, K5>, R>, V, TPlugin> by(Function2<V, Integer, R> f);
	}

}
