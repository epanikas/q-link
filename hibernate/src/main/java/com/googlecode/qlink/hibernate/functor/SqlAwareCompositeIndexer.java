package com.googlecode.qlink.hibernate.functor;

import java.util.Collections;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.googlecode.qlink.api.functor.Function2;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.functor.CompositeIndexer;

public class SqlAwareCompositeIndexer<K1, K2, V, T>
	extends CompositeIndexer<K1, K2, V, T>
	implements SqlClauseSnippet
{

	public SqlAwareCompositeIndexer(Function2<T, Integer, Pair<K1, V>> ind1, Function2<T, Integer, Pair<K2, V>> ind2)
	{
		super(ind1, ind2);
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append(getInd1()).append(getInd2())
			.toString();
	}

	@Override
	public String getSqlClause()
	{
		return SqlAwareFunctions.getSqlClauseSnippet(getInd1()).getSqlClause() + ", "
			+ SqlAwareFunctions.getSqlClauseSnippet(getInd2()).getSqlClause();
	}

	@Override
	public java.util.List<Object> getParams()
	{
		return Collections.emptyList();
	}
}
