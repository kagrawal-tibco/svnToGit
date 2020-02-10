package com.tibco.cep.studio.cluster.topology.model.impl;

import com.tibco.cep.studio.cluster.topology.model.BeRuntime;

public class BeRuntimeImpl extends ClusterTopology{
 
	public BeRuntimeImpl(BeRuntime beRuntime){
		this.beRuntime = beRuntime;
	}

	public String getVersion() {
		return beRuntime.getVersion();
	}
	
	public void setVersion(String value) {
		beRuntime.setVersion(value);
		notifyObservers();
	}

	public BeRuntime getBeRuntime() {
		return beRuntime;
	}
}
