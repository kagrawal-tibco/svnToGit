package com.tibco.rta.property.impl;

import com.tibco.rta.property.PropertyAtom;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 9/5/13
 * Time: 12:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyAtomString implements PropertyAtom<String> {

    private String value;

    public PropertyAtomString(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
