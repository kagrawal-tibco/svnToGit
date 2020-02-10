package com.tibco.cep.bemm.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tibco.cep.bemm.common.model.CPUUsage;
import com.tibco.cep.bemm.common.model.ProcessMemoryUsage;
import com.tibco.cep.bemm.model.impl.DeploymentVariables;
import com.tibco.cep.bemm.model.impl.ServiceInstanceImpl;
import com.tibco.cep.bemm.monitoring.metric.probe.accumulator.Attribute;
import com.tibco.cep.bemm.monitoring.service.RemoteMetricsCollectorService;

/**
 * This interface is used to get details of Service Instance(Deployment of
 * Processing Unit)
 * 
 * @author dijadhav
 *
 */
@JsonDeserialize(as = ServiceInstanceImpl.class)
public interface ServiceInstance extends Serializable, Monitorable, Versionable {

	/**
	 * Get the JMX port
	 * 
	 * @return the JMX_PORT
	 */
	int getJmxPort();

	/**
	 * Set JMX port
	 * 
	 * @param JMX_PORT
	 */
	void setJmxPort(int jmxPort);

	/**
	 * Get deployment path of the instance.
	 * 
	 * @return the deploymentPath
	 */
	String getDeploymentPath();

	/**
	 * 
	 * @param deploymentPath
	 */
	void setDeploymentPath(String deploymentPath);

	/**
	 * @return the processId
	 */
	String getProcessId();

	/**
	 * @param processId
	 *            the processId to set
	 */
	void setProcessId(String processId);

	/**
	 * @return the globalVariables
	 */
	DeploymentVariables getGlobalVariables();

	/**
	 * @param globalVariables
	 *            the globalVariables to set
	 */
	void setGlobalVariables(DeploymentVariables globalVariables);

	/**
	 * @return the systemVariables
	 */
	DeploymentVariables getSystemVariables();

	/**
	 * @param systemVariables
	 *            the systemVariables to set
	 */
	void setSystemVariables(DeploymentVariables systemVariables);

	/**
	 * @return the JVMProperties
	 */
	DeploymentVariables getJVMProperties();

	/**
	 * @param JVMProperties
	 *            the JVMProperties to set
	 */
	void setJVMProperties(DeploymentVariables JVMProperties);

	/**
	 * @return Logger Log levels
	 */
	DeploymentVariables getLoggerLogLevels();

	/**
	 * @param loggerLogLevels
	 */
	void setLoggerLogLevels(DeploymentVariables loggerLogLevels);

	/**
	 * @return the useAsEngineName
	 */
	boolean isUseAsEngineName();

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
	 * @param useAsEngineName
	 *            the useAsEngineName to set
	 */
	void setUseAsEngineName(boolean useAsEngineName);

	/**
	 * @return the predefined
	 */
	boolean isPredefined();

	/**
	 * @param predefined
	 *            the predefined to set
	 */
	void setPredefined(boolean predefined);

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
	 * @return the host
	 */
	Host getHost();

	/**
	 * @param host
	 *            the host to set
	 */
	void setHost(Host host);

	/**
	 * @return the deploymentStatus
	 */
	String getDeploymentStatus();

	/**
	 * @param deploymentStatus
	 *            the deploymentStatus to set
	 */
	void setDeploymentStatus(String deploymentStatus);

	/**
	 * @return the duId
	 */
	String getDuId();

	/**
	 * @param duId
	 *            the duId to set
	 */
	void setDuId(String duId);

	/**
	 * Add the agent class
	 * 
	 * @param agent
	 *            - Agent object
	 */
	void addAgent(Agent agent);

	/**
	 * Get all agents from processing unit
	 * 
	 * @return
	 */
	List<Agent> getAgents();

	/**
	 * Service instance is running.
	 * 
	 * @return True/False
	 */
	boolean isRunning();

	/**
	 * @return
	 */
	long getUpTime();

	/**
	 * @return
	 */
	String getStartTimeStamp();

	/**
	 * @return
	 */
	void setUpTime(Long startTime);

	/**
	 * @param startingTime
	 */
	void setStartingTime(long startingTime);

	/**
	 * Get the Starting time
	 */
	long getStartingTime();

	/**
	 * Service instance is Stopping.
	 * 
	 * @return True/False
	 */
	boolean isStopping();

	/**
	 * Service instance is Stopped.
	 * 
	 * @return True/False
	 */
	boolean isStopped();

	/**
	 * Service instance is Starting.
	 * 
	 * @return True/False
	 */
	boolean isStarting();

	/**
	 * Service instance is out off sync.
	 * 
	 * @return True/False
	 */
	boolean isOutOfSync();

	/**
	 * @return
	 */
	RemoteMetricsCollectorService getRemoteMetricsCollectorService();

	/**
	 * @param remoteMetricsCollectorService
	 */
	void setRemoteMetricsCollectorService(RemoteMetricsCollectorService remoteMetricsCollectorService);

	/**
	 * @return
	 */
	String getDeploymentDescription();

	/**
	 * @param decription
	 */
	void setDeploymentDescription(String decription);

	/**
	 * @param deployed
	 */
	void setDeployed(boolean deployed);

	/**
	 * @return
	 */
	boolean getDeployed();

	/**
	 * @return
	 */
	boolean getNeedReDeployment();

	/**
	 * @return
	 */
	boolean getNeedsDeployment();

	/**
	 * Get last deployment time
	 * 
	 * @return
	 */
	Long getLastDeploymentTime();

	/**
	 * Set Last deployment Time
	 * 
	 * @param date
	 */
	void setLastDeploymentTime(Long date);

	/**
	 * @param isHotDeployable
	 */
	void setHotDeployable(boolean isHotDeployable);

	/**
	 * @return true if instance is Hot deployable, false otherwise
	 */
	boolean isHotDeployable();

	/**
	 * @return
	 */
	ProcessMemoryUsage getMemoryUsage();

	/**
	 * @return
	 */
	CPUUsage getCpuUsage();

	/**
	 * @return Basic attributes to be included in a fact for a service instance
	 */
	List<Attribute> getBasicAttributesObject();

	DeploymentVariables getLoggerPatternAndLogLevel();

	void setLoggerPatternAndLogLevel(DeploymentVariables deploymentVariables);

	/**
	 * @param isDeployClasses
	 */
	void setDeployClasses(boolean isDeployClasses);

	/**
	 * @return true if instance is eligible to deploy classes, false otherwise
	 */
	boolean isDeployClasses();

	/**
	 * Set health status of the ServiceInstance
	 * 
	 * @return healthStatus
	 */
	public String getHealthStatus();

	/**
	 * Get health status of the ServiceInstance
	 * 
	 * @param healthStatus
	 */
	public void setHealthStatus(String healthStatus);

	/**
	 * @param isRuleTemplateDeploy
	 */
	void setRuleTemplateDeploy(boolean isRuleTemplateDeploy);

	/**
	 * @return true if instance is eligible to deploy ruleTemplate, false
	 *         otherwise
	 */
	boolean isRuleTemplateDeploy();

	/**
	 * Get the JMX user Name
	 * 
	 * @return
	 */
	String getJmxUserName();

	/**
	 * Set Jmx user name
	 * 
	 * @param jmxUserName
	 */
	void setJmxUserName(String jmxUserName);

	/**
	 * Get jmx password
	 * 
	 * @return
	 */
	String getJmxPassword();

	/**
	 * Set jmx password
	 * 
	 * @param jmxPassword
	 */
	void setJmxPassword(String jmxPassword);

	/**
	 * @return the beproperties
	 */
	DeploymentVariables getBEProperties();

	/**
	 * @param beproperties
	 *            the beproperties to set
	 */
	void setBEProperties(DeploymentVariables beProperties);

	/**
	 * @return the beId
	 */
	public String getBeId();

	/**
	 * @param beId
	 *            the beId to set
	 */
	public void setBeId(String beId);

	String getDefaultProfile();

	void setDefaultProfile(String defaultProfile);

	/**
	 * @return the ruleTemplateDeployDir
	 */
	String getRuleTemplateDeployDir();

	/**
	 * @param ruleTemplateDeployDir
	 *            the ruleTemplateDeployDir to set
	 */
	public void setRuleTemplateDeployDir(String ruleTemplateDeployDir);
	
	String getExternalClassesPath();

	public void setExternalClassesPath(String dTExternalClassesPath);


	byte getEngineStatus();

	void setEngineStatus(byte engineStatus);

	boolean isOldInstance();

	void setOldInstance(boolean oldInstance);
}