package com.tibco.cep.runtime.model.element.impl.property.simple;

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
public class PropertyArrayIntSimple extends PropertyArraySimple implements PropertyArrayInt {

    public PropertyArrayIntSimple(ConceptImpl parent, int initialSize) {
        super(parent, initialSize);
    }

    @Override
    public PropertyArraySimple copy(Object newOwner) {
        return _copy(new PropertyArrayIntSimple((ConceptImpl)newOwner, 0));
    }

    @Override
    protected PropertyAtomIntSimple newEmptyAtom(PropertyArraySimple newOwner) {
        return new PropertyAtomIntSimple(newOwner);
    }

    private PropertyAtomIntSimple addToEnd(boolean setValue, int value) {
        pre_addToEnd();
        PropertyAtomIntSimple atom;
        if(setValue)
            atom = new PropertyAtomIntSimple(this, value);
        else
            atom = new PropertyAtomIntSimple(this);
        m_properties[m_len] = atom;
        m_len++;
        setConceptModified();
        return atom;
    }

    public int add(Object value) {
        return add(PropertyAtomIntSimple.objectToInt(value));
    }

    public void add(int index, Object value) {
        add(index, PropertyAtomIntSimple.objectToInt(value));
    }

    public int add(int value) {
        ((ConceptImpl)getParent()).checkSession();
        addToEnd(true, value);
        return m_len -1;
    }

    public void add(int index, int value) {
        pre_add(index);
        m_properties[index] = new PropertyAtomIntSimple(this, value);
        m_len++;
        setConceptModified();
    }

    public PropertyAtom add() {
        ((ConceptImpl)getParent()).checkSession();
        return addToEnd(false, 0);
    }

    public void set(int index, int value) {
    	((ConceptImpl)getParent()).checkSession();
        if (index < 0) {
        	index = 0;        	
        }
        if (index > m_len) {
            throw new IndexOutOfBoundsException("Integer value " + value + " at " + index);
        } else if(index == m_len) {
            add(value);
        } else {
            ((PropertyAtomIntSimple)get(index)).setInt(value);
        }
    }

    public PropertyAtomInt removePropertyAtom(int value) {
        ((ConceptImpl)getParent()).checkSession();
        if(m_len == 0) return null;
        for(int i=0; i<m_len; i++) {
            if(((PropertyAtomIntSimple)m_properties[i]).getInt() == value) {
                return (PropertyAtomIntSimple) remove(i);
            }
        }
        return null;
    }
}

