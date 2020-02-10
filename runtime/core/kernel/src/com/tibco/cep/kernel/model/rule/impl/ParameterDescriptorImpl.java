package com.tibco.cep.kernel.model.rule.impl;

import com.tibco.cep.kernel.model.rule.RuleFunction;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Aug 19, 2006
 * Time: 3:59:01 AM
 * To change this template use File | Settings | File Templates.
 */
public class ParameterDescriptorImpl implements RuleFunction.ParameterDescriptor {

    String  name;
    Class   type;
    boolean input;

    public ParameterDescriptorImpl(String name, Class type, boolean isInput) {
        this.name  = name;
        this.type  = type;
        this.input = isInput;
    }

    public String getName() {
        return name;
    }

    public Class getType() {
        return type;
    }

    public boolean isInput() {
        return input;
    }
}
