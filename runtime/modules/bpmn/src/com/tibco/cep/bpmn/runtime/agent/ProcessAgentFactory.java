package com.tibco.cep.bpmn.runtime.agent;

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.AgentClassConfig;
import com.tibco.be.util.config.cdd.AgentConfig;
import com.tibco.be.util.config.cdd.CacheAgentClassConfig;
import com.tibco.be.util.config.cdd.InferenceAgentClassConfig;
import com.tibco.be.util.config.cdd.ProcessAgentClassConfig;
import com.tibco.cep.bpmn.runtime.config.ProcessAgentConfiguration;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.cluster.agent.AgentFactory;
import com.tibco.cep.runtime.service.cluster.agent.DefaultAgentConfiguration;
import com.tibco.cep.runtime.service.cluster.agent.NewInferenceAgent;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.util.GvCommonUtils;

/*
* Author: Suresh Subramani / Date: 11/25/11 / Time: 7:36 PM
* This class is regsitered in AgentBuilder,
* static map.
* May be going forward, we should use an xml file to register the AgentFactory, and its config file.
*
* This is kind of SCA :-)
*/
public class ProcessAgentFactory implements AgentFactory {

    @Override
    public CacheAgent createAgent(RuleServiceProvider rsp, String clusterName, AgentConfig agentConfig) throws Exception {

        AgentClassConfig configClass = agentConfig.getRef();
        GlobalVariables gvs=rsp.getProject().getGlobalVariables();
        final String key = GvCommonUtils.getStringValueIfExists(gvs,CddTools.getValueFromMixed(agentConfig.getKey()));
        if (configClass instanceof InferenceAgentClassConfig) {
            DefaultAgentConfiguration config = new DefaultAgentConfiguration(configClass.getId(), key, rsp);
            return new NewInferenceAgent(config, rsp, CacheAgent.Type.INFERENCE);
        }
        else if (configClass instanceof CacheAgentClassConfig) {
            DefaultAgentConfiguration config = new ProcessAgentConfiguration(agentConfig, configClass.getId(),key, rsp);
            return new ProcessAgentImpl(config, rsp, CacheAgent.Type.CACHESERVER);
        }
        else if (configClass instanceof ProcessAgentClassConfig) {
            ProcessAgentConfiguration config = new ProcessAgentConfiguration(agentConfig, configClass.getId(), key, rsp);
            return new ProcessAgentImpl(config, rsp, CacheAgent.Type.PROCESS);
        }
        throw new RuntimeException("ProcessAgentFactory doesn't support this configClass " + configClass.getClass().getName());

    }
}
