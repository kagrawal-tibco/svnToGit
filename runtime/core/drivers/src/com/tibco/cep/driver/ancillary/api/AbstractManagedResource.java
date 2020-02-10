package com.tibco.cep.driver.ancillary.api;

import java.util.concurrent.atomic.AtomicBoolean;

import com.tibco.cep.runtime.service.ManagedResource;

/*
* Author: Ashwin Jayaprakash Date: Mar 19, 2009 Time: 11:05:40 AM
*/
public abstract class AbstractManagedResource implements ManagedResource {
    protected final AtomicBoolean stopFlag;

    protected AbstractManagedResource() {
        this.stopFlag = new AtomicBoolean();
    }

    protected boolean isStopped() {
        return stopFlag.get();
    }

    public void stop() throws Exception {
        stopFlag.set(true);
    }
}
