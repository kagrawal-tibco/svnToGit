package com.tibco.cep.runtime.model.element.impl.property.history;

import java.util.Calendar;

import com.tibco.cep.runtime.model.element.PropertyArrayDateTime;
import com.tibco.cep.runtime.model.element.PropertyAtomDateTime;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.property.AbstractPropertyArray;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 26, 2006
 * Time: 12:50:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyArrayDateTimeImpl extends PropertyArrayImpl implements PropertyArrayDateTime {

    public PropertyArrayDateTimeImpl(ConceptImpl parent, int initialSize) {
        super(parent, initialSize);
    }

    @Override
    public AbstractPropertyArray copy(Object owner) {
        return _copy(new PropertyArrayDateTimeImpl((ConceptImpl)owner, 0));
    }

    @Override
    protected PropertyAtomDateTimeImpl newEmptyAtom(AbstractPropertyArray newOwner) {
        return new PropertyAtomDateTimeImpl(newOwner.getHistorySize(), newOwner);
    }

    public int add(Object value) {
        return add(PropertyAtomDateTimeImpl.objectToDateTime(value));
    }

    public void add(int index, Object value) {
        add(index, PropertyAtomDateTimeImpl.objectToDateTime(value));
    }

    public int add(Calendar value) {
        ((ConceptImpl)getParent()).checkSession();
        PropertyAtomDateTimeImpl newAtom = new PropertyAtomDateTimeImpl(getHistorySize(), this, value);
        return doAdd(newAtom);
    }

    public void add(int index, Calendar value) {
        ((ConceptImpl)getParent()).checkSession();
        if (index < 0 || index > m_len) {
            throw new IndexOutOfBoundsException();
        }
        PropertyAtomDateTimeImpl newAtom = new PropertyAtomDateTimeImpl(getHistorySize(), this, value);
        doAdd(newAtom, index);
    }

    public void set(int index, Calendar value) {
        ((ConceptImpl)getParent()).checkSession();
        if (index < 0 || index > m_len) {
            throw new IndexOutOfBoundsException();
        } else if(index == m_len) {
            add(index, value);
        } else {
            ((PropertyAtomDateTimeImpl)get(index)).setDateTime(value);
        }
    }

    public PropertyAtomDateTime removePropertyAtom(Calendar value) {
        ((ConceptImpl)getParent()).checkSession();        
        if(m_properties == null) return null;
        if(value == null) {
            for(int i=0; i<m_len; i++) {
                if(((PropertyAtomDateTimeImpl)m_properties[i]).getDateTime() == null) {
                    return (PropertyAtomDateTimeImpl) remove(i);
                }
            }
        }
        else {
            for(int i=0; i<m_len; i++) {
                if(value.equals(((PropertyAtomDateTimeImpl)m_properties[i]).getDateTime())) {
                    return (PropertyAtomDateTimeImpl) remove(i);
                }
            }
        }
        return null;
    }
}