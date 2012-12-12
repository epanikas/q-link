package com.googlecode.qlink.core.functor;

import com.googlecode.qlink.api.functor.SamplePredicate;
import com.googlecode.qlink.core.context.enums.ESampleType;

public class SamplePredicates
{

	public static SamplePredicate even()
	{
		return new SamplePredicate() {
			@Override
			public boolean evaluate(int i, int size)
			{
				return i % 2 == 0;
			}

			@Override
			public String toString()
			{
				return "sample: even";
			}
		};
	}

	public static SamplePredicate odd()
	{
		return new SamplePredicate() {
			@Override
			public boolean evaluate(int i, int size)
			{
				return i % 2 != 0;
			}

			@Override
			public String toString()
			{
				return "sample: odd";
			}
		};
	}

	public static SamplePredicate first()
	{
		return new SamplePredicate() {
			@Override
			public boolean evaluate(int i, int size)
			{
				return i == 0;
			}

			@Override
			public String toString()
			{
				return "sample: first";
			}
		};
	}

	public static SamplePredicate last()
	{
		return new SamplePredicate() {

			@Override
			public boolean evaluate(int i, int size)
			{
				return i == size - 1;
			}

			@Override
			public String toString()
			{
				return "sample: last";
			}
		};
	}

	public static SamplePredicate middle()
	{
		return new SamplePredicate() {

			@Override
			public boolean evaluate(int i, int size)
			{
				return i == size / 2;
			}

			@Override
			public String toString()
			{
				return "sample: last";
			}
		};
	}

	public static SamplePredicate nth(final int nth)
	{
		return new SamplePredicate() {

			@Override
			public boolean evaluate(int i, int size)
			{
				return i == nth;
			}

			@Override
			public String toString()
			{
				return "sample: nth " + nth;
			}
		};
	}

	public static SamplePredicate head(final int length)
	{
		return new SamplePredicate() {

			@Override
			public boolean evaluate(int i, int size)
			{
				return i < length;
			}

			@Override
			public String toString()
			{
				return "sample: head " + length;
			}
		};
	}

	public static SamplePredicate tail(final int length)
	{
		return new SamplePredicate() {

			@Override
			public boolean evaluate(int i, int size)
			{
				return i >= size - length;
			}

			@Override
			public String toString()
			{
				return "sample: tail " + length;
			}
		};
	}

	public static SamplePredicate createSamplePredicate(ESampleType type, int sampleParam)
	{
		if (type == null) {
			return null;
		}

		switch (type) {
			case even:
				return even();

			case odd:
				return odd();

			case first:
				return first();

			case last:
				return last();

			case middle:
				return middle();

			case nth:
				return nth(sampleParam);

			case head:
				return head(sampleParam);

			case tail:
				return tail(sampleParam);

			default:
				throw new IllegalArgumentException("unknown sample type " + type);
		}
	}
}
