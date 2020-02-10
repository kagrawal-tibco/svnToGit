package com.tibco.cep.runtime.service.management.agent.impl;

import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.management.RecorderMethodsImpl;
import com.tibco.cep.runtime.service.management.agent.AgentMBeansSetter;
import com.tibco.cep.runtime.service.management.agent.AgentRecorderMBean;
import com.tibco.cep.runtime.service.management.agent.MethodToBeExecuted;
import com.tibco.cep.runtime.session.RuleServiceProvider;

import javax.management.openmbean.TabularDataSupport;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Jan 25, 2010
 * Time: 4:51:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class AgentRecorderMBeanImpl extends RecorderMethodsImpl implements AgentRecorderMBean, AgentMBeansSetter, MethodToBeExecuted {
    private CacheAgent cacheAgent;

    public AgentRecorderMBeanImpl(CacheAgent cacheAgent, Logger logger, RuleServiceProvider rsp) {
        this.cacheAgent = cacheAgent;
        super.logger = logger;
        super.ruleServiceProvider = rsp;
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

    public TabularDataSupport StartFileBasedRecorder(String directory, String mode) throws Exception {
        return StartFileBasedRecorder(getSessionName(cacheAgent),directory, mode);
    }

    public TabularDataSupport execute (String methodName, String args) throws Exception {
        if (methodName.equals("StartFileBasedRecorder")) {
            String[] argSplit = args.split(" ",2);
            if (argSplit.length == 0) {
                return StartFileBasedRecorder("","");   //dir and mode not specified
            } else if (argSplit.length == 1) {
                return StartFileBasedRecorder(argSplit[0],"");  //mode not specified
            } else {
                //picks the first string of args as the directory and everything else as the mode
                return StartFileBasedRecorder(argSplit[0], argSplit[1]);
            }

        } else if (methodName.equals("StopFileBasedRecorder")) {
            return StopFileBasedRecorder();

        } else     // the code should never get here; just to be safe
            return null;
    }

    public TabularDataSupport StopFileBasedRecorder() throws Exception {
        return StopFileBasedRecorder(getSessionName(cacheAgent));
    }
} //class
