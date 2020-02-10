package com.tibco.cep.studio.cluster.topology.model.impl;

import com.tibco.cep.studio.cluster.topology.model.MasterFiles;


public class MasterFilesImpl extends ClusterTopology{

	public MasterFilesImpl(MasterFiles masterFiles){
		this.masterFiles = masterFiles;
	}

	public String getCddMaster() {
		return masterFiles.getCddMaster();
	}

	public void setCddMaster(String value) {
		masterFiles.setCddMaster(value);
		notifyObservers();
	}

	public String getEarMaster() {
		return masterFiles.getEarMaster();
	}

	public void setEarMaster(String value) {
		masterFiles.setEarMaster(value);
		notifyObservers();
	}

	public MasterFiles getMasterFiles() {
		return masterFiles;
	}
}
