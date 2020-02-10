package com.tibco.cep.studio.cluster.topology.model.impl;

import com.tibco.cep.studio.cluster.topology.model.DeploymentMapping;

public class DeploymentMappingImpl extends ClusterTopology{
 
	public DeploymentMappingImpl(DeploymentMapping deploymentMapping){
		this.deploymentMapping = deploymentMapping;
	}
	
	public Object getDeploymentUnitRef() {
		return deploymentMapping.getDeploymentUnitRef();
	}

	public void setDeploymentUnitRef(Object value) {
		deploymentMapping.setDeploymentUnitRef(value);
		notifyObservers();
	}
	
	public Object getHostRef() {
		return deploymentMapping.getHostRef();
	}

	public void setHostRef(Object value) {
		deploymentMapping.setHostRef(value);
		notifyObservers();
	}
	
	public DeploymentMapping getDeploymentMapping() {
		return deploymentMapping;
	}
}