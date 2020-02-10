package com.tibco.cep.bemm.model;

public interface ConnectionInfo {

	void setRemoteHost(String host);
	
	String getRemoteHost();
	
	void setRemotePort(int port);
	
	int getRemotePort();	
}
