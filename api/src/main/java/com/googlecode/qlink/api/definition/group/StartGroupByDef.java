package com.googlecode.qlink.api.definition.group;

import java.util.List;

import com.googlecode.qlink.api.definition.group.GroupDefinitions.GroupByDef;
import com.googlecode.qlink.api.functor.Function;
import com.googlecode.qlink.api.functor.Function2;
import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.api.tuple.Pair;

public interface StartGroupByDef<T, TPlugin>
{

	<K> GroupByDef<K, List<T>, TPlugin> by(String propName, Class<K> cls);

	<K> GroupByDef<K, List<T>, TPlugin> by(TProperty<K> tProp);

	<K> GroupByDef<K, List<T>, TPlugin> by(Function<T, K> f);

	<K> GroupByDef<K, List<T>, TPlugin> by(Function2<T, Integer, K> f);

	<K, V> GroupWithDef<K, List<V>, TPlugin> with(Function<T, Pair<K, V>> ind);

	<K, V> GroupWithDef<K, List<V>, TPlugin> with(Function2<T, Integer, Pair<K, V>> ind);

}
