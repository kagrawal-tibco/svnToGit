package com.tibco.cep.runtime.model.element.impl.property.history;

import com.tibco.cep.runtime.model.element.PropertyArrayBoolean;
import com.tibco.cep.runtime.model.element.PropertyAtomBoolean;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.property.AbstractPropertyArray;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 26, 2006
 * Time: 12:48:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyArrayBooleanImpl extends PropertyArrayImpl implements PropertyArrayBoolean {

    public PropertyArrayBooleanImpl(ConceptImpl parent, int initialSize) {
        super(parent, initialSize);
    }

    @Override
    public AbstractPropertyArray copy(Object owner) {
        return _copy(new PropertyArrayBooleanImpl((ConceptImpl)owner, 0));
    }

    @Override
    protected PropertyAtomBooleanImpl newEmptyAtom(AbstractPropertyArray newOwner) {
        return new PropertyAtomBooleanImpl(newOwner.getHistorySize(), newOwner);
    }

    public int add(Object value) {
        return add(PropertyAtomBooleanImpl.objectToBoolean(value));
    }

    public void add(int index, Object value) {
        add(index, PropertyAtomBooleanImpl.objectToBoolean(value));
    }

    public int add(boolean value) {
        ((ConceptImpl)getParent()).checkSession();
        PropertyAtomBooleanImpl newAtom = new PropertyAtomBooleanImpl(getHistorySize(), this, value);
        return doAdd(newAtom);
    }

    public void add(int index, boolean value) {
        ((ConceptImpl)getParent()).checkSession();
        if (index < 0 || index > m_len) {
            throw new IndexOutOfBoundsException();
        }
        PropertyAtomBooleanImpl newAtom = new PropertyAtomBooleanImpl(getHistorySize(), this, value);
        doAdd(newAtom, index);
    }

    public void set(int index, boolean value) {
        ((ConceptImpl)getParent()).checkSession();
        if (index < 0 || index > m_len) {
            throw new IndexOutOfBoundsException();
        } else if(index == m_len) {
            add(index, value);
        } else {
            ((PropertyAtomBooleanImpl)get(index)).setBoolean(value);
        }
    }

    public PropertyAtomBoolean removePropertyAtom(boolean value) {
        ((ConceptImpl)getParent()).checkSession();        
        if(m_properties == null) return null;
        for(int i=0; i<m_len; i++) {
            if(((PropertyAtomBoolean)m_properties[i]).getBoolean() == value) {
                return (PropertyAtomBoolean) remove(i);
            }
        }
        return null;
    }
}