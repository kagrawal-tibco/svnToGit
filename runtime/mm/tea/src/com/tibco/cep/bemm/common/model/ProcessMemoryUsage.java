package com.tibco.cep.bemm.common.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tibco.cep.bemm.common.model.impl.ProcessMemoryUsageImpl;

@JsonDeserialize(as = ProcessMemoryUsageImpl.class)
public interface ProcessMemoryUsage {

	/**
	 * @return the free
	 */
	long getFree();

	/**
	 * @param free
	 *            the free to set
	 */
	void setFree(Long free);

	/**
	 * @return the max
	 */
	long getMax();

	/**
	 * @param max
	 *            the max to set
	 */
	void setMax(Long max);

	/**
	 * @return the percentUsed
	 */
	double getPercentUsed();

	/**
	 * @param percentUsed
	 *            the percentUsed to set
	 */
	void setPercentUsed(Double percentUsed);

	/**
	 * @return the used
	 */
	long getUsed();

	/**
	 * @param used
	 *            the used to set
	 */
	void setUsed(Long used);

}