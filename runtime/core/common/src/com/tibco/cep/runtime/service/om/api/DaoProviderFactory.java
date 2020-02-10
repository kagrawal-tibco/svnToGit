/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 26/7/2010
 */

package com.tibco.cep.runtime.service.om.api;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.util.SystemProperty;

/**
 * Created by IntelliJ IDEA.
 * User: suresh
 * Date: Jun 28, 2010
 * Time: 5:41:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class DaoProviderFactory {

    private ConcurrentHashMap<Cluster, DaoProvider> providers;
    static AtomicReference<DaoProviderFactory> instance = new AtomicReference<DaoProviderFactory>();
    private static boolean initialized = false;

    private DaoProviderFactory() {
        providers = new ConcurrentHashMap<Cluster, DaoProvider>();
    }

    public static DaoProviderFactory getInstance() {
        DaoProviderFactory factory = instance.get();
        if (factory == null) {
            factory = new DaoProviderFactory();
            if (!instance.compareAndSet(null, factory)) {
                factory = instance.get();
            }
        }
        return factory;
    }

    /**
     * Calls this method at the beginning (RuleServiceProviderManager... before creating the RSP)
     * Any static initializer needed such as injecting class or interface inheritance makes it easy/
     * @throws Exception
     * @see CoherenceDaoProvider static initializer.
     */
    public static synchronized void initialize() throws Exception {
        if (!initialized) {
            String className = System.getProperty(SystemProperty.VM_DAOPROVIDER_CLASSNAME.getPropertyName(), null);
            if (className != null) {
                Class.forName(className);
            }
            initialized = true;
        }
    }
    
    public DaoProvider newProvider() throws Exception {
        DaoProvider daoProvider;
        String className = System.getProperty(SystemProperty.VM_DAOPROVIDER_CLASSNAME.getPropertyName(),
                SystemProperty.VM_DAOPROVIDER_CLASSNAME.getValidValues()[0].toString());
        Class<DaoProvider> cls = (Class<DaoProvider>) Class.forName(className);
        daoProvider = cls.newInstance();
        return daoProvider;
    }
}
