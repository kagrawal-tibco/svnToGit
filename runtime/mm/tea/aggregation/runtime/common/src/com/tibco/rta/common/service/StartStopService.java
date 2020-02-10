package com.tibco.rta.common.service;

/**
 * @author bgokhale
 * 
 * This service is a basic reusable component providing start/stop generic methods
 * 
 * 
 */

import java.util.Properties;


public interface StartStopService extends EngineService {
	
	public void init(Properties configuration) throws Exception;
	
	public void start() throws Exception;
	
	public void stop() throws Exception;
	
	public void suspend();
	
	public void resume();

	boolean isStarted();
}
