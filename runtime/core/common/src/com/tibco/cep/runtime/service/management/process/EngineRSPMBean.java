package com.tibco.cep.runtime.service.management.process;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Sep 22, 2009
 * Time: 12:31:58 PM
 * To change this template use File | Settings | File Templates.
 */
public interface EngineRSPMBean {
    //RSP Operations
    //    TODO: There are issues with the suspendRuleServiceProvider backend code that cause BEMM to hang. So we are going
    //    to hide the methods for now, until we fix this problem.
    public void ResumeRuleServiceProvider();
    public void SuspendRuleServiceProvider();
}
