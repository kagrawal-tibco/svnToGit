package com.tibco.cep.runtime.model.element.impl.property.atomic;

import com.tibco.cep.runtime.model.element.PropertyArrayLong;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.PropertyAtomLong;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 26, 2006
 * Time: 2:28:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyArrayLongAtomic extends PropertyArrayAtomic implements PropertyArrayLong {
    public PropertyArrayLongAtomic(ConceptImpl parent, int initialSize) {
        super(parent, initialSize);
    }

    @Override
    public PropertyArrayAtomic copy(Object newOwner) {
        return _copy(new PropertyArrayLongAtomic((ConceptImpl)newOwner, 0));
    }

    @Override
    protected PropertyAtomLongAtomic newEmptyAtom(PropertyArrayAtomic newOwner) {
        return new PropertyAtomLongAtomic(newOwner);
    }

    public int add(Object value) {
        return add(PropertyAtomLongAtomic.objectToLong(value));
    }

    public void add(int index, Object value) {
        add(index, PropertyAtomLongAtomic.objectToLong(value));
    }

    private PropertyAtomLongAtomic addToEnd(boolean setValue, long value) {
        pre_addToEnd();
        PropertyAtomLongAtomic atom;
        if(setValue)
            atom = new PropertyAtomLongAtomic(this, value);
        else
            atom = new PropertyAtomLongAtomic(this);
        m_properties[m_len] = atom;
        m_len++;
        setConceptModified();
        return atom;
    }


    public int add(long value) {
        ((ConceptImpl)getParent()).checkSession();
        addToEnd(true, value);
        return m_len -1;
    }

    public void add(int index, long value) {
        pre_add(index);
        m_properties[index] = new PropertyAtomLongAtomic(this, value);
        m_len++;
        setConceptModified();
    }

    public PropertyAtom add() {
        ((ConceptImpl)getParent()).checkSession();
        return addToEnd(false, 0);
    }

    public void set(int index, long value) {
        ((ConceptImpl)getParent()).checkSession();
        if (index < 0 || index > m_len) {
            throw new IndexOutOfBoundsException();
        } else if(index == m_len) {
            add(value);
        } else {
            ((PropertyAtomLongAtomic)get(index)).setLong(value);
        }
    }

    public PropertyAtomLong removePropertyAtom(long value) {
        ((ConceptImpl)getParent()).checkSession();
        if(m_len == 0) return null;
        for(int i=0; i<m_len; i++) {
            if(((PropertyAtomLongAtomic)m_properties[i]).getLong() == value) {
                return (PropertyAtomLongAtomic) remove(i);
            }
        }
        return null;
    }
}

