package com.tibco.rta.log;

import java.lang.reflect.Constructor;
import java.util.concurrent.ConcurrentHashMap;

import com.tibco.rta.log.impl.DefaultLoggerImpl;

/**
 * 
 * Factory class to get a logger.
 *
 */

public class LoggerFactory {

	/**
	 * The logger instance.
	 */
    public static final LoggerFactory INSTANCE = new LoggerFactory();

    private static final ConcurrentHashMap<String, Logger> logRegistry = new ConcurrentHashMap<String, Logger>();

    private static Class<? extends Logger> loggerClass = null;

    private LoggerFactory() {

    }

    /**
     * Initialize the logger with a class name.
     * 
     * @param loggerClassName the class name to initialize the logger with.
     */
    public static void init(String loggerClassName) {
        if (loggerClassName == null || loggerClassName.length() < 1) {
            loggerClass = DefaultLoggerImpl.class;
        } else {
            try {
                loggerClass = (Class<? extends Logger>) Class.forName(loggerClassName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("Logger ClassName: " + loggerClassName + " not found. Initializing default log4j based logger.");
            }
        }
    }

    /**
     * Get a logger.
     * @param name name of the logger to get.
     * @return the logger.
     */
    public static Logger getLogger(String name) {
        //Check if registry has it
    	if(loggerClass == null) {
    		init(null);
    	}
        Logger logger = logRegistry.get(name);
        if (logger == null) {
            final Logger newLogger;
            try {
                Constructor loggerConstructor = loggerClass.getConstructor(String.class);
                newLogger = (Logger) loggerConstructor.newInstance(name);
            } catch (Exception e) {
            	e.printStackTrace();
                throw new RuntimeException(e);
            }
            logger = logRegistry.putIfAbsent(name, newLogger);
            if (null == logger) {
                logger = logRegistry.get(name);
            }
        }
        return logger;
    }

    /**
     * Get a logger
     * @param clazz the class for which a logger is desired.
     * @return the logger.
     */
    public static Logger getLogger(Class<?> clazz) {
        String className = clazz.getName();
        return getLogger(className);
    }
}
