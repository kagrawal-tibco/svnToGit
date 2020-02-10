package com.tibco.cep.runtime.model.element.impl.property.simple;

import com.tibco.cep.runtime.model.element.PropertyArrayString;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.PropertyAtomString;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 26, 2006
 * Time: 2:30:08 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyArrayStringSimple extends PropertyArraySimple implements PropertyArrayString {

    public PropertyArrayStringSimple(ConceptImpl parent, int initialSize) {
        super(parent, initialSize);
    }

    @Override
    public PropertyArraySimple copy(Object newOwner) {
        return _copy(new PropertyArrayStringSimple((ConceptImpl)newOwner, 0));
    }

    @Override
    protected PropertyAtomStringSimple newEmptyAtom(PropertyArraySimple newOwner) {
        return new PropertyAtomStringSimple(newOwner);
    }

    public int add(Object value) {
        return add((value == null) ? null : value.toString());
    }

    public void add(int index, Object value) {
        add(index, (value == null) ? null : value.toString());
    }

    private PropertyAtomStringSimple addToEnd(boolean setValue, String value) {
        pre_addToEnd();
        PropertyAtomStringSimple atom;
        if (setValue)
            atom = new PropertyAtomStringSimple(this, value);
        else
            atom = new PropertyAtomStringSimple(this);
        m_properties[m_len] = atom;
        m_len++;
        setConceptModified();
        return atom;
    }


    public int add(String value) {
        ((ConceptImpl)getParent()).checkSession();
        addToEnd(true, value);
        return m_len -1;
    }

    public void add(int index, String value) {
        pre_add(index);
        m_properties[index] = new PropertyAtomStringSimple(this, value);
        m_len++;
        setConceptModified();
    }

    public PropertyAtom add() {
        ((ConceptImpl)getParent()).checkSession();
        return addToEnd(false, null);
    }

    public void set(int index, String value) {
        ((ConceptImpl)getParent()).checkSession();
        if (index < 0 || index > m_len) {
            throw new IndexOutOfBoundsException("String value " + value + " at " + index);
        } else if(index == m_len) {
            add(value);
        } else {
            ((PropertyAtomStringSimple)get(index)).setString(value);
        }
    }

    public PropertyAtomString removePropertyAtom(String value) {
        ((ConceptImpl)getParent()).checkSession();
        if(m_len == 0) return null;
        if(value == null) {
            for(int i=0; i<m_len; i++) {
                if(((PropertyAtomStringSimple)m_properties[i]).getString() == null) {
                    return (PropertyAtomStringSimple) remove(i);
                }
            }
        }
        else {
            for(int i=0; i<m_len; i++) {
                if(value.equals(((PropertyAtomStringSimple)m_properties[i]).getString())) {
                    return (PropertyAtomStringSimple) remove(i);
                }
            }
        }
        return null;
    }
}
