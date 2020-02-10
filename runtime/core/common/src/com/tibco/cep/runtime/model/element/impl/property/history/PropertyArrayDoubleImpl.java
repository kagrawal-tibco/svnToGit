package com.tibco.cep.runtime.model.element.impl.property.history;

import com.tibco.cep.runtime.model.element.PropertyArrayDouble;
import com.tibco.cep.runtime.model.element.PropertyAtomDouble;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.property.AbstractPropertyArray;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 26, 2006
 * Time: 12:51:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyArrayDoubleImpl extends  PropertyArrayImpl implements PropertyArrayDouble {

    public PropertyArrayDoubleImpl(ConceptImpl parent, int initialSize) {
        super(parent, initialSize);
    }

    @Override
    public AbstractPropertyArray copy(Object owner) {
        return _copy(new PropertyArrayDoubleImpl((ConceptImpl)owner, 0));
    }

    @Override
    protected PropertyAtomDoubleImpl newEmptyAtom(AbstractPropertyArray newOwner) {
        return new PropertyAtomDoubleImpl(newOwner.getHistorySize(), newOwner);
    }

    public int add(Object value) {
        return add(PropertyAtomDoubleImpl.objectToDouble(value));
    }

    public void add(int index, Object value) {
        add(index, PropertyAtomDoubleImpl.objectToDouble(value));
    }

    public int add(double value) {
        ((ConceptImpl)getParent()).checkSession();
        PropertyAtomDoubleImpl newAtom = new PropertyAtomDoubleImpl(getHistorySize(), this, value);
        return doAdd(newAtom);
    }

    public void add(int index, double value) {
        ((ConceptImpl)getParent()).checkSession();
        if (index < 0 || index > m_len) {
            throw new IndexOutOfBoundsException();
        }
        PropertyAtomDoubleImpl newAtom = new PropertyAtomDoubleImpl(getHistorySize(), this, value);
        doAdd(newAtom, index);
    }

    public void set(int index, double value) {
        ((ConceptImpl)getParent()).checkSession();
        if (index < 0 || index > m_len) {
            throw new IndexOutOfBoundsException();
        } else if(index == m_len) {
            add(index, value);
        } else {
            ((PropertyAtomDoubleImpl)get(index)).setDouble(value);
        }
    }

    public PropertyAtomDouble removePropertyAtom(double value) {
        ((ConceptImpl)getParent()).checkSession();
        if(m_properties == null) return null;
        for(int i=0; i<m_len; i++) {
            if(((PropertyAtomDoubleImpl)m_properties[i]).getDouble() == value) {
                return (PropertyAtomDoubleImpl) remove(i);
            }
        }
        return null;
    }
}

