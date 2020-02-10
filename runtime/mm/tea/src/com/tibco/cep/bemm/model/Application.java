package com.tibco.cep.bemm.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tibco.cep.bemm.common.handler.ApplicationDataFeedHandler;
import com.tibco.cep.bemm.common.tra.config.model.HostTraConfigs;
import com.tibco.cep.bemm.common.util.OMEnum;
import com.tibco.cep.bemm.model.impl.ApplicationImpl;

/**
 * This interface is used to get details of BusinessEvents Application.
 * 
 * @author dijadhav
 *
 */
@JsonDeserialize(as = ApplicationImpl.class)
public interface Application extends Serializable, Monitorable, Versionable {

	/**
	 * @return the name
	 */
	String getName();

	/**
	 * @param name
	 *            the name to set
	 */
	void setName(String name);

	/**
	 * @return the needsDeployment
	 */
	String getDeploymentStatus();

	/**
	 * @param needsDeployment
	 *            the needsDeployment to set
	 */
	void setDeploymentStatus(String deploymentStatus);

	/**
	 * @return the description
	 */
	String getDescription();

	/**
	 * @param description
	 *            the description to set
	 */
	void setDescription(String description);

	/**
	 * @return the status
	 */
	String getStatus();

	/**
	 * @param status
	 *            the status to set
	 */
	void setStatus(String status);

	/**
	 * @return the key
	 */
	String getKey();

	/**
	 * @return the clusterName
	 */
	String getClusterName();

	/**
	 * @param clusterName
	 *            the clusterName to set
	 */
	void setClusterName(String clusterName);

	/**
	 * This method is used to add the processing unt
	 * 
	 * @param processingUnit
	 */
	void addProcessingUnit(ProcessingUnit processingUnit);

	/**
	 * @return the processingUnits
	 */
	Collection<ProcessingUnit> getProcessingUnits();

	/**
	 * Get processing unit by id.
	 * 
	 * @param puId
	 * @return
	 */
	ProcessingUnit getProcessingUnit(String puId);

	/**
	 * This method is used to add the host details.
	 * 
	 * @param host
	 */
	void addHost(Host host);

	/**
	 * @return the hosts
	 */
	List<Host> getHosts();

	/**
	 * Get the next deployment unit id for the deployment unit id.
	 * 
	 * @return
	 */
	@JsonIgnore
	String getNextDeploymentUnitId();

	/**
	 * Add application data provider service
	 * 
	 * @param dataFeedHandler
	 *            - ApplicationDataFeedHandler
	 */
	void addApplicationDataFeedHandler(OMEnum omMode, ApplicationDataFeedHandler<?> dataFeedHandler);

	/**
	 * Get the application data provider feed handler
	 * 
	 * @return
	 */
	<T extends Monitorable> ApplicationDataFeedHandler<T> getApplicationDataFeedHandler(OMEnum omMode);

	/**
	 * Removes all the Application feed handlers
	 */
	<T extends Monitorable> void removeApplicationDataFeedHandler(OMEnum omMode);

	/**
	 * Get the deployment description of application
	 * 
	 * @return Deployment description of application
	 */
	String getDeploymentDescription();

	/**
	 * Set the deployment description of application
	 * 
	 * @param decription
	 *            - Deployment description of application
	 */
	void setDeploymentDescription(String decription);

	void setDeployed(boolean deployed);

	boolean getDeployed();

	/**
	 * @return the author
	 */
	public String getAuthor();

	/**
	 * @param author
	 *            the author to set
	 */
	public void setAuthor(String author);

	/**
	 * @return the comments
	 */
	public String getComments();

	/**
	 * @param comments
	 *            the comments to set
	 */
	public void setComments(String comments);

	/**
	 * @return the version
	 */
	public String getCddVersion();

	/**
	 * @param version
	 *            the version to set
	 */
	public void setCddVersion(String version);

	/**
	 * @return the date
	 */
	public String getDate();

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(String date);

	/**
	 * @return
	 */
	public boolean isInMemoryMode();

	/**
	 * @param isInMemoryMode
	 */
	public void setInMemoryMode(boolean isInMemoryMode);

	public boolean connectToCluster();

	public void setConnectToCluster(boolean connectToCluster);

	/**
	 * Get the discovery URL
	 * 
	 * @return Discovery URL
	 */
	String getDiscoveryURL();

	/**
	 * Set Discovery URL for the instance.
	 * 
	 * @param discoveryURL
	 */
	void setDiscoveryURL(String discoveryURL);

	/**
	 * Get Listen URL
	 * 
	 * @return Listen URL
	 */
	String getListenURL();

	/**
	 * Set the listen URL
	 * 
	 * @param listenURL
	 *            - Listen URL
	 */
	void setListenURL(String listenURL);

	/**
	 * Set Application Creation Time
	 * 
	 * @param creationTime
	 */
	void setCreationTime(long creationTime);

	/**
	 * Application Creation Time
	 * 
	 * @param creationTime
	 */
	long getCreationTime();

	/**
	 * @return true if instance is Hot deployable, false otherwise
	 */
	boolean isHotDeployable();

	/**
	 * Get the external class files path
	 * 
	 * @return External class file path
	 */
	String getExternalClassesPath();

	/**
	 * Set external class file path
	 * 
	 * @param externalClassesPath
	 */
	void setExternalClassesPath(String externalClassesPath);

	void setHostTraConfigs(HostTraConfigs hostTraConfigs);

	/**
	 * Set health status of the Application
	 * 
	 * @return healthStatus
	 */
	public String getHealthStatus();

	/**
	 * Get health status of the Application
	 * 
	 * @param healthStatus
	 */
	public void setHealthStatus(String healthStatus);

	HostTraConfigs getHostTraConfigs();

	String getRuleTemplateDeployDir();

	void setRuleTemplateDeployDir(String ruleTemplateDeployDir);

	Map<String, String> getGlobalVariables();

	void setGlobalVariables(Map<String, String> globalVariables);

	Map<String, String> getSystemProperties();

	void setSystemProperties(Map<String, String> systemProperties);

	Map<String, String> getBEProperties();

	void setBEProperties(Map<String, String> beProperties);

	Set<String> getProfiles();

	void addProfile(String profile);

	String getDefaultProfile();

	void setDefaultProfile(String defaultProfile);
	
	Integer getAlertCount();
	
	void setAlertCount(Integer alertCount);

	String getAppVersion();

	void setAppVersion(String appVersion);

	boolean isMonitorableOnly();

	void setMonitorableOnly(boolean isMonitorableOnly);
}