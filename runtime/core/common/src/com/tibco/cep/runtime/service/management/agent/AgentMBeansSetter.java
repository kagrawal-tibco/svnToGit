package com.tibco.cep.runtime.service.management.agent;

import com.tibco.cep.runtime.service.cluster.CacheAgent;
import com.tibco.cep.runtime.service.management.EntityMBeansSetter;


/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Mar 8, 2010
 * Time: 4:54:10 PM
 * To change this template use File | Settings | File Templates.
 */
public interface AgentMBeansSetter extends EntityMBeansSetter {
    //Used only in Cache Mode
    void setCacheAgent(CacheAgent cacheAgent);

    //Used only In-Memory Mode
    void setAgentName(String agentName);
}
