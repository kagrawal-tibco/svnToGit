package com.tibco.be.model.functions;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Sep 1, 2004
 * Time: 2:51:44 PM
 * To change this template use Options | File Templates.
 */
public interface Variable {
    public String getName();
    public Object getObject();
    public boolean isArray();
}
