package com.googlecode.qlink.hibernate.behavior;

import java.util.ArrayList;
import java.util.List;


import com.googlecode.qlink.api.behavior.DoResultAsList;
import com.googlecode.qlink.api.functor.Function2;
import com.googlecode.qlink.api.tuple.TupleN;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.enums.ETransformResultType;
import com.googlecode.qlink.hibernate.utils.SqlAwareFunctionUtils;
import com.googlecode.qlink.tuples.Tuples;


public class HibernateDoResultAsList<T, TPlugin>
	extends HibernateDoResultSupport
	implements DoResultAsList<T, TPlugin>
{

	public HibernateDoResultAsList(IPipelineContext ctxt)
	{
		super(ctxt);
	}

	@SuppressWarnings({"unchecked","rawtypes"})
	private List<T> internalToList()
	{
		List<?> res = (List<?>) makeRowResultInSql(/*countOnly*/false);

		/*
		 * only in case of tuple further processing is needed
		 */
		Function2<?, Integer, ?>[] functions =
			SqlAwareFunctionUtils.getTransformFunctions(getHibernateCtxt().getPipelineDef().getTransformStack());

		if (getCtxt().getPipelineDef().getTransformResultType() == ETransformResultType.tuple && functions.length > 5) {
			/*
			 * only in this case the result transformation is needed
			 * for fewer number of elements it's done with 'select new' hibernate construction 
			 */
			List<TupleN> tupleRes = new ArrayList<TupleN>();
			for (Object arr : res) {
				Object[] objArr = (Object[]) arr;
				tupleRes.add((TupleN) Tuples.createTupleFor(objArr));
			}

			return (List<T>) tupleRes;
		}

		return (List<T>) res;
	}

	@Override
	public List<T> toList()
	{
		return internalToList();
	}

	@Override
	public T toUniqueResult()
	{
		List<T> res = internalToList();
		if (res.size() > 1) {
			throw new IllegalArgumentException("expected 1 result, but got " + res.size());
		}

		return res.size() == 0 ? null : res.get(0);
	}

	@Override
	public int size()
	{
		return ((Long) makeRowResultInSql(true)).intValue();
	}

	@Override
	public boolean isEmpty()
	{
		return Long.valueOf(0).equals(makeRowResultInSql(true));
	}

	@SuppressWarnings("unchecked")
	@Override
	public TPlugin plugin()
	{
		return (TPlugin) getCtxt().getPlugin();
	}

}
