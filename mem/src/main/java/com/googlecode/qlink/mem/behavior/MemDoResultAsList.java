package com.googlecode.qlink.mem.behavior;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.googlecode.qlink.api.behavior.DoResultAsList;
import com.googlecode.qlink.api.functor.Function2;
import com.googlecode.qlink.api.functor.Predicate;
import com.googlecode.qlink.api.functor.SamplePredicate;
import com.googlecode.qlink.api.functor.Visitor2;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.functor.SamplePredicates;
import com.googlecode.qlink.core.utils.StackPruningUtils;
import com.googlecode.qlink.mem.context.MemPipelineContext;
import com.googlecode.qlink.mem.pruning.MemPruningRules;

public class MemDoResultAsList<T, TPlugin>
	extends MemDoResultSupport
	implements DoResultAsList<T, TPlugin>

{

	public MemDoResultAsList(IPipelineContext ctxt)
	{
		super(ctxt);
	}

	private MemPipelineContext getMemCtxt()
	{
		return (MemPipelineContext) getCtxt();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> toList()
	{
		List<Object> lstRes = new ArrayList<Object>();
		lstRes.addAll(getMemCtxt().getSourceList());

		final Predicate<Object> f =
			(Predicate<Object>) StackPruningUtils.createFilterPredicate(MemPruningRules.filterPruner, getCtxt()
				.getPipelineDef().getFilterStack());

		if (f != null) {

			CollectionUtils.filter(lstRes, new org.apache.commons.collections.Predicate() {

				@Override
				public boolean evaluate(Object obj)
				{
					return f.evaluate(obj);
				}
			});
		}

		final Comparator<Object> c =
			(Comparator<Object>) StackPruningUtils.createComparator(MemPruningRules.orderPruner, getCtxt()
				.getPipelineDef().getOrderStack());
		if (c != null) {
			Collections.sort(lstRes, c);
		}

		SamplePredicate samplePredicate = getCtxt().getPipelineDef().getSamplePredicate();
		if (samplePredicate == null) {
			samplePredicate =
				SamplePredicates.createSamplePredicate(getCtxt().getPipelineDef().getSampleType(), getCtxt()
					.getPipelineDef().getSampleParam());
		}

		if (samplePredicate != null) {
			Iterator<Object> it = lstRes.iterator();
			int n = lstRes.size();
			for (int i = 0; i < n; ++i) {
				it.next();
				if (samplePredicate.evaluate(i, n) == false) {
					it.remove();
				}
			}
		}

		final Visitor2<Object, Integer> visitor =
			(Visitor2<Object, Integer>) StackPruningUtils.createVisitor(MemPruningRules.visitorPruner, getCtxt()
				.getPipelineDef().getVisitStack());
		if (visitor != null) {
			int i = 0;
			for (Object obj : lstRes) {
				visitor.apply(obj, i++);
			}
		}

		final Function2<Object, Integer, T> func = (Function2<Object, Integer, T>) createTransformFunction();

		if (func != null) {
			List<T> res1 = new ArrayList<T>();
			int i = 0;
			for (Object obj : lstRes) {
				res1.add(func.apply(obj, i));
			}

			return res1;
		}

		return (List<T>) lstRes;
	}

	@Override
	public T toUniqueResult()
	{
		List<T> res = toList();
		if (res.size() > 1) {
			throw new IllegalArgumentException("unique result expected, but found " + res.size());
		}

		return res.size() == 0 ? null : res.get(0);
	}

	@Override
	public int size()
	{
		return toList().size();
	}

	@Override
	public boolean isEmpty()
	{
		return size() == 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public TPlugin plugin()
	{
		return (TPlugin) getCtxt().getPlugin();
	}

}
