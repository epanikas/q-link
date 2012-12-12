package com.googlecode.qlink.api.definition.group;

import com.googlecode.qlink.api.definition.group.IndexDefinitions.IndexByDef;
import com.googlecode.qlink.api.functor.Function;
import com.googlecode.qlink.api.functor.Function2;
import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.api.tuple.Pair;

public interface StartIndexByDef<T, TPlugin>
{

	<K> IndexByDef<K, T, TPlugin> by(String propName, Class<K> cls);

	<K> IndexByDef<K, T, TPlugin> by(TProperty<K> tProp);

	<K> IndexByDef<K, T, TPlugin> by(Function<T, K> f);

	<K> IndexByDef<K, T, TPlugin> by(Function2<T, Integer, K> f);

	<K, V> IndexWithDef<K, V, TPlugin> with(Function<T, Pair<K, V>> ind);

	<K, V> IndexWithDef<K, V, TPlugin> with(Function2<T, Integer, Pair<K, V>> ind);

}
