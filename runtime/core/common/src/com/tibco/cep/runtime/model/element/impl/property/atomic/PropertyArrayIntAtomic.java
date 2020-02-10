package com.tibco.cep.runtime.model.element.impl.property.atomic;

import com.tibco.cep.runtime.model.element.PropertyArrayInt;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.PropertyAtomInt;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 26, 2006
 * Time: 2:27:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyArrayIntAtomic extends PropertyArrayAtomic implements PropertyArrayInt {

    public PropertyArrayIntAtomic(ConceptImpl parent, int initialSize) {
        super(parent, initialSize);
    }

    @Override
    public PropertyArrayAtomic copy(Object newOwner) {
        return _copy(new PropertyArrayIntAtomic((ConceptImpl)newOwner, 0));
    }

    @Override
    protected PropertyAtomIntAtomic newEmptyAtom(PropertyArrayAtomic newOwner) {
        return new PropertyAtomIntAtomic(newOwner);
    }

    private PropertyAtomIntAtomic addToEnd(boolean setValue, int value) {
        pre_addToEnd();
        PropertyAtomIntAtomic atom;
        if(setValue)
            atom = new PropertyAtomIntAtomic(this, value);
        else
            atom = new PropertyAtomIntAtomic(this);
        m_properties[m_len] = atom;
        m_len++;
        setConceptModified();
        return atom;
    }

    public int add(Object value) {
        return add(PropertyAtomIntAtomic.objectToInt(value));
    }

    public void add(int index, Object value) {
        add(index, PropertyAtomIntAtomic.objectToInt(value));
    }

    public int add(int value) {
        ((ConceptImpl)getParent()).checkSession();
        addToEnd(true, value);
        return m_len -1;
    }

    public void add(int index, int value) {
        pre_add(index);
        m_properties[index] = new PropertyAtomIntAtomic(this, value);
        m_len++;
        setConceptModified();
    }

    public PropertyAtom add() {
        ((ConceptImpl)getParent()).checkSession();
        return addToEnd(false, 0);
    }

    public void set(int index, int value) {
    	((ConceptImpl)getParent()).checkSession();
        if(index < 0){
        	index = 0;        	
        }
        if (index > m_len) {
            throw new IndexOutOfBoundsException();
        } else if(index == m_len) {
            add(value);
        } else {
            ((PropertyAtomIntAtomic)get(index)).setInt(value);
        }
    }

    public PropertyAtomInt removePropertyAtom(int value) {
        ((ConceptImpl)getParent()).checkSession();
        if(m_len == 0) return null;
        for(int i=0; i<m_len; i++) {
            if(((PropertyAtomIntAtomic)m_properties[i]).getInt() == value) {
                return (PropertyAtomIntAtomic) remove(i);
            }
        }
        return null;
    }
}

