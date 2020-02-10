package com.tibco.cep.runtime.service.management.agent;

import javax.management.openmbean.TabularDataSupport;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Mar 8, 2010
 * Time: 6:14:51 PM
 * To change this template use File | Settings | File Templates.
 */
public interface AgentOMMBean {
    public TabularDataSupport GetEvent(String id, String isExternal) throws Exception;
    public TabularDataSupport GetInstance(String id, String isExternal) throws Exception;
    public TabularDataSupport GetScorecards(String URI) throws Exception;

}
