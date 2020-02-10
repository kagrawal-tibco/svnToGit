package com.tibco.cep.runtime.model.element.impl.property.raw;

import com.tibco.cep.runtime.model.element.PropertyArrayString;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.PropertyAtomString;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;

/**
 * @author Pranab Dhar
 *
 */
abstract public class PropertyArrayStringRaw extends PropertyArrayRaw implements PropertyArrayString {

	public PropertyArrayStringRaw(ConceptImpl parent, int initialSize) {
        super(parent, initialSize);
    }

	protected PropertyAtomStringRaw newEmptyAtom(PropertyArrayRaw newOwner) {
        return new Elem(newOwner);
    }

	@Override
	public int add(Object value) {
		return add((value == null) ? null : value.toString());
	}

	@Override
	public void add(int index, Object value) {
		add(index, (value == null) ? null : value.toString());
		
	}
	private PropertyAtomStringRaw addToEnd(boolean setValue, String value) {
        pre_addToEnd();
        PropertyAtomStringRaw atom;
		if (setValue) {
			try {
				atom = new Elem(this, value);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			atom = new Elem(this);
		}
        m_properties[m_len] = atom;
        m_len++;
        setConceptModified();
        return atom;
    }

	@Override
	public int add(String value) {
        addToEnd(true, value);
        return m_len -1;
	}

	@Override
	public void add(int index, String value) {
		pre_add(index);
        try {
			m_properties[index] = new Elem(this, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
        m_len++;
        setConceptModified();
		
	}

	@Override
	public PropertyAtomString removePropertyAtom(String value) {
        if(m_len == 0) return null;
        if(value == null) {
            for(int i=0; i<m_len; i++) {
                if(((PropertyAtomStringRaw)m_properties[i]).getString() == null) {
                    return (PropertyAtomStringRaw) remove(i);
                }
            }
        }
        else {
            for(int i=0; i<m_len; i++) {
                if(value.equals(((PropertyAtomStringRaw)m_properties[i]).getString())) {
                    return (PropertyAtomStringRaw) remove(i);
                }
            }
        }
        return null;
	}

	@Override
	public void set(int index, String value) {
        if (index < 0 || index > m_len) {
            throw new IndexOutOfBoundsException("String value " + value + " at " + index);
        } else if(index == m_len) {
            add(value);
        } else {
            ((PropertyAtomStringRaw)get(index)).setString(value);
        }
		
	}

	@Override
	public PropertyAtom add() {
        return addToEnd(false, null);
	}
    
	protected static class Elem extends PropertyAtomStringRaw
	{
		public Elem(PropertyArrayRaw owner) {
			super(owner);
		}
		
		public Elem(PropertyArrayRaw owner, String value) throws Exception {
			super(owner, value);
		}
		
		public String getName() {
			return ((PropertyArrayRaw)getOwner()).getName();
		}
	}
}