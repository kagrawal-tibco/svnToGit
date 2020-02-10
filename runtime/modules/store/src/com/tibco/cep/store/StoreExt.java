/**
 * 
 */
package com.tibco.cep.store;

/**
 * @author vpatil
 *
 */
public abstract class StoreExt {
	
	/**
	 * 
	 * @return
	 */
	public abstract String getVersion();
	
	/**
	 * 
	 * @param logLevel
	 * @throws Exception
	 */
	public abstract void setLogLevel(String logLevel) throws Exception;
	
	/**
	 * 
	 * @param filePrefix
	 * @param maxFileSize
	 * @param maxFiles
	 * @throws Exception
	 */
	public abstract void setLogFiles(String filePrefix, long maxFileSize, int maxFiles) throws Exception;
	
	/**
	 * 
	 * @param logHandler
	 * @throws Exception
	 */
	public abstract void setLogHandler(Object logHandler) throws Exception;
	
}
