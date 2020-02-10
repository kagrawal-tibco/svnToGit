package com.tibco.be.util.packaging.descriptors.impl;
/*
 * User: nprade
 * Date: Feb 4, 2010
 * Time: 3:50:01 PM
 */

import com.tibco.be.util.packaging.Constants.DD;
import com.tibco.xml.data.primitive.ExpandedName;


public class NameValuePairBoolean
        extends NameValuePairImpl {


    public NameValuePairBoolean() {
        super();
    }


    public NameValuePairBoolean(String name) {
        super(name);
    }


    public NameValuePairBoolean(String name, boolean value) {
        super(name, String.valueOf(value));
    }


    public NameValuePairBoolean(String name, boolean value, String description, boolean requiresConfiguration) {
        super(name, String.valueOf(value), description, requiresConfiguration);
    }


    @Override
    public ExpandedName getTypeXName() {
        return DD.XNames.NAME_VALUE_PAIR_BOOLEAN;
    }


    public void setValue(boolean value) {
        super.setValue(String.valueOf(value));
    }


    @Override
    public void setValue(String value) {
        this.setValue(Boolean.valueOf(value));
    }


}