package com.tibco.cep.runtime.service.management.agent;

import com.tibco.cep.runtime.service.management.exception.BEMMUserActivityException;

import javax.management.openmbean.TabularDataSupport;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Sep 22, 2009
 * Time: 12:32:26 PM
 * To change this template use File | Settings | File Templates.
 */
public interface AgentWMMBean {
    //WM Operations  
    public TabularDataSupport GetRules() throws Exception;
    public TabularDataSupport GetRule(String URI) throws Exception;
    public TabularDataSupport ActivateRule(String URI) throws BEMMUserActivityException;
    public TabularDataSupport DeactivateRule(String URI) throws BEMMUserActivityException;
    public void ResetTotalNumberRulesFired() throws Exception;
    public TabularDataSupport GetTotalNumberRulesFired() throws BEMMUserActivityException;
//    public TabularDataSupport GetWorkingMemoryDump() throws Exception;
    public TabularDataSupport GetRuleSession() throws Exception;

}//interface
