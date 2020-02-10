package com.tibco.rta.service.metric;

public interface ParallelMetricServiceImplMBean {

	/**
	 * 
	 * Returns total facts processed by engine till now
	 * 
	 * @return
	 */
	public long getTotalFactsProcessed();
	
	/**
	 * 
	 * Returns Average Trip Time of a fact within SPM server
	 * 
	 * @return
	 */
	public double getAvgFactProcessTimeInMilis();
	
	/**
	 * 
	 * Returns Average fact saving time
	 * 
	 * @return
	 */
	public double getAvgFactSaveTimeInMilis();
	
	/**
	 * 
	 * Returns TPS in facts per second 
	 * 
	 * @return
	 */
	public double getFactPerSecond();
}
