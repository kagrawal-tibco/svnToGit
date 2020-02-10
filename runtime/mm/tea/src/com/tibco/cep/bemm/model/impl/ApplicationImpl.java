package com.tibco.cep.bemm.model.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tibco.cep.bemm.common.handler.ApplicationDataFeedHandler;
import com.tibco.cep.bemm.common.tra.config.model.HostTraConfigs;
import com.tibco.cep.bemm.common.util.OMEnum;
import com.tibco.cep.bemm.model.Application;
import com.tibco.cep.bemm.model.ConnectionInfo;
import com.tibco.cep.bemm.model.Host;
import com.tibco.cep.bemm.model.Monitorable;
import com.tibco.cep.bemm.model.ProcessingUnit;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.monitoring.metric.MetricAttribute;
import com.tibco.tea.agent.be.util.BEEntityHealthStatus;
import com.tibco.tea.agent.be.util.BETeaAgentStatus;

/**
 * Hold the details of application.
 * 
 * @author dijadhav
 *
 */
public class ApplicationImpl extends AbstractMonitorableEntity implements Application {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7889558989627090633L;
	/**
	 * Name of the application
	 */
	private String name;
	/**
	 * application deployment
	 */
	private String deploymentStatus;
	/**
	 * Description of the application
	 */
	private String description;

	/**
	 * Status of the application
	 */
	private String status = BETeaAgentStatus.NEEDsDEPLOYMENT.getStatus();

	/**
	 * ClusterName of the application
	 */
	private String clusterName;
	/**
	 * Deployment description of application.
	 */
	private String deploymentDescription;

	/**
	 * List of the processing units from the application.
	 */
	@JsonIgnore
	private Map<String, ProcessingUnit> processingUnits = new HashMap<String, ProcessingUnit>();

	/**
	 * Hosts on which service instances of application running.
	 */
	@JsonIgnore
	private List<Host> hosts = new CopyOnWriteArrayList<Host>();

	@JsonIgnore
	private Map<String, ApplicationDataFeedHandler<? extends Monitorable>> dataFeedHandlers = new ConcurrentHashMap<>();

	/**
	 *  
	 */
	private boolean deployed;

	private boolean inMemoryOM;

	private boolean connectToCluster = false;
	
	private String author;
	private String comments;
	private String cddVersion;
	private String date;

	private String discoveryURL;

	private String listenURL;

	private long creationTime;
	private String externalClassesPath;
	private HostTraConfigs hostTraConfigs;
	private String ruleTemplateDeployDir;
	
	private Integer alertCount;
	private String appVersion;

	/**
	 * health state of application
	 */
	private String healthStatus=BEEntityHealthStatus.ok.getHealthStatus(); //default health ok
	
	
	private AtomicLong version = new AtomicLong(0);
	private Map<String, String> globalVariables=new ConcurrentHashMap<>();
	private Map<String, String> systemProperties=new ConcurrentHashMap<>();
	private Map<String, String> beProperties=new ConcurrentHashMap<>();
	private Set<String> profiles=new HashSet<String>();
	private String defaultProfile;
	private boolean monitorableOnly;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.model.impl.Application#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.tea.agent.be.model.impl.Application#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.model.impl.Application#getDescription()
	 */
	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getDeploymentStatus() {
		return deploymentStatus;
	}

	@Override
	public void setDeploymentStatus(String deploymentStatus) {
		this.deploymentStatus = deploymentStatus;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.tea.agent.be.model.impl.Application#setDescription(java.lang
	 * .String)
	 */
	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.model.impl.Application#getStatus()
	 */
	@Override
	public String getStatus() {
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.tea.agent.be.model.impl.Application#setStatus(java.lang.String)
	 */
	@Override
	public void setStatus(String status) {
		this.status = status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.model.impl.Application#getKey()
	 */
	@Override
	public String getKey() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.model.impl.Application#getClusterName()
	 */
	@Override
	public String getClusterName() {
		return clusterName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.tea.agent.be.model.impl.Application#setClusterName(java.lang
	 * .String)
	 */
	@Override
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.tea.agent.be.model.impl.Application#addProcessingUnit(com.tibco
	 * .tea.agent.be.model.impl.ProcessingUnitImpl)
	 */
	@Override
	public void addProcessingUnit(ProcessingUnit processingUnit) {
		if (null != processingUnit) {
			processingUnits.put(processingUnit.getPuId(), processingUnit);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.model.impl.Application#getProcessingUnits()
	 */
	@Override
	public Collection<ProcessingUnit> getProcessingUnits() {
		return processingUnits.values();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.tea.agent.be.model.Application#getProcessingUnit(java.lang.
	 * String)
	 */
	@Override
	public ProcessingUnit getProcessingUnit(String puId) {
		return processingUnits.get(puId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.tea.agent.be.model.impl.Application#addHost(com.tibco.tea.agent
	 * .be.model.impl.HostImpl)
	 */
	@Override
	public void addHost(Host host) {
		if (null != host) {
			hosts.add(host);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.model.impl.Application#getHosts()
	 */
	@Override
	public List<Host> getHosts() {
		return hosts;
	}

	@Override
	public String getNextDeploymentUnitId() {
		List<Integer> noList = new ArrayList<Integer>();
		String numStr;
		Collection<Host> hosts = this.getHosts();
		for (Host host : hosts) {
			Iterator<ServiceInstance> iterator = host.getInstances().iterator();

			String duId;
			while (iterator.hasNext()) {
				duId = iterator.next().getDuId();
				if (duId.startsWith("DU_")) {
					numStr = duId.substring(duId.indexOf("_") + 1);
					noList.add(getValidNo(numStr));
				}
			}
		}

		if (noList.size() > 0) {
			Object[] noArray = noList.toArray();
			java.util.Arrays.sort(noArray);
			int num = ((Integer) noArray[noArray.length - 1]).intValue();
			num++;
			return "DU_" + num;
		}
		return "DU_0";
	}

	private static int getValidNo(String no) {
		int n;
		try {
			n = Integer.parseInt(no);
		} catch (Exception e) {
			return 0;
		}
		return n;
	}

	public void addApplicationDataFeedHandler(OMEnum omMode,
			ApplicationDataFeedHandler<? extends Monitorable> dataFeedHandler) {
		dataFeedHandlers.put(omMode.name(), dataFeedHandler);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Monitorable> ApplicationDataFeedHandler<T> getApplicationDataFeedHandler(OMEnum omMode) {
		return (ApplicationDataFeedHandler<T>) dataFeedHandlers.get(omMode.name());
	}

	@Override
	public <T extends Monitorable> void removeApplicationDataFeedHandler(OMEnum omMode) {
		dataFeedHandlers.remove(omMode.name());
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
	public void setDeployed(boolean deployed) {
		this.deployed = deployed;
	}

	@Override
	public boolean getDeployed() {
		return deployed;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author
	 *            the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments
	 *            the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return the version
	 */
	public String getCddVersion() {
		return cddVersion;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setCddVersion(String version) {
		this.cddVersion = version;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public ENTITY_TYPE getType() {
		return ENTITY_TYPE.CLUSTER;
	}

	@Override
	public Map<String, Object> getBasicAttributes() {
		Map<String, Object> basicAttributes = new HashMap<>();
		basicAttributes.put(MetricAttribute.CLUSTER, this.getClusterName());
		return basicAttributes;
	}

	@Override
	public ConnectionInfo getConnectionInfo(String connectionType) {
		return null;
	}

	@Override
	public boolean isInMemoryMode() {
		return inMemoryOM;
	}

	@Override
	public String getDiscoveryURL() {
		return discoveryURL;
	}

	@Override
	public void setInMemoryMode(boolean isInMemoryMode) {
		this.inMemoryOM = isInMemoryMode;
	}

	@Override
	public boolean connectToCluster() {
		return connectToCluster;
	}

	@Override
	public void setConnectToCluster(boolean connectToCluster) {
		this.connectToCluster = connectToCluster;
	}

	@Override
	public void setDiscoveryURL(String discoveryURL) {
		this.discoveryURL = discoveryURL;
	}

	@Override
	public String getListenURL() {
		return listenURL;
	}

	@Override
	public void setListenURL(String listenURL) {
		this.listenURL = listenURL;
	}

	@Override
	public void setCreationTime(long creationTime) {
		this.creationTime = creationTime;
	}

	@Override
	public long getCreationTime() {
		return this.creationTime;
	}

	@Override
	public boolean isHotDeployable() {
		boolean isHotDeploy = false;
		for (Host host : hosts) {
			for (ServiceInstance serviceInstance : host.getInstances()) {
				if (serviceInstance.isHotDeployable()) {
					isHotDeploy = true;
					break;
				}
			}
			if (isHotDeploy) {
				break;
			}
		}
		return isHotDeploy;
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
	public void setHostTraConfigs(HostTraConfigs hostTraConfigs) {
		this.hostTraConfigs = hostTraConfigs;
	}

	@Override
	public HostTraConfigs getHostTraConfigs() {
		return hostTraConfigs;
	}

	@Override
	public String getHealthStatus() {
		return healthStatus;
	}

	@Override
	public void setHealthStatus(String healthStatus) {
		this.healthStatus = healthStatus;
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
	public Long incrementVersion() {
		return version.incrementAndGet();
	}

	@Override
	public Long getVersion() {
		return version.get();
	}


	@Override
	public Map<String, String> getGlobalVariables() {
		return globalVariables;
	}

	@Override
	public void setGlobalVariables(Map<String, String> globalVariables) {
		this.globalVariables=globalVariables;
	}

	@Override
	public Set<String> getProfiles() {
		return profiles;
	}

	@Override
	public void addProfile(String profile) {
		profiles.add(profile);
	}

	/**
	 * @return the defaultProfile
	 */
	@Override
	public String getDefaultProfile() {
		return defaultProfile;
	}

	/**
	 * @param defaultProfile the defaultProfile to set
	 */
	@Override
	public void setDefaultProfile(String defaultProfile) {
		this.defaultProfile = defaultProfile;
	}


	@Override
	public Map<String, String> getSystemProperties() {
		return systemProperties;
	}

	@Override
	public void setSystemProperties(Map<String, String> systemProperties) {
		this.systemProperties=systemProperties;
	}

	@Override
	public Map<String, String> getBEProperties() {
		return beProperties;
	}

	@Override
	public void setBEProperties(Map<String, String> beProperties) {
		this.beProperties=beProperties;
	}

	@Override
	public Integer getAlertCount() {
		return alertCount;
	}

	@Override
	public void setAlertCount(Integer alertCount) {
		this.alertCount = alertCount;
	}	
	@Override
	public String getAppVersion() {
		return appVersion;
	}

	@Override
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}	
	@Override
	public boolean isMonitorableOnly() {
		return monitorableOnly;
	}

	@Override
	public void setMonitorableOnly(boolean isMonitorableOnly) {
		this.monitorableOnly = isMonitorableOnly;
	}
}
