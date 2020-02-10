package com.tibco.cep.studio.cluster.topology.model.impl;

import com.tibco.cep.studio.cluster.topology.model.Pstools;

public class PstoolsImpl extends ClusterTopology{

	public PstoolsImpl(Pstools pstools){
		this.pstools = pstools;
	}

	public Pstools getPstools() {
		return pstools;
	}
}
