package com.tibco.cep.runtime.model.event.impl;

import com.tibco.cep.runtime.model.ReferenceCountAble;
import com.tibco.cep.runtime.model.event.AckInterceptor;
import com.tibco.cep.runtime.model.event.EventContext;

/**
 * Created by IntelliJ IDEA. User: ssubrama Date: Nov 15, 2006 Time: 6:18:51 PM To change this template use File |
 * Settings | File Templates.
 */
public abstract class AbstractEventContext implements EventContext, ReferenceCountAble {
    short refCount = 1;

    protected AckInterceptor optionalAckInterceptor;
    protected boolean modified = false;

    synchronized public int incrementCount() {
        return ++refCount;
    }

    synchronized public int decrementCount() {
        return --refCount;
    }

    synchronized public int getCount() {
        return refCount;
    }

    public boolean canReply() {
        return false;
    }

    public void setOptionalAckInterceptor(AckInterceptor ackInterceptor) {
        this.optionalAckInterceptor = ackInterceptor;
    }

    public AckInterceptor getOptionalAckInterceptor() {
        return optionalAckInterceptor;
    }

    /**
     * If the {@link #setOptionalAckInterceptor(AckInterceptor)} is set, the {@link AckInterceptor#acknowledge()} is
     * called.
     */
    @Override
    public void acknowledge() {
        if (optionalAckInterceptor != null) {
            optionalAckInterceptor.acknowledge();
        }
    }


    @Override
    public void rollBack()
    {
        if (optionalAckInterceptor != null) {
            optionalAckInterceptor.rollBack();
        }
    }


    public void modified() {
    	modified = true;
    }

    public boolean isEventModified() {
    	return modified;
    }
}
