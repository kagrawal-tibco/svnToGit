/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.events.notification;

import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.config.Configuration;
import com.tibco.cep.runtime.service.Service;
import com.tibco.cep.runtime.service.ServiceRegistry;
import com.tibco.cep.runtime.service.cluster.system.MetadataCache;
import com.tibco.cep.runtime.session.RuleSession;

/*
* Author: Ashwin Jayaprakash Date: Nov 12, 2008 Time: 12:59:19 PM
*/

public class CacheChangeListenerService implements Service {
    /**
     * {@value}
     */
    public static final String PROP_LISTENER_CLASS = "be.om.cacheChangeListener.listenerClass";

    private static final String ERR_ILLEGAL_ARGS = Map.class.getName() + " of "
            + RuleSession.class.getName() + " and " + MetadataCache.class.getName() +
            " is expected.";

    protected HashMap<RuleSession, CacheChangeListener> allListeners;

    public String getId() {
        return CacheChangeListenerService.class.getName();
    }

    /**
     * @param configuration
     * @param otherArgs     The first element must be a {@link java.util.Map} of {@link
     *                      com.tibco.cep.runtime.session.RuleSession}s and {@link
     *                      com.tibco.cep.runtime.service.cluster.system.MetadataCache}.
     * @throws Exception
     */
    public void init(Configuration configuration, Object... otherArgs)
            throws Exception {
        if (otherArgs == null || otherArgs.length < 1) {
            throw new IllegalArgumentException(ERR_ILLEGAL_ARGS);
        }

        if (otherArgs[0] instanceof Map == false) {
            throw new IllegalArgumentException(ERR_ILLEGAL_ARGS);
        }

        //------------

        Map<RuleSession, MetadataCache> ruleSessions =
                (Map<RuleSession, MetadataCache>) otherArgs[0];

        if (ruleSessions.isEmpty()) {
            return;
        }

        //------------

        String value = configuration.getPropertyRecursively(PROP_LISTENER_CLASS);

        if (value == null) {
            return;
        }

        //------------

        Class<? extends CacheChangeListener> listenerClass = null;

        ServiceRegistry registry = ServiceRegistry.getSingletonServiceRegistry();
        //Logger logger = registry.getService(Logger.class);
        Logger logger = null;
        if (logger == null) {
            RuleSession someRS = ruleSessions.keySet().iterator().next();
            logger = someRS.getRuleServiceProvider().getLogger(CacheChangeListenerService.class);
        }

        try {
            Class clazz = Class.forName(value, true, getClass().getClassLoader());

            listenerClass = (Class<? extends CacheChangeListener>) clazz;
        }
        catch (Exception e) {
            String msg = "Error occurred while initializing the implementation class for ["
                    + CacheChangeListener.class.getName()
                    + "]. Value provided [" + value + "].";

            logger.log(Level.ERROR, msg, e);

            return;
        }

        //------------

        doInit(ruleSessions, listenerClass, logger);
    }

    protected void doInit(Map<RuleSession, MetadataCache> ruleSessionAndCaches,
                          Class<? extends CacheChangeListener> listenerClass, Logger logger)
            throws Exception {
        allListeners = new HashMap<RuleSession, CacheChangeListener>();

        for (RuleSession ruleSession : ruleSessionAndCaches.keySet()) {
            CacheChangeListener listener = null;

            try {
                listener = listenerClass.newInstance();
            }
            catch (Exception e) {
                String msg = "Error occurred while instantiating the implementation class for ["
                        + CacheChangeListener.class.getName()
                        + "]. Value provided [" + listenerClass.getName() + "].";

                //Cleanup.
                if (allListeners.isEmpty() == false) {
                    for (CacheChangeListener cacheChangeListener : allListeners.values()) {
                        try {
                            cacheChangeListener.stop();
                        }
                        catch (Exception e1) {
                            //Ignore.
                        }
                    }

                    allListeners.clear();
                    allListeners = null;
                }

                return;
            }

            allListeners.put(ruleSession, listener);
        }

        //------------

        if (allListeners.isEmpty()) {
            allListeners = null;
        } else {
            for (RuleSession ruleSession : allListeners.keySet()) {
                CacheChangeListener listener = allListeners.get(ruleSession);

                MetadataCache metadataCache = ruleSessionAndCaches.get(ruleSession);

                try {
                    listener.init(ruleSession, metadataCache);
                }
                catch (Exception e) {
                    logger.log(Level.ERROR, "Error occurred while initializing the listener ["
                            + listenerClass.getName() + "] instance for RuleSession ["
                            + ruleSession.getName() + "].", e);
                }
            }
        }
    }

    /**
     * Does not do anything. The listeners must be started individually.
     *
     * @throws Exception
     */
    public void start() throws Exception {
    }

    public void stop() throws Exception {
        if (allListeners != null) {
            for (RuleSession ruleSession : allListeners.keySet()) {
                CacheChangeListener listener = allListeners.get(ruleSession);

                listener.stop();
            }

            allListeners.clear();
            allListeners = null;
        }
    }

    //--------------

    /**
     * @return <code>true</code> if there is at least one listener present in {@link
     *         #getAllListeners()}.
     */
    public boolean areListenersPresent() {
        return (allListeners != null && allListeners.size() > 0);
    }

    /**
     * Do not modify this.
     *
     * @return
     */
    public Map<RuleSession, CacheChangeListener> getAllListeners() {
        return allListeners;
    }
}
