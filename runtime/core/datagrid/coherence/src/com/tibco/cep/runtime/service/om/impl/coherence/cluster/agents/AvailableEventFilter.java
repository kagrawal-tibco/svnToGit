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

package com.tibco.cep.runtime.service.om.impl.coherence.cluster.agents;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.tangosol.io.ExternalizableLite;
import com.tangosol.util.Filter;
import com.tibco.cep.runtime.service.cluster.events.EventTuple;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: May 5, 2008
 * Time: 10:45:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class AvailableEventFilter implements Filter, ExternalizableLite {

    public AvailableEventFilter() {
    }

    protected boolean filter(EventTuple event) {
        return event.isAvailable();
    }

    public boolean evaluate(Object o) {
        if (o instanceof EventTuple) {
            return filter(((EventTuple) o));
        }
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void readExternal(DataInput dataInput) throws IOException {
    }

    public void writeExternal(DataOutput dataOutput) throws IOException {
    }
}


