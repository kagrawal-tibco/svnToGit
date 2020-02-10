package com.tibco.cep.bemm.management.service.impl;

/**
 * Download log file entry
 * 
 * @author dijadhav
 *
 */
public class BETeaDownloadLogEntry {
	private String logfilename;
	private String logFilePath;
	private int errorCode;
	public BETeaDownloadLogEntry() {
	}

	/**
	 * @return the logfilename
	 */
	public String getLogfilename() {
		return logfilename;
	}

	/**
	 * @param logfilename
	 *            the logfilename to set
	 */
	public void setLogfilename(String logfilename) {
		this.logfilename = logfilename;
	}

	/**
	 * @return the logFilePath
	 */
	public String getLogFilePath() {
		return logFilePath;
	}

	/**
	 * @param logFilePath the logFilePath to set
	 */
	public void setLogFilePath(String logFilePath) {
		this.logFilePath = logFilePath;
	}

	/**
	 * @return the errorCode
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	

}