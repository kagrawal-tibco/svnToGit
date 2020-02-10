package com.tibco.cep.bemm.model.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tibco.cep.bemm.model.Application;
import com.tibco.cep.bemm.model.BE;
import com.tibco.cep.bemm.model.ConnectionInfo;
import com.tibco.cep.bemm.model.Host;
import com.tibco.cep.bemm.model.MasterHost;
import com.tibco.cep.bemm.model.ServiceInstance;
import com.tibco.cep.bemm.monitoring.metric.MetricAttribute;

/**
 * This file contains the details of host
 * 
 * @author dijadhav
 *
 */
public class HostImpl extends AbstractMonitorableEntity implements Host {

	private static final long serialVersionUID = -5490546402446440231L;

	/**
	 * Master Host s
	 */
	private MasterHost masterHost;

	/**
	 * List of the service instances
	 */
	@JsonIgnore
	private List<ServiceInstance> instances = new CopyOnWriteArrayList<ServiceInstance>();
	/**
	 * Parent of the host
	 */
	private Application application;

	private boolean deployed;
	/**
	 * Host deployment status
	 */
	private String deploymentStatus;

	/**
	 * Deployment description of host.
	 */
	private String deploymentDescription;
	/**
	 * Selected BE Home
	 */
	private String selectedBEHome;

	private String selectedBETra;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.model.impl.Host#getHostName()
	 */
	@Override
	public String getHostName() {
		return masterHost.getHostName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.model.impl.Monitorable#getName()
	 */
	@Override
	public String getName() {
		return masterHost.getHostName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.model.impl.Host#getHostId()
	 */
	@Override
	public String getHostId() {
		return masterHost.getHostId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.model.impl.Host#getHostIp()
	 */
	@Override
	public String getHostIp() {
		return masterHost.getHostIp();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.model.impl.Host#getOs()
	 */
	@Override
	public String getOs() {
		return masterHost.getOs();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.model.impl.Host#getUserName()
	 */
	@Override
	public String getUserName() {
		return masterHost.getUserName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.model.impl.Host#getPassword()
	 */
	@Override
	public String getPassword() {
		return masterHost.getPassword();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.model.impl.Host#getSshPort()
	 */
	@Override
	public int getSshPort() {
		return masterHost.getSshPort();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.model.impl.Host#getMachineName()
	 */
	@Override
	public String getMachineName() {
		return masterHost.getMachineName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.model.impl.Host#isPredefined()
	 */
	@Override
	public boolean isPredefined() {
		return masterHost.isPredefined();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.model.impl.Host#getKey()
	 */
	@Override
	public String getKey() {
		return application.getKey() + "/" + masterHost.getHostId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.model.impl.Host#getInstances()
	 */
	@Override
	public List<ServiceInstance> getInstances() {
		return instances;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.tea.agent.be.model.impl.Host#addInstance(com.tibco.tea.agent
	 * .be.model.impl.ServiceInstanceImpl)
	 */
	@Override
	public void addInstance(ServiceInstance instance) {
		if (null != instance) {
			instances.add(instance);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.model.impl.Host#getApplication()
	 */
	@Override
	public Application getApplication() {
		return application;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.tea.agent.be.model.impl.Host#setApplication(com.tibco.tea.agent
	 * .be.model.Application)
	 */
	@Override
	public void setApplication(Application application) {
		this.application = application;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.model.impl.Host#getStatus()
	 */
	@Override
	public String getStatus() {
		return masterHost.getStatus();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.model.impl.Host#setStatus(java.lang.String)
	 */
	@Override
	public void setStatus(String status) {
		// No-op
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
	public ENTITY_TYPE getType() {
		return ENTITY_TYPE.HOST;
	}

	@Override
	public Map<String, Object> getBasicAttributes() {
		Map<String, Object> basicAttributes = new HashMap<>();
		basicAttributes.put(MetricAttribute.CLUSTER, this.getApplication().getClusterName());
		basicAttributes.put(MetricAttribute.HOST, this.getHostName());
		basicAttributes.put(MetricAttribute.HOST_IS_PREDEFINED, isPredefined());
		return basicAttributes;
	}

	@Override
	public ConnectionInfo getConnectionInfo(String connectionType) {
		return null;
	}

	@Override
	public MasterHost getMasterHost() {
		return masterHost;
	}

	@Override
	public void setMasterHost(MasterHost masterHost) {
		this.masterHost = masterHost;
	}

	@Override
	public BE getBEDetailsById(String id) {
		List<BE> beDetails = masterHost.getBE();
		if (null != beDetails && !beDetails.isEmpty()) {
			for (BE be : beDetails) {
				if (null != be && be.getId().equals(id)) {
					return be;
				}
			}
		}
		return null;
	}

	@Override
	public List<BE> getBE() {
		return masterHost.getBE();
	}

}
