package com.tibco.cep.runtime.metrics.impl;

/*
* Author: Ashwin Jayaprakash Date: Jan 23, 2009 Time: 2:33:35 PM
*/
public interface SlabProvider<S extends Slab> {
    long getSlabResolutionMillis();

    S fetchSlab(long startTimeMillis);

    void resurrectSlab(S slab);
}
