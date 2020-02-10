/*
 * Copyright (c) 2010.  TIBCO Software Inc.
 * All Rights Reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 */

package com.tibco.cep.runtime.session;

import java.util.Locale;
import java.util.Properties;

import com.tibco.cep.kernel.service.IdGenerator;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.repo.DeployedProject;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.runtime.channel.Channel;
import com.tibco.cep.runtime.channel.ChannelManager;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.service.cluster.Cluster;

/**
 * Provides all services for one BE archive deployed on one engine.
 * Services include <code>RuleRuntime</code>, <code>TypeManager</code>, etc.</p>
 * <p>Each <code>RuleServiceProvider</code> is managed by one <code>RuleServiceProviderManager</code>.</p>
 *
 * @version 2.0.0
 * @.category public-api
 * @see RuleServiceProviderManager
 * @since 2.0.0
 */
public interface RuleServiceProvider {

    /**
     * In <code>MODE_LOAD_ONLY</code> only project, classes, Global Variable are loaded.
     *
     * @.category public-api
     * @since 2.0.0
     */
    int MODE_LOAD_ONLY = 0;


    /**
     * In <code>MODE_INIT</code> all services are initialized, but no service is started.
     *
     * @.category public-api
     * @since 2.0.0
     */
    int MODE_INIT = 1;

    /**
     * In <code>MODE_API</code> all services are intialized, but only the rule sessions are started.
     *
     * @.category public-api
     * @since 2.0.0
     */
    int MODE_API = 2;

    /**
     * In <code>MODE_PRIMARY</code> all services are initialized and started.
     *
     * @.category public-api
     * @since 2.0.0
     */
    int MODE_PRIMARY = 3;

    int MODE_CACHESERVER = 4;

    int MODE_CACHESERVER_W_CHANNEL = 5;

    int MODE_QUERYAGENT = 14;

    int MODE_QUERYAGENT_W_CHANNEL = 15;

    /**
     * <code>MODE_CLUSTER</code> starts runtime in Cluster mode with all services are initialized and started.
     *
     * @.category public-api
     * @since 3.0.2
     */
    int MODE_CLUSTER = 6;

    int MODE_CLUSTER_CACHESERVER = 7;

    // Same as mode_primary but doesn't block on shutdownGuard
    int MODE_START_ALL = 8;

    /**
     * Initializes and starts this <code>RuleServiceProvider</code> in the given mode.
     *
     * @param mode one of MODE_API, MODE_INIT, MODE_PRIMARY, MODE_LOAD_ONLY
     * @throws Exception
     * @.category public-api
     * @see #MODE_API
     * @see #MODE_INIT
     * @see #MODE_PRIMARY
     * @see #MODE_LOAD_ONLY
     */
    void configure(int mode) throws Exception;
    // TODO change name?
    // TODO what happens upon failure?


    /**
     * Gets the <code>ChannelManager</code> which manages all the {@link Channel Channels}
     * in this <code>RuleServiceProvider</code>.
     *
     * @return A <code>ChannelManager</code>.
     * @since 2.0.0
     */
    ChannelManager getChannelManager();


    /**
     * Gets the class loader used by deserialization and hot deployment.
     *
     * @return a <code>ClassLoader</code>.
     * @.category public-api
     * @since 2.0.0
     */
    ClassLoader getClassLoader();


    /**
     * Gets the <code>GlobalVariables</code> that manages all global variables
     * in this <code>RuleServiceProvider</code>.
     *
     * @return a GlobalVariables.
     * @.category public-api
     * @since 2.0.0
     */
    GlobalVariables getGlobalVariables();


    /**
     * Gets the <code>IdGenerator</code> which manages unique IDs
     * in this <code>RuleServiceProvider</code>.
     *
     * @return an IdGenerator
     * @since 2.0.0
     */
    IdGenerator getIdGenerator();


    Locale getLocale();

    
    /**
     * Gets the <code>Logger</code> which manages all logging
     * in this <code>RuleServiceProvider</code>.
     *
     * @return a <code>Logger</code>.
     * @.category public-api
     * @since 2.0.0
     * @deprecated
     */
    //com.tibco.cep.kernel.service.Logger getLogger();


    /**
     * Gets the <code>Logger</code> for a given module.
     *
     * @param name String name of the module.
     * @return a <code>Logger</code>.
     * @.category public-api
     * @since 4.0.0
     */
    Logger getLogger(String name);


    /**
     * Gets the <code>Logger</code> for a given module.
     *
     * @param clazz Class used to determine the module.
     * @return a <code>Logger</code>.
     * @.category public-api
     * @since 4.0.0
     */
    Logger getLogger(Class clazz);


    /**
     * Gets the name under which this <code>RuleServiceProvider</code> is registered
     * in its parent <code>RuleServiceProviderManager</code>.
     *
     * @return a <code>String</code>.
     * @.category public-api
     * @since 2.0
     */
    String getName();


    /**
     * Gets the <code>DeployedProject</code> which contains the part of the packaged project that has been deployed
     * to this <code>RuleServiceProvider</code>.
     *
     * @return a <code>DeployedProject</code>
     * @since 2.0.0
     */
    DeployedProject getProject();


    /**
     * Gets the properties used to build this <code>RuleServiceProvider</code>.
     *
     * @return a <code>Properties</code>.
     * @.category public-api
     * @since 2.0.0
     */
    Properties getProperties();


    /**
     * Gets the <code>RuleAdministrator</code> which manages remote administration
     * for this <code>RuleServiceProvider</code>.
     *
     * @return A <code>RuleAdministrator</code>.
     * @since 2.0.0
     */
    RuleAdministrator getRuleAdministrator();


    /**
     * Returns the <code>RuleRuntime</code> that manages the rule sessions in this <code>RuleServiceProvider</code>.
     *
     * @return A <code>RuleRuntime</code>.
     * @.category public-api
     * @since 2.0.0
     */
    RuleRuntime getRuleRuntime();


    /**
     * Gets the <code>TypeManager</code> which manages all Ontology type definitions
     * in this <code>RuleServiceProvider</code>.
     *
     * @return a <code>TypeManager</code>.
     * @.category public-api
     * @since 2.0.0
     */
    TypeManager getTypeManager();


    /**
     * Stops the components in the reverse order they were started. Valid only when running in a MODE_PRIMARY.
     * For API and INIT Mode it is a no-op.
     *
     * @.category public-api
     * @since 2.0.0
     */
    void shutdown();

    
    void initProject() throws Exception;

    boolean isMultiEngineMode();

    Cluster getCluster();

    boolean isCacheServerMode();
}
