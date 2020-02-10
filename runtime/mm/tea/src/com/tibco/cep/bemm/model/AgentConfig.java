package com.tibco.cep.bemm.model;

import java.util.Map;

import com.tibco.cep.bemm.model.impl.AgentType;

public interface AgentConfig {

	String getAgentName();

	void setAgentName(String name);

	AgentType getAgentType();

	void setAgentType(AgentType type);

	String getAgentKey();

	void setAgentKey(String key);

	String getAgentPriority();

	void setAgentPriority(String priority);

	void setTeaObjectKey(String teaKey);

	String getTeaObjectKey();

	void setDeployed(boolean deployed);

	boolean getDeployed();

	/**
	 * @return the localCache
	 */
	public LocalCache getLocalCache();

	/**
	 * @param localCache
	 *            the localCache to set
	 */
	public void setLocalCache(LocalCache localCache);

	/**
	 * @return the sharedQueue
	 */
	public SharedQueue getSharedQueue();

	/**
	 * @param sharedQueue
	 *            the sharedQueue to set
	 */
	public void setSharedQueue(SharedQueue sharedQueue);

	/**
	 * @return the bwURI
	 */
	public String getBwURI();

	/**
	 * @param bwURI
	 *            the bwURI to set
	 */
	public void setBwURI(String bwURI);

	/**
	 * @return the maxActive
	 */
	public String getMaxActive();

	/**
	 * @param maxActive
	 *            the maxActive to set
	 */
	public void setMaxActive(String maxActive);

	/**
	 * @return the concurrentRTC
	 */
	public boolean isConcurrentRTC();

	/**
	 * @param concurrentRTC
	 *            the concurrentRTC to set
	 */
	public void setConcurrentRTC(boolean concurrentRTC);

	/**
	 * @return the checkforDuplicates
	 */
	public boolean isCheckforDuplicates();

	/**
	 * @param checkforDuplicates
	 *            the checkforDuplicates to set
	 */
	public void setCheckforDuplicates(boolean checkforDuplicates);

	/**
	 * @return the properties
	 */
	public Map<String, String> getProperties();

	/**
	 * @param properties
	 *            the properties to set
	 */
	public void setProperties(Map<String, String> properties);

	/**
	 * @return the deploymentStatus
	 */
	public String getDeploymentStatus();

	/**
	 * @param deploymentStatus
	 *            the deploymentStatus to set
	 */
	public void setDeploymentStatus(String deploymentStatus);

	/**
	 * @return the deploymentDescription
	 */
	public String getDeploymentDescription();

	/**
	 * @param deploymentDescription
	 *            the deploymentDescription to set
	 */
	public void setDeploymentDescription(String deploymentDescription);
}
