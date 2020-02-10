/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 *  All Rights Reserved.
 *
 *  This software is confidential and proprietary information of
 *  TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.agent;

import com.tibco.be.util.config.cdd.AgentConfig;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/*
* Author: Suresh Subramani / Date: Dec 6, 2010 / Time: 5:34:20 PM
*/

/**
 * Implementors of this class should generate an instance with no state.
 * The builder constructs an instance of this factory every time.
 */

public interface AgentFactory {

    /**
     * Create an instance of CacheAgent using the following parameters.
     * @param rsp
     * @param clusterName
     * @param agentConfig
     * @return
     * @throws Exception
     */
    CacheAgent createAgent(RuleServiceProvider rsp, String clusterName, AgentConfig agentConfig) throws Exception;
}
