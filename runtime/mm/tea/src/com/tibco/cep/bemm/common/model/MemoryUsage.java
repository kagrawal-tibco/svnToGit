package com.tibco.cep.bemm.common.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tibco.cep.bemm.common.model.impl.MemoryUsageImpl;

@JsonDeserialize(as = MemoryUsageImpl.class)
public interface MemoryUsage {

	/**
	 * @return the init
	 */
	long getInit();

	/**
	 * @return the commited
	 */
	long getCommited();

	/**
	 * @return the max
	 */
	long getMax();

	/**
	 * @return the used
	 */
	long getUsed();

}