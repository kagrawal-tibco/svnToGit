package com.tibco.cep.runtime.model.element.impl.property.atomic;

import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.property.AbstractPropertyArray;
import com.tibco.cep.runtime.model.element.impl.property.AbstractPropertyAtom;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyArraySimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomSimple;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 26, 2006
 * Time: 1:17:35 AM
 * To change this template use File | Settings | File Templates.
 */
abstract public class PropertyArrayAtomic extends AbstractPropertyArray {
    protected PropertyArrayAtomic(ConceptImpl parent, int length) {
        super(parent, length);
    }
    
    @Override
    abstract public PropertyArrayAtomic copy(Object newOwner);
    
    public PropertyArrayAtomic _copy(PropertyArrayAtomic ret) {
    	return (PropertyArrayAtomic)super._copy(ret);
    }

    @Override
    protected AbstractPropertyAtom newEmptyAtom(AbstractPropertyArray newOwner) {
    	return newEmptyAtom((PropertyArrayAtomic)newOwner);
    }
    
	abstract protected PropertyAtomAtomic newEmptyAtom(PropertyArrayAtomic newOwner);
}
