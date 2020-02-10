package com.tibco.cep.studio.cluster.topology.model.impl;

import com.tibco.cep.studio.cluster.topology.model.RunVersion;

public class RunVersionImpl extends ClusterTopology{

	protected BeRuntimeImpl _beRuntime;
	 
	public RunVersionImpl(RunVersion runVersion){
		this.runVersion = runVersion;
	}

	public BeRuntimeImpl getBeRuntime() {
		return _beRuntime;
	}

	public void setBeRuntime(BeRuntimeImpl value) {
		_beRuntime = value;
		runVersion.setBeRuntime(value.getBeRuntime());
		notifyObservers();
	}

	public RunVersion getRunVersion() {
		return runVersion;
	}
}
