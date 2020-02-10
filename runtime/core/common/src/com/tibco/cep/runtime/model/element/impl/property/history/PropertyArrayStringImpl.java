package com.tibco.cep.runtime.model.element.impl.property.history;

import com.tibco.cep.runtime.model.element.PropertyArrayString;
import com.tibco.cep.runtime.model.element.PropertyAtomString;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.property.AbstractPropertyArray;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 26, 2006
 * Time: 12:56:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyArrayStringImpl extends PropertyArrayImpl implements PropertyArrayString {

    public PropertyArrayStringImpl(ConceptImpl parent, int initialSize) {
        super(parent, initialSize);
    }

    @Override
    public AbstractPropertyArray copy(Object owner) {
        return _copy(new PropertyArrayStringImpl((ConceptImpl)owner, 0));
    }

    @Override
    protected PropertyAtomStringImpl newEmptyAtom(AbstractPropertyArray newOwner) {
        return new PropertyAtomStringImpl(newOwner.getHistorySize(), newOwner);
    }
    
    public int add(Object value) {
        return add(value.toString());
    }

    public void add(int index, Object value) {
        add(index, value.toString());
    }

    public int add(String value) {
        ((ConceptImpl)getParent()).checkSession();
        PropertyAtomStringImpl newAtom = new PropertyAtomStringImpl(getHistorySize(), this, value);
        return doAdd(newAtom);
    }

    public void add(int index, String value) {
        ((ConceptImpl)getParent()).checkSession();
        if (index < 0 || index > m_len) {
            throw new IndexOutOfBoundsException();
        }
        PropertyAtomStringImpl newAtom = new PropertyAtomStringImpl(getHistorySize(), this, value);
        doAdd(newAtom, index);
    }

    public void set(int index, String value) {
        ((ConceptImpl)getParent()).checkSession();
        if (index < 0 || index > m_len) {
            throw new IndexOutOfBoundsException();
        } else if(index == m_len) {
            add(index, value);
        } else {
            ((PropertyAtomStringImpl)get(index)).setString(value);
        }
    }

    public PropertyAtomString removePropertyAtom(String value) {
        ((ConceptImpl)getParent()).checkSession();
        if(m_properties == null) return null;
        if(value == null) {
            for(int i=0; i<m_len; i++) {
                if(((PropertyAtomStringImpl)m_properties[i]).getString() == null) {
                    return (PropertyAtomStringImpl) remove(i);
                }
            }
        }
        else {
            for(int i=0; i<m_len; i++) {
                if(value.equals(((PropertyAtomStringImpl)m_properties[i]).getString())) {
                    return (PropertyAtomStringImpl) remove(i);
                }
            }
        }
        return null;
    }
}