package com.tibco.cep.bemm.common.service.logging;

/**
 * @author vdhumal
 *
 */
public interface Logger {

    public void close();

    /**
     * Gets the current Level of this logger.
     * @return Level of this logger.
     */
    Level getLevel();

    /**
     * Gets the name of this logger.
     * @return String
     */
    String getName();


    /**
     * Checks if a message of the given level would actually be logged by this logger.
     * @param level Level Priority
     * @return boolean true if level is not Level.OFF and level <= getLevel().
     */
    boolean isEnabledFor(Level level);


    /**
     * Logs a message.
     * @param level Level Priority
     * @param msg   String Message
     */
    void log(Level level, String msg);

    /**
     * Logs a message.
     *
     * @param level  Level Priority
     * @param format String  Containing formatting operators
     * @param args   Object... Message object to log.
     */
    void log(Level level, String format, Object... args);


    /**
     * Logs a message.
     *
     * @param level  Level Priority
     * @param format String Containing formatting operators
     * @param thrown Throwable Throwable of the logging request, may be null.
     * @param args   Object... Message object to log
     */
    void log(Level level, String format, Throwable thrown, Object... args);


    /**
     * Logs a message, with associated Throwable information.
     *
     * @param level  Level Priority
     * @param msg    String Message to log
     * @param thrown Throwable Throwable of the logging request, may be null.
     * @.category public-api
     * @since 4.0.0
     */
    void log(Level level, Throwable thrown, String msg);


    /**
     * Logs a message, with associated Throwable information.
     *
     * @param level  Level Priority
     * @param thrown Throwable Throwable of the logging request.
     * @param format String Containing formatting operators
     * @param args   Object... Message object to log
     * @.category public-api
     * @since 4.0.0
     */
    void log(Level level, Throwable thrown, String format, Object... args);


    /**
     * Sets the maximum Level logged by this object.
     *
     * @param level Level Priority
     * @.category public-api
     * @since 4.0.0
     */
    void setLevel(Level level);


}

