package com.tibco.rta.common.service;


public interface CustomWorkItemService extends WorkItemService{

	/**
	 * pause the executor service 
	 */
	public void pauseExecutor();
	
	/**
	 * resume the paused executor service
	 */
	public void resumeExecutor();
}
