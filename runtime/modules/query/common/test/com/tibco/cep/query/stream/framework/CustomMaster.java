/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 11/8/2010
 */

package com.tibco.cep.query.stream.framework;

import com.tibco.cep.query.stream.cache.Cache;
import com.tibco.cep.query.stream.cache.CacheBuilder;
import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.impl.Master;
import com.tibco.cep.query.stream.impl.cache.SimpleCacheBuilder;
import com.tibco.cep.query.stream.impl.cache.SimpleLocalCache;
import com.tibco.cep.query.stream.impl.rete.integ.Manager;
import com.tibco.cep.query.stream.monitor.CustomMultiSourceException;
import com.tibco.cep.query.stream.monitor.Logger;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.runtime.service.ServiceRegistry;

import java.util.ArrayList;
import java.util.Properties;

/*
 * Author: Ashwin Jayaprakash Date: Mar 7, 2008 Time: 5:35:19 PM
 */

public class CustomMaster extends Master {
    protected TestSession testSession;

    protected Properties properties;

    protected AgentServiceImpl agentService;

    public CustomMaster() throws Exception {
        this(null);
    }

    public CustomMaster(TestSession testSession) throws Exception {
        ServiceRegistry servRegistry = ServiceRegistry.getSingletonServiceRegistry();
        servRegistry.init(new Properties());
        this.testSession = testSession;

        this.properties = new Properties();

        this.agentService = new AgentServiceImpl();

        Manager.ManagerInput.RegionInput regionInput =
                new Manager.ManagerInput.RegionInput(agentService, true, false);

        ArrayList<Manager.ManagerInput.RegionInput> regionInputs =
                new ArrayList<Manager.ManagerInput.RegionInput>();
        regionInputs.add(regionInput);


        Manager.ManagerInput managerInput = new Manager.ManagerInput(regionInputs);

        this.properties.put(Manager.ManagerInput.KEY_INPUT, managerInput);

        //-----------

        SimpleCacheBuilder.BuilderInput builderInput =
                new SimpleCacheBuilder.BuilderInput(10000, 15 * 60 * 1000, 3000, 5 * 60 * 1000);

        this.properties.put(CacheBuilder.BuilderInput.KEY_INPUT, builderInput);
    }

    public AgentServiceImpl getAgentService() {
        return agentService;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setCacheCluster(CacheCluster cluster) {
        this.agentService.setCacheCluster(cluster);
    }

    @Override
    protected void registerComponents(Registry registry) {
        super.registerComponents(registry);

        SimpleLogger simpleLogger = new SimpleLogger();
        registry.register(Logger.class, simpleLogger);

        if (testSession != null) {
            CacheBuilder builder = new CacheBuilderImpl();

            TrackedCache.setTestSession(testSession);
            registry.register(CacheBuilder.class, builder);
        }
    }

    @Override
    public void stop() throws CustomMultiSourceException {
        super.stop();

        properties.clear();
        properties = null;

        agentService.discard();
        agentService = null;
    }

    //----------

    protected static class CacheBuilderImpl implements CacheBuilder {
        private final ResourceId id = new ResourceId(getClass().getName());

        public Cache build(String name, ResourceId parent, Type type) {
            if (type == Type.PRIMARY) {
                return new TrackedCache();
            }

            return new SimpleLocalCache(parent, Type.DEADPOOL.name(), 3000, 5 * 60 * 1000);
        }

        public void discard() throws Exception {
        }

        public void init(Properties properties) throws Exception {
        }

        public void start() throws Exception {
        }

        public void stop() throws Exception {
        }

        public ResourceId getResourceId() {
            return id;
        }

        public BuilderInput getBuilderInput() {
            return null;
        }
    }
}
