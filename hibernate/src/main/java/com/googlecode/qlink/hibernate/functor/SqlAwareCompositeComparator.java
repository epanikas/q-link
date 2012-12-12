package com.googlecode.qlink.hibernate.functor;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.googlecode.qlink.core.functor.CompositeComparator;

public class SqlAwareCompositeComparator<T>
	extends CompositeComparator<T>
	implements SqlClauseSnippet
{

	private final String sqlClause;

	public SqlAwareCompositeComparator(Comparator<T>... comparators)
	{
		super(comparators);
		String tmpSqlClause = "";
		boolean first = true;
		for (Comparator<?> c : getComparators()) {
			tmpSqlClause += (first ? "" : ", ") + ((SqlClauseSnippet) c).getSqlClause();
			first = false;
		}
		sqlClause = tmpSqlClause;
	}

	@Override
	public String getSqlClause()
	{
		return sqlClause;
	}

	@Override
	public List<Object> getParams()
	{
		return Collections.emptyList();
	}

}
