package com.tibco.cep.runtime.model.element.impl.property.raw;

import com.tibco.cep.runtime.model.element.impl.property.AbstractPropertyAtom;

/*
 * Notes about raw properties.  They don't have an associated metaproperty.
 * This means they must implement getName as an overridden method to avoid storing a copy of the name in each instance.
 * They don't just extend PropertyAtom<Type>Simple and override getName and etc. because the these classes have lots
 * of fields that are not used by the raw properties and would be wasted memory (plus other reasons not remembered?).
 * The code in ConceptImpl does not know about raw properties so they do not have an entry in the concept's dirty bit array.
 * Therefore raw properties should never call setConceptModified().
 * To dirty the concept for persistence use ConceptImpl.setPersistenceModified()
 * This means that setting be.backingstore.unmodified.skip is not compatible with BPMN because it uses
 * dirty bit statuses to avoid writing persisting unchanged properties to the backing store.
 */

abstract public class PropertyAtomRaw extends AbstractPropertyAtom {
    protected PropertyAtomRaw(Object owner) {
        super(owner);
    }
    
    @Override
    final public PropertyAtomRaw copy(Object newOwner) {
    	throw new UnsupportedOperationException("copy not implemented for raw properties");
    }
    
    @Override
    abstract public String getName();
    
    @Override
    protected boolean setConceptModified() {
    	return false;
    }
}