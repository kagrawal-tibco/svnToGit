package com.tibco.cep.studio.cluster.topology.model.impl;

import com.tibco.cep.studio.cluster.topology.model.Ssh;

public class SshImpl extends ClusterTopology{

	public SshImpl(Ssh ssh){
		this.ssh = ssh;
	}
	public String getPort() {
		return ssh.getPort();
	}

	public void setPort(String value) {
		ssh.setPort(value);
		notifyObservers();
	}
	
	public Ssh getSsh() {
		return ssh;
	}
}
