/**
 * 
 */
package com.tibco.cep.liveview.agent;

import java.util.HashMap;
import java.util.Map;

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.AgentConfig;
import com.tibco.be.util.config.cdd.EntityConfig;
import com.tibco.be.util.config.cdd.LDMConnectionConfig;
import com.tibco.be.util.config.cdd.LiveViewAgentClassConfig;
import com.tibco.cep.repo.GlobalVariableDescriptor;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.service.cluster.agent.DefaultAgentConfiguration;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.security.AXSecurityException;
import com.tibco.security.ObfuscationEngine;

/**
 * @author vpatil
 *
 */
public class LiveViewAgentConfiguration extends DefaultAgentConfiguration {
	
	private static final int DEFAULT_INITIAL_SIZE = 1;
	private static final int DEFAULT_MAX_SIZE = 5;
	
	private static final int DEFAULT_THREAD_COUNT = 4;
	private static final int DEFAULT_QUEUE_SIZE = 1024;
	
	private static final String DEFAULT_LV_URI = "lv://localhost";
	
	private AgentConfig agentConfig;
	
	private GlobalVariables globalVariables;
		
	public LiveViewAgentConfiguration(AgentConfig agentConfig, String name, String key,
			RuleServiceProvider rsp) {
		super(name, key, rsp);
		this.agentConfig = agentConfig;
		this.globalVariables = rsp.getGlobalVariables();
	}
	
	public String getConnectionURI() {
		String uri = CddTools.getValueFromMixed(getLDMConnectionConfig().getLdmUrl());
		uri = globalVariables.substituteVariables(uri).toString();
		return ((null == uri) || uri.isEmpty()) ? DEFAULT_LV_URI : uri;
	}
	
	public String getUserName() {
		String userName = CddTools.getValueFromMixed(getLDMConnectionConfig().getUserName());
		userName = globalVariables.substituteVariables(userName).toString();
		return userName;
	}
	
	public String getUserPassword() {
		String userPassword = CddTools.getValueFromMixed(getLDMConnectionConfig().getUserPassword());
		GlobalVariableDescriptor gvDescriptor = globalVariables.getVariable(trimGlobalVar(userPassword));
		if (gvDescriptor != null) {
			if (gvDescriptor.getType().equals("Password")) {
				try {
					userPassword = new String(ObfuscationEngine.decrypt(gvDescriptor.getValueAsString()));
				} catch (AXSecurityException e) {
					e.printStackTrace();
				}
			} else {
				userPassword = gvDescriptor.getValueAsString();
			}
		} else {
			userPassword = globalVariables.substituteVariables(userPassword).toString();
		}
		return userPassword;
	}
	
	public int getInitialSize() {
		String initialSize = CddTools.getValueFromMixed(getLDMConnectionConfig().getInitialSize());
		initialSize = globalVariables.substituteVariables(initialSize).toString();
		return ((null == initialSize) || initialSize.isEmpty()) ? DEFAULT_INITIAL_SIZE : Integer.parseInt(initialSize);
	}
	
	public int getMaxSize() {
		String maxSize = CddTools.getValueFromMixed(getLDMConnectionConfig().getMaxSize());
		maxSize = globalVariables.substituteVariables(maxSize).toString();
		return ((null == maxSize) || maxSize.isEmpty()) ? DEFAULT_MAX_SIZE : Integer.parseInt(maxSize);
	}
	
	public int getMaxActive() {
		LiveViewAgentClassConfig lvAgentClassConfig = (LiveViewAgentClassConfig) agentConfig.getRef();
		String maxActive = (lvAgentClassConfig.getLoad() != null) ? CddTools.getValueFromMixed(lvAgentClassConfig.getLoad().getMaxActive()) : null;
		return ((null == maxActive) || maxActive.isEmpty()) ? 0 : Integer.parseInt(maxActive);
	}
	
	public int getPublisherThreadCount() {
		LiveViewAgentClassConfig lvAgentClassConfig = (LiveViewAgentClassConfig) agentConfig.getRef();
		String threadCount = CddTools.getValueFromMixed(lvAgentClassConfig.getPublisher().getPublisherThreadCount());
		return ((null == threadCount) || threadCount.isEmpty()) ? DEFAULT_THREAD_COUNT : Integer.parseInt(threadCount);
	}
	
	public int getPublisherQueueSize() {
		LiveViewAgentClassConfig lvAgentClassConfig = (LiveViewAgentClassConfig) agentConfig.getRef();
		String queueSize = CddTools.getValueFromMixed(lvAgentClassConfig.getPublisher().getPublisherQueueSize());
		return ((null == queueSize) || queueSize.isEmpty()) ? DEFAULT_QUEUE_SIZE : Integer.parseInt(queueSize);
	}
	
	private LDMConnectionConfig getLDMConnectionConfig() {
		LiveViewAgentClassConfig lvAgentClassConfig = (LiveViewAgentClassConfig) agentConfig.getRef();
		return lvAgentClassConfig.getLdmConnection();
	}
	
	public Map<String, String> getEntitySet() {
		Map<String, String> entityToFilterMap = new HashMap<String, String>();
		LiveViewAgentClassConfig lvAgentClassConfig = (LiveViewAgentClassConfig) agentConfig.getRef();
		for (EntityConfig ec : lvAgentClassConfig.getEntitySet().getEntity()) {
			if (!ec.getUri().isEmpty()) {
				String filter = CddTools.getValueFromMixed(ec.getFilter());
				entityToFilterMap.put(ec.getUri(), filter);
			}
		}
		
		return entityToFilterMap;
	}
	
	private String trimGlobalVar(String str) {
		if (str==null)
			return str;
		
		if (str.startsWith("%%") && str.endsWith("%%")) {
			String[] tokens = str.split("%%");
			if (tokens.length==2) {
				return tokens[1];
			}
		}
		return str;
	}

}
