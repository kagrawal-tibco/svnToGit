package com.tibco.cep.kernel.service.logging;


public interface LogManager {

    /**
     * Gets a Logger by name
     * @param name String name of a Logger.
     * @return Logger
     */
    Logger getLogger(String name);

    /**
     * Gets a Logger by class
     * @param clazz Class to get a Logger for.
     * @return Logger
     */
    Logger getLogger(Class clazz);

    /**
     * Sets the log level for a given logger name or logger name pattern.
     * The pattern can have '*' in it. A '*' represents the root. All the loggers will be impacted.
     * @param logNamePattern The logger name or a pattern.
     * @param level The level to set. Can be the string name or the integer name
     * @see Level#valueOf(Integer)
     * @see Level#valueOf(String)
     */
    void setLevel(String logNamePattern, String level);

    /**
     * Returns all the loggers in the system
     * @return An array of {@link Logger}
     */
    Logger[] getLoggers();

    /**
     * Closes the LogManager. No further attempts to use this object should be made after calling this method.
     */
    void close();

}
