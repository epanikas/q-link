package com.googlecode.qlink.api.behavior;

import com.googlecode.qlink.api.definition.sample.SampleDef;

public interface CanSample<T, TPlugin> {

	SampleDef<T, TPlugin> sample();

}
