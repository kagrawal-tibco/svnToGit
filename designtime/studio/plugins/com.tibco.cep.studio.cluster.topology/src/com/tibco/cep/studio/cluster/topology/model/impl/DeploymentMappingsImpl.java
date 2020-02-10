package com.tibco.cep.studio.cluster.topology.model.impl;

import java.util.List;

import com.tibco.cep.studio.cluster.topology.model.DeploymentMapping;
import com.tibco.cep.studio.cluster.topology.model.DeploymentMappings;

public class DeploymentMappingsImpl extends ClusterTopology{

	public DeploymentMappingsImpl(DeploymentMappings deploymentMappings){
		this.deploymentMappings = deploymentMappings;
	}

	public List<DeploymentMapping> getDeploymentMapping() {
		return deploymentMappings.getDeploymentMapping();
	}

	public void addDeploymentMapping(DeploymentMappingImpl deploymentMapping){
		getDeploymentMapping().add(deploymentMapping.getDeploymentMapping());
		notifyObservers();
	}

	public void addDeploymentMapping(int index, DeploymentMappingImpl deploymentMapping){
		getDeploymentMapping().add(index, deploymentMapping.getDeploymentMapping());
		notifyObservers();
	}

	public void removeDeploymentMapping(DeploymentMappingImpl deploymentMapping){
		getDeploymentMapping().remove(deploymentMapping.getDeploymentMapping());
		notifyObservers();
	}

	public void removeDeploymentMapping(int index){
		getDeploymentMapping().remove(index);
		notifyObservers();
	}

	public DeploymentMappings getDeploymentMappings() {
		return deploymentMappings;
	}
}
