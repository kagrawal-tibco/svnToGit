package com.tibco.cep.studio.cluster.topology.model.impl;

import java.util.List;

import com.tibco.cep.studio.cluster.topology.model.DeploymentUnit;
import com.tibco.cep.studio.cluster.topology.model.DeploymentUnits;

public class DeploymentUnitsImpl extends ClusterTopology{

	public DeploymentUnitsImpl(DeploymentUnits deploymentUnits){
		this.deploymentUnits = deploymentUnits;
	}

	public List<DeploymentUnit> getDeploymentUnit() {
		return deploymentUnits.getDeploymentUnit();
	}
	
	public void addDeploymentUnit(DeploymentUnitImpl deploymentUnit){
		getDeploymentUnit().add(deploymentUnit.getDeploymentUnit());
		notifyObservers();
	}

	public void addDeploymentUnit(int index, DeploymentUnitImpl deploymentUnit){
		getDeploymentUnit().add(index, deploymentUnit.getDeploymentUnit());
		notifyObservers();
	}

	public void removeDeploymentUnit(DeploymentUnitImpl machine){
		getDeploymentUnit().remove(machine.getDeploymentUnit());
		notifyObservers();
	}

	public void removeDeploymentUnit(int index){
		getDeploymentUnit().remove(index);
		notifyObservers();
	}

	public DeploymentUnits getDeploymentUnits() {
		return deploymentUnits;
	}
}