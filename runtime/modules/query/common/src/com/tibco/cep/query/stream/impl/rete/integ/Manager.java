package com.tibco.cep.query.stream.impl.rete.integ;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.tibco.cep.kernel.core.base.BaseObjectManager;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.query.stream.core.Component;
import com.tibco.cep.query.stream.impl.rete.service.AgentService;
import com.tibco.cep.query.stream.impl.rete.service.RegionManager;
import com.tibco.cep.query.stream.monitor.CustomMultiSourceException;
import com.tibco.cep.query.stream.monitor.ResourceId;

/*
 * Author: Ashwin Jayaprakash Date: Mar 6, 2008 Time: 4:22:43 PM
 */

public class Manager implements Component {
    protected final ResourceId id;

    protected HashMap<String, RegionManager> regionManagers;

    protected ManagerInput managerInput;

    protected Properties properties;

    public Manager() {
        this.id = new ResourceId(Manager.class.getName());
    }

    public ResourceId getResourceId() {
        return id;
    }

    public ManagerInput getManagerInput() {
        return managerInput;
    }

    public Properties getProperties() {
        return properties;
    }

    /**
     * Do not modify this!
     *
     * @return
     */
    public Map<String, RegionManager> getRegionManagers() {
        return regionManagers;
    }

    /**
     * @param properties Expects {@link com.tibco.cep.query.stream.impl.rete.integ.Manager.ManagerInput} to be present
     *                   under the key: {@link com.tibco.cep.query.stream.impl.rete.integ.Manager.ManagerInput#KEY_INPUT}.
     */
    public void init(Properties properties) throws Exception {
        managerInput = (ManagerInput) properties.get(ManagerInput.KEY_INPUT);

        regionManagers = new HashMap<String, RegionManager>();

        for (ManagerInput.RegionInput regionInput : managerInput.getRegionInputs()) {
            RegionManager regionManager = null;
            String regionName = regionInput.getName();

            if (regionInput.getWorkingMemory() == null) {
                regionManager =
                        new RegionManagerImpl(id, regionName, properties,
                                regionInput.getAgentService(),
                                regionInput.isCachePrefetchAggressive(),
                                regionInput.isStandaloneMode());
            }
            else {
                WorkingMemory wm = regionInput.getWorkingMemory();
                BaseObjectManager om = regionInput.getBaseObjectManager();

                regionManager =
                        new RegionManagerImpl(id, regionName, properties,
                                regionInput.getAgentService(), wm,
                                om, regionInput.isCachePrefetchAggressive(),
                                regionInput.isStandaloneMode());
            }

            regionManagers.put(regionManager.getRegionName(), regionManager);
        }

        this.properties = properties;
    }

    public void start() throws Exception {
        for (RegionManager manager : regionManagers.values()) {
            manager.start();
        }
    }

    public void stop() throws Exception {
        CustomMultiSourceException errorLog = new CustomMultiSourceException(id);

        for (RegionManager manager : regionManagers.values()) {
            try {
                manager.stop();
            }
            catch (Exception e) {
                errorLog.addSource(new CustomMultiSourceException.Source(e));
            }
        }

        if (errorLog.hasSources()) {
            throw errorLog;
        }
    }

    public void discard() throws Exception {
        regionManagers.clear();
        regionManagers = null;

        managerInput = null;

        id.discard();
    }

    //-----------

    public static class ManagerInput {
        /**
         * {@value}
         */
        public static final String KEY_INPUT = ManagerInput.class.getName();

        protected Collection<RegionInput> regionInputs;

        public ManagerInput(Collection<RegionInput> regionInputs) {
            this.regionInputs = regionInputs;
        }

        public Collection<RegionInput> getRegionInputs() {
            return regionInputs;
        }

        public void setRegionInputs(Collection<RegionInput> regionInputs) {
            this.regionInputs = regionInputs;
        }

        //-----------

        public static class RegionInput {
            protected AgentService agentService;

            protected WorkingMemory workingMemory;

            protected BaseObjectManager baseObjectManager;

            protected boolean cachePrefetchAggressive;

            protected boolean standaloneMode;

            public RegionInput(AgentService agentService, boolean cachePrefetchAggressive,
                               boolean standaloneMode) {
                this.agentService = agentService;
                this.cachePrefetchAggressive = cachePrefetchAggressive;
                this.standaloneMode = standaloneMode;
            }

            public RegionInput(AgentService agentService,
                               WorkingMemory workingMemory,
                               BaseObjectManager baseObjectManager,
                               boolean cachePrefetchAggressive,
                               boolean standaloneMode) {
                this.agentService = agentService;
                this.workingMemory = workingMemory;
                this.baseObjectManager = baseObjectManager;
                this.cachePrefetchAggressive = cachePrefetchAggressive;
                this.standaloneMode = standaloneMode;
            }

            /**
             * @return {@link #getAgentService()#getName()}.
             */
            public String getName() {
                return agentService.getName();
            }

            public AgentService getAgentService() {
                return agentService;
            }

            public void setAgentService(AgentService agentService) {
                this.agentService = agentService;
            }

            public WorkingMemory getWorkingMemory() {
                return workingMemory;
            }

            public void setWorkingMemory(WorkingMemory workingMemory) {
                this.workingMemory = workingMemory;
            }

            public BaseObjectManager getBaseObjectManager() {
                return baseObjectManager;
            }

            public void setBaseObjectManager(BaseObjectManager baseObjectManager) {
                this.baseObjectManager = baseObjectManager;
            }

            public boolean isCachePrefetchAggressive() {
                return cachePrefetchAggressive;
            }

            public void setCachePrefetchAggressive(boolean cachePrefetchAggressive) {
                this.cachePrefetchAggressive = cachePrefetchAggressive;
            }

            public boolean isStandaloneMode() {
                return standaloneMode;
            }

            public void setStandaloneMode(boolean standaloneMode) {
                this.standaloneMode = standaloneMode;
            }
        }
    }
}
