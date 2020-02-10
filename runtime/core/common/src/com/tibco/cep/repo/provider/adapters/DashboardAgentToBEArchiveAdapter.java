package com.tibco.cep.repo.provider.adapters;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.be.util.config.cdd.DashboardAgentClassConfig;
import com.tibco.be.util.config.cdd.RulesConfig;
import com.tibco.be.util.packaging.Constants;
import com.tibco.cep.repo.ArchiveInputDestinationConfig;

/*
* User: Nicolas Prade
* Date: Sep 3, 2009
* Time: 7:20:06 PM
*/

public class DashboardAgentToBEArchiveAdapter
        extends AbstractAgentToBEArchiveAdapter {


    public DashboardAgentToBEArchiveAdapter(
            ClusterConfig config,
            DashboardAgentClassConfig agentGroupConfig,
            String agentKey) {
        super(config, agentGroupConfig, agentKey);
    }


    public boolean equals(Object o) {
        return (o instanceof DashboardAgentToBEArchiveAdapter)
                && this.getName().equals(((DashboardAgentToBEArchiveAdapter) o).getName());
    }

    protected DashboardAgentClassConfig getDashboardAgentGroupConfig() {
        return (DashboardAgentClassConfig) this.agentClassConfig;
    }

    public Set<String> getDeployedRuleUris() {
    	return this.getRuleUris(this.getDashboardAgentGroupConfig().getRules());
    }
    
    private Set<String> getRuleUris(RulesConfig rulesConfig) {
        final Set<String> uris = new HashSet<String>();
        uris.addAll(rulesConfig.getAllUris());
        return uris;
    }    

    public Set<ArchiveInputDestinationConfig> getInputDestinations() {
    	return this.getInputDestinations(this.getDashboardAgentGroupConfig().getDestinations());
    }

    public List<String> getShutdownFunctionUris() {
    	final List<String> list = this.getDashboardAgentGroupConfig().getShutdown().getAllUris();
    	final List<String> copy = new ArrayList(list.size());
    	copy.addAll(list);
    	return copy;
    }


    public List<String> getStartupFunctionUris() {
    	final List<String> list = this.getDashboardAgentGroupConfig().getStartup().getAllUris();
    	final List<String> copy = new ArrayList(list.size());
    	copy.addAll(list);
    	return copy;
    }


    public Constants.ArchiveType getType() {
    	return Constants.ArchiveType.DASHBOARD;
    }

}
