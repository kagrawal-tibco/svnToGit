package com.tibco.cep.runtime.metrics.impl;

import com.tibco.cep.runtime.metrics.Data;
import com.tibco.cep.runtime.metrics.DataDef;

/*
* Author: Ashwin Jayaprakash Date: Feb 16, 2009 Time: 10:29:05 PM
*/
public interface SlabWorker<S extends Slab> {
    /**
     * Creates a slab that produces data as defined by {@link #getSlabDataDef()} when extracted
     * using {@link #extractSlabData(Slab)}.
     *
     * @param roundedOffSlabStartTime
     * @param slabEndTimeMillis
     * @return
     */
    S createSlab(long roundedOffSlabStartTime, long slabEndTimeMillis);

    DataDef getSlabDataDef();

    /**
     * @param slab
     * @return
     * @see #getSlabDataDef()
     */
    Data extractSlabData(S slab);
}
