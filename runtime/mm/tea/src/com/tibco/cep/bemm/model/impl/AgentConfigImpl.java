package com.tibco.cep.bemm.model.impl;

import java.util.Map;

import com.tibco.cep.bemm.model.AgentConfig;
import com.tibco.cep.bemm.model.LocalCache;
import com.tibco.cep.bemm.model.SharedQueue;

public class AgentConfigImpl implements AgentConfig {

	private String name = null;
	private AgentType type = null;
	private String key = null;
	private String priority = null;
	private String teaKey;
	private boolean deployed;
	private LocalCache localCache;
	private SharedQueue sharedQueue;
	private String bwURI;
	private String maxActive;
	private boolean concurrentRTC;
	private boolean checkforDuplicates;
	private Map<String, String> properties;
	/**
	 * Host deployment status
	 */
	private String deploymentStatus;

	/**
	 * Deployment description of host.
	 */
	private String deploymentDescription;

	@Override
	public String getAgentName() {
		return name;
	}

	@Override
	public AgentType getAgentType() {
		return type;
	}

	@Override
	public String getAgentKey() {
		return key;
	}

	@Override
	public String getAgentPriority() {
		return priority;
	}

	@Override
	public void setAgentName(String name) {
		this.name = name;
	}

	@Override
	public void setAgentType(AgentType type) {
		this.type = type;
	}

	@Override
	public void setAgentKey(String key) {
		this.key = key;
	}

	@Override
	public void setAgentPriority(String priority) {
		this.priority = priority;
	}

	@Override
	public void setTeaObjectKey(String teaKey) {
		this.teaKey = teaKey;

	}

	@Override
	public String getTeaObjectKey() {
		return teaKey;
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
	 * @return the localCache
	 */
	public LocalCache getLocalCache() {
		return localCache;
	}

	/**
	 * @param localCache
	 *            the localCache to set
	 */
	public void setLocalCache(LocalCache localCache) {
		this.localCache = localCache;
	}

	/**
	 * @return the sharedQueue
	 */
	public SharedQueue getSharedQueue() {
		return sharedQueue;
	}

	/**
	 * @param sharedQueue
	 *            the sharedQueue to set
	 */
	public void setSharedQueue(SharedQueue sharedQueue) {
		this.sharedQueue = sharedQueue;
	}

	/**
	 * @return the bwURI
	 */
	public String getBwURI() {
		return bwURI;
	}

	/**
	 * @param bwURI
	 *            the bwURI to set
	 */
	public void setBwURI(String bwURI) {
		this.bwURI = bwURI;
	}

	/**
	 * @return the maxActive
	 */
	public String getMaxActive() {
		return maxActive;
	}

	/**
	 * @param maxActive
	 *            the maxActive to set
	 */
	public void setMaxActive(String maxActive) {
		this.maxActive = maxActive;
	}

	/**
	 * @return the concurrentRTC
	 */
	public boolean isConcurrentRTC() {
		return concurrentRTC;
	}

	/**
	 * @param concurrentRTC
	 *            the concurrentRTC to set
	 */
	public void setConcurrentRTC(boolean concurrentRTC) {
		this.concurrentRTC = concurrentRTC;
	}

	/**
	 * @return the checkforDuplicates
	 */
	public boolean isCheckforDuplicates() {
		return checkforDuplicates;
	}

	/**
	 * @param checkforDuplicates
	 *            the checkforDuplicates to set
	 */
	public void setCheckforDuplicates(boolean checkforDuplicates) {
		this.checkforDuplicates = checkforDuplicates;
	}

	/**
	 * @return the properties
	 */
	public Map<String, String> getProperties() {
		return properties;
	}

	/**
	 * @param properties
	 *            the properties to set
	 */
	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
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
}
