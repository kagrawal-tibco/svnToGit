package com.tibco.be.model.functions;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Sep 1, 2004
 * Time: 2:55:04 PM
 * To change this template use Options | File Templates.
 */
public class VariableImpl implements Variable {

    String varName;
    Object varData;
    boolean isArray;

    public VariableImpl(String name, Object obj) {
        this(name, obj, false);
    }
    public VariableImpl(String name, Object obj, boolean isArray) {
        varName = name;
        varData = obj;
        this.isArray = isArray;
    }

    public String getName() {
        return varName;
    }

    public Object getObject() {
        return varData;
    }

    public boolean isArray() {
        return isArray;
    }
}
