package com.tibco.cep.repo.provider.adapters;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tibco.be.util.config.cdd.ClusterConfig;
import com.tibco.be.util.config.cdd.InferenceAgentClassConfig;
import com.tibco.be.util.config.cdd.RulesConfig;
import com.tibco.be.util.packaging.Constants;
import com.tibco.cep.repo.ArchiveInputDestinationConfig;

/*
* User: Nicolas Prade
* Date: Sep 3, 2009
* Time: 7:20:06 PM
*/


public class InferenceAgentToBEArchiveAdapter
        extends AbstractAgentToBEArchiveAdapter {


    public InferenceAgentToBEArchiveAdapter(
            ClusterConfig config,
            InferenceAgentClassConfig agentClassConfig,
            String agentKey) {
        super(config, agentClassConfig, agentKey);
    }


    public boolean equals(Object o) {
        return (o instanceof InferenceAgentToBEArchiveAdapter)
                && this.getName().equals(((InferenceAgentToBEArchiveAdapter) o).getName());
    }


    protected InferenceAgentClassConfig getInferenceAgentGroupConfig() {
        return (InferenceAgentClassConfig) this.agentClassConfig;
    }


    public Set<String> getDeployedRuleUris() {
        return this.getRuleUris(this.getInferenceAgentGroupConfig().getRules());
    }


    private Set<String> getRuleUris(RulesConfig rulesConfig) {
        final Set<String> uris = new HashSet<String>();
        uris.addAll(rulesConfig.getAllUris());
        return uris;
    }    


    public Set<ArchiveInputDestinationConfig> getInputDestinations() {
        return this.getInputDestinations(this.getInferenceAgentGroupConfig().getDestinations());
    }


    public List<String> getShutdownFunctionUris() {
    	final List<String> list = this.getInferenceAgentGroupConfig().getShutdown().getAllUris();
    	final List<String> copy = new ArrayList(list.size());
    	copy.addAll(list);
    	return copy;
    }


    public List<String> getStartupFunctionUris() {
    	final List<String> list = this.getInferenceAgentGroupConfig().getStartup().getAllUris();
    	final List<String> copy = new ArrayList(list.size());
    	copy.addAll(list);
    	return copy;
    }


    public Constants.ArchiveType getType() {
        return Constants.ArchiveType.INFERENCE;
    }


}
