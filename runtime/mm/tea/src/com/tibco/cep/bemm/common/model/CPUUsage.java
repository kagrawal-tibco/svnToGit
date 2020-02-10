package com.tibco.cep.bemm.common.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tibco.cep.bemm.common.model.impl.CPUUsageImpl;

@JsonDeserialize(as = CPUUsageImpl.class)
public interface CPUUsage {

	/**
	 * @return the cpucnt
	 */
	int getCpucnt();

	/**
	 * @param cpucnt
	 *            the cpucnt to set
	 */
	void setCpucnt(int cpucnt);

	/**
	 * @return the currUpTime
	 */
	long getCurrUpTime();

	/**
	 * @param currUpTime
	 *            the currUpTime to set
	 */
	void setCurrUpTime(Long currUpTime);

	/**
	 * @return the currCPUTime
	 */
	long getCurrCPUTime();

	/**
	 * @param currCPUTime
	 *            the currCPUTime to set
	 */
	void setCurrCPUTime(Long currCPUTime);

	/**
	 * @return the cpuUsageInPercent
	 */
	double getCpuUsageInPercent();

	/**
	 * @param cpuUsageInPercent
	 *            the cpuUsageInPercent to set
	 */
	void setCpuUsageInPercent(Double cpuUsageInPercent);

}