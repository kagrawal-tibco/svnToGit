package com.tibco.cep.bemm.common.service.logging;

import com.tibco.cep.bemm.common.service.logging.impl.DefaultLogManager;
import com.tibco.cep.runtime.util.SystemProperty;


/**
 * A factory to get a log manager.
 */
public class LogManagerFactory {

    private static LogManager logManager;

    public static void setLogManager(LogManager logManager) {
        LogManagerFactory.logManager = logManager;
    }

    /**
     * Return supplied instance of log manager or default one
     * @return
     */
    public static LogManager getLogManager() {
    	init(null);
        return logManager;
    }

    /**
     * Perform initialization once.
     * @param logManagerClassName
     */
    @SuppressWarnings("unchecked")
	public static void init(String logManagerClassName) {
        //If initialized do not initialize again
        if (logManager == null) {
        	//Disable the BE trace logger initialize
        	System.setProperty(SystemProperty.TRACE_ENABLED.getPropertyName(), "false");
        	if (logManagerClassName == null || logManagerClassName.length() < 1) {
                logManager = new DefaultLogManager();
            } else {
                try {
                    ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();
                    Class<? extends LogManager> clazz = (Class<? extends LogManager>) currentClassLoader.loadClass(logManagerClassName);
                    logManager = clazz.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Logger ClassName: " + logManagerClassName + " not found. Initializing default log4j based logger.");
                    logManager = new DefaultLogManager();
                }
            }
        }
    }
}
