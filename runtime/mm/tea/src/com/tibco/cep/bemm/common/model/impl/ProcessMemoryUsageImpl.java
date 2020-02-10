/**
 * 
 */
package com.tibco.cep.bemm.common.model.impl;

import com.tibco.cep.bemm.common.model.ProcessMemoryUsage;

/**
 * @author dijadhav
 *
 */
public class ProcessMemoryUsageImpl implements ProcessMemoryUsage {
	private Long free;
	private Long max;
	private Double percentUsed;
	private Long used;

	/**
	 * @param free
	 * @param max
	 * @param percentUsed
	 * @param used
	 */
	public ProcessMemoryUsageImpl(Long free, Long max, Double percentUsed, Long used) {
		super();
		this.free = free;
		this.max = max;
		this.percentUsed = percentUsed;
		this.used = used;
	}

	/* (non-Javadoc)
	 * @see com.tibco.tea.agent.be.mbean.model.impl.ProcessMemoryUsage#getFree()
	 */
	@Override
	public long getFree() {
		return free;
	}

	/* (non-Javadoc)
	 * @see com.tibco.tea.agent.be.mbean.model.impl.ProcessMemoryUsage#setFree(long)
	 */
	@Override
	public void setFree(Long free) {
		this.free = free;
	}

	/* (non-Javadoc)
	 * @see com.tibco.tea.agent.be.mbean.model.impl.ProcessMemoryUsage#getMax()
	 */
	@Override
	public long getMax() {
		return max;
	}

	/* (non-Javadoc)
	 * @see com.tibco.tea.agent.be.mbean.model.impl.ProcessMemoryUsage#setMax(long)
	 */
	@Override
	public void setMax(Long max) {
		this.max = max;
	}

	/* (non-Javadoc)
	 * @see com.tibco.tea.agent.be.mbean.model.impl.ProcessMemoryUsage#getPercentUsed()
	 */
	@Override
	public double getPercentUsed() {
		return percentUsed;
	}

	/* (non-Javadoc)
	 * @see com.tibco.tea.agent.be.mbean.model.impl.ProcessMemoryUsage#setPercentUsed(long)
	 */
	@Override
	public void setPercentUsed(Double percentUsed) {
		this.percentUsed = percentUsed;
	}

	/* (non-Javadoc)
	 * @see com.tibco.tea.agent.be.mbean.model.impl.ProcessMemoryUsage#getUsed()
	 */
	@Override
	public long getUsed() {
		return used;
	}

	/* (non-Javadoc)
	 * @see com.tibco.tea.agent.be.mbean.model.impl.ProcessMemoryUsage#setUsed(long)
	 */
	@Override
	public void setUsed(Long used) {
		this.used = used;
	}

}
