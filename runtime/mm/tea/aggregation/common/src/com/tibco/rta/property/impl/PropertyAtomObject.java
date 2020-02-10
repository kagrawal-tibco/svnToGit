package com.tibco.rta.property.impl;

import com.tibco.rta.property.PropertyAtom;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 15/5/13
 * Time: 4:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyAtomObject implements PropertyAtom<Object> {

    private Object value;

    public PropertyAtomObject(Object value) {
        this.value = value;
    }

    @Override
    public Object getValue() {
        return value;
    }
}
