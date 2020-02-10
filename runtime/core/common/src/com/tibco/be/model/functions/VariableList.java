package com.tibco.be.model.functions;

import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Sep 1, 2004
 * Time: 2:51:16 PM
 * To change this template use Options | File Templates.
 */
public interface VariableList {
    public void addVariable(Variable var);
    public void addVariable(String varName, Object holder);
    public Iterator variables();
    public Variable getVariable(String varName);
    public int size();
}
