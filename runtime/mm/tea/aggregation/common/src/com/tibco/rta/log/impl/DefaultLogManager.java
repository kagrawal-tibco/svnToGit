package com.tibco.rta.log.impl;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.tibco.cep.bemm.common.service.BEMMServiceProviderManager;
import com.tibco.rta.common.ConfigProperty;
import com.tibco.rta.log.LogConfigurator;
import com.tibco.rta.log.LogManager;
import com.tibco.rta.log.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 8/2/13
 * Time: 10:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class DefaultLogManager implements LogManager {

    public DefaultLogManager() {
        try {
            registerMBean();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private static final ConcurrentHashMap<String, Logger> LOGGER_REGISTRY = new ConcurrentHashMap<String, Logger>();

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Logger> T getLogger(String name) {
        //Check if registry has it
        Logger logger = LOGGER_REGISTRY.get(name);
        if (logger == null) {
            final Logger newLogger;
            try {
                newLogger = new DefaultLoggerImpl(name);
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
    public List<String> getLoggers() {
        Set<Map.Entry<String, Logger>> entrySet = LOGGER_REGISTRY.entrySet();
        List<String> loggers = new ArrayList<String>(entrySet.size());

        for (Map.Entry<String, Logger> entry : entrySet) {
            loggers.add(entry.getKey());
        }
        return loggers;
    }

    private void registerMBean() throws Exception {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        String mbeanPrefix = (String) ConfigProperty.BE_TEA_AGENT_SERVICE_MBEANS_PREFIX.getValue(BEMMServiceProviderManager.getInstance().getConfiguration());
        ObjectName name = new ObjectName(mbeanPrefix + ".logging:type=Logger");
        if (!mbs.isRegistered(name)) {
            mbs.registerMBean(new LogConfigurator(), name);
        }
    }
}
