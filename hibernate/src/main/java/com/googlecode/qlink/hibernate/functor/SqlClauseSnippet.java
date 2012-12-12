package com.googlecode.qlink.hibernate.functor;

import java.util.List;

public interface SqlClauseSnippet
{
	String getSqlClause();

	List<Object> getParams();
}
