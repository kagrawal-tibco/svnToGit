package com.tibco.cep.dashboard.management;


public interface Client {
	
	public STATE getStatus();
	
	public void cleanup() throws ManagementException;
	

}
