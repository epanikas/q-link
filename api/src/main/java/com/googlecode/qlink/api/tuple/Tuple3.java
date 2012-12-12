package com.googlecode.qlink.api.tuple;

public interface Tuple3<T1, T2, T3>
	extends Tuple
{
	T1 getFirst();

	T2 getSecond();

	T3 getThird();
}
