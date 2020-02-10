package com.tibco.cep.runtime.metrics.impl;

import java.util.concurrent.atomic.AtomicLong;

/*
* Author: Ashwin Jayaprakash Date: Feb 16, 2009 Time: 10:20:47 PM
*/
public class TxnSlab extends SimpleSlab {
    //protected AtomicInteger txnCounter;

    protected AtomicLong txnDuration;

    /**
     * @param slabStartTimeMillis Inclusive.
     * @param slabEndTimeMillis   Inclusive.
     */
    public TxnSlab(long slabStartTimeMillis, long slabEndTimeMillis) {
        super(slabStartTimeMillis, slabEndTimeMillis);

        //this.txnCounter = new AtomicInteger();
        this.txnDuration = new AtomicLong();
    }

    public long getTxnCounter() {
        //return txnCounter.get();
        return counter.get();
    }

    public long getTxnDuration() {
        return txnDuration.get();
    }

    public void addCountAndDuration(long counter, long duration) {
        /*
        This is not exactly the way to do it. Count and duration must go as a single atomic
        operation. That would entail locking.
        */
        //txnCounter.addAndGet(counter);
        this.counter.addAndGet(counter);
        txnDuration.addAndGet(duration);
    }
}
