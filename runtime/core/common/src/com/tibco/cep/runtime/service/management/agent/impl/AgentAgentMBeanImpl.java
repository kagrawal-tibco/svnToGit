package com.tibco.cep.runtime.service.management.agent.impl;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.management.EntityMethodsImpl;
import com.tibco.cep.runtime.service.management.agent.AgentAgentMBean;
import com.tibco.cep.runtime.service.management.agent.AgentMBeansSetter;
import com.tibco.cep.runtime.session.RuleServiceProvider;

import javax.management.openmbean.TabularDataSupport;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Sep 22, 2009
 * Time: 12:58:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class AgentAgentMBeanImpl extends EntityMethodsImpl implements AgentAgentMBean, AgentMBeansSetter {

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

    public TabularDataSupport GetNumberOfEvents() throws Exception {
        return GetNumberOfEvents(getSessionName(cacheAgent));
    }

    public TabularDataSupport GetNumberOfInstances() throws Exception {
        return GetNumberOfInstances( getSessionName(cacheAgent) );
    }

    public void Suspend() {
        try {
            if (cacheAgent != null)
                cacheAgent.suspend();
            else
                suspendInMem();
        } catch (Exception e) {
            logger.log(Level.ERROR, e, e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
//        RuleSessionManager.getCurrentRuleSession().getTaskController().suspend();
    } //suspend

    public void Resume() {
        try {
            if (cacheAgent != null)
                cacheAgent.resume();
            else
                resumeInMem();
        } catch (Exception e) {
            logger.log(Level.ERROR, e, e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    } //resume

    protected void suspendInMem() throws Exception {

        final String agentNameStr = "Agent " + agentName + "-" + 0;

        if (ruleServiceProvider.isCacheServerMode()) {
            logger.log(Level.INFO, agentNameStr + ": Suspended");
            return;
        }
        logger.log(Level.INFO, agentNameStr + ": Suspend Request");

        logger.log(Level.INFO, agentNameStr + ": Suspending Channel Threads");
        ruleServiceProvider.getRuleRuntime().getRuleSession(agentName).getTaskController().suspend();

        logger.log(Level.INFO, agentNameStr + ": Waiting for all threads to suspend");
        waitForThreadsToSuspend();


        logger.log(Level.INFO, agentNameStr + ": Suspended");
    }

    protected void resumeInMem() throws Exception {
        final String agentNameStr = "Agent " + agentName + "-" + 0;

        if (ruleServiceProvider.isCacheServerMode()) {
            logger.log(Level.INFO, agentNameStr + ": Resumed");
            return;
        }

        logger.log(Level.INFO, agentNameStr + ": Resuming Channel Threads");

        ruleServiceProvider.getRuleRuntime().getRuleSession(agentName).getTaskController().resume();

        logger.log(Level.INFO, agentNameStr + ": Resumed");
    }


    protected void waitForThreadsToSuspend() {
        while (true) {
            if (ruleServiceProvider.getRuleRuntime().getRuleSession(agentName).getTaskController().isRunning()) {
                continue;
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            return;
        }
    }




} //class
