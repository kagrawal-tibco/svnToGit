package com.tibco.rta.service.metric;

public interface AssetBasedParallelMetricServiceImplMBean {

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
	public double getAvgFactProcessTimeInMilliSeconds();
	
	/**
	 * 
	 * Returns Average fact saving time
	 * 
	 * @return
	 */
	//public double getAvgFactSaveTimeInMilliSeconds();
	
	/**
	 * 
	 * Returns TPS in facts per second 
	 * 
	 * @return
	 */
	public double getFactPerSecond();
	
	/**
	 * Returns the average batch size for batches of compute jobs.
	 * 
	 * @return
	 */
	public double getAvgMetricJobsBatchSize();
	
	/**
	 * reset the metrics based metrics, required if you want recent averages unlike averages from start time.
	 * 
	 */
	//public void resetMetricMetrics();
	
	/**
	 * reset the metrics based metrics cumulatively, required if you want recent averages unlike averages from start time.
	 * 
	 */
	
	public void resetMetricMetricsCumulative();
	
	/**
	 * Average facts batch size sent by client.
	 * 
	 * @return
	 */
	double getAvgFactBatchSize();
	
	/**
	 * Average time spent in the persistence layer to save a batch of facts.
	 * 
	 * 
	 * @return time taken in milliseconds.
	 */
	double getAvgFactBatchSaveTimeInMilliSeconds();
	
	/**
	 * reset the facts metrics, required if you want recent averages unlike averages from start time.
	 * 
	 */
	//void resetFactsMetrics();
	
	/**
	 * reset the facts metrics cumulatively, required if you want recent averages unlike averages from start time.
	 * 
	 */
	
	void resetFactsMetricsCumulative();
	/**
	 * Get total transactions performed since last reset. One transaction is 1 batch job
	 * 
	 * @return
	 */
	
	long getTotalTransactions();
	
	/**
	 * Total time taken in the persistence layer for saving metrics.
	 * 
	 * @return
	 */
	long getTotalTxnSaveTimeInMilliSeconds();
	
	/**
	 * Average time for saving a transaction list
	 * 
	 * @return
	 */
	double getAvgTxnSaveTimeInMilliSeconds();
	
	/**
	 * Average size of a transaction list.
	 * 
	 * @return
	 */
	double getAvgTxnSize();
	
	/**
	 * How much time is spent in Worker.call method on an average. This combined with getAvgFactSaveTimeInMilis will tell %time spent in persistence layer
	 * 
	 * @return
	 */
	double getAvgWorkerThreadExecTimeInMilliSeconds();
	
	/**
	 * How much time is spent in MetricThread.call method on an average. This combined with getAvgTxnSaveTime will tell %time spent in persistence layer.
	 * @return
	 */
	double getAvgMetricThreadExecTimeInMilliSeconds();

	/**
	 * One transaction list contains several metric nodes. this return all metric node counts (including inserts/updates/deletes)
	 * 
	 * @return
	 */
	long getTotalTransactionListSize();
	/**
	 * transaction save percent time as percent of average transaction save time to average metric thread execution time
	 * 
	 * @return
	 */
	double getTxnSavePercentTime();
	/**
	 * message throughput based on timer
	 * 
	 * @return
	 */
	double getMessageTps();
	/**
	 * facts throughput based on timer
	 * 
	 * @return
	 */
	double getFactTps();
}
