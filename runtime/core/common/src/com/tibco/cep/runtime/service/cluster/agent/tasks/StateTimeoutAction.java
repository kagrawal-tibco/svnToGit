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

package com.tibco.cep.runtime.service.cluster.agent.tasks;

import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.cluster.agent.InferenceAgent;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: May 8, 2008
 * Time: 12:39:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class StateTimeoutAction implements AgentAction {
    long sm_id;
    String property_name;
    int count;
    long scheduledTime;

    public StateTimeoutAction(long sm_id, String property_name, int count, long scheduledTime) {
        this.sm_id = sm_id;
        this.property_name = property_name;
        this.count = count;
        this.scheduledTime = scheduledTime;
    }

    public void run(CacheAgent cacheAgent) throws Exception{
        actionImpl(cacheAgent, sm_id, property_name, count, scheduledTime);
    }
    
    public static void actionImpl(CacheAgent cacheAgent, long sm_id, String property_name, int count, long scheduledTime) throws Exception {
        if (cacheAgent instanceof InferenceAgent) {
            InferenceAgent ia= (InferenceAgent) cacheAgent;
            //ia.scheduleStateTimeout(stateTimeoutEvent, true);
            ia.scheduleSMTimeout(sm_id, property_name, count, scheduledTime);
        }
    }
}
