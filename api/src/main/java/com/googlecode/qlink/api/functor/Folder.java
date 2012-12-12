package com.googlecode.qlink.api.functor;

public interface Folder<I, O>
{
	O apply(O a, I b);
}
