package com.tibco.cep.runtime.metrics.impl;

import com.tibco.cep.runtime.metrics.Data;
import com.tibco.cep.runtime.metrics.DataDef;

/*
* Author: Ashwin Jayaprakash Date: Feb 16, 2009 Time: 10:47:28 PM
*/
public class DefaultSlabWorker implements SlabWorker<SimpleSlab> {
    public SimpleSlab createSlab(long roundedOffSlabStartTime, long slabEndTimeMillis) {
        return new SimpleSlab(roundedOffSlabStartTime, slabEndTimeMillis);
    }

    public DataDef getSlabDataDef() {
        return new SimpleDataDef(
                new SimpleDataDef.SimpleColumnDef("startTimeMillis", Long.class),
                new SimpleDataDef.SimpleColumnDef("endTimeMillis", Long.class),
                new SimpleDataDef.SimpleColumnDef("count", Long.class),
                new SimpleDataDef.SimpleColumnDef("newOrUpdate", Boolean.class));
    }

    public Data extractSlabData(SimpleSlab slab) {
        boolean newSlab = !(slab.isAlreadyProcessed());
        if (newSlab) {
            slab.markAlreadyProcessed();
        }

        Long start = slab.getSlabStartTimeMillis();
        Long end = slab.getSlabEndTimeMillis();
        Long count = slab.getCount();

        return new SimpleData(start, end, count, newSlab);
    }
}
