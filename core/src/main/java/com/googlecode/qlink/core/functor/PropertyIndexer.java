package com.googlecode.qlink.core.functor;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.googlecode.qlink.api.functor.Function2;
import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.utils.TypedBeanUtils;
import com.googlecode.qlink.tuples.Tuples;

public class PropertyIndexer<K, T>
	implements Function2<T, Integer, Pair<K, T>>
{
	private final TProperty<K> prop;

	public PropertyIndexer(TProperty<K> prop)
	{
		this.prop = prop;
	}

	public TProperty<K> getProp()
	{
		return prop;
	}

	@Override
	public Pair<K, T> apply(T t, Integer i)
	{
		K propVal = TypedBeanUtils.getPropertyAs(t, prop);

		return Tuples.tie(propVal, t);
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append(prop).toString();
	}
}
