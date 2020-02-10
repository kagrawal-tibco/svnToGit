package com.tibco.cep.studio.cluster.topology.model.impl;

import com.tibco.cep.studio.cluster.topology.model.DeployedFiles;

public class DeployedFilesImpl extends ClusterTopology{

	public DeployedFilesImpl(DeployedFiles deployedFiles){
		this.deployedFiles = deployedFiles;
	}

	public String getCddMaster() {
		return deployedFiles.getCddDeployed();
	}

	public void setCddDeployed(String value) {
		deployedFiles.setCddDeployed(value);
		notifyObservers();
	}

	public String getEarDeployed() {
		return deployedFiles.getEarDeployed();
	}

	public void setEarDeployed(String value) {
		deployedFiles.setEarDeployed(value);
		notifyObservers();
	}

	public DeployedFiles getDeployedFiles() {
		return deployedFiles;
	}
}
