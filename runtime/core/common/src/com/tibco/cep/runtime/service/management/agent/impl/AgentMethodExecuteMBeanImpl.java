package com.tibco.cep.runtime.service.management.agent.impl;

import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.management.EntityMBeansHelper;
import com.tibco.cep.runtime.service.management.agent.AgentMBeansSetter;
import com.tibco.cep.runtime.service.management.agent.AgentMethodExecuteMBean;
import com.tibco.cep.runtime.service.management.agent.MethodToBeExecuted;
import com.tibco.cep.runtime.service.management.exception.BEMMUserActivityException;
import com.tibco.cep.runtime.session.RuleServiceProvider;

import java.util.HashMap;


/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Oct 21, 2010
 * Time: 12:28:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class AgentMethodExecuteMBeanImpl extends EntityMBeansHelper implements AgentMethodExecuteMBean, AgentMBeansSetter {
    private RuleServiceProvider ruleServiceProvider;
    private CacheAgent cacheAgent;
    private String[] methName = {"StartFileBasedRecorder","StopFileBasedRecorder","GetWorkingMemoryDump"};
    private HashMap<String, MethodToBeExecuted> methodNameToClassObj;

    public AgentMethodExecuteMBeanImpl() {
        methodNameToClassObj = new HashMap<String, MethodToBeExecuted>();
    }

    public void setRuleServiceProvider(RuleServiceProvider ruleServiceProvider) {
        this.ruleServiceProvider = ruleServiceProvider;
    }

    public void setLogger(Logger logger) {
        super.logger = logger;
    }

    public void setCacheAgent(CacheAgent cacheAgent) {
        this.cacheAgent = cacheAgent;
        // The reason registration is put in here is related with the order this class'
        // setter methods are called in EntityMBeansManager. You need to have rsp, logger,
        // cacheagent set before passing them to the constructors called in register()
        register();
    }

    public void setAgentName(String agentName) {
        super.agentName = agentName;
    }

    private void register() {
        AgentRecorderMBeanImpl armbi = new AgentRecorderMBeanImpl(cacheAgent, logger, ruleServiceProvider);
        methodNameToClassObj.put(methName[0], armbi);
        methodNameToClassObj.put(methName[1], armbi);
        methodNameToClassObj.put(methName[2], new AgentWMMBeanImpl(cacheAgent, logger, ruleServiceProvider));
    }


    public Object ExecuteMethod(String methodName, String args) throws Exception {
        methodName = methodName.trim();
        args = args.trim();
        validateMethName(methodName);

        return methodNameToClassObj.get(methodName).execute(methodName, args);

    }

    private void validateMethName(String methodName) throws BEMMUserActivityException {
        if (! methodNameToClassObj.containsKey(methodName)) {
            throw new BEMMUserActivityException("Cannot execute method \'"+ methodName + "\'.\n" +
                                                "Consult documentation or contact TIBCO support for more information");
        }

        /* if (! methodNameToClassObj.containsKey(methodName) ) { //method is not registered and cannot be executed
            StringBuilder sb = new StringBuilder();
            for(String s : methName) {
                sb.append(s).append(", ");
            }
            sb.deleteCharAt(sb.length()-2);

            throw new BEMMUserActivityException("Cannot execute method \'"+ methodName + "\'.\n" +
                    "List of methods that is possible to execute: " + sb.toString().trim());
        } */
    }

}
