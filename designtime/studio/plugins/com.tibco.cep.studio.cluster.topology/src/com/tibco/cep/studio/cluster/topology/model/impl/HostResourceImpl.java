package com.tibco.cep.studio.cluster.topology.model.impl;

import com.tibco.cep.studio.cluster.topology.model.HostResource;


public class HostResourceImpl extends ClusterTopology implements Cloneable{

	protected UserCredentialsImpl _userCredentials;
	protected StartPuMethodImpl _startPuMethod;
	protected SoftwareImpl _software;


	public HostResourceImpl(HostResource hostResource){
		this.hostResource = hostResource;
	}

	public String getHostname() {
		return hostResource.getHostname();
	}

	public void setHostname(String value) {
		hostResource.setHostname(value);
		notifyObservers();
	}

	public String getIp() {
		return hostResource.getIp();
	}

	public void setIp(String value) {
		hostResource.setIp(value);
		notifyObservers();
	}

	public UserCredentialsImpl getUserCredentials() {
		return _userCredentials;
	}

	public void setUserCredentials(UserCredentialsImpl value) {
		_userCredentials = value;
		hostResource.setUserCredentials(value.getUserCredentials());
		notifyObservers();
	}

	public String getOsType() {
		return hostResource.getOsType();
	}

	public void setOsType(String value) {
		hostResource.setOsType(value);
		notifyObservers();
	}

	public SoftwareImpl getSoftware() {
		_software = new SoftwareImpl(hostResource.getSoftware());
		return _software;
	}

	public void setSoftware(SoftwareImpl value) {
		_software = value; 
		hostResource.setSoftware(value.getSoftware());
		notifyObservers();
	}

	public StartPuMethodImpl getStartPuMethod() {
		return _startPuMethod;
	}

	public void setStartPuMethod(StartPuMethodImpl value) {
		_startPuMethod = value;
		hostResource.setStartPuMethod(value.getStartPuMethod());
		notifyObservers();
	}

	public String getId() {
		return hostResource.getId();
	}

	public void setId(String value) {
		hostResource.setId(value);
		notifyObservers();
	}

	public HostResource getHostResource() {
		return hostResource;
	}

}
