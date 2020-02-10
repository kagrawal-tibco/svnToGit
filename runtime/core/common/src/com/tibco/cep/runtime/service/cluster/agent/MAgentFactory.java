/**
 * 
 */
package com.tibco.cep.runtime.service.cluster.agent;

import java.util.ArrayList;

import com.tibco.be.util.config.cdd.AgentConfig;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * @author Nick
 *
 */
public interface MAgentFactory extends AgentFactory {

	ArrayList<CacheAgent> createAgents(RuleServiceProvider rsp, String clusterName, AgentConfig agentConfig) throws Exception;
}
