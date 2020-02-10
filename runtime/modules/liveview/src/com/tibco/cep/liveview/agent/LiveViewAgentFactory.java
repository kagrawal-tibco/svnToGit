/**
 * 
 */
package com.tibco.cep.liveview.agent;

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.AgentClassConfig;
import com.tibco.be.util.config.cdd.AgentConfig;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.cluster.agent.AgentFactory;
import com.tibco.cep.runtime.service.cluster.agent.DefaultAgentConfiguration;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * @author vpatil
 *
 */
public class LiveViewAgentFactory implements AgentFactory {

	/* (non-Javadoc)
	 * @see com.tibco.cep.runtime.service.cluster.agent.AgentFactory#createAgent(com.tibco.cep.runtime.session.RuleServiceProvider, java.lang.String, com.tibco.be.util.config.cdd.AgentConfig)
	 */
	@Override
	public CacheAgent createAgent(RuleServiceProvider rsp, String clusterName, AgentConfig agentConfig)
			throws Exception {
		AgentClassConfig configClass = agentConfig.getRef();
        DefaultAgentConfiguration config = new LiveViewAgentConfiguration(agentConfig, configClass.getId(),
        		CddTools.getValueFromMixed(agentConfig.getKey()), rsp);
        return new LiveViewAgent(config, rsp, CacheAgent.Type.LIVEVIEW);
	}
}
