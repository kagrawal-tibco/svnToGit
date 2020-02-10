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

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Apr 29, 2008
 * Time: 11:17:10 AM
 * To change this template use File | Settings | File Templates.
 */
public interface AgentAction {
    public void run(CacheAgent agent) throws Exception;
}
