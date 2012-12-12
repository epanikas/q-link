package com.googlecode.qlink.hibernate.functor;

import java.util.Collections;
import java.util.List;


import com.googlecode.qlink.api.functor.TProperty;
import com.googlecode.qlink.core.context.enums.EOrderDirection;
import com.googlecode.qlink.core.functor.PropertyComparator;

public class SqlAwarePropertyComparator<R extends Comparable<R>, T>
	extends PropertyComparator<R, T>
	implements SqlClauseSnippet
{

	private final String orderByClause;

	public SqlAwarePropertyComparator(String propName, Class<R> cls, EOrderDirection dir)
	{
		super(propName, cls, dir);
		this.orderByClause = propName + " " + dir;
	}

	public SqlAwarePropertyComparator(TProperty<R> prop, EOrderDirection dir)
	{
		super(prop, dir);
		this.orderByClause = prop.getName() + " " + dir;
	}

	public String getOrderClause()
	{
		return orderByClause;
	}

	@Override
	public String getSqlClause()
	{
		return orderByClause;
	}

	@Override
	public List<Object> getParams()
	{
		return Collections.emptyList();

	}

}
