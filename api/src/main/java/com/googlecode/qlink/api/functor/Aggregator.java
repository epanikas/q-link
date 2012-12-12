package com.googlecode.qlink.api.functor;

import java.util.Collection;

public interface Aggregator<T, O> {

	O aggregate(Collection<T> lst);

}
