package com.tibco.cep.impl.service;

import com.tibco.cep.common.exception.RecoveryException;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.impl.common.resource.DefaultId;
import com.tibco.cep.service.Clock;

/*
* Author: Ashwin Jayaprakash Date: Jun 30, 2009 Time: 11:09:22 AM
*/
public class DefaultClock implements Clock {
    protected DefaultId resourceId;

    public DefaultClock() {
        String s = getClass().getName();

        this.resourceId = new DefaultId(s, 1);
    }

    public Id getResourceId() {
        return resourceId;
    }

    public void start() {
    }

    public void stop() {
    }

    public DefaultClock recover(ResourceProvider resourceProvider, Object... params)
            throws RecoveryException {
        return this;
    }

    //--------------

    public final long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }
}
