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
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.events.EventTuple;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: May 5, 2008
 * Time: 2:12:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReleaseEvent extends AbstractProcessor {
    int agentId;
    Logger m_logger;


    public ReleaseEvent(int agentId) {
        this.agentId = agentId;
    }

    public ReleaseEvent() {
    }

    private Logger getLogger() {
        try {
            if (m_logger == null) {
                m_logger = CacheClusterProvider.getInstance().getCacheCluster().getRuleServiceProvider().getLogger(ReleaseEvent.class);
            }
            return m_logger;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }


    public Object process(InvocableMap.Entry entry) {

        getLogger().log(Level.DEBUG, "process called " + entry.getValue());
        if (entry.getValue() instanceof EventTuple) {
            EventTuple tuple = (EventTuple) entry.getValue();
            if (tuple.isOwned(agentId)) {
                tuple.releaseAgentId();
                entry.setValue(tuple);
                return true;
            }
            return false;
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

