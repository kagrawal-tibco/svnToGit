package com.tibco.cep.runtime.model.element.impl.property.history;

import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.property.AbstractPropertyArray;
import com.tibco.cep.runtime.model.element.impl.property.AbstractPropertyAtom;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 23, 2006
 * Time: 8:43:40 PM
 * To change this template use File | Settings | File Templates.
 */
abstract public class PropertyArrayImpl extends AbstractPropertyArray {
    protected PropertyArrayImpl(ConceptImpl parent, int initialSize) {
        super(parent, initialSize);
    }

    @Override
    public int getHistorySize() {
        return getMetaProperty().getHistorySize();
    }
    
    public PropertyAtom add() {
        ((ConceptImpl)getParent()).checkSession();
        AbstractPropertyAtom atom = newEmptyAtom(this);
        doAdd(atom);
        return atom;
    }
    
    protected void doAdd(AbstractPropertyAtom newAtom, int index) {
    	pre_add(index);
        m_properties[index] = newAtom;
        m_len++;
        setConceptModified();
    }
    
    protected int doAdd(AbstractPropertyAtom newAtom) {
    	doAdd(newAtom, m_len);
        return m_len -1;
    }
}
