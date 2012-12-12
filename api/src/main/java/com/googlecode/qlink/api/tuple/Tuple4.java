package com.googlecode.qlink.api.tuple;

public interface Tuple4<T1, T2, T3, T4>
	extends Tuple
{

	T1 getFirst();

	T2 getSecond();

	T3 getThird();

	T4 getFourth();
}
