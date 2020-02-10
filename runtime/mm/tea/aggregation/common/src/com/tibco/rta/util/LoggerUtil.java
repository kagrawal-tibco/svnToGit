package com.tibco.rta.util;

import com.tibco.rta.log.Level;
import com.tibco.rta.log.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 30/11/13
 * Time: 8:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class LoggerUtil {

    /**
     *
     * @param logger
     * @param level
     * @param message
     * @param object
     */
    public static void log(Logger logger,
                           Level level,
                           String message,
                           Object... object) {
        if (logger.isEnabledFor(level)) {
            logger.log(level, message, object);
        }
    }

    /**
     *
     * @param logger
     * @param level
     * @param exception
     * @param message
     * @param object
     */
    public static void log(Logger logger,
                           Level level,
                           Throwable exception,
                           String message,
                           Object... object) {
        if (logger.isEnabledFor(level)) {
            logger.log(level, message, exception, object);
        }
    }
}
