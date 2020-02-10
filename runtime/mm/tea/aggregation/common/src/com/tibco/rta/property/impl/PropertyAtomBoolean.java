package com.tibco.rta.property.impl;

import com.tibco.rta.property.PropertyAtom;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 9/5/13
 * Time: 12:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyAtomBoolean implements PropertyAtom<Boolean> {

    private Boolean value;

    public PropertyAtomBoolean(Boolean value) {
        this.value = value;
    }

    @Override
    public Boolean getValue() {
        return value;
    }
}
