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

package com.tibco.cep.runtime.service.om.impl.coherence.cluster;

import java.lang.reflect.Constructor;

import com.tibco.be.util.BEProperties;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.events.EventTable;
import com.tibco.cep.runtime.service.cluster.events.EventTableCache;


/*
* Author: Ashwin Jayaprakash Date: Jan 7, 2009 Time: 3:56:15 PM
*/

public class BEEventTableProvider {
    /**
     * {@value}.
     */
    public static final String KEY_TABLE_PROVIDER_CLASS_NAME = "be.om.eventTable.className";

    public static EventTable create(BEProperties properties, Logger logger,
                                    Cluster cluster, Class eventClass) {
        String className = properties
                .getString(KEY_TABLE_PROVIDER_CLASS_NAME, EventTableCache.class.getName());

        EventTable eventTable = null;
        try {
            Class<? extends EventTable> clazz =
                    (Class<? extends EventTable>) Class.forName(className);

            Constructor ctor = clazz.getConstructor(new Class[] {Class.class});

            eventTable = (EventTable) ctor.newInstance(new Object[] {eventClass});
            eventTable.init(cluster);
            logger.log(Level.INFO, "Initialized " + EventTable.class.getSimpleName() + " [" +
                    eventTable.getClass().getName() + "]");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        return eventTable;
    }
}