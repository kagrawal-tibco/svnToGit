/**
 * 
 */
package com.tibco.cep.dashboard.psvr.plugin;

import java.util.Properties;

import com.tibco.cep.dashboard.management.ExceptionHandler;
import com.tibco.cep.dashboard.management.MessageGenerator;
import com.tibco.cep.dashboard.psvr.common.NonFatalException;
import com.tibco.cep.kernel.service.logging.Logger;

/**
 * @author anpatil
 *
 */
public abstract class AbstractHandler {
	
	protected Logger logger;
	
	protected PlugIn plugIn;
	
	protected Properties properties;
	
	protected ExceptionHandler exceptionHandler;
	
	protected MessageGenerator messageGenerator;
	
	protected AbstractHandler(){
	}

	protected abstract void init();
	
	protected abstract void shutdown() throws NonFatalException;
}