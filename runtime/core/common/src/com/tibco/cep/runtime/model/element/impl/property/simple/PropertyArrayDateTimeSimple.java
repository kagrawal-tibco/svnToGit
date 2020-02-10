package com.tibco.cep.runtime.model.element.impl.property.simple;

import java.util.Calendar;

import com.tibco.cep.runtime.model.element.PropertyArrayDateTime;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.PropertyAtomDateTime;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 26, 2006
 * Time: 2:24:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyArrayDateTimeSimple extends PropertyArraySimple implements PropertyArrayDateTime {

    public PropertyArrayDateTimeSimple(ConceptImpl parent, int initialSize) {
        super(parent, initialSize);
    }

    @Override
    public PropertyArraySimple copy(Object newOwner) {
        return _copy(new PropertyArrayDateTimeSimple((ConceptImpl)newOwner, 0));
    }

    @Override
    protected PropertyAtomDateTimeSimple newEmptyAtom(PropertyArraySimple newOwner) {
        return new PropertyAtomDateTimeSimple(newOwner);
    }

    private PropertyAtomDateTimeSimple addToEnd(boolean setValue, Calendar value) {
        pre_addToEnd();
        PropertyAtomDateTimeSimple atom;
        if(setValue)
            atom = new PropertyAtomDateTimeSimple(this, value);
        else
            atom = new PropertyAtomDateTimeSimple(this);
        m_properties[m_len] = atom;
        m_len++;
        setConceptModified();
        return atom;
    }


    public int add(Object value) {
        return add(PropertyAtomDateTimeSimple.objectToDateTime(value));
    }

    public void add(int index, Object value) {
        add(index, PropertyAtomDateTimeSimple.objectToDateTime(value));
    }

    public int add(Calendar value) {
        ((ConceptImpl)getParent()).checkSession();
        addToEnd(true, value);
        return m_len -1;
    }

    public void add(int index, Calendar value) {
        pre_add(index);
        m_properties[index] = new PropertyAtomDateTimeSimple(this, value);
        m_len++;
        setConceptModified();
    }

    public PropertyAtom add() {
        ((ConceptImpl)getParent()).checkSession();
        return addToEnd(false, null);
    }

    public void set(int index, Calendar value) {
        ((ConceptImpl)getParent()).checkSession();
        if (index < 0) {
        	index = 0;        	
        }
        if (index > m_len) {
            throw new IndexOutOfBoundsException("DateTime value " + value + " at " + index);
        } else if(index == m_len) {
            add(value);
        } else {
            ((PropertyAtomDateTimeSimple)get(index)).setDateTime(value);
        }
    }

    public PropertyAtomDateTime removePropertyAtom(Calendar value) {
        ((ConceptImpl)getParent()).checkSession();
        if(m_len == 0) return null;
        if(value == null) {
            for(int i=0; i<m_len; i++) {
                if(((PropertyAtomDateTimeSimple)m_properties[i]).getDateTime() == null) {
                    return (PropertyAtomDateTimeSimple) remove(i);
                }
            }
        }
        else {
            for(int i=0; i<m_len; i++) {
                if(value.equals(((PropertyAtomDateTimeSimple)m_properties[i]).getDateTime())) {
                    return (PropertyAtomDateTimeSimple) remove(i);
                }
            }
        }
        return null;
    }
}