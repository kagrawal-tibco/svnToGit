/**
 * 
 */
package com.tibco.cep.bemm.common.model.impl;

import com.tibco.cep.bemm.common.model.LogDetail;

/**
 * @author dijadhav
 *
 */
public class LogDetailImpl implements LogDetail {
	private String loggerName;
	private String logLevel;

	/**
	 * @param loggerName
	 * @param logLevel
	 */
	public LogDetailImpl(String loggerName, String logLevel) {
		super();
		this.loggerName = loggerName;
		this.logLevel = logLevel;
	}

	/* (non-Javadoc)
	 * @see com.tibco.tea.agent.be.mbean.model.impl.LogDetail#getLoggerName()
	 */
	@Override
	public String getLoggerName() {
		return loggerName;
	}

	/* (non-Javadoc)
	 * @see com.tibco.tea.agent.be.mbean.model.impl.LogDetail#getLogLevel()
	 */
	@Override
	public String getLogLevel() {
		return logLevel;
	}

}
