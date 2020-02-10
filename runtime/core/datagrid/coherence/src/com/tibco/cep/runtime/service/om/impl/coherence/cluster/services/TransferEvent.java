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

import com.tangosol.util.InvocableMap;
import com.tangosol.util.processor.AbstractProcessor;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: May 5, 2008
 * Time: 8:40:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class TransferEvent extends AbstractProcessor {
    int agentId;
    int transferTo;

    public TransferEvent(int agentId, int transferTo) {
        this.agentId = agentId;
        this.transferTo = transferTo;
    }

    public TransferEvent() {
    }


    public Object process(InvocableMap.Entry entry) {
//        if (entry.getValue() instanceof EventTuple) {
//            if (((EventTuple) entry.getValue()).getAgentId() == agentId) {
//                ((EventTuple) entry.getValue()).setAgentId(transferTo);
//                return true;
//            } else
//                return false;
//        } else {
//            return false;
//        }
        return null;
    }

    public void readExternal(DataInput dataInput) throws IOException {
        agentId = dataInput.readInt();
    }

    public void writeExternal(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(agentId);
    }
}


