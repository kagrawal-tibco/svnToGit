package com.tibco.cep.studio.cluster.topology.model.impl;

import com.tibco.cep.studio.cluster.topology.model.DeployedFiles;
import com.tibco.cep.studio.cluster.topology.model.DeploymentUnit;
import com.tibco.cep.studio.cluster.topology.model.ProcessingUnitsConfig;


public class DeploymentUnitImpl extends ClusterTopology implements Cloneable{

	protected DeployedFilesImpl deployedFiles;
	protected ProcessingUnitsConfigImpl processingUnitsConfig;
   
   
	public DeploymentUnitImpl(DeploymentUnit deploymentUnit){
		this.deploymentUnit = deploymentUnit;
	}

	public DeployedFiles getDeployedFiles() {
		return deploymentUnit.getDeployedFiles();
	}

	public void setDeployedFiles(DeployedFilesImpl value) {
		deployedFiles = value;
		deploymentUnit.setDeployedFiles(value.getDeployedFiles());
		notifyObservers();
	}

	public ProcessingUnitsConfig getProcessingUnitsConfig() {
		return deploymentUnit.getProcessingUnitsConfig();
	}

	public void setProcessingUnitsConfig(ProcessingUnitsConfigImpl value) {
		processingUnitsConfig = value;
		deploymentUnit.setProcessingUnitsConfig(value.getProcessingUnitsConfig());
		notifyObservers();
	}

	public String getName() {
		return deploymentUnit.getName();
	}

	public void setName(String value) {
		deploymentUnit.setName(value);
		notifyObservers();
	}
	
	public String getId() {
		return deploymentUnit.getId();
	}

	public void setId(String value) {
		deploymentUnit.setId(value);
		notifyObservers();
	}
	
	public DeploymentUnit getDeploymentUnit() {
		return deploymentUnit;
	}

	}