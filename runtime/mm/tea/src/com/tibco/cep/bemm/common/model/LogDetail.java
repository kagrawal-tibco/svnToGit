package com.tibco.cep.bemm.common.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tibco.cep.bemm.common.model.impl.LogDetailImpl;

@JsonDeserialize(as = LogDetailImpl.class)
public interface LogDetail {

	/**
	 * @return the loggerName
	 */
	String getLoggerName();

	/**
	 * @return the logLevel
	 */
	String getLogLevel();

}