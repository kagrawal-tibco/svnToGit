package com.tibco.cep.bemm.model.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.tibco.cep.bemm.model.AgentConfig;
import com.tibco.cep.bemm.model.Application;
import com.tibco.cep.bemm.model.ProcessingUnit;

/**
 * Holds details of processing unit
 * 
 * @author dijadhav
 *
 */
public class ProcessingUnitImpl implements ProcessingUnit {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1786104955728116632L;

	private Application application;
	private Map<String, String> properties;
	private boolean hotDeploy;
	private boolean enableCacheStorage;
	private boolean enableDBConcept;
	private boolean deployed;
	/**
	 * Processing Unit deployment status
	 */
	private String deploymentStatus;

	/**
	 * Deployment description of Processing Unit.
	 */
	private String deploymentDescription;
	/**
	 * Processing Unit id
	 */
	private String puId;

	/**
	 * List of the agents from processing unit
	 */
	private List<AgentConfig> agents = new ArrayList<AgentConfig>();

	private String logConfig = null;
	private String logConfigId = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.model.impl.ProcessingUnit#getKey()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.model.impl.test#getKey()
	 */
	@Override
	public String getKey() {
		return application.getKey() + "/PU/" + puId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.model.impl.ProcessingUnit#getPuId()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.model.impl.test#getPuId()
	 */
	@Override
	public String getPuId() {
		return puId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.tea.agent.be.model.impl.ProcessingUnit#setPuId(java.lang.String
	 * )
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.model.impl.test#setPuId(java.lang.String)
	 */
	@Override
	public void setPuId(String puId) {
		this.puId = puId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.tea.agent.be.model.impl.ProcessingUnit#addAgent(com.tibco.tea
	 * .agent.be.model.Agent)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.bemm.model.impl.test#addAgent(com.tibco.cep.bemm.model.
	 * AgentConfig)
	 */
	@Override
	public void addAgent(AgentConfig agent) {
		if (null != agent) {
			agents.add(agent);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.model.impl.ProcessingUnit#getAgents()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.model.impl.test#getAgents()
	 */
	@Override
	public List<AgentConfig> getAgents() {
		return agents;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.tea.agent.be.model.impl.ProcessingUnit#getApplicationConfig()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.model.impl.test#getApplicationConfig()
	 */
	@Override
	public Application getApplicationConfig() {
		return application;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.tea.agent.be.model.impl.ProcessingUnit#setApplicationConfig
	 * (com.tibco.tea.agent.be.model.Application)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.cep.bemm.model.impl.test#setApplicationConfig(com.tibco.cep
	 * .bemm.model.Application)
	 */
	@Override
	public void setApplicationConfig(Application application) {
		this.application = application;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.model.impl.test#isHotDeploy()
	 */
	@Override
	public boolean isHotDeploy() {
		return hotDeploy;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.model.impl.test#setHotDeploy(boolean)
	 */
	@Override
	public void setHotDeploy(boolean hotDeploy) {
		this.hotDeploy = hotDeploy;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.model.impl.test#isEnableCacheStorage()
	 */
	@Override
	public boolean isEnableCacheStorage() {
		return enableCacheStorage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.model.impl.test#setEnableCacheStorage(boolean)
	 */
	@Override
	public void setEnableCacheStorage(boolean enableCacheStorage) {
		this.enableCacheStorage = enableCacheStorage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.model.impl.test#isEnableDBConcept()
	 */
	@Override
	public boolean isEnableDBConcept() {
		return enableDBConcept;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.model.impl.test#setEnableDBConcept(boolean)
	 */
	@Override
	public void setEnableDBConcept(boolean enableDBConcept) {
		this.enableDBConcept = enableDBConcept;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.model.impl.test#getProperties()
	 */
	@Override
	public Map<String, String> getProperties() {
		return properties;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.cep.bemm.model.impl.test#put(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public void put(String key, String value) {
		if (null == properties) {
			properties = new TreeMap<String, String>();
		}
		properties.put(key, value);
	}

	@Override
	public void setDeployed(boolean deployed) {
		this.deployed = deployed;
	}

	@Override
	public boolean getDeployed() {
		return deployed;
	}

	/**
	 * @return the deploymentStatus
	 */
	@Override
	public String getDeploymentStatus() {
		return deploymentStatus;
	}

	/**
	 * @param deploymentStatus
	 *            the deploymentStatus to set
	 */
	@Override
	public void setDeploymentStatus(String deploymentStatus) {
		this.deploymentStatus = deploymentStatus;
	}

	/**
	 * @return the deploymentDescription
	 */
	@Override
	public String getDeploymentDescription() {
		return deploymentDescription;
	}

	/**
	 * @param deploymentDescription
	 *            the deploymentDescription to set
	 */
	@Override
	public void setDeploymentDescription(String deploymentDescription) {
		this.deploymentDescription = deploymentDescription;
	}

	@Override
	public String getLogConfig() {
		return logConfig;
	}

	@Override
	public void setLogConfig(String logConfig) {
		this.logConfig = logConfig;
	}

	@Override
	public String getLogConfigId() {
		return logConfigId;
	}

	@Override
	public void setLogConfigId(String logConfigId) {
		this.logConfigId =logConfigId;
	}
}
