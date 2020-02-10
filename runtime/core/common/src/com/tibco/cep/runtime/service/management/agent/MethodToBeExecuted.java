package com.tibco.cep.runtime.service.management.agent;

/**
 * Created by IntelliJ IDEA.
 * User: hlouro
 * Date: Oct 21, 2010
 * Time: 2:21:02 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MethodToBeExecuted {
     /** Every method that is to be executed using the execute method must implement this interface */
    public Object execute (String methodName, String args) throws Exception;
}
