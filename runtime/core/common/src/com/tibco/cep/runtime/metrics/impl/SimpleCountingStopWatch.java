package com.tibco.cep.runtime.metrics.impl;

import com.tibco.cep.runtime.metrics.CountingStopWatch;
import com.tibco.cep.runtime.util.FQName;

/*
* Author: Ashwin Jayaprakash Date: Jan 26, 2009 Time: 6:36:41 PM
*/
public class SimpleCountingStopWatch<S extends Slab> extends SimpleStopWatch<S>
        implements CountingStopWatch {
    public SimpleCountingStopWatch(FQName ownerName, SlabProvider slabProvider) {
        super(ownerName, slabProvider);
    }

    @Override
    protected void afterStart() {
        //Do nothing here.
    }

    public void count() {
        privateCounter++;

        if (privateCounter > FLUSH_PRIVATE_COUNTER_AFTER) {
            flushPrivateCounter();
        }
    }

    public void count(int i) {
        privateCounter = privateCounter + i;

        if (privateCounter > FLUSH_PRIVATE_COUNTER_AFTER) {
            flushPrivateCounter();
        }
    }
}
