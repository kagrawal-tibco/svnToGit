package com.tibco.cep.bemm.common.service.logging.impl;

import java.util.concurrent.ConcurrentHashMap;

import com.tibco.cep.bemm.common.service.logging.LogManager;
import com.tibco.cep.bemm.common.service.logging.Logger;


/**
 * @author vdhumal
 *
 */
public class DefaultLogManager implements LogManager {

    private static final ConcurrentHashMap<String, Logger> LOGGER_REGISTRY = new ConcurrentHashMap<String, Logger>();

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Logger> T getLogger(String name) {
        //Check if registry has it
        Logger logger = LOGGER_REGISTRY.get(name);
        if (logger == null) {
            final Logger newLogger;
            try {
                newLogger = new LoggerImpl(name);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            logger = LOGGER_REGISTRY.putIfAbsent(name, newLogger);
            if (null == logger) {
                logger = LOGGER_REGISTRY.get(name);
            }
        }
        return (T)logger;
    }

    @Override
    public <T extends Logger> T getLogger(Class<?> clazz) {
        String className = clazz.getName();
        return getLogger(className);
    }

    @Override
    public Logger[] getLoggers() {
        return LOGGER_REGISTRY.values().toArray(new Logger[LOGGER_REGISTRY.size()]);
    }

}
