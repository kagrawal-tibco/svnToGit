package com.tibco.rta.property.impl;

import com.tibco.rta.property.PropertyAtom;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 9/5/13
 * Time: 11:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyAtomInt implements PropertyAtom<Integer> {

    private Integer value;

    public PropertyAtomInt(Integer value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
