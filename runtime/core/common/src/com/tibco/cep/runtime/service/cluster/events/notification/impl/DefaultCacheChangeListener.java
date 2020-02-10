/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.service.cluster.events.notification.impl;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Properties;

import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.kernel.service.IdGenerator;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.scheduler.TaskController;
import com.tibco.cep.runtime.scheduler.impl.WorkerBasedController;
import com.tibco.cep.runtime.service.cluster.events.notification.CacheChangeEvent;
import com.tibco.cep.runtime.service.cluster.events.notification.CacheChangeListener;
import com.tibco.cep.runtime.service.cluster.system.MetadataCache;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;

/**
 * Created by IntelliJ IDEA. User: ssubrama Date: Nov 12, 2008 Time: 6:26:12 PM To change this
 * template use File | Settings | File Templates.
 */
public class DefaultCacheChangeListener implements CacheChangeListener {
    /**
     * {@value}.
     */
    public static final String PROP_LISTENER_PREPROCESSOR =
            "be.om.cacheChangeListener.default.preProcessor";

    /**
     * {@value}.
     */
    public static final String PROP_LISTENER_EVENTFILTER =
            "be.om.cacheChangeListener.default.eventFilter";

    public static final String CACHE_EVENT_ENTITY_ID = "id";

    public static final String CACHE_EVENT_ENTITY_EXTID = "extId";

    public static final String CACHE_EVENT_ENTITY_VERSION = "version";

    public static final String CACHE_EVENT_ENTITY_TS = "ts";

    public static final String CACHE_EVENT_ENTITY_CHANGE_TYPE = "changeType";

    RuleSession ruleSession;

    MetadataCache metadataCache;

    RuleServiceProvider rsp;

    RuleFunction preProcessor;

    Class cacheEventClass;

    IdGenerator idGenerator;

    TaskController taskController;

    HashSet eventFilterSet;

    Constructor cacheEventCons;

    Logger logger;

    Config config;

    public DefaultCacheChangeListener() {
        eventFilterSet = new HashSet();
    }

    public String getId() {
        return getClass().getName() + ":" + System.identityHashCode(this);
    }

    /**
     * @param ruleSession
     * @return
     * @throws IllegalArgumentException
     */
    public void init(RuleSession ruleSession, MetadataCache metadataCache) {
        this.ruleSession = ruleSession;
        rsp = this.ruleSession.getRuleServiceProvider();
        this.metadataCache = metadataCache;
        logger = rsp.getLogger(CacheChangeListener.class);
        idGenerator = ruleSession.getRuleServiceProvider().getIdGenerator();
        taskController = ruleSession.getTaskController();
    }

    /**
     * @param rs
     * @param functionName
     * @throws IllegalArgumentException
     */
    protected void parsePreProc(RuleSession rs, String functionName) {
        if (functionName == null) {
            throw new IllegalArgumentException(
                    "Property [" + PROP_LISTENER_PREPROCESSOR + "] has not been provided.");
        }

        preProcessor = ((RuleSessionImpl) rs).getRuleFunction(functionName);

        RuleFunction.ParameterDescriptor[] descriptors = preProcessor.getParameterDescriptors();
        //Length is 2 because of input args and return type.
        if (descriptors.length == 2 &&
                SimpleEvent.class.isAssignableFrom(descriptors[0].getType())) {
            cacheEventClass = descriptors[0].getType();
        } else {
            throw new IllegalArgumentException(
                    "PreProcessor function does not accept the correct arguments.");
        }
    }

    /**
     * @param rs
     * @param commaSepURIs
     * @return
     * @throws IllegalArgumentException
     */
    protected Collection<Integer> parseTypeURIsToTypeIds(RuleSession rs, String commaSepURIs) {
        if (commaSepURIs == null || commaSepURIs.length() == 0) {
            return new LinkedList<Integer>();
        }

        //---------

        String[] interestedURIs = commaSepURIs.split(",");
        ArrayList<String> uriList = new ArrayList<String>(interestedURIs.length);
        for (int i = 0; i < interestedURIs.length; i++) {
            interestedURIs[i] = interestedURIs[i].trim();

            if (interestedURIs[i] != null && interestedURIs[i].length() > 0) {
                uriList.add(interestedURIs[i]);
            }
        }

        if (uriList.isEmpty()) {
            throw new IllegalArgumentException(
                    "The URIs provided seem to be empty [" + commaSepURIs + "].");
        }

        //---------

        RuleServiceProvider provider = rs.getRuleServiceProvider();
        TypeManager typeManager = provider.getTypeManager();

        ArrayList<Integer> finalTypeIdList = new ArrayList<Integer>(uriList.size());

        for (int i = uriList.size() - 1; i >= 0; i--) {
            String uri = uriList.get(i);
            TypeManager.TypeDescriptor descriptor = typeManager.getTypeDescriptor(uri);

            if (descriptor == null) {
                throw new IllegalArgumentException("The URI [" + uri + "] provided is invalid.");
            }

            Class implClass = descriptor.getImplClass();
            try {
                int typeId = metadataCache.getTypeId(implClass);
                finalTypeIdList.add(typeId);
            }
            catch (Exception e) {
                throw new IllegalArgumentException("The URI [" + uri + "] provided is invalid.", e);
            }
        }

        return finalTypeIdList;
    }

    public void start() throws Exception {
        Properties properties = rsp.getProperties();

        String func = properties.getProperty(PROP_LISTENER_PREPROCESSOR);
        parsePreProc(ruleSession, func);

        //--------------

        String filterURIString = properties.getProperty(PROP_LISTENER_EVENTFILTER);
        Collection<Integer> typeIds = parseTypeURIsToTypeIds(ruleSession, filterURIString);

        //--------------

        logger.log(Level.INFO, getClass().getSimpleName() + " started successfully with URI: "
                + func + " and filter: " + filterURIString);


        config = new ConfigImpl(typeIds);
    }

    public Config getConfig() {
        return config;
    }

    public void onEvent(CacheChangeEvent cacheEvent) {
        try {
            if (cacheEventCons == null) {
                cacheEventCons = cacheEventClass.getConstructor(long.class);
            }

            long id = idGenerator.nextEntityId(cacheEventClass);
            SimpleEvent evt = (SimpleEvent) cacheEventCons.newInstance(id);
            mapEvent(cacheEvent, evt);

            CacheChangeEventTask cacheTask = new CacheChangeEventTask(evt);
            taskController.processTask(WorkerBasedController.DEFAULT_POOL_NAME, cacheTask);
        }
        catch (Exception e) {
            logger.log(Level.ERROR, getClass().getSimpleName(),
                    "Error occurred while handling Cache change event", e);
        }
    }

    private void mapEvent(CacheChangeEvent cacheEvent, SimpleEvent event) throws Exception {
        event.setProperty(CACHE_EVENT_ENTITY_ID, cacheEvent.getEntityId());
        event.setProperty(CACHE_EVENT_ENTITY_EXTID, cacheEvent.getEntityExtId());
        event.setProperty(CACHE_EVENT_ENTITY_VERSION, cacheEvent.getEntityChangeVersion());
        event.setProperty(CACHE_EVENT_ENTITY_TS, System.currentTimeMillis());
        event.setProperty(CACHE_EVENT_ENTITY_CHANGE_TYPE, cacheEvent.getChangeType().name());
    }

    public void stop() throws Exception {
        logger.log(Level.INFO, "Cache change listener stopped successfully");
    }

    class CacheChangeEventTask implements Runnable {
        SimpleEvent event;

        public CacheChangeEventTask(SimpleEvent evt) {
            this.event = evt;
        }

        public void run() {
            ((RuleSessionImpl) ruleSession).preprocessPassthru(preProcessor, event);
        }
    }

    static class ConfigImpl implements Config {
        protected final int[] interestedTypeIds;

        ConfigImpl(Collection<Integer> ids) {
            if (ids != null && ids.size() > 0) {
                interestedTypeIds = new int[ids.size()];

                int x = 0;
                for (Integer id : ids) {
                    interestedTypeIds[x] = id;
                    x++;
                }
            } else {
                interestedTypeIds = null;
            }
        }

        public int[] getInterestedTypeIds() {
            return interestedTypeIds;
        }
    }
}
