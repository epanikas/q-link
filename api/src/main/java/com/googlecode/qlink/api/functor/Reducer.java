package com.googlecode.qlink.api.functor;

public interface Reducer<R>
{
	R reduce(R a, R b);
}
