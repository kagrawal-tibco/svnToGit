package com.tibco.cep.bemm.common.service;

import java.util.Properties;
import java.util.concurrent.Future;

import com.tibco.rta.common.service.WorkItem;

/**
 * This service is used to manage the thread pool
 * 
 * @author dijadhav
 *
 */
public interface BETeaThreadPoolService extends StartStopService{
	/**
	 * Initialize the service
	 * 
	 * @param configuration
	 * @throws Exception
	 */
	void init(Properties configuration) throws Exception;

	/**
	 * This method is used to process the job
	 * 
	 * @param job
	 * @return
	 */
	Future<WorkItem> processJob(WorkItem job);
	
}
