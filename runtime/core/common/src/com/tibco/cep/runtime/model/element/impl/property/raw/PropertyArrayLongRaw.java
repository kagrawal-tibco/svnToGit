package com.tibco.cep.runtime.model.element.impl.property.raw;

import com.tibco.cep.runtime.model.element.PropertyArrayLong;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.PropertyAtomLong;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;

/**
 * @author Pranab Dhar
 *
 */
abstract public class PropertyArrayLongRaw extends PropertyArrayRaw implements PropertyArrayLong {

	public PropertyArrayLongRaw(ConceptImpl parent, int initialSize) {
        super(parent, initialSize);
    }

	protected PropertyAtomLongRaw newEmptyAtom(PropertyArrayRaw newOwner) {
        return new Elem(newOwner);
    }

    public int add(Object value) {
        return add(PropertyAtomLongRaw.objectToLong(value));
    }

    public void add(int index, Object value) {
        add(index, PropertyAtomLongRaw.objectToLong(value));
    }

    private PropertyAtomLongRaw addToEnd(boolean setValue, long value) {
        pre_addToEnd();
        PropertyAtomLongRaw atom;
        if(setValue) atom = new Elem(this, value);
        else atom = new Elem(this);
        
        m_properties[m_len] = atom;
        m_len++;
        setConceptModified();
        return atom;
    }


    public int add(long value) {
        addToEnd(true, value);
        return m_len -1;
    }

    public void add(int index, long value) {
        pre_add(index);
        m_properties[index] = new Elem(this, value);
        m_len++;
        setConceptModified();
    }

    public PropertyAtom add() {
        return addToEnd(false, 0);
    }

    public void set(int index, long value) {
        if (index < 0 || index > m_len) {
            throw new IndexOutOfBoundsException();
        } else if(index == m_len) {
            add(value);
        } else {
            ((PropertyAtomLongRaw)get(index)).setLong(value);
        }
    }

    public PropertyAtomLong removePropertyAtom(long value) {
        if(m_len == 0) return null;
        for(int i=0; i<m_len; i++) {
            if(((PropertyAtomLongRaw)m_properties[i]).getLong() == value) {
                return (PropertyAtomLongRaw) remove(i);
            }
        }
        return null;
    }

	protected static class Elem extends PropertyAtomLongRaw
	{
		public Elem(PropertyArrayRaw owner) {
			super(owner);
		}
		
		public Elem(PropertyArrayRaw owner, long value) {
			super(owner, value);
		}
		
		public String getName() {
			return ((PropertyArrayRaw)getOwner()).getName();
		}
	}	
}