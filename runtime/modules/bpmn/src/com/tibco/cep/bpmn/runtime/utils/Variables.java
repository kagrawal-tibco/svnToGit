package com.tibco.cep.bpmn.runtime.utils;

import java.util.LinkedHashMap;
import java.util.Map;

/*
* Author: Suresh Subramani / Date: 1/9/12 / Time: 11:47 AM
*/
public class Variables {

    Map<String,Object> vars = new LinkedHashMap<String,Object>();

    public Variables() {

    }

    public void setVariable(String name, Object value)
    {
        vars.put(name, value);
    }

    public Object getVariable(String name) {
        return vars.get(name);
    }

    public Map<String,Object> asMap()
    {
        return vars;
    }
    
    
    public boolean containsKey(String key) {
    	return vars.containsKey(key);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("{vars=").append(vars);
        sb.append('}');
        return sb.toString();
    }
}
