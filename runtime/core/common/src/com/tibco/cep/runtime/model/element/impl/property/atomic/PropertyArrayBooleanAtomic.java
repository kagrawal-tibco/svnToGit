package com.tibco.cep.runtime.model.element.impl.property.atomic;

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
public class PropertyArrayBooleanAtomic extends PropertyArrayAtomic implements PropertyArrayBoolean {

    public PropertyArrayBooleanAtomic(ConceptImpl parent, int initialSize) {
        super(parent, initialSize);
    }

    @Override
    public PropertyArrayAtomic copy(Object newOwner) {
        return _copy(new PropertyArrayBooleanAtomic((ConceptImpl)newOwner, 0));
    }

    @Override
    protected PropertyAtomBooleanAtomic newEmptyAtom(PropertyArrayAtomic newOwner) {
        return new PropertyAtomBooleanAtomic(newOwner);
    }

    public int add(Object value) {
        return add(PropertyAtomBooleanAtomic.objectToBoolean(value));
    }

    public void add(int index, Object value) {
        add(index, PropertyAtomBooleanAtomic.objectToBoolean(value));
    }

    private PropertyAtomBooleanAtomic addToEnd(boolean setValue, boolean value) {
        pre_addToEnd();
        PropertyAtomBooleanAtomic atom;
        if(setValue)
            atom = new PropertyAtomBooleanAtomic(this, value);
        else
            atom = new PropertyAtomBooleanAtomic(this);
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
        m_properties[index] = new PropertyAtomBooleanAtomic(this, value);
        m_len++;
        setConceptModified();
    }

    public void set(int index, boolean value) {
        ((ConceptImpl)getParent()).checkSession();
        if (index < 0 || index > m_len) {
            throw new IndexOutOfBoundsException();
        } else if(index == m_len) {
            add(value);
        } else {
            ((PropertyAtomBooleanAtomic)get(index)).setBoolean(value);
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