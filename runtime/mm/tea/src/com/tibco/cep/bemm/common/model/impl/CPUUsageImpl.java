package com.tibco.cep.bemm.common.model.impl;

import com.tibco.cep.bemm.common.model.CPUUsage;

/**
 * This model is used to store the CPUUsage details
 * 
 * @author dijadhav
 *
 */
public class CPUUsageImpl implements CPUUsage {
	private int cpucnt;
	private Long currUpTime;
	private Long currCPUTime;
	private Double cpuUsageInPercent;

	/**
	 * @param cpucnt
	 * @param currUpTime
	 * @param currCPUTime
	 * @param cpuUsageInPercent
	 */
	public CPUUsageImpl(int cpucnt, Long currUpTime, Long currCPUTime, Double cpuUsageInPercent) {
		super();
		this.cpucnt = cpucnt;
		this.currUpTime = currUpTime;
		this.currCPUTime = currCPUTime;
		this.cpuUsageInPercent = cpuUsageInPercent;
	}

	/* (non-Javadoc)
	 * @see com.tibco.tea.agent.be.mbean.model.impl.CPUUsage#getCpucnt()
	 */
	@Override
	public int getCpucnt() {
		return cpucnt;
	}

	/* (non-Javadoc)
	 * @see com.tibco.tea.agent.be.mbean.model.impl.CPUUsage#setCpucnt(int)
	 */
	@Override
	public void setCpucnt(int cpucnt) {
		this.cpucnt = cpucnt;
	}

	/* (non-Javadoc)
	 * @see com.tibco.tea.agent.be.mbean.model.impl.CPUUsage#getCurrUpTime()
	 */
	@Override
	public long getCurrUpTime() {
		return currUpTime;
	}

	/* (non-Javadoc)
	 * @see com.tibco.tea.agent.be.mbean.model.impl.CPUUsage#setCurrUpTime(long)
	 */
	@Override
	public void setCurrUpTime(Long currUpTime) {
		this.currUpTime = currUpTime;
	}

	/* (non-Javadoc)
	 * @see com.tibco.tea.agent.be.mbean.model.impl.CPUUsage#getCurrCPUTime()
	 */
	@Override
	public long getCurrCPUTime() {
		return currCPUTime;
	}

	/* (non-Javadoc)
	 * @see com.tibco.tea.agent.be.mbean.model.impl.CPUUsage#setCurrCPUTime(long)
	 */
	@Override
	public void setCurrCPUTime(Long currCPUTime) {
		this.currCPUTime = currCPUTime;
	}

	/* (non-Javadoc)
	 * @see com.tibco.tea.agent.be.mbean.model.impl.CPUUsage#getCpuUsageInPercent()
	 */
	@Override
	public double getCpuUsageInPercent() {
		return cpuUsageInPercent;
	}

	/* (non-Javadoc)
	 * @see com.tibco.tea.agent.be.mbean.model.impl.CPUUsage#setCpuUsageInPercent(double)
	 */
	@Override
	public void setCpuUsageInPercent(Double cpuUsageInPercent) {
		this.cpuUsageInPercent = cpuUsageInPercent;
	}

}
