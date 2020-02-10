package com.tibco.cep.runtime.service.management.agent.impl;

import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.management.ProfilerMethodsImpl;
import com.tibco.cep.runtime.service.management.agent.AgentMBeansSetter;
import com.tibco.cep.runtime.service.management.agent.AgentProfilerMBean;
import com.tibco.cep.runtime.session.RuleServiceProvider;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Sep 22, 2009
 * Time: 12:30:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class AgentProfilerMBeanImpl extends ProfilerMethodsImpl implements AgentProfilerMBean, AgentMBeansSetter {

    private CacheAgent cacheAgent;

    public void setRuleServiceProvider(RuleServiceProvider ruleServiceProvider) {
        super.ruleServiceProvider = ruleServiceProvider;  //To change body of implemented methods use File | Settings | File Templates.
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

    public void StartFileBasedProfiler(String fileName, int level, long duration) throws Exception{
        StartFileBasedProfiler(getSessionName(cacheAgent),fileName,level,duration);
    }

    public void StopFileBasedProfiler() throws Exception{
        StopFileBasedProfiler(getSessionName(cacheAgent));
    }
}  //class
