/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 *  All Rights Reserved.
 *
 *  This software is confidential and proprietary information of
 *  TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.agent;

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.AgentClassConfig;
import com.tibco.be.util.config.cdd.AgentConfig;
import com.tibco.be.util.config.cdd.CacheAgentClassConfig;
import com.tibco.be.util.config.cdd.InferenceAgentClassConfig;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.util.GvCommonUtils;

/*
* Author: Suresh Subramani / Date: Dec 6, 2010 / Time: 5:36:43 PM
*/
public class InferenceAgentFactory implements AgentFactory{

    /**
     * constructor - noargs
     */
    AgentFactory delegateFactory = null;

    public InferenceAgentFactory() {

        try {
            Class<AgentFactory> cls = (Class<AgentFactory>) Class.forName("com.tibco.cep.bpmn.runtime.agent.ProcessAgentFactory");
            if (cls != null) {
                delegateFactory = cls.newInstance();
            }
        }
        catch (Throwable t) { }
    }

    @Override
    public CacheAgent createAgent(RuleServiceProvider rsp, String clusterName, AgentConfig agentConfig) throws Exception {

        if (delegateFactory != null) {
            return delegateFactory.createAgent(rsp, clusterName, agentConfig);
        }

        AgentClassConfig configClass = agentConfig.getRef();
        GlobalVariables gvs=rsp.getProject().getGlobalVariables();
        final String key = GvCommonUtils.getStringValueIfExists(gvs,CddTools.getValueFromMixed(agentConfig.getKey()));

        if (configClass instanceof InferenceAgentClassConfig) {
            DefaultAgentConfiguration config = new DefaultAgentConfiguration(configClass.getId(), key, rsp);
            return new NewInferenceAgent(config, rsp, CacheAgent.Type.INFERENCE);
        }
        else if (configClass instanceof CacheAgentClassConfig) {
            DefaultAgentConfiguration config = new DefaultAgentConfiguration(configClass.getId(), key, rsp);
            return new NewInferenceAgent(config, rsp, CacheAgent.Type.CACHESERVER);
        }
        throw new RuntimeException("InferenceAgentFactory doesn't support this configClass " + configClass.getClass().getName());
    }
}
