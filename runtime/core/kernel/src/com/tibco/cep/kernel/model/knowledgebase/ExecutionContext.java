package com.tibco.cep.kernel.model.knowledgebase;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Apr 11, 2007
 * Time: 12:17:51 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ExecutionContext {

    public Object getCause();

    public Object getParams();

    public String[] info();
}
