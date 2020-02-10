package com.tibco.cep.common.log;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.tibco.cep.service.Service;
import com.tibco.cep.util.annotation.LogCategory;

/*
* Author: Ashwin Jayaprakash / Date: Dec 16, 2009 / Time: 2:42:08 PM
*/

/**
 * This is a simple logger service that does not depend on any other modules.
 * <p/>
 * We just use the JDK {@link Logger} class but the implementations of this class will wrap and
 * delegate calls to which even logger is prevalent.
 * <p/>
 * Stick to the following levels, <b>in order</b>:
 * <p/>
 * {@link Level#SEVERE}
 * <p/>
 * {@link Level#WARNING}
 * <p/>
 * {@link Level#INFO} (default)
 * <p/>
 * {@link Level#FINE} ({@link Level#CONFIG} will be treated as {@link Level#FINE})
 */
public interface LoggerService extends Service {
    Logger getRootLogger();

    /**
     * @param clazz Directly or package annotated with {@link LogCategory}. If there is no
     *              annotation, then it reverts to the FQN of the class.
     * @return
     */
    Logger getLogger(Class clazz);

    Logger getLogger(String name);
}
