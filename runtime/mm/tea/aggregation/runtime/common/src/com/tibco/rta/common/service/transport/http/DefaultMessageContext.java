package com.tibco.rta.common.service.transport.http;

import com.tibco.rta.common.service.FactMessageContext;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 24/1/14
 * Time: 1:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultMessageContext implements FactMessageContext {

    private long createdTime;

    public DefaultMessageContext(long createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public void acknowledge() throws Exception {
        //Do nothing
    }

    @Override
    public boolean isAcked() {
        return true;
    }

    @Override
    public void setBatchSize(int factBatchSize) {

    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public long getCreatedTime() {
        return createdTime;
    }
}
