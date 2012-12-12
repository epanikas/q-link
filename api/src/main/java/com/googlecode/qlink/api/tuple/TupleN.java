package com.googlecode.qlink.api.tuple;


public interface TupleN<THead extends Tuple, B>
	extends Tuple
{
	THead getHead();

	B getLast();

	/**
	 * this funciton is supposed to return the flattened array, not the array of two elements
	 */
	@Override
	public Object[] toArray();
}
