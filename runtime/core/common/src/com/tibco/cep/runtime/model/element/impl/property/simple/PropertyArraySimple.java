package com.tibco.cep.runtime.model.element.impl.property.simple;

import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.property.AbstractPropertyArray;
import com.tibco.cep.runtime.model.element.impl.property.AbstractPropertyAtom;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 26, 2006
 * Time: 1:17:35 AM
 * To change this template use File | Settings | File Templates.
 */
abstract public class PropertyArraySimple extends AbstractPropertyArray {
    protected PropertyArraySimple(ConceptImpl parent, int length) {
        super(parent, length);
    }
    
    @Override
    abstract public PropertyArraySimple copy(Object newOwner);
    
    public PropertyArraySimple _copy(PropertyArraySimple ret) {
    	return (PropertyArraySimple)super._copy(ret);
    }

    @Override
    protected AbstractPropertyAtom newEmptyAtom(AbstractPropertyArray newOwner) {
    	return newEmptyAtom((PropertyArraySimple)newOwner);
    }
    
	abstract protected PropertyAtomSimple newEmptyAtom(PropertyArraySimple newOwner);
}
