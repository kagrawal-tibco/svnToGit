package com.tibco.cep.bemm.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tibco.cep.bemm.model.impl.ProcessingUnitImpl;

@JsonDeserialize(as = ProcessingUnitImpl.class)
public interface ProcessingUnit extends Serializable {

	/**
	 * @return the key
	 */
	String getKey();

	/**
	 * @return the puId
	 */
	String getPuId();

	/**
	 * @param puId
	 *            the puId to set
	 */
	void setPuId(String puId);

	/**
	 * Add the agent class
	 * 
	 * @param agent
	 *            - Agent object
	 */
	void addAgent(AgentConfig agent);

	/**
	 * Get all agents from processing unit
	 * 
	 * @return
	 */
	List<AgentConfig> getAgents();

	/**
	 * @return the application
	 */
	Application getApplicationConfig();

	/**
	 * @param application
	 *            the application to set
	 */
	void setApplicationConfig(Application application);

	/**
	 * @return the hotDeploy
	 */
	boolean isHotDeploy();

	/**
	 * @param hotDeploy
	 *            the hotDeploy to set
	 */
	void setHotDeploy(boolean hotDeploy);

	/**
	 * @return the enableCacheStorage
	 */
	boolean isEnableCacheStorage();

	/**
	 * @param enableCacheStorage
	 *            the enableCacheStorage to set
	 */
	void setEnableCacheStorage(boolean enableCacheStorage);

	/**
	 * @return the enableDBConcept
	 */
	boolean isEnableDBConcept();

	/**
	 * @param enableDBConcept
	 *            the enableDBConcept to set
	 */
	void setEnableDBConcept(boolean enableDBConcept);

	/**
	 * @return the properties
	 */
	Map<String, String> getProperties();

	/**
	 * @return the properties
	 */
	void put(String key, String value);

	void setDeployed(boolean deployed);

	boolean getDeployed();

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
	
	/**
	 * @return
	 */
	public String getLogConfigId();
	
	/**
	 * @param logConfigId
	 */
	public void setLogConfigId(String logConfigId);
	/**
	 * @return
	 */
	public String getLogConfig();
	
	/**
	 * @param logConfig
	 */
	public void setLogConfig(String logConfig);
}