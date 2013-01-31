package com.googlecode.qlink.mem.behavior;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.qlink.api.behavior.DoResultAsList;
import com.googlecode.qlink.api.behavior.DoResultAsSingleValue;
import com.googlecode.qlink.api.functor.Aggregator;
import com.googlecode.qlink.api.functor.Folder;
import com.googlecode.qlink.api.functor.Reducer;
import com.googlecode.qlink.api.functor.SamplePredicate;
import com.googlecode.qlink.api.tuple.Pair;
import com.googlecode.qlink.core.context.IPipelineContext;
import com.googlecode.qlink.core.context.PipelineContextAwareSupport;
import com.googlecode.qlink.core.context.blocks.TransformBlock;
import com.googlecode.qlink.core.context.enums.EAggregatorType;
import com.googlecode.qlink.core.context.enums.EFoldSide;
import com.googlecode.qlink.core.context.enums.ETransformResultType;
import com.googlecode.qlink.core.functor.SamplePredicates;
import com.googlecode.qlink.core.utils.AggregationUtils;
import com.googlecode.qlink.core.utils.SimpleAssert;
import com.googlecode.qlink.mem.utils.MemAggregationUtils;

public class MemDoResultAsSingleValue<T, TPlugin>
	extends PipelineContextAwareSupport
	implements DoResultAsSingleValue<T, TPlugin>
{

	public MemDoResultAsSingleValue(IPipelineContext ctxt)
	{
		super(ctxt);
	}

	@SuppressWarnings({"unchecked"})
	@Override
	public T toValue()
	{

		DoResultAsList<?, ?> doResultAsList = getCtxt().getFactory().create(DoResultAsList.class);
		List<?> lst = doResultAsList.toList();

		SamplePredicate samplePredicate = getCtxt().getPipelineDef().getSamplePredicate();
		if (samplePredicate == null) {
			samplePredicate =
				SamplePredicates.createSamplePredicate(getCtxt().getPipelineDef().getSampleType(), getCtxt()
					.getPipelineDef().getSampleParam());
		}

		if (samplePredicate != null) {
			SimpleAssert.isEquals(1, lst.size());
			return (T) lst.get(0);
		}

		Reducer<T> reducer = (Reducer<T>) getCtxt().getPipelineDef().getReducer();
		if (reducer != null) {
			return AggregationUtils.<T> applyReducer((List<T>) lst, reducer);
		}

		Folder<Object, T> folder = (Folder<Object, T>) getCtxt().getPipelineDef().getFolder();
		if (folder != null) {
			T initVal = (T) getCtxt().getPipelineDef().getFoldInit();
			EFoldSide side = getCtxt().getPipelineDef().getFoldSide();
			return AggregationUtils.applyFolder((List<Object>) lst, folder, initVal, side);
		}

		List<Aggregator<?, ?>> aggregators = new ArrayList<Aggregator<?, ?>>();
		for (Pair<EAggregatorType, TransformBlock> p : getCtxt().getPipelineDef().getAggregators()) {

			TransformBlock tb = p.getSecond();
			Aggregator<?, ?> aggregator = tb.getAggregator();
			if (aggregator == null) {
				aggregator = MemAggregationUtils.chooseAggregator(tb.getAggregatorType(), tb.getProperty());
			}

			aggregators.add(aggregator);
		}

		if (aggregators.size() == 0) {
			throw new IllegalArgumentException("neither reduce nor fold were specified for " + getCtxt());
		}

		Object[] aggregatedResult = new Object[aggregators.size()];
		if (lst.size() < 1) {
			throw new IllegalArgumentException("not enough elements to apply aggregate (" + lst.size()
				+ "), for " + getCtxt());
		}

		int i = 0;
		for (Aggregator<?, ?> r : aggregators) {

			@SuppressWarnings("rawtypes")
			Object aggRes = r.aggregate((List) lst);

			aggregatedResult[i++] = aggRes;
		}

		ETransformResultType aggregateResultType = getCtxt().getPipelineDef().getAggregatedResultType();
		Class<?> aggregateResultClass = getCtxt().getPipelineDef().getAggregatedResultClass();

		return (T) AggregationUtils.adaptAggregatedResult(aggregatedResult,
			getCtxt().getPipelineDef().getAggregators(), aggregateResultType, aggregateResultClass);

	}

	@SuppressWarnings("unchecked")
	@Override
	public TPlugin plugin()
	{
		return (TPlugin) getCtxt().getPlugin();
	}

}
