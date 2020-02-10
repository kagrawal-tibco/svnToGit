package com.tibco.cep.runtime.model.element.impl.property.history;

import com.tibco.cep.runtime.model.element.PropertyArrayLong;
import com.tibco.cep.runtime.model.element.PropertyAtomLong;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.property.AbstractPropertyArray;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 26, 2006
 * Time: 12:55:09 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyArrayLongImpl extends PropertyArrayImpl implements PropertyArrayLong {
    public PropertyArrayLongImpl(ConceptImpl parent, int initialSize) {
        super(parent, initialSize);
    }

    @Override
    public AbstractPropertyArray copy(Object owner) {
        return _copy(new PropertyArrayLongImpl((ConceptImpl)owner, 0));
    }
    
    @Override
    protected PropertyAtomLongImpl newEmptyAtom(AbstractPropertyArray newOwner) {
        return new PropertyAtomLongImpl(newOwner.getHistorySize(), newOwner);
    }

    public int add(Object value) {
        return add(PropertyAtomLongImpl.objectToLong(value));
    }

    public void add(int index, Object value) {
        add(index, PropertyAtomLongImpl.objectToLong(value));
    }

    public int add(long value) {
        ((ConceptImpl)getParent()).checkSession();
        PropertyAtomLongImpl newAtom = new PropertyAtomLongImpl(getHistorySize(), this, value);
        return doAdd(newAtom);
    }

    public void add(int index, long value) {
        ((ConceptImpl)getParent()).checkSession();
        if (index < 0 || index > m_len) {
            throw new IndexOutOfBoundsException();
        }
        PropertyAtomLongImpl newAtom = new PropertyAtomLongImpl(getHistorySize(), this, value);
        doAdd(newAtom, index);
    }

    public void set(int index, long value) {
        ((ConceptImpl)getParent()).checkSession();
        if (index < 0 || index > m_len) {
            throw new IndexOutOfBoundsException();
        } else if(index == m_len) {
            add(index, value);
        } else {
            ((PropertyAtomLongImpl)get(index)).setLong(value);
        }
    }

    public PropertyAtomLong removePropertyAtom(long value) {
        ((ConceptImpl)getParent()).checkSession();
        if(m_properties == null) return null;
        for(int i=0; i<m_len; i++) {
            if(((PropertyAtomLongImpl)m_properties[i]).getLong() == value) {
                return (PropertyAtomLongImpl) remove(i);
            }
        }
        return null;
    }
}