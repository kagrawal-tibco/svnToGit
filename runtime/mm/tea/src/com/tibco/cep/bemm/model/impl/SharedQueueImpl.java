/**
 * 
 */
package com.tibco.cep.bemm.model.impl;

import com.tibco.cep.bemm.model.SharedQueue;

/**
 * @author dijadhav
 *
 */
public class SharedQueueImpl implements SharedQueue {
	private long queueSize;
	private long threadCount;

	/* (non-Javadoc)
	 * @see com.tibco.cep.bemm.model.impl.SharedQueue#getQueueSize()
	 */
	@Override
	public long getQueueSize() {
		return queueSize;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.bemm.model.impl.SharedQueue#setQueueSize(long)
	 */
	@Override
	public void setQueueSize(long queueSize) {
		this.queueSize = queueSize;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.bemm.model.impl.SharedQueue#getThreadCount()
	 */
	@Override
	public long getThreadCount() {
		return threadCount;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.bemm.model.impl.SharedQueue#setThreadCount(long)
	 */
	@Override
	public void setThreadCount(long threadCount) {
		this.threadCount = threadCount;
	}

}
