package com.tibco.rta.log;

/**
 * Clients may implement this interface
 * to allow SPM to log in a manner consistent
 * with their needs.
 * <p>
 *     The default implementation uses log4j
 * </p>
 * 
 */

public interface Logger {

	/**
	 * Close the logger.
	 */
	void close();

	/**
	 * Get the name of the logger.
	 * @return name of the logger.
	 */
	String getName();

	/**
	 * Returns the stauts of the given log level.
	 * @param level the log level.
	 * @return true if the level is enabled, false otherwise.
	 */
	boolean isEnabledFor(Level level);

	/**
	 * Logs a message at a given level.
	 * @param level the level to use for logging.
	 * @param msg the message to log.
	 */
	void log(Level level, String msg);

	/**
	 * Logs a message at a given level with a format.
	 * @param level the level to log.
	 * @param format the log message format to use.
	 * @param args arguments to pass to the formatter.
	 */
	void log(Level level, String format, Object... args);

	/**
	 * Logs a {@code Throwable} 
	 * @param level the level to log.
	 * @param format the log message format to use.
	 * @param thrown the exception to log
	 * @param args arguments to pass to the formatter.
	 */
	void log(Level level, String format, Throwable thrown, Object... args);

	/**
	 * Sets the logging level.
	 * @param level the level to set to.
	 */
	void setLevel(Level level);

    /**
     *
     * @return
     */
    Level getLevel();

}
