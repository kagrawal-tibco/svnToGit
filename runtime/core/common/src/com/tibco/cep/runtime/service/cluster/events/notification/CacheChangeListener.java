/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.events.notification;

import com.tibco.cep.runtime.service.cluster.system.MetadataCache;
import com.tibco.cep.runtime.session.RuleSession;

/*
* Author: Ashwin Jayaprakash Date: Nov 11, 2008 Time: 8:14:20 PM
*/

public interface CacheChangeListener {
    /**
     * Specify what this listener is and how to identify it.
     *
     * @return
     */
    String getId();

    //---------------

    /**
     * @param ruleSession
     * @throws Exception
     */
    void init(RuleSession ruleSession, MetadataCache metadataCache) throws Exception;

    /**
     * Called after a successful {@link #init(com.tibco.cep.runtime.session.RuleSession,
     * com.tibco.cep.runtime.service.cluster.system.MetadataCache)}.
     *
     * @return
     */
    Config getConfig();

    void start() throws Exception;

    void onEvent(CacheChangeEvent event);

    void stop() throws Exception;

    //---------------

    public static interface Config {
        /**
         * @return <code>null</code> to listen to everything or a non-empty array to listen to a
         *         specific list.
         */
        int[] getInterestedTypeIds();
    }
}
