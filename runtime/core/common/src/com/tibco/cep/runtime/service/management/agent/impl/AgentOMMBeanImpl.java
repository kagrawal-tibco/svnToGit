package com.tibco.cep.runtime.service.management.agent.impl;

import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.management.OMMethodsImpl;
import com.tibco.cep.runtime.service.management.agent.AgentMBeansSetter;
import com.tibco.cep.runtime.service.management.agent.AgentOMMBean;
import com.tibco.cep.runtime.session.RuleServiceProvider;

import javax.management.openmbean.TabularDataSupport;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Mar 8, 2010
 * Time: 6:15:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class AgentOMMBeanImpl extends OMMethodsImpl implements AgentOMMBean, AgentMBeansSetter {

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

    public TabularDataSupport GetEvent(String id, String isExternal) throws Exception {
        return GetEvent(getSessionName(cacheAgent),id, isExternal);
    }

    public TabularDataSupport GetInstance(String id, String isExternal) throws Exception {
        return GetInstance(getSessionName(cacheAgent),id, isExternal);
    }

    public TabularDataSupport GetScorecards(String URI) throws Exception {
        return GetScorecards(getSessionName(cacheAgent),URI);
    }
}
