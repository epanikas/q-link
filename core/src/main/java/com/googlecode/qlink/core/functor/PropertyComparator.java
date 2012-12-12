package com.googlecode.qlink.core.functor;

import java.util.Comparator;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.core.context.TPropertyImpl;
import com.googlecode.qlink.core.context.enums.EOrderDirection;
import com.googlecode.qlink.core.utils.TypedBeanUtils;

public class PropertyComparator<R extends Comparable<R>, T>
	implements Comparator<T>
{
	private final TProperty<R> prop;
	private final EOrderDirection dir;

	public PropertyComparator(String propName, Class<R> cls, EOrderDirection dir)
	{
		prop = TPropertyImpl.forTypedName(propName, cls);
		this.dir = dir;
	}

	public PropertyComparator(TProperty<R> prop, EOrderDirection dir)
	{
		this.prop = prop;
		this.dir = dir;
	}

	@Override
	public int compare(T o1, T o2)
	{
		R p1 = TypedBeanUtils.getPropertyAs(o1, prop);
		R p2 = TypedBeanUtils.getPropertyAs(o2, prop);

		return dir == EOrderDirection.asc ? p1.compareTo(p2) : p2.compareTo(p1);
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("prop", prop).append("dir", dir)
			.toString();
	}

}
