package com.tibco.cep.bemm.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tibco.cep.bemm.model.impl.SharedQueueImpl;

@JsonDeserialize(as = SharedQueueImpl.class)
public interface SharedQueue {

	/**
	 * @return the queueSize
	 */
	long getQueueSize();

	/**
	 * @param queueSize
	 *            the queueSize to set
	 */
	void setQueueSize(long queueSize);

	/**
	 * @return the threadCount
	 */
	long getThreadCount();

	/**
	 * @param threadCount
	 *            the threadCount to set
	 */
	void setThreadCount(long threadCount);

}