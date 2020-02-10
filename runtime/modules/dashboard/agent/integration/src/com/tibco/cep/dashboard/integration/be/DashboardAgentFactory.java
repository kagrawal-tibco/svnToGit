/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 *  All Rights Reserved.
 *
 *  This software is confidential and proprietary information of
 *  TIBCO Software Inc.
 */

package com.tibco.cep.dashboard.integration.be;

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.AgentClassConfig;
import com.tibco.be.util.config.cdd.AgentConfig;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.cluster.agent.AgentFactory;
import com.tibco.cep.runtime.service.cluster.agent.DefaultAgentConfiguration;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/*
* Author: Suresh Subramani / Date: Dec 7, 2010 / Time: 4:49:16 PM
*/
public class DashboardAgentFactory implements AgentFactory {

    public DashboardAgentFactory() {

    }

    @Override
    public CacheAgent createAgent(RuleServiceProvider rsp, String clusterName, AgentConfig agentConfig) throws Exception {
        AgentClassConfig configClass = agentConfig.getRef();
        DefaultAgentConfiguration config = new DefaultAgentConfiguration(configClass.getId(),
        		CddTools.getValueFromMixed(agentConfig.getKey()), rsp);
        return new DashboardAgent(config, rsp, CacheAgent.Type.DASHBOARD);
    }
}
