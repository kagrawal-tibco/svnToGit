package com.tibco.cep.runtime.model.element.impl.property.simple;

import com.tibco.cep.runtime.model.element.PropertyArrayDouble;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.PropertyAtomDouble;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 26, 2006
 * Time: 2:26:40 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyArrayDoubleSimple extends PropertyArraySimple implements PropertyArrayDouble {

    public PropertyArrayDoubleSimple(ConceptImpl parent, int initialSize) {
        super(parent, initialSize);
    }

    @Override
    public PropertyArraySimple copy(Object newOwner) {
        return _copy(new PropertyArrayDoubleSimple((ConceptImpl)newOwner, 0));
    }

    @Override
    protected PropertyAtomDoubleSimple newEmptyAtom(PropertyArraySimple newOwner) {
        return new PropertyAtomDoubleSimple(newOwner);
    }

    private PropertyAtomDoubleSimple addToEnd(boolean setValue, double value) {
        pre_addToEnd();
        PropertyAtomDoubleSimple atom;
        if(setValue)
            atom = new PropertyAtomDoubleSimple(this, value);
        else
            atom = new PropertyAtomDoubleSimple(this);
        m_properties[m_len] = atom;
        m_len++;
        setConceptModified();
        return atom;
    }


    public int add(Object value) {
        return add(PropertyAtomDoubleSimple.objectToDouble(value));
    }

    public void add(int index, Object value) {
        add(index, PropertyAtomDoubleSimple.objectToDouble(value));
    }

    public int add(double value) {
        ((ConceptImpl)getParent()).checkSession();
        addToEnd(true, value);
        return m_len -1;
    }

    public void add(int index, double value) {
        pre_add(index);
        m_properties[index] = new PropertyAtomDoubleSimple(this, value);
        m_len++;
        setConceptModified();
    }

    public PropertyAtom add() {
        ((ConceptImpl)getParent()).checkSession();
        return addToEnd(false, 0.0);
    }

    public void set(int index, double value) {
        ((ConceptImpl)getParent()).checkSession();
        if (index < 0 || index > m_len) {
            throw new IndexOutOfBoundsException("Double value " + value + " at " + index);
        } else if(index == m_len) {
            add(value);
        } else {
            ((PropertyAtomDoubleSimple)get(index)).setDouble(value);
        }
    }

    public PropertyAtomDouble removePropertyAtom(double value) {
        ((ConceptImpl)getParent()).checkSession();
        if(m_len == 0) return null;
        for(int i=0; i<m_len; i++) {
            if(((PropertyAtomDoubleSimple)m_properties[i]).getDouble() == value) {
                return (PropertyAtomDoubleSimple) remove(i);
            }
        }
        return null;
    }
}