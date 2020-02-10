package com.tibco.cep.bemm.common.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = ThreadDetail.class)
public interface ThreadDetail {

	/**
	 * @return the threadCount
	 */
	long getThreadCount();

	/**
	 * @return the deadLogThreadCount
	 */
	long getDeadLogThreadCount();

}