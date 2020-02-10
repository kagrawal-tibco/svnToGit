package com.tibco.cep.runtime.metrics.impl;

import com.tibco.cep.runtime.metrics.Data;
import com.tibco.cep.runtime.metrics.DataDef;

/*
* Author: Ashwin Jayaprakash Date: Feb 17, 2009 Time: 11:14:39 AM
*/
public class TxnSlabWorker implements SlabWorker<TxnSlab> {
    public TxnSlab createSlab(long roundedOffSlabStartTime, long slabEndTimeMillis) {
        return new TxnSlab(roundedOffSlabStartTime, slabEndTimeMillis);
    }

    public DataDef getSlabDataDef() {
        return new SimpleDataDef(
                new SimpleDataDef.SimpleColumnDef("startTimeMillis", Long.class),
                new SimpleDataDef.SimpleColumnDef("endTimeMillis", Long.class),
                new SimpleDataDef.SimpleColumnDef("count", Long.class),
                new SimpleDataDef.SimpleColumnDef("avgTxnMillis", Double.class),
                new SimpleDataDef.SimpleColumnDef("newOrUpdate", Boolean.class));
    }

    public Data extractSlabData(TxnSlab slab) {
        boolean newSlab = !(slab.isAlreadyProcessed());
        if (newSlab) {
            slab.markAlreadyProcessed();
        }

        Long start = slab.getSlabStartTimeMillis();
        Long end = slab.getSlabEndTimeMillis();
        Long count = slab.getCount();

        Double duration = 0.0;
        long counterI = slab.getTxnCounter();
        if (counterI > 0) {
            long durationL = slab.getTxnDuration();
            duration = durationL / (double) counterI;
        }

        return new SimpleData(start, end, count, duration, newSlab);
    }
}
