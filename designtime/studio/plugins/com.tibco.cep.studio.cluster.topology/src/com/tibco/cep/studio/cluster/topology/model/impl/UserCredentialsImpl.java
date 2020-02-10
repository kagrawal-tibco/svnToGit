package com.tibco.cep.studio.cluster.topology.model.impl;

import com.tibco.cep.studio.cluster.topology.model.UserCredentials;

public class UserCredentialsImpl extends ClusterTopology{

	public UserCredentialsImpl(UserCredentials userCredentials){
		this.userCredentials = userCredentials;
	}

	public String getUsername() {
		return userCredentials.getUsername();
	}

	public void setUsername(String value) {
		userCredentials.setUsername(value);
		notifyObservers();
	}

	public String getPassword() {
		return userCredentials.getPassword();
	}

	public void setPassword(String value) {
		userCredentials.setPassword(value);
		notifyObservers();
	}

	public UserCredentials getUserCredentials() {
		return userCredentials;
	}
}
