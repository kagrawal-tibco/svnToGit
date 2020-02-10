package com.tibco.cep.designtime.model.service.channel.impl;


import java.util.regex.Pattern;

import com.tibco.cep.designtime.model.service.channel.PropertyDescriptor;

/*
* User: Nicolas Prade
* Date: Oct 1, 2009
* Time: 5:30:12 PM
*/


public class PropertyDescriptorImpl
        implements PropertyDescriptor {


    private String defaultValue;
    private String name;
    private Pattern pattern;
    private int type;


    public PropertyDescriptorImpl(
            String name,
            int type,
            String defaultValue,
            Pattern pattern) {
        this.defaultValue = defaultValue;
        this.name = name;
        this.type = type;
        this.pattern = pattern;
    }


    public String getDefaultValue() {
        return this.defaultValue;
    }


    public String getName() {
        return this.name;
    }


    public Pattern getPattern() {
        return this.pattern;
    }


    public int getType() {
        return this.type;
    }

}
