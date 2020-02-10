package com.tibco.cep.impl.common.log;

import static com.tibco.cep.common.log.Helper.$logCategory;
import static com.tibco.cep.common.log.Helper.ROOT_LOGGER_NAME;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.exception.RecoveryException;
import com.tibco.cep.common.log.LoggerService;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.impl.common.resource.DefaultId;
import com.tibco.cep.util.Flags;

/*
* Author: Ashwin Jayaprakash / Date: Dec 16, 2009 / Time: 2:48:54 PM
*/

/**
 * Uses the JDK {@link Logger}.
 */
public class DefaultLoggerService implements LoggerService {
    protected DefaultId resourceId;

    public DefaultLoggerService() {
        this.resourceId = new DefaultId(getClass().getName());
    }

    @Override
    public Id getResourceId() {
        return resourceId;
    }

    @Override
    public void start() throws LifecycleException {
        Logger rootLogger = Logger.getLogger(ROOT_LOGGER_NAME);

        if (Flags.DEBUG) {
            rootLogger.setLevel(Level.FINE);

            Handler[] handlers = rootLogger.getHandlers();
            for (Handler handler : handlers) {
                handler.setLevel(Level.FINE);
            }
        }
        else {
            rootLogger.setLevel(Level.INFO);
        }
    }

    @Override
    public Logger getRootLogger() {
        return Logger.getLogger(ROOT_LOGGER_NAME);
    }

    @Override
    public Logger getLogger(Class clazz) {
        String name = $logCategory(clazz);

        return getLogger(name);
    }

    @Override
    public Logger getLogger(String name) {
        return Logger.getLogger(name);
    }

    @Override
    public void stop() throws LifecycleException {
    }

    @Override
    public DefaultLoggerService recover(ResourceProvider resourceProvider, Object... params)
            throws RecoveryException {
        return this;
    }
}
