package com.googlecode.qlink.core.factory;

import java.util.List;
import java.util.Map;

import com.googlecode.qlink.api.behavior.DoResultAsList;
import com.googlecode.qlink.api.behavior.DoResultAsMap;
import com.googlecode.qlink.api.behavior.DoResultAsSingleValue;
import com.googlecode.qlink.core.context.IPipelineContext;

public class DoResults
{

	public static <T, TPlugin> DoResultAsList<T, TPlugin> getDoResultAsList(final IPipelineContext ctxt)
	{
		return new DoResultAsList<T, TPlugin>() {

			@Override
			public List<T> toList()
			{
				throw new IllegalStateException("toList not implemented");
			}

			@Override
			public T toUniqueResult()
			{
				throw new IllegalStateException("toUniqueResult not implemented");
			}

			@Override
			public int size()
			{
				throw new IllegalStateException("size not implemented");
			}

			@Override
			public boolean isEmpty()
			{
				throw new IllegalStateException("isEmpty not implemented");
			}

			@SuppressWarnings("unchecked")
			@Override
			public TPlugin plugin()
			{
				return (TPlugin) ctxt.getPlugin();
			}

		};
	}

	public static <K, V, TPlugin> DoResultAsMap<K, V, TPlugin> getDoResultAsMap(final IPipelineContext ctxt)
	{
		return new DoResultAsMap<K, V, TPlugin>() {

			@Override
			public Map<K, V> toMap()
			{
				throw new IllegalStateException("toMap not implemented");
			}

			@Override
			public int size()
			{
				throw new IllegalStateException("size not implemented");
			}

			@Override
			public boolean isEmpty()
			{
				throw new IllegalStateException("isEmpty not implemented");
			}

			@SuppressWarnings("unchecked")
			@Override
			public TPlugin plugin()
			{
				return (TPlugin) ctxt.getPlugin();
			}

		};
	}

	public static <T, TPlugin> DoResultAsSingleValue<T, TPlugin> getDoResultAsSingleValue(final IPipelineContext ctxt)
	{
		return new DoResultAsSingleValue<T, TPlugin>() {

			@Override
			public T toValue()
			{
				throw new IllegalStateException("toValue not implemented");
			}

			@SuppressWarnings("unchecked")
			@Override
			public TPlugin plugin()
			{
				return (TPlugin) ctxt.getPlugin();
			}

		};
	}
}
