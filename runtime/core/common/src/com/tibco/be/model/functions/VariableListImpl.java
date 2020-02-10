package com.tibco.be.model.functions;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: ssubrama
 * Date: Sep 1, 2004
 * Time: 2:54:19 PM
 * To change this template use Options | File Templates.
 */
public class VariableListImpl implements VariableList {

    HashMap variables;

    public VariableListImpl() {
        variables = new HashMap();
    }
    
    public static VariableListImpl newVariableListImpl(String[] varNames, Object[] holders) {
        VariableListImpl varList = new VariableListImpl();
        if(varNames != null && holders != null && varNames.length == holders.length) {
            for(int ii = 0; ii < holders.length; ii++) {
                String varName = varNames[ii];
                boolean isArray = varName.endsWith("*");
                
                //* at the end of a name means it is an array
                if(isArray) {
                    varName = varName.substring(0, varName.length() - 1);
                }
                varList.addVariable(varName, holders[ii], isArray);
            }
        }
        return varList;
    }

    public void addVariable(Variable var) {
        variables.put(var.getName(), var);
    }

    public void addVariable(String varName, Object holder) {
        variables.put(varName, new VariableImpl(varName, holder));
    }
    
    public void addVariable(String varName, Object holder, boolean isArray) {
        variables.put(varName, new VariableImpl(varName, holder, isArray));
    }

    public Variable getVariable(String varName) {
        return (Variable) variables.get(varName);
    }

    public Iterator variables() {
        return variables.values().iterator();
    }

    public int size() {
        return variables.size();
    }

}
