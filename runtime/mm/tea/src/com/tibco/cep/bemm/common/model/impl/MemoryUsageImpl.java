/**
 * 
 */
package com.tibco.cep.bemm.common.model.impl;

import com.tibco.cep.bemm.common.model.MemoryUsage;

/**
 * @author dijadhav
 *
 */
public class MemoryUsageImpl implements MemoryUsage {
	private long init;
	private long commited;
	private long max;
	private long used;

	/**
	 * @param init
	 * @param commited
	 * @param max
	 * @param used
	 */
	public MemoryUsageImpl(long init, long commited, long max, long used) {
		super();
		this.init = init;
		this.commited = commited;
		this.max = max;
		this.used = used;
	}

	/* (non-Javadoc)
	 * @see com.tibco.tea.agent.be.mbean.model.impl.MemeoryUsage#getInit()
	 */
	@Override
	public long getInit() {
		return init;
	}

	/* (non-Javadoc)
	 * @see com.tibco.tea.agent.be.mbean.model.impl.MemeoryUsage#getCommited()
	 */
	@Override
	public long getCommited() {
		return commited;
	}

	/* (non-Javadoc)
	 * @see com.tibco.tea.agent.be.mbean.model.impl.MemeoryUsage#getMax()
	 */
	@Override
	public long getMax() {
		return max;
	}

	/* (non-Javadoc)
	 * @see com.tibco.tea.agent.be.mbean.model.impl.MemeoryUsage#getUsed()
	 */
	@Override
	public long getUsed() {
		return used;
	}

}
