package com.tibco.cep.runtime.model.element.impl.property.simple;

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
public class PropertyArrayLongSimple extends PropertyArraySimple implements PropertyArrayLong {

    public PropertyArrayLongSimple(ConceptImpl parent, int initialSize) {
        super(parent, initialSize);
    }

    @Override
    public PropertyArraySimple copy(Object newOwner) {
        return _copy(new PropertyArrayLongSimple((ConceptImpl)newOwner, 0));
    }

    @Override
    protected PropertyAtomLongSimple newEmptyAtom(PropertyArraySimple newOwner) {
        return new PropertyAtomLongSimple(newOwner);
    }

    public int add(Object value) {
        return add(PropertyAtomLongSimple.objectToLong(value));
    }

    public void add(int index, Object value) {
        add(index, PropertyAtomLongSimple.objectToLong(value));
    }

    private PropertyAtomLongSimple addToEnd(boolean setValue, long value) {
        pre_addToEnd();
        PropertyAtomLongSimple atom;
        if(setValue)
            atom = new PropertyAtomLongSimple(this, value);
        else
            atom = new PropertyAtomLongSimple(this);
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
        m_properties[index] = new PropertyAtomLongSimple(this, value);
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
            throw new IndexOutOfBoundsException("Long value " + value + " at " + index);
        } else if(index == m_len) {
            add(value);
        } else {
            ((PropertyAtomLongSimple)get(index)).setLong(value);
        }
    }

    public PropertyAtomLong removePropertyAtom(long value) {
        ((ConceptImpl)getParent()).checkSession();
        if(m_len == 0) return null;
        for(int i=0; i<m_len; i++) {
            if(((PropertyAtomLongSimple)m_properties[i]).getLong() == value) {
                return (PropertyAtomLongSimple) remove(i);
            }
        }
        return null;
    }
}

