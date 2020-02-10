/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 26/8/2010
 */

package com.tibco.cep.runtime.service.cluster.events.notification;

import com.tibco.cep.runtime.service.cluster.events.EventTable;
import com.tibco.cep.runtime.service.cluster.events.EventTuple;
import com.tibco.cep.runtime.service.om.api.Filter;


/**
 * Created by IntelliJ IDEA. User: apuneet Date: May 5, 2008 Time: 10:08:04 AM To change this
 * template use File | Settings | File Templates.
 */
public interface ClusterEventQueueListener {
    public final static byte EVENT_ASSERTED = 1;

    public final static byte EVENT_MODIFIED = 2;

    public final static byte EVENT_DELETED = 3;

    public void onEvent(EventTuple tuple, byte opcode, EventTable eventQueue);

    public Filter getEventFilter();

    public String getListenerName();
}
