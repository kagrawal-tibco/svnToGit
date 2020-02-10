package com.tibco.cep.repo.provider.adapters;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tibco.be.util.config.cdd.CacheAgentClassConfig;
import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.be.util.packaging.Constants;
import com.tibco.cep.repo.ArchiveInputDestinationConfig;

/*
* User: Nicolas Prade
* Date: Sep 3, 2009
* Time: 7:20:06 PM
*/


public class CacheAgentToBEArchiveAdapter
        extends AbstractAgentToBEArchiveAdapter {


    public CacheAgentToBEArchiveAdapter(
            ClusterConfig config,
            CacheAgentClassConfig agentGroupConfig,
            String agentKey) {
        super(config, agentGroupConfig, agentKey);
    }


    public boolean equals(Object o) {
        return (o instanceof CacheAgentToBEArchiveAdapter)
                && this.getName().equals(((CacheAgentToBEArchiveAdapter) o).getName());
    }


    public Set<String> getDeployedRuleUris() {
        return null;
    }


    public Set<ArchiveInputDestinationConfig> getInputDestinations() {
        return new HashSet<ArchiveInputDestinationConfig>();
    }


    public List<String> getShutdownFunctionUris() {
        return null;
    }


    public List<String> getStartupFunctionUris() {
        return null;
    }


    public Constants.ArchiveType getType() {
        return Constants.ArchiveType.DATAGRID;
    }

}
