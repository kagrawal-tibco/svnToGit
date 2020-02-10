package com.tibco.cep.studio.cluster.topology.model.impl;

import com.tibco.cep.studio.cluster.topology.model.DeploymentUnit;
import com.tibco.cep.studio.cluster.topology.model.ProcessingUnitConfig;


public class ProcessingUnitConfigImpl extends ClusterTopology implements Cloneable{

	
	public ProcessingUnitConfigImpl(ProcessingUnitConfig processingUnitConfig, DeploymentUnitImpl duImpl){
		this.processingUnitConfig = processingUnitConfig;
		this.deploymentUnitImpl=duImpl;
	}


	public DeploymentUnitImpl getDeploymentUnitImpl() {
		return this.deploymentUnitImpl;
	}
	
	public DeploymentUnit getDeploymentUnit() {
		return this.deploymentUnit;
	}
	
	public String getId() {
		return processingUnitConfig.getId();
	}

	public void setId(String value) {
		processingUnitConfig.setId(value);
		notifyObservers();
	}

	public boolean isUseAsEngineName() {
		return processingUnitConfig.isUseAsEngineName();
	}

	public void setUseAsEngineName(Boolean value) {
		processingUnitConfig.setUseAsEngineName(value);
		notifyObservers();
	}

	public String getPuid() {
		return processingUnitConfig.getPuid();
	}

	public void setPuid(String value) {
		processingUnitConfig.setPuid(value);
		notifyObservers();
	}
	
	

	public String getJmxport() {
		return processingUnitConfig.getJmxport();
	}

	public void setJmxport(String value) {
		processingUnitConfig.setJmxport(value);
		notifyObservers();
	}

	public ProcessingUnitConfig getProcessingUnitConfig() {
		return processingUnitConfig;
	}
}
