package com.tibco.cep.runtime.model.element.impl.property.atomic;

import com.tibco.cep.runtime.model.element.impl.property.AbstractPropertyAtom;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 26, 2006
 * Time: 1:12:27 AM
 * To change this template use File | Settings | File Templates.
 */
abstract public class PropertyAtomAtomic extends AbstractPropertyAtom {

	protected PropertyAtomAtomic(Object owner) {
        super(owner);
    }

    public boolean isSet() {
        return true;
    }

    public void setIsSet() {}

    public void clearIsSet() {}
}