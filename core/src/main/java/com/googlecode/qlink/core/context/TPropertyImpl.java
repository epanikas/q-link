package com.googlecode.qlink.core.context;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.googlecode.qlink.api.functor.TProperty;

public class TPropertyImpl<R>
	implements TProperty<R>
{
	private final String propName;
	private final Class<R> propClass;

	private TPropertyImpl(String propName, Class<R> propCls)
	{
		this.propName = propName;
		this.propClass = propCls;
	}

	public Class<R> getPropCls()
	{
		return propClass;
	}

	public String getPropName()
	{
		return propName;
	}

	public static TPropertyImpl<Object> forName(String propName)
	{
		return new TPropertyImpl<Object>(propName, Object.class);
	}

	public static <R> TPropertyImpl<R> forTypedName(String propName, Class<R> cls)
	{
		return new TPropertyImpl<R>(propName, cls);
	}

	@Override
	public Class<R> getPropertyCls()
	{
		return propClass;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null || obj instanceof TPropertyImpl == false) {
			return false;
		}

		@SuppressWarnings("unchecked")
		TPropertyImpl<R> that = (TPropertyImpl<R>) obj;

		return new EqualsBuilder().append(propName, that.getPropName()).append(propClass, that.getPropCls()).isEquals();
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder().append(propName).append(propClass).toHashCode();
	}

	@Override
	public String getName()
	{
		return propName;
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append(propName).append(propClass)
			.toString();
	}

}
