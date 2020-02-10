package com.tibco.rta.common.service.impl;

import javax.management.openmbean.TabularDataSupport;

public interface StickyThreadPoolImplMBean {
	
	/**
	 *
	 * @return details of each thread pool executor within a thread pool
	 * 
	 */
	public TabularDataSupport getThreadDetails();
	
	/**
	 * 
	 * @return total of completed tasks in whole thread pool
	 * 
	 */
	public long getCompletedTasks();
	
	/**
	 * 
	 * @return total pending tasks in queues
	 * 
	 */
	public long getCurrentQueueSize();
	
	/**
	 * 
	 * @return configured queue capacity of thread pool 
	 * 
	 */
	public long getQueueCapacity();
}
