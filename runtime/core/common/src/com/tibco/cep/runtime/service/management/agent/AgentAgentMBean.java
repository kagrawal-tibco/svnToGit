package com.tibco.cep.runtime.service.management.agent;

import javax.management.openmbean.TabularDataSupport;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Sep 22, 2009
 * Time: 12:57:57 AM
 * To change this template use File | Settings | File Templates.
 */
public interface AgentAgentMBean {

    // Engine operations
    public TabularDataSupport GetNumberOfEvents() throws Exception;
    public TabularDataSupport GetNumberOfInstances() throws Exception;
    public void  Suspend();
    public void  Resume();

} //interface
