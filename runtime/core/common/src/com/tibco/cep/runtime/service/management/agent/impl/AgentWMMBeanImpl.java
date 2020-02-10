package com.tibco.cep.runtime.service.management.agent.impl;

import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.management.WMMethodsImpl;
import com.tibco.cep.runtime.service.management.agent.AgentMBeansSetter;
import com.tibco.cep.runtime.service.management.agent.AgentWMMBean;
import com.tibco.cep.runtime.service.management.agent.MethodToBeExecuted;
import com.tibco.cep.runtime.service.management.exception.BEMMUserActivityException;
import com.tibco.cep.runtime.session.RuleServiceProvider;

import javax.management.openmbean.TabularDataSupport;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Sep 22, 2009
 * Time: 12:33:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class AgentWMMBeanImpl extends WMMethodsImpl implements AgentWMMBean, AgentMBeansSetter, MethodToBeExecuted {

    private CacheAgent cacheAgent;

    public AgentWMMBeanImpl() {
    }

    public AgentWMMBeanImpl(CacheAgent cacheAgent, Logger logger, RuleServiceProvider rsp) {
        this.cacheAgent = cacheAgent;
        super.ruleServiceProvider = rsp;
        super.logger = logger;
    }

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

    public TabularDataSupport GetRules() throws Exception {
        return GetRules(getSessionName(cacheAgent));
    }

    public TabularDataSupport GetRule(String URI) throws Exception {
        return GetRule(getSessionName(cacheAgent), URI);
    }

    public TabularDataSupport ActivateRule(String URI) throws BEMMUserActivityException {
        return ActivateRule(getSessionName(cacheAgent), URI);
    }

    public TabularDataSupport DeactivateRule(String URI) throws BEMMUserActivityException {
        return DeactivateRule(getSessionName(cacheAgent), URI);
    }

    public void ResetTotalNumberRulesFired() throws Exception {
        ResetTotalNumberRulesFired( getSessionName(cacheAgent) );
    }

    public TabularDataSupport GetTotalNumberRulesFired() throws BEMMUserActivityException {
        return GetTotalNumberRulesFired( getSessionName(cacheAgent) );
    }

    public TabularDataSupport GetWorkingMemoryDump() throws Exception {
        return GetWorkingMemoryDump( getSessionName(cacheAgent) );
    }

    public TabularDataSupport GetRuleSession() throws Exception {
        return geTRuleSessions( getSessionName(cacheAgent) );
    } //getSessions

    public Object execute(String methodName, String args) throws Exception {
        return GetWorkingMemoryDump();
    }
} //class
