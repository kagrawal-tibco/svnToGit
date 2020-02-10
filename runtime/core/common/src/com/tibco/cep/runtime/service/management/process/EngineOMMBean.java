package com.tibco.cep.runtime.service.management.process;

import javax.management.openmbean.TabularDataSupport;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Sep 22, 2009
 * Time: 12:29:07 PM
 * To change this template use File | Settings | File Templates.
 */
public interface EngineOMMBean {
    //OM Operations
//    public void forceOMCheckpoint(String session);
    public TabularDataSupport GetEvent(String sessionName, String id, String isExternal) throws Exception;
    public TabularDataSupport GetInstance(String sessionName, String id, String isExternal) throws Exception;
    public TabularDataSupport GetScorecards(String sessionName, String URI) throws Exception;
    public TabularDataSupport GetInstanceByUri(String sessionName, String extId, String uri) throws Exception;
}
