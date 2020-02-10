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

package com.tibco.cep.runtime.service.cluster.scheduler;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.om.api.ControlDao;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: May 2, 2008
 * Time: 11:18:37 AM
 * To change this template use File | Settings | File Templates.
 */
//Not used in 3.0.2 or 4.0
@Deprecated
public class ExpireEntities implements Runnable {

    ControlDao timeCache;
    java.util.List agentListeners = Collections.synchronizedList(new ArrayList());


    protected ExpireEntities(ControlDao timeCache) {
        this.timeCache = timeCache;
    }

    protected ExpireEntities() {
    }

    protected void setTimeCache(ControlDao timeCache) {
        this.timeCache = timeCache;
    }

    protected void addTimeEventListener(CacheAgent agent) {
        if (!agentListeners.contains(agent)) {
            agentListeners.add(agent);
        }
    }

    protected void removeTimeEventListener(com.tibco.cep.runtime.service.cluster.CacheAgent agent) {
        agentListeners.remove(agent);
    }

    protected boolean dispatch(Object key) {
        for (int i = 0; i < agentListeners.size(); i++) {
            CacheAgent agent = (CacheAgent) agentListeners.get(i);
            if ((agent.getAgentState() == CacheAgent.AgentState.ACTIVATED) && !agent.isSuspended()) {
                //agent.onTimeEvent(key);
                return true;
            }
        }
        return false;
    }

    protected boolean isReady() {
        for (int i = 0; i < agentListeners.size(); i++) {
            com.tibco.cep.runtime.service.cluster.CacheAgent agent = (com.tibco.cep.runtime.service.cluster.CacheAgent) agentListeners.get(i);
            if ((agent.getAgentState() == CacheAgent.AgentState.ACTIVATED) && !agent.isSuspended()) {
                return true;
            }
        }
        return false;
    }

    public void run() {
        java.util.List expiredKeys = new ArrayList();
        try {

            //Thread.sleep(100);
            if (!isReady()) {
                return;
            }
            long now = System.currentTimeMillis();
//            java.util.Iterator expiryItems=timeCache.keySet(new LessEqualsFilter(IdentityExtractor.INSTANCE, new Long(now))).iterator();
            //Set keyset = timeCache.keySet(new LessEqualsFilter(TimeoutExtractor.INSTANCE, new Long(now)));
            //Suresh : commented above @Sept 4th 2010 : The class is deprecated and just needed to compile.
            Set keyset = timeCache.keySet();
            if (keyset == null) {
                return;
            }
            java.util.Iterator expiryItems = keyset.iterator();
            while (expiryItems.hasNext()) {
                Object key = expiryItems.next();
                try {
                    timeCache.lock(key, -1);
                    //Object val=timeCache.get(key);
                    //if (val != null) {
                    if (dispatch(key)) {
                        timeCache.remove(key);
                    } else {
                        return;
                    }
                    //}
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    timeCache.unlock(key);
                }
            }
            //timeCache.invokeAll(expiredKeys, new ConditionalRemove(AlwaysFilter.INSTANCE));
        } finally {
            expiredKeys.clear();
        }
    }
}


