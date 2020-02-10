package com.tibco.cep.repo.provider.adapters;


import java.util.HashSet;
import java.util.Set;

import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.cdd.AgentClassConfig;
import com.tibco.be.util.config.cdd.CacheManagerConfig;
import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.be.util.config.cdd.DestinationConfig;
import com.tibco.be.util.config.cdd.DestinationsConfig;
import com.tibco.be.util.config.cdd.ObjectManagerConfig;
import com.tibco.be.util.packaging.Constants;
import com.tibco.cep.repo.ArchiveInputDestinationConfig;
import com.tibco.cep.repo.GlobalVariables;
import com.tibco.cep.repo.impl.BEArchiveResourceImpl;

/*
* User: Nicolas Prade
* Date: Sep 3, 2009
* Time: 6:48:25 PM
*/


public class AbstractAgentToBEArchiveAdapter
        extends BEArchiveResourceImpl {


    protected final ClusterConfig clusterConfig;
    protected final AgentClassConfig agentClassConfig;


    public AbstractAgentToBEArchiveAdapter(
            ClusterConfig config,
            AgentClassConfig agentClassConfig,
            String agentKey) {
        super(agentKey);
        this.agentClassConfig = agentClassConfig;
        this.clusterConfig = config;

        final ObjectManagerConfig omConfig = (ObjectManagerConfig)
        		this.clusterConfig.getObjectManagement().eContents().get(0);
        if (omConfig instanceof CacheManagerConfig) {
            this.cacheConfig.putAll(omConfig.toProperties());//todo
        }
        final String name = CddTools.getValueFromMixed(config.getName());
        this.cacheConfig.put(Constants.PROPERTY_NAME_OM_TANGOSOL_AGENT_NAME, agentClassConfig.getId());
        this.cacheConfig.put("tangosol.coherence.cluster", name);
    }


    public AgentClassConfig getAgentGroupConfig() {
        return this.agentClassConfig;
    }


    public ClusterConfig getClusterConfig() {
        return this.clusterConfig;
    }


    protected Set<ArchiveInputDestinationConfig> getInputDestinations(
            DestinationsConfig destinationsConfig) {
        final Set<ArchiveInputDestinationConfig> inputDestinationConfigs = new HashSet<ArchiveInputDestinationConfig>();
        if (null != destinationsConfig) {
            for (DestinationConfig cfg : destinationsConfig.getAllDestinations()) {
               inputDestinationConfigs.add(this.getInputDestination(cfg));
            }
        }
        return inputDestinationConfigs;
    }


    protected ArchiveInputDestinationConfig getInputDestination(
            DestinationConfig destinationConfig) {
    	//TODO may be we should understand what type of threading model we have to override & set numWorker to -1
    	final String threadCount = CddTools.getValueFromMixed(destinationConfig.getThreadCount());
    	final String queueSize = CddTools.getValueFromMixed(destinationConfig.getQueueSize());
        return new InputDestinationConfigImpl(
                destinationConfig.getUri(),
                destinationConfig.getPreProcessor(),
                destinationConfig.getThreadingModel().getName(),
                destinationConfig.getThreadAffinityRuleFunction(),
                ((null == threadCount) || threadCount.isEmpty()) ? 0 : Integer.parseInt(threadCount),
                ((null == queueSize) || queueSize.isEmpty()) ? 0 : Integer.parseInt(queueSize),
                0);  //todo weight and thread model? 
    }



    public boolean isCacheEnabled(
            GlobalVariables gv) {
    	return null != this.clusterConfig.getObjectManagement().getCacheManager();
    }

    @Override
    public String getReferenceClassName() {
        return agentClassConfig.getId();
    }
}
