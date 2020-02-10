package com.tibco.cep.bemm.common.service.logging;

import java.util.List;

/**
 * Basic log manager which clients of SPM API may implement
 * to return instances of {@link Logger} interface.
 */
public interface LogManager {

    /**
     * Get a logger 
     * @param name name of the logger to get.
     * @param <T>
     * @return the logger
     */
    <T extends Logger> T getLogger(String name);

    /**
     * Get a logger by class name
     * @param clazz the class name to get a logger. 
     * @param <T>
     * @return the logger.
     */
    <T extends Logger> T getLogger(Class<?> clazz);

    /**
     * Get all logger names managed by this log manager.
     * @return
     */
    Logger[] getLoggers();
}
