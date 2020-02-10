package com.tibco.cep.studio.cluster.topology.model.impl;

import com.tibco.cep.studio.cluster.topology.model.Cluster;

public class ClusterImpl extends ClusterTopology{

	protected MasterFilesImpl _masterFiles;
	protected RunVersionImpl _runVersion;
	protected DeploymentUnitsImpl _deploymentUnits;
	protected DeploymentMappingsImpl _deploymentMappings;
	
	public ClusterImpl(Cluster cluster){
		this.cluster = cluster;
	}

	public MasterFilesImpl getMasterFiles() {
		return _masterFiles;
	}

	public void setMasterFiles(MasterFilesImpl value) {
		_masterFiles = value;
		cluster.setMasterFiles(value.getMasterFiles());
		notifyObservers();
	}

	public RunVersionImpl getRunVersion() {
		return _runVersion;
	}

	public void setRunVersion(RunVersionImpl value) {
		_runVersion = value;
		cluster.setRunVersion(value.getRunVersion());
		notifyObservers();
	}

	public DeploymentUnitsImpl getDeploymentUnits() {
		return _deploymentUnits;
	}

	public void setDeploymentUnits(DeploymentUnitsImpl value) {
		_deploymentUnits = value;
		cluster.setDeploymentUnits(value.getDeploymentUnits());
		notifyObservers();
	}

	public DeploymentMappingsImpl getDeploymentMappings() {
		return _deploymentMappings;
	}

	public void setDeploymentMappings(DeploymentMappingsImpl value) {
		_deploymentMappings = value;
		cluster.setDeploymentMappings(value.getDeploymentMappings());
		notifyObservers();
	}
	
	public String getName() {
		return cluster.getName();
	}

	public void setName(String value) {
		cluster.setName(value);
		notifyObservers();
	}
	
	public String getProjectCdd() {
		return cluster.getProjectCdd();
	}

	public void setProjectCdd(String value) {
		cluster.setProjectCdd(value);
		notifyObservers();
	}
	
	public Cluster getCluster() {
		return cluster;
	}
}