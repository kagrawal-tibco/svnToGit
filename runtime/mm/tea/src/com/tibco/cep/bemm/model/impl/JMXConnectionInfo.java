package com.tibco.cep.bemm.model.impl;

import com.tibco.cep.bemm.model.ConnectionInfo;

public class JMXConnectionInfo implements ConnectionInfo {

	private String remoteHost = null;
	private int jmxPort;
	
	@Override
	public void setRemoteHost(String host) {
		this.remoteHost = host;
	}

	@Override
	public String getRemoteHost() {
		return this.remoteHost;
	}

	@Override
	public void setRemotePort(int port) {
		this.jmxPort = port;		
	}

	@Override
	public int getRemotePort() {
		return this.jmxPort;
	}

	
}
