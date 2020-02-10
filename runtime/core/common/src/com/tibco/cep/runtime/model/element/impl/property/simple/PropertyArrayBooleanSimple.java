package com.tibco.cep.runtime.model.element.impl.property.simple;

import com.tibco.cep.runtime.model.element.PropertyArrayBoolean;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.PropertyAtomBoolean;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 26, 2006
 * Time: 2:01:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyArrayBooleanSimple extends PropertyArraySimple implements PropertyArrayBoolean {

    public PropertyArrayBooleanSimple(ConceptImpl parent, int initialSize) {
        super(parent, initialSize);
    }

    @Override
    public PropertyArraySimple copy(Object newOwner) {
        return _copy(new PropertyArrayBooleanSimple((ConceptImpl)newOwner, 0));
    }

    @Override
    protected PropertyAtomBooleanSimple newEmptyAtom(PropertyArraySimple newOwner) {
        return new PropertyAtomBooleanSimple(newOwner);
    }

    public int add(Object value) {
        return add(PropertyAtomBooleanSimple.objectToBoolean(value));
    }

    public void add(int index, Object value) {
        add(index, PropertyAtomBooleanSimple.objectToBoolean(value));
    }

    private PropertyAtomBooleanSimple addToEnd(boolean setValue, boolean value) {
        pre_addToEnd();
        PropertyAtomBooleanSimple atom;
        if(setValue)
            atom = new PropertyAtomBooleanSimple(this, value);
        else
            atom = new PropertyAtomBooleanSimple(this);
        m_properties[m_len] = atom;
        m_len++;
        setConceptModified();
        return atom;
    }

    public PropertyAtom add() {
        ((ConceptImpl)getParent()).checkSession();
        return addToEnd(false, false);
    }

    public int add(boolean value) {
        ((ConceptImpl)getParent()).checkSession();
        addToEnd(true, value);
        return m_len -1;
    }

    public void add(int index, boolean value) {
        pre_add(index);
        m_properties[index] = new PropertyAtomBooleanSimple(this, value);
        m_len++;
        setConceptModified();
    }

    public void set(int index, boolean value) {
        ((ConceptImpl)getParent()).checkSession();
        if (index < 0 || index > m_len) {
            throw new IndexOutOfBoundsException("Boolean value " + value + " at " + index);
        } else if(index == m_len) {
            add(value);
        } else {
            ((PropertyAtomBooleanSimple)get(index)).setBoolean(value);
        }
    }

    public PropertyAtomBoolean removePropertyAtom(boolean value) {
        ((ConceptImpl)getParent()).checkSession();
        if(m_len == 0) return null;
        for(int i=0; i<m_len; i++) {
            if(((PropertyAtomBoolean)m_properties[i]).getBoolean() == value) {
                return (PropertyAtomBoolean) remove(i);
            }
        }
        return null;
    }
}