/**
 * 
 */
package com.tibco.cep.bemm.runtime.service.management.process;

import javax.management.openmbean.TabularDataSupport;

/**
 * @author dijadhav
 *
 */
public interface BETeaAgentMBean {
	/**
	 * list of all the logger names and their levels
	 */
	TabularDataSupport getLoggerNamesWithLevels();


	/**
	 * Set the log level for a given logger
	 */
	void setLogLevel(String logger, String level);
}
