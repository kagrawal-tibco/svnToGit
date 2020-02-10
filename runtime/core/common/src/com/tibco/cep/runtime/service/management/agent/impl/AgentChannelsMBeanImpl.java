package com.tibco.cep.runtime.service.management.agent.impl;

import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.management.ChannelMethodsImpl;
import com.tibco.cep.runtime.service.management.agent.AgentChannelsMBean;
import com.tibco.cep.runtime.service.management.agent.AgentMBeansSetter;
import com.tibco.cep.runtime.session.RuleServiceProvider;

import javax.management.openmbean.TabularDataSupport;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Mar 8, 2010
 * Time: 5:53:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class AgentChannelsMBeanImpl extends ChannelMethodsImpl implements AgentChannelsMBean, AgentMBeansSetter {

    private CacheAgent cacheAgent;

    public void setRuleServiceProvider(RuleServiceProvider ruleServiceProvider) {
        super.ruleServiceProvider = ruleServiceProvider;
    }

    public void setLogger(Logger logger) {
        super.logger = logger;
    }

    public void setCacheAgent(CacheAgent cacheAgent) {
        this.cacheAgent = cacheAgent;
    }

    public void setAgentName(String agentName) {
        super.agentName = agentName;
    }

    public TabularDataSupport GetSessionInputDestinations() throws Exception {
        return GetSessionInputDestinations(getSessionName(cacheAgent));
    }
}
