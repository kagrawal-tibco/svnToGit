package com.tibco.cep.bemm.model.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tibco.cep.bemm.common.model.CPUUsage;
import com.tibco.cep.bemm.common.model.ProcessMemoryUsage;
import com.tibco.cep.bemm.common.model.impl.CPUUsageImpl;
import com.tibco.cep.bemm.common.model.impl.ProcessMemoryUsageImpl;
import com.tibco.cep.bemm.management.util.ManagementUtil;
import com.tibco.cep.bemm.model.Agent;
import com.tibco.cep.bemm.model.ConnectionInfo;
import com.tibco.cep.bemm.model.Host;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.monitoring.metric.MetricAttribute;
import com.tibco.cep.bemm.monitoring.metric.probe.accumulator.Attribute;
import com.tibco.cep.bemm.monitoring.service.RemoteMetricsCollectorService;
import com.tibco.tea.agent.be.util.BEEntityHealthStatus;
import com.tibco.tea.agent.be.util.BETeaAgentStatus;

/**
 * This class is used to store the configuration details of Service instance.
 * 
 * @author dijadhav
 *
 */
public class ServiceInstanceImpl extends AbstractMonitorableEntity implements ServiceInstance {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5985568133359770710L;

	/**
	 * Name of the Service Instance
	 */
	private String name;

	/**
	 * Processing unit id
	 */
	private String puId;
	/**
	 * JMX port
	 */
	private int jmxPort;
	/**
	 * Deployment path of the instance.
	 */
	private String deploymentPath;
	/**
	 * Boolean value which indicates whether to use instance name as engine name
	 * or not
	 */
	private boolean useAsEngineName;

	/**
	 * Boolean value which indicates whether the instance is predefined or not.
	 */
	private boolean predefined;
	/**
	 * Parent host of the instance
	 */
	private Host host;

	/**
	 * System Process Id.
	 */
	private String processId;
	/**
	 * Global Variables
	 */
	private DeploymentVariables globalVariables;
	/**
	 * System variable.
	 */
	private DeploymentVariables systemVariables;
	/**
	 * Deployment status of the application
	 */
	private String deploymentStatus = BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus();

	/**
	 * Deployment id of the application
	 */
	private String duId;
	/**
	 * Agents in service instance
	 */
	private List<Agent> serviceInstanceAgents = new CopyOnWriteArrayList<Agent>();
	/**
	 * JVM Properties
	 */
	private DeploymentVariables jVMProperties;
	/**
	 * Logger Log levels
	 */
	private DeploymentVariables loggerLogLevels;

	private ProcessMemoryUsage memoryUsage;

	private CPUUsage cpuUsage;

	@JsonIgnore(value = true)
	private RemoteMetricsCollectorService remoteMetricsCollectorService;
	/**
	 * Deployment description of application.
	 */
	private String deploymentDescription;

	private boolean deployed;

	private Long upTime = new Long(-1);

	private Long lastDeploymentTime;

	private long startingTime = -1;

	private boolean isHotDeployable = false;

	private AtomicLong version = new AtomicLong(0);
	/**
	 * Log Pattern and Log level
	 */
	private DeploymentVariables logPatternAndLevel;

	private boolean isDeployClasses;
	private boolean isRuleTemplateDeploy;
	/**
	 * Global Variables
	 */
	private DeploymentVariables beProperties;

	/**
	 * health state of application
	 */
	private String healthStatus=BEEntityHealthStatus.ok.getHealthStatus(); //default health ok

	private String jmxUserName;

	private String jmxPassword;

	private String selectedBeId;
	private String defaultProfile;

	private String ruleTemplateDeployDir;
	private String externalClassesPath;
	private boolean oldInstance;
	private byte engineStatus=-1;

	public ServiceInstanceImpl() {
		memoryUsage = new ProcessMemoryUsageImpl(0L, 0L, 0.0D, 0L);
		cpuUsage = new CPUUsageImpl(0, 0L, 0L, 0.0D);
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.model.impl.ProcessingUnit#getJmxPort()
	 */
	@Override
	public int getJmxPort() {
		return jmxPort;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.model.impl.ProcessingUnit#setJmxPort(int)
	 */
	@Override
	public void setJmxPort(int jmxPort) {
		this.jmxPort = jmxPort;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.model.impl.ProcessingUnit#getDeploymentPath()
	 */
	@Override
	public String getDeploymentPath() {
		return deploymentPath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.tea.agent.be.model.impl.ProcessingUnit#setDeploymentPath(java
	 * .lang.String)
	 */
	@Override
	public void setDeploymentPath(String deploymentPath) {
		this.deploymentPath = deploymentPath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.model.impl.ProcessingUnit#getProcessId()
	 */
	@Override
	public String getProcessId() {
		return processId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.tea.agent.be.model.impl.ProcessingUnit#setProcessId(java.lang
	 * .String)
	 */
	@Override
	public void setProcessId(String processId) {
		this.processId = processId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.tea.agent.be.model.impl.ProcessingUnit#getGlobalVariables()
	 */
	@Override
	public DeploymentVariables getGlobalVariables() {
		return globalVariables;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.tea.agent.be.model.impl.ProcessingUnit#setGlobalVariables(java
	 * .util.List)
	 */
	@Override
	public void setGlobalVariables(DeploymentVariables globalVariables) {
		this.globalVariables = globalVariables;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.tea.agent.be.model.impl.ProcessingUnit#getSystemVariables()
	 */
	@Override
	public DeploymentVariables getSystemVariables() {
		return systemVariables;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.tea.agent.be.model.impl.ProcessingUnit#setSystemVariables(java
	 * .util.List)
	 */
	@Override
	public void setSystemVariables(DeploymentVariables systemVariables) {
		this.systemVariables = systemVariables;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.model.impl.ProcessingUnit#isUseAsEngineName()
	 */
	@Override
	public boolean isUseAsEngineName() {
		return useAsEngineName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.model.impl.ProcessingUnit#getPuId()
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
	@Override
	public void setPuId(String puId) {
		this.puId = puId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.tea.agent.be.model.impl.ProcessingUnit#setUseAsEngineName(boolean
	 * )
	 */
	@Override
	public void setUseAsEngineName(boolean useAsEngineName) {
		this.useAsEngineName = useAsEngineName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.model.impl.ProcessingUnit#isPredefined()
	 */
	@Override
	public boolean isPredefined() {
		return predefined;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.tea.agent.be.model.impl.ProcessingUnit#setPredefined(boolean)
	 */
	@Override
	public void setPredefined(boolean predefined) {
		this.predefined = predefined;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.model.impl.ProcessingUnit#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.tea.agent.be.model.impl.ProcessingUnit#setName(java.lang.String
	 * )
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.model.impl.ProcessingUnit#getKey()
	 */
	@Override
	public String getKey() {
		return host.getApplication().getKey() + "/ServiceInstance/" + name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.model.impl.ProcessingUnit#getHost()
	 */
	@Override
	public Host getHost() {
		return host;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.tea.agent.be.model.impl.ProcessingUnit#setHost(com.tibco.tea
	 * .agent.be.model.Host)
	 */
	@Override
	public void setHost(Host host) {
		this.host = host;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.tea.agent.be.model.impl.ProcessingUnit#getDeploymentStatus()
	 */
	@Override
	public String getDeploymentStatus() {
		return deploymentStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.tea.agent.be.model.impl.ProcessingUnit#setDeploymentStatus(
	 * java.lang.String)
	 */
	@Override
	public void setDeploymentStatus(String deploymentStatus) {
		this.deploymentStatus = deploymentStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.model.impl.ProcessingUnit#getDuId()
	 */
	@Override
	public String getDuId() {
		return duId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.tea.agent.be.model.impl.ProcessingUnit#setDuId(java.lang.String
	 * )
	 */
	@Override
	public void setDuId(String duId) {
		this.duId = duId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.tea.agent.be.model.impl.ProcessingUnit#addAgent(com.tibco.tea
	 * .agent.be.model.Agent)
	 */
	@Override
	public void addAgent(Agent agent) {
		if (null != agent) {
			serviceInstanceAgents.add(agent);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.model.impl.ProcessingUnit#getAgents()
	 */
	@Override
	public List<Agent> getAgents() {
		return serviceInstanceAgents;
	}

	@Override
	public DeploymentVariables getJVMProperties() {
		return jVMProperties;
	}

	@Override
	public void setJVMProperties(DeploymentVariables jVMProperties) {
		this.jVMProperties = jVMProperties;
	}

	@Override
	public boolean isRunning() {
		return BETeaAgentStatus.RUNNING.getStatus().equals(status) ? true : false;
	}

	@Override
	public boolean isStopping() {
		return BETeaAgentStatus.STOPPING.getStatus().equals(status) ? true : false;
	}

	@Override
	public boolean isStopped() {
		return BETeaAgentStatus.STOPPED.getStatus().equals(status) ? true : false;
	}

	@Override
	public boolean isStarting() {
		return BETeaAgentStatus.STARTING.getStatus().equals(status) ? true : false;
	}

	@Override
	public boolean isOutOfSync() {
		return BETeaAgentStatus.OUTOFSYNC.getStatus().equals(status) ? true : false;
	}

	@Override
	public String getStatus() {
		return status;
	}

	@Override
	public void setStatus(String status) {
		super.setStatus(status);
	}

	@Override
	public long getUpTime() {
		return upTime;
	}

	@Override
	public String getStartTimeStamp() {
		return ManagementUtil.formatTime(this.upTime);
	}

	@Override
	public void setUpTime(Long startTime) {
		this.upTime = startTime;
	}

	@Override
	public RemoteMetricsCollectorService getRemoteMetricsCollectorService() {
		return remoteMetricsCollectorService;
	}

	@Override
	public void setRemoteMetricsCollectorService(RemoteMetricsCollectorService remoteMetricsCollectorService) {
		this.remoteMetricsCollectorService = remoteMetricsCollectorService;
	}

	@Override
	public String getDeploymentDescription() {
		return this.deploymentDescription;
	}

	@Override
	public void setDeploymentDescription(String decription) {
		this.deploymentDescription = decription;
	}

	@Override
	public boolean getNeedsDeployment() {
		return BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus().equals(deploymentStatus) ? true : false;
	}

	@Override
	public boolean getNeedReDeployment() {
		return BETeaAgentStatus.NEEDREDEPLOYMENT.getStatus().equals(deploymentStatus) ? true : false;
	}

	@Override
	public void setDeployed(boolean deployed) {
		this.deployed = deployed;
	}

	@Override
	public boolean getDeployed() {
		return deployed;
	}

	@Override
	public ENTITY_TYPE getType() {
		return ENTITY_TYPE.PU_INSTANCE;
	}

	@Override
	public Map<String, Object> getBasicAttributes() {
		Map<String, Object> basicAttributes = new HashMap<>();
		basicAttributes.put(MetricAttribute.CLUSTER, this.getHost().getApplication().getClusterName());
		basicAttributes.put(MetricAttribute.APPLICATION, this.getHost().getApplication().getName());
		basicAttributes.put(MetricAttribute.HOST, this.getHost().getHostName());
		basicAttributes.put(MetricAttribute.PU_INSTANCE, this.getName());
		basicAttributes.put(MetricAttribute.PU_INSTANCE_IS_PREDEFINED, isPredefined());
		basicAttributes.put(MetricAttribute.PU_INSTANCE_ISACTIVE, isRunning());
		return basicAttributes;
	}

	@Override
	public List<Attribute> getBasicAttributesObject() {
		List<Attribute> basicAttributes = new ArrayList<Attribute>();
		basicAttributes.add(new Attribute(MetricAttribute.CLUSTER, this.getHost().getApplication().getClusterName()));
		basicAttributes.add(new Attribute(MetricAttribute.APPLICATION, this.getHost().getApplication().getName()));
		basicAttributes.add(new Attribute(MetricAttribute.HOST, this.getHost().getHostName()));
		basicAttributes.add(new Attribute(MetricAttribute.PU_INSTANCE, this.getName()));
		basicAttributes.add(new Attribute(MetricAttribute.PU_INSTANCE_IS_PREDEFINED, isPredefined()));
		basicAttributes.add(new Attribute(MetricAttribute.PU_NAME, this.getPuId()));
		basicAttributes.add(new Attribute(MetricAttribute.PU_INSTANCE_ISACTIVE, (isRunning() == true) ? 1 : 0));
		basicAttributes.add(new Attribute(MetricAttribute.PU_INSTANCE_COUNT, 1));

		return basicAttributes;
	}

	
	@Override
	public ConnectionInfo getConnectionInfo(String connectionType) {
		ConnectionInfo remoteJMXConnectionContext = null;
		if ("JMX".equalsIgnoreCase(connectionType)) {
			remoteJMXConnectionContext = new JMXConnectionInfo();
			remoteJMXConnectionContext.setRemoteHost(host.getHostName());
			remoteJMXConnectionContext.setRemotePort(getJmxPort());
		}
		return remoteJMXConnectionContext;
	}

	@Override
	public Long getLastDeploymentTime() {
		return this.lastDeploymentTime;
	}

	@Override
	public void setLastDeploymentTime(Long lastDeploymentTime) {
		this.lastDeploymentTime = lastDeploymentTime;

	}

	@Override
	public DeploymentVariables getLoggerLogLevels() {
		return loggerLogLevels;
	}

	@Override
	public void setLoggerLogLevels(DeploymentVariables loggerLogLevels) {
		this.loggerLogLevels = loggerLogLevels;
	}

	@Override
	public void setStartingTime(long startingTime) {
		this.startingTime = startingTime;
	}

	@Override
	public long getStartingTime() {
		return this.startingTime;
	}

	@Override
	public void setHotDeployable(boolean isHotDeployable) {
		this.isHotDeployable = isHotDeployable;
	}

	@Override
	public boolean isHotDeployable() {
		return isHotDeployable;
	}

	@Override
	public ProcessMemoryUsage getMemoryUsage() {
		return memoryUsage;
	}

	@Override
	public CPUUsage getCpuUsage() {
		return cpuUsage;
	}

	@Override
	public DeploymentVariables getLoggerPatternAndLogLevel() {
		return logPatternAndLevel;
	}

	@Override
	public void setLoggerPatternAndLogLevel(DeploymentVariables logPatternAndLevel) {
		this.logPatternAndLevel = logPatternAndLevel;
	}

	@Override
	public void setDeployClasses(boolean isDeployClasses) {
		this.isDeployClasses = isDeployClasses;
	}

	@Override
	public boolean isDeployClasses() {
		return isDeployClasses;
	}

	@Override
	public Long incrementVersion() {
		return version.incrementAndGet();
	}

	@Override
	public Long getVersion() {
		return version.get();
	}

	
	
	@Override
	public String getHealthStatus() {
		return healthStatus;
	}

	@Override
	public void setHealthStatus(String healthStatus) {
		this.healthStatus = healthStatus;
	}

	@Override
	public void setRuleTemplateDeploy(boolean isRuleTemplateDeploy) {
		this.isRuleTemplateDeploy = isRuleTemplateDeploy;
	}

	@Override
	public boolean isRuleTemplateDeploy() {
		return isRuleTemplateDeploy;
	}

	@Override
	public String getJmxUserName() {
		return jmxUserName;
	}

	@Override
	public void setJmxUserName(String jmxUserName) {
		this.jmxUserName = jmxUserName;
	}

	@Override
	public String getJmxPassword() {
		return jmxPassword;
	}

	@Override
	public void setJmxPassword(String jmxPassword) {
		this.jmxPassword = jmxPassword;
	}

	@Override
	public DeploymentVariables getBEProperties() {
		return beProperties;
	}

	@Override
	public void setBEProperties(DeploymentVariables beProperties) {
		this.beProperties = beProperties;
	}

	@Override
	public String getBeId() {
		return this.selectedBeId;
	}

	@Override
	public void setBeId(String beId) {
		this.selectedBeId = beId;
	}

	@Override
	public String getDefaultProfile() {
		return defaultProfile;
	}

	@Override
	public void setDefaultProfile(String defaultProfile) {
		this.defaultProfile = defaultProfile;
	}

	/**
	 * @return the ruleTemplateDeployDir
	 */
	@Override
	public String getRuleTemplateDeployDir() {
		return ruleTemplateDeployDir;
	}

	/**
	 * @param ruleTemplateDeployDir
	 *            the ruleTemplateDeployDir to set
	 */
	@Override
	public void setRuleTemplateDeployDir(String ruleTemplateDeployDir) {
		this.ruleTemplateDeployDir = ruleTemplateDeployDir;
	}


	@Override
	public String getExternalClassesPath() {
		return externalClassesPath;
	}


	@Override
	public void setExternalClassesPath(String externalClassesPath) {
		this.externalClassesPath = externalClassesPath;
		
	}

	@Override
	public byte getEngineStatus() {
		return engineStatus;
	}

	@Override
	public void setEngineStatus(byte engineStatus) {
		this.engineStatus = engineStatus;
	}
	@Override
	public boolean isOldInstance() {
		return oldInstance;
	}
	@Override
	public void setOldInstance(boolean oldInstance) {
		this.oldInstance = oldInstance;
	}

}
