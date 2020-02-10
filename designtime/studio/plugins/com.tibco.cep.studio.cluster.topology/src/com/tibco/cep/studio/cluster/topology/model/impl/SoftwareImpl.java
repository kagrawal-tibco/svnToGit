package com.tibco.cep.studio.cluster.topology.model.impl;

import java.util.List;

import com.tibco.cep.studio.cluster.topology.model.Be;
import com.tibco.cep.studio.cluster.topology.model.Software;

public class SoftwareImpl extends ClusterTopology{

	public SoftwareImpl(Software software){
		this.software = software;
	}

	public List<Be> getBe() {
		return software.getBe();
	}

	public Software getSoftware() {
		return software;
	}

}
