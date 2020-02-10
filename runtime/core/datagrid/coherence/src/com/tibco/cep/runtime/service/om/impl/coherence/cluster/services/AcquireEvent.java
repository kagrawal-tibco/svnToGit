/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 27/8/2010
 */

package com.tibco.cep.runtime.service.om.impl.coherence.cluster.services;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.tangosol.io.ExternalizableLite;
import com.tangosol.util.InvocableMap;
import com.tangosol.util.processor.AbstractProcessor;
import com.tibco.cep.runtime.service.cluster.events.EventTuple;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: May 4, 2008
 * Time: 8:09:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class AcquireEvent extends AbstractProcessor implements ExternalizableLite {
    int agentId;

    public AcquireEvent(int agentId) {
        this.agentId = agentId;
    }

    public AcquireEvent() {
    }


    public Object process(InvocableMap.Entry entry) {
        if (entry.getValue() instanceof EventTuple) {
            EventTuple tuple = (EventTuple) entry.getValue();
            if (tuple.acquire(agentId)) {
                entry.setValue(tuple);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void readExternal(DataInput dataInput) throws IOException {
        agentId = dataInput.readInt();
    }

    public void writeExternal(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(agentId);
    }
}

