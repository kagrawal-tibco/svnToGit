package com.tibco.cep.impl.common.log;

import static com.tibco.cep.common.log.Helper.$logCategory;
import static com.tibco.cep.common.log.Helper.ROOT_LOGGER_NAME;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.tibco.cep.common.exception.LifecycleException;
import com.tibco.cep.common.exception.RecoveryException;
import com.tibco.cep.common.log.LoggerService;
import com.tibco.cep.common.resource.Id;
import com.tibco.cep.common.resource.ResourceProvider;
import com.tibco.cep.impl.common.resource.DefaultId;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;

/*
* Author: Ashwin Jayaprakash / Date: Dec 16, 2009 / Time: 6:02:34 PM
*/

public class LogDelegatorService implements LoggerService {
    protected DefaultId resourceId;

    protected transient com.tibco.cep.kernel.service.logging.LogManager delegateLogManager;

    protected transient LogManager jdkLogManager;

    public LogDelegatorService() {
        this.resourceId = new DefaultId(getClass().getName());
    }

    @Override
    public Id getResourceId() {
        return resourceId;
    }

    @Override
    public void start() throws LifecycleException {
        delegateLogManager = LogManagerFactory.getLogManager();
        jdkLogManager = LogManager.getLogManager();
    }

    private Logger fetchLogger(String loggerName) {
        Logger jdkLogger = jdkLogManager.getLogger(loggerName);

        //Create a new delegate and register that with the JDK logmanager.
        if (jdkLogger == null) {
            com.tibco.cep.kernel.service.logging.Logger delegateLogger =
                    delegateLogManager.getLogger(loggerName);

            jdkLogger = new DelegatingLogger(delegateLogger);

            //Someone else beat us to the punch and created one. Use theirs.
            if (jdkLogManager.addLogger(jdkLogger) == false) {
                jdkLogger = jdkLogManager.getLogger(loggerName);
            }
        }

        if ((jdkLogger instanceof DelegatingLogger) == false) {
            String s = "The delegate logger for [" + loggerName +
                    "] could not be created because there is already a non-delegated JDK logger [" +
                    jdkLogger.getClass().getName() + "] registered";

            //Log error in both loggers.

            jdkLogger.severe(s);

            com.tibco.cep.kernel.service.logging.Logger delegateLogger =
                    delegateLogManager.getLogger(loggerName);
            delegateLogger.log(Level.ERROR, s);
        }

        return jdkLogger;
    }

    @Override
    public Logger getRootLogger() {
        return fetchLogger(ROOT_LOGGER_NAME);
    }

    @Override
    public Logger getLogger(Class clazz) {
        String name = $logCategory(clazz);

        return getLogger(name);
    }

    @Override
    public Logger getLogger(String name) {
        return fetchLogger(name);
    }

    @Override
    public void stop() throws LifecycleException {
    }

    @Override
    public LogDelegatorService recover(ResourceProvider resourceProvider, Object... params)
            throws RecoveryException {
        return this;
    }
}
