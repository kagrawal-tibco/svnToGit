package com.tibco.cep.repo.provider.adapters;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.be.util.config.cdd.FunctionsConfig;
import com.tibco.be.util.config.cdd.QueryAgentClassConfig;
import com.tibco.be.util.packaging.Constants;
import com.tibco.cep.repo.ArchiveInputDestinationConfig;

/*
* User: Nicolas Prade
* Date: Sep 3, 2009
* Time: 7:20:06 PM
*/


public class QueryAgentToBEArchiveAdapter
        extends AbstractAgentToBEArchiveAdapter {


    public QueryAgentToBEArchiveAdapter(
            ClusterConfig config,
            QueryAgentClassConfig agentGroupConfig,
            String agentKey) {
        super(config, agentGroupConfig, agentKey);
    }


    public boolean equals(Object o) {
        return (o instanceof QueryAgentToBEArchiveAdapter)
                && this.getName().equals(((QueryAgentToBEArchiveAdapter) o).getName());
    }


    protected QueryAgentClassConfig getQueryAgentGroupConfig() {
        return (QueryAgentClassConfig) this.agentClassConfig;
    }


    public Set<String> getDeployedRuleUris() {
        return null;
    }


    public Set<ArchiveInputDestinationConfig> getInputDestinations() {
        return this.getInputDestinations(this.getQueryAgentGroupConfig().getDestinations());
    }


    public List<String> getShutdownFunctionUris() {
        FunctionsConfig fc = getQueryAgentGroupConfig().getShutdown();
        if(fc == null){
            return Collections.EMPTY_LIST;
        }

        return new ArrayList(fc.getAllUris());
    }


    public List<String> getStartupFunctionUris() {
        FunctionsConfig fc = getQueryAgentGroupConfig().getStartup();
        if(fc == null){
            return Collections.EMPTY_LIST;
        }
        
    	return new ArrayList(fc.getAllUris());
    }
    

    public Constants.ArchiveType getType() {
        return Constants.ArchiveType.QUERY;
    }


}
