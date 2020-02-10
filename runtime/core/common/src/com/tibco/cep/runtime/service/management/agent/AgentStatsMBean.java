package com.tibco.cep.runtime.service.management.agent;

import com.tibco.cep.runtime.service.management.agent.impl.StatsMBeanException;

/**
 * Created with IntelliJ IDEA.
 * User: hlouro
 * Date: 10/11/12
 * Time: 2:54 PM
 * To change this template use File | Settings | File Templates.
 */
public interface AgentStatsMBean {
    int register(String uri) throws StatsMBeanException;
    int unregister(String uri) throws StatsMBeanException;
}
