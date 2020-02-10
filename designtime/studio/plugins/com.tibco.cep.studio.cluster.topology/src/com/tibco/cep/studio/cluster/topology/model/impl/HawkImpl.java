package com.tibco.cep.studio.cluster.topology.model.impl;

import com.tibco.cep.studio.cluster.topology.model.Hawk;

public class HawkImpl extends ClusterTopology{

	public HawkImpl(Hawk hawk){
		this.hawk = hawk;
	}
	
	public Hawk getHawk() {
		return hawk;
	}
}
