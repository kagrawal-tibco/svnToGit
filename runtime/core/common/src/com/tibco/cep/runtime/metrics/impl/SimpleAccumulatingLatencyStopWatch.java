package com.tibco.cep.runtime.metrics.impl;

import java.util.concurrent.atomic.AtomicLong;

import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Jan 26, 2009 Time: 6:36:41 PM
*/
public class SimpleAccumulatingLatencyStopWatch
        extends SimpleAccumulatingStopWatch<TxnSlab> {
    protected long txnDuration;

    protected int txnCount;

    public SimpleAccumulatingLatencyStopWatch(FQName ownerName, SlabProvider slabProvider,
                                              AtomicLong sharedCounter) {
        super(ownerName, slabProvider, sharedCounter);
    }

    @Override
    protected long captureStartTime() {
        txnCount++;

        return System.currentTimeMillis();
    }

    @Override
    protected long captureEndTime() {
        long endTime = System.currentTimeMillis();

        txnDuration = txnDuration + (endTime - startTimeMillis);

        return endTime;
    }

    @Override
    protected void flushPrivateCounter() {
        TxnSlab s = getCurrentSlab();
        if (s == null) {
            return;
        }

        s.addCountAndDuration(txnCount, txnDuration);
        txnCount = 0;
        txnDuration = 0;

        super.flushPrivateCounter();
    }
}
