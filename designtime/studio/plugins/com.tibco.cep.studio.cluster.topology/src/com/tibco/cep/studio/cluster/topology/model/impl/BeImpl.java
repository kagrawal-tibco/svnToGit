package com.tibco.cep.studio.cluster.topology.model.impl;

import com.tibco.cep.studio.cluster.topology.model.Be;

public class BeImpl extends ClusterTopology{

	public BeImpl(Be be){
		this.be = be;
	}

	public String getTra() {
		return be.getTra();
	}

	public void setTra(String value) {
		be.setTra(value);
		notifyObservers();
	}

	public String getHome() {
		return be.getHome();
	}

	public void setHome(String value) {
		be.setHome(value);
		notifyObservers();
	}

	public String getVersion() {
		return be.getVersion();
	}

	public void setVersion(String value) {
		be.setVersion(value);
		notifyObservers();
	}

	public Be getBe() {
		return be;
	}
}
