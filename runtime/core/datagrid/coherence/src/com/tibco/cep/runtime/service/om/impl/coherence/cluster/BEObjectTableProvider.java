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

package com.tibco.cep.runtime.service.om.impl.coherence.cluster;

import com.tibco.be.util.BEProperties;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.system.ObjectTable;
import com.tibco.cep.runtime.service.cluster.system.impl.ObjectTableCache;

/*
* Author: Ashwin Jayaprakash Date: Jan 7, 2009 Time: 3:56:15 PM
*/

public class BEObjectTableProvider {
    /**
     * {@value}.
     */
    public static final String KEY_TABLE_PROVIDER_CLASS_NAME = "be.om.objectTable.className";

    public static ObjectTable create(BEProperties properties, Logger logger,
                                     Cluster cluster) {
        String className = properties
                .getString(KEY_TABLE_PROVIDER_CLASS_NAME, ObjectTableCache.class.getName());

        ObjectTable objectTable = null;
        try {
            Class<? extends ObjectTable> clazz =
                    (Class<? extends ObjectTable>) Class.forName(className);

            objectTable = clazz.newInstance();

            //objectTable.init(cluster);
            logger.log(Level.INFO, "Initialized " + ObjectTable.class.getSimpleName() + " [" +
                    objectTable.getClass().getName() + "]");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        return objectTable;
    }
}