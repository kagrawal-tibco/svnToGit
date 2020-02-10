package com.tibco.cep.runtime.metrics.impl;

import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Jan 26, 2009 Time: 6:36:41 PM
*/
public class SimpleCountingLatencyStopWatch extends SimpleCountingStopWatch<TxnSlab> {
    protected long txnDuration;

    //protected int txnCount;

    public SimpleCountingLatencyStopWatch(FQName ownerName, SlabProvider slabProvider) {
        super(ownerName, slabProvider);
    }

    @Override
    protected long captureStartTime() {
        //txnCount++;

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

        s.addCountAndDuration(privateCounter, txnDuration);
        privateCounter=0;
        txnDuration = 0;

        super.flushPrivateCounter();
    }
}
