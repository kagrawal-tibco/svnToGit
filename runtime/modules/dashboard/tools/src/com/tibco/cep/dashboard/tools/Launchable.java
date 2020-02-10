package com.tibco.cep.dashboard.tools;


public interface Launchable {
	
	public void launch(String[] args) throws IllegalArgumentException, Exception;
	
	public String getArgmentUsage();

}
