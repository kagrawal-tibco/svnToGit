package com.tibco.cep.runtime.model.element.impl.property.history;

import com.tibco.cep.runtime.model.element.PropertyArrayInt;
import com.tibco.cep.runtime.model.element.PropertyAtomInt;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.property.AbstractPropertyArray;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 26, 2006
 * Time: 12:53:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyArrayIntImpl extends PropertyArrayImpl implements PropertyArrayInt {

    public PropertyArrayIntImpl(ConceptImpl parent, int initialSize) {
        super(parent, initialSize);
    }

    @Override
    public AbstractPropertyArray copy(Object owner) {
        return _copy(new PropertyArrayIntImpl((ConceptImpl)owner, 0));
    }

    @Override
    protected PropertyAtomIntImpl newEmptyAtom(AbstractPropertyArray newOwner) {
        return new PropertyAtomIntImpl(newOwner.getHistorySize(), newOwner);
    }

    public int add(Object value) {
        return add(PropertyAtomIntImpl.objectToInt(value));
    }

    public void add(int index, Object value) {
        add(index, PropertyAtomIntImpl.objectToInt(value));
    }

    public int add(int value) {
        ((ConceptImpl)getParent()).checkSession();
        PropertyAtomIntImpl newAtom = new PropertyAtomIntImpl(getHistorySize(), this, value);
        return doAdd(newAtom);
    }

    public void add(int index, int value) {
        ((ConceptImpl)getParent()).checkSession();
        if (index < 0 || index > m_len) {
            throw new IndexOutOfBoundsException();
        }
        PropertyAtomIntImpl newAtom = new PropertyAtomIntImpl(getHistorySize(), this, value);
        doAdd(newAtom, index);
    }

    public void set(int index, int value) {
        ((ConceptImpl)getParent()).checkSession();
        if (index < 0 || index > m_len) {
            throw new IndexOutOfBoundsException((index < 0) ? index + " < 0" : index + " > " + m_len);
        } else if(index == m_len) {
            add(index, value);
        } else {
            ((PropertyAtomIntImpl)get(index)).setInt(value);
        }
    }

    public PropertyAtomInt removePropertyAtom(int value) {
        ((ConceptImpl)getParent()).checkSession();
        if(m_properties == null) return null;
        for(int i=0; i<m_len; i++) {
            if(((PropertyAtomIntImpl)m_properties[i]).getInt() == value) {
                return (PropertyAtomIntImpl) remove(i);
            }
        }
        return null;
    }
}

