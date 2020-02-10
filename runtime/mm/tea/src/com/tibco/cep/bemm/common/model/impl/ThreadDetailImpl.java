/**
 * 
 */
package com.tibco.cep.bemm.common.model.impl;

import com.tibco.cep.bemm.common.model.ThreadDetail;

/**
 * @author dijadhav
 *
 */
public class ThreadDetailImpl implements ThreadDetail {
	private long threadCount;
	private long deadLogThreadCount;

	/**
	 * @param threadCount
	 * @param deadLogThreadCount
	 */
	public ThreadDetailImpl(long threadCount, long deadLogThreadCount) {
		super();
		this.threadCount = threadCount;
		this.deadLogThreadCount = deadLogThreadCount;
	}

	/* (non-Javadoc)
	 * @see com.tibco.tea.agent.be.mbean.model.impl.ThreadDetail#getThreadCount()
	 */
	@Override
	public long getThreadCount() {
		return threadCount;
	}

	/* (non-Javadoc)
	 * @see com.tibco.tea.agent.be.mbean.model.impl.ThreadDetail#getDeadLogThreadCount()
	 */
	@Override
	public long getDeadLogThreadCount() {
		return deadLogThreadCount;
	}

}
