package com.googlecode.qlink.hibernate.functor;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.core.functor.PropertyIndexer;

public class SqlAwarePropertyIndexer<K, T>
	extends PropertyIndexer<K, T>
	implements SqlClauseSnippet
{

	public SqlAwarePropertyIndexer(TProperty<K> prop)
	{
		super(prop);
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append(getProp()).toString();
	}

	@Override
	public String getSqlClause()
	{
		return getProp().getName();
	}

	@Override
	public List<Object> getParams()
	{
		return Collections.emptyList();
	}

}
