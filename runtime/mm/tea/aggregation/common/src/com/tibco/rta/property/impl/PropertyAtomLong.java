package com.tibco.rta.property.impl;

import com.tibco.rta.property.PropertyAtom;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 9/5/13
 * Time: 12:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyAtomLong implements PropertyAtom<Long> {

    private Long value;

    public PropertyAtomLong(Long value) {
        this.value = value;
    }

    @Override
    public Long getValue() {
        return value;
    }
}
