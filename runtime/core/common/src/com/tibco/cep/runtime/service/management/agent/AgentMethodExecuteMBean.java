package com.tibco.cep.runtime.service.management.agent;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Oct 20, 2010
 * Time: 5:40:17 PM
 * To change this template use File | Settings | File Templates.
 */
public interface AgentMethodExecuteMBean {
    public Object ExecuteMethod(String methodName, String args) throws Exception;
}
