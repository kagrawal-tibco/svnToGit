package com.tibco.cep.runtime.service.management.process;

import javax.management.openmbean.TabularDataSupport;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Mar 8, 2010
 * Time: 7:10:06 PM
 */
public interface EngineWMMBean {
    public TabularDataSupport GetRuleSessions() throws Exception;
    public TabularDataSupport GetRules(String sessionName) throws Exception;
    public TabularDataSupport GetRule(String sessionName, String URI) throws Exception;
    public TabularDataSupport ActivateRule(String sessionName, String URI) throws Exception;
    public TabularDataSupport DeactivateRule(String sessionName, String URI) throws Exception;
    public void ResetTotalNumberRulesFired(String sessionName)throws Exception;
    public TabularDataSupport GetTotalNumberRulesFired(String sessionName)throws Exception;
//    public TabularDataSupport GetWorkingMemoryDump(String sessionName) throws Exception;
    
    //public TabularDataSupport GetEntityStatistic(String ruleSessionName, String eventUri) throws Exception;
}
