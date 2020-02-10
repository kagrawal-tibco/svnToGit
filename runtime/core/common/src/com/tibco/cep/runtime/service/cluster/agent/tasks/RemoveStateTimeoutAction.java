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
 * Date: Aug 6, 2008
 * Time: 2:54:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class RemoveStateTimeoutAction implements AgentAction {
    long sm_id;
    String property_name;
    int count;

    public RemoveStateTimeoutAction(long sm_id, String property_name, int count) {
        this.sm_id = sm_id;
        this.property_name = property_name;
        this.count = count;
    }

    public void run(CacheAgent cacheAgent) throws Exception {
        if (cacheAgent instanceof InferenceAgent) {
            InferenceAgent ia = (InferenceAgent) cacheAgent;
            //ia.removeStateTimeout(sm_id, property_name, count);
            ia.removeScheduledSMTimeout(sm_id, property_name, count);
        }
    }
}
