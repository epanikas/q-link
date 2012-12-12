package com.googlecode.qlink.api.tuple;


public interface Pair<A, B>
	extends Tuple
{
	A getFirst();

	B getSecond();
}
