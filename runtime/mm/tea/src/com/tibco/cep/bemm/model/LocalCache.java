package com.tibco.cep.bemm.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tibco.cep.bemm.model.impl.LocalCacheImpl;

@JsonDeserialize(as = LocalCacheImpl.class)
public interface LocalCache {

	/**
	 * @return the maxSize
	 */
	long getMaxSize();

	/**
	 * @param maxSize
	 *            the maxSize to set
	 */
	void setMaxSize(long maxSize);

	/**
	 * @return the evictionTime
	 */
	long getEvictionTime();

	/**
	 * @param evictionTime
	 *            the evictionTime to set
	 */
	void setEvictionTime(long evictionTime);

}