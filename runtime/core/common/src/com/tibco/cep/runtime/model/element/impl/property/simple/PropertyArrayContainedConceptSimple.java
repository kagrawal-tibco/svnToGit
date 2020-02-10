package com.tibco.cep.runtime.model.element.impl.property.simple;

import com.tibco.cep.runtime.model.element.ContainedConcept;
import com.tibco.cep.runtime.model.element.PropertyArrayContainedConcept;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.PropertyAtomContainedConcept;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 26, 2006
 * Time: 2:05:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyArrayContainedConceptSimple extends PropertyArraySimple implements PropertyArrayContainedConcept {

    public PropertyArrayContainedConceptSimple(ConceptImpl parent, int initialSize) {
        super(parent, initialSize);
    }

    @Override
    public PropertyArraySimple copy(Object newOwner) {
        return _copy(new PropertyArrayContainedConceptSimple((ConceptImpl)newOwner, 0));
    }

    @Override
    protected PropertyAtomContainedConceptSimple newEmptyAtom(PropertyArraySimple newOwner) {
        return new PropertyAtomContainedConceptSimple(newOwner);
    }

    public void clearReferences() {
        for(int ii = 0; ii < m_len; ii ++) {
            if (m_properties[ii] != null)
                ((PropertyAtomContainedConceptSimple) m_properties[ii]).clearReferences();
        }
    }

    public Class getType() {
        return getMetaProperty().getType();
    }

    public int modifiedIndex() {
        return getParentConceptImpl().getMaxDirtyBitIdx() - getMetaProperty().getContainedPropIndex();
    }

    public int add(Object value) {
        return add(PropertyAtomContainedConceptSimple.objectToContainedConcept(getType(), value));
    }

    public void add(int index, Object value) {
        add(index, PropertyAtomContainedConceptSimple.objectToContainedConcept(getType(), value));
    }

    private PropertyAtomContainedConceptSimple addToEnd(boolean setValue, ContainedConcept value) {
        pre_addToEnd();
        PropertyAtomContainedConceptSimple atom;
        if(setValue)
            atom = new PropertyAtomContainedConceptSimple(this, value);
        else
            atom = new PropertyAtomContainedConceptSimple(this);
        m_properties[m_len] = atom;
        m_len++;
        setConceptModified();
        return atom;
    }


    public int add(ContainedConcept instance) {
        ((ConceptImpl)getParent()).checkSession();
        addToEnd(true, instance);
        return m_len -1;
    }

    public void add(int index, ContainedConcept instance) {
        pre_add(index);
        m_properties[index] = new PropertyAtomContainedConceptSimple(this, instance);
        m_len++;
        setConceptModified();
    }

    public PropertyAtom add() {
        ((ConceptImpl)getParent()).checkSession();
        return addToEnd(false, null);
    }

    public void set(int index, ContainedConcept value) {
        ((ConceptImpl)getParent()).checkSession();
        if (index < 0 || index > m_len) {
            throw new IndexOutOfBoundsException();
        } else if(index == m_len) {
            add(value);
        } else {
            ((PropertyAtomContainedConceptSimple)get(index)).setContainedConcept(value);
        }

    }

    public int put(ContainedConcept instance) {
        ((ConceptImpl)getParent()).checkSession();
        if(m_len != 0) {
            if(instance == null) {
                for(int i=0; i<m_len; i++) {
                    if(((PropertyAtomContainedConceptSimple)m_properties[i]).getContainedConceptId() == 0) {
                        return i;
                    }
                }
            }
            else {
                for(int i=0; i<m_len; i++) {
                    if(instance.getId() == ((PropertyAtomContainedConceptSimple)m_properties[i]).getContainedConceptId()) {
                        return i;
                    }
                }
            }
        }
        return add(instance);
    }

    public PropertyAtomContainedConcept remove(ContainedConcept instance) {
        ((ConceptImpl)getParent()).checkSession();
        if(m_len == 0) return null;
        if(instance == null) {
            for(int i=0; i<m_len; i++) {
                if(((PropertyAtomContainedConceptSimple)m_properties[i]).getContainedConceptId() == 0) {
                    return (PropertyAtomContainedConceptSimple) remove(i);
                }
            }
        }
        else {
            for(int i=0; i<m_len; i++) {
                if(instance.getId() == ((PropertyAtomContainedConceptSimple)m_properties[i]).getContainedConceptId()) {
                    return (PropertyAtomContainedConceptSimple) remove(i);
                }
            }
        }
        return null;
    }

    /***  DON'T USE THIS METHOD, ONLY FOR INTERNAL ENGINE USE - DELETING CONCEPT */
    public PropertyAtomContainedConcept removeById(long instanceId, boolean removeReverseRef) {
        if(m_len == 0) return null;
        for(int i=0; i<m_len; i++) {
            if(((PropertyAtomContainedConceptSimple)m_properties[i]).getContainedConceptId() == instanceId) {
                if(removeReverseRef)
                    return  (PropertyAtomContainedConceptSimple) remove(i);
                else
                    return (PropertyAtomContainedConceptSimple) super.remove(i);
            }
        }
        return null;
    }

    public PropertyAtomContainedConcept getPropertyAtomContainedConcept(ContainedConcept instance) {
        if(m_len == 0) return null;
        if (instance == null) {
            for(int i=0; i<m_len; i++) {
                if(((PropertyAtomContainedConceptSimple)m_properties[i]).getContainedConceptId() == 0) {
                    return (PropertyAtomContainedConceptSimple) m_properties[i];
                }
            }
        }
        else {
            for(int i=0; i<m_len; i++) {
                if(instance.getId() == ((PropertyAtomContainedConceptSimple)m_properties[i]).getContainedConceptId()) {
                    return (PropertyAtomContainedConceptSimple) m_properties[i];
                }
            }
        }
        return null;
    }

    public PropertyAtomContainedConcept getPropertyAtomContainedConcept(long instanceId) {
        if(m_len == 0) return null;
        for(int i=0; i<m_len; i++) {
            if(((PropertyAtomContainedConceptSimple)m_properties[i]).getContainedConceptId() == instanceId) {
                return (PropertyAtomContainedConceptSimple) m_properties[i];
            }
        }
        return null;
    }


    public int getPropertyAtomContainedConceptIndex(ContainedConcept instance) {
        if(m_len == 0) return -1;
        if(instance == null) {
            for(int i=0; i<m_len; i++) {
                if(((PropertyAtomContainedConceptSimple)m_properties[i]).getContainedConceptId() == 0) {
                    return i;
                }
            }
        }
        else {
            for(int i=0; i<m_len; i++) {
                if(instance.getId() == ((PropertyAtomContainedConceptSimple)m_properties[i]).getContainedConceptId()) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void clear() {
        ((ConceptImpl)getParent()).checkSession();
        if (m_len != 0) {
            for(int i=0; i<m_len; i++) {
                ContainedConcept cc = ((PropertyAtomContainedConceptSimple)m_properties[i]).getContainedConcept();
                 if(cc != null) {
                     nullParent(((ConceptImpl)cc));
                 }
            }
        }
        super.clear();
    }

    public PropertyAtom remove(int index) {
        ((ConceptImpl)getParent()).checkSession();
        PropertyAtom p =super.remove(index);
        if(p != null) {
            ContainedConcept cc = ((ContainedConcept)((PropertyAtomContainedConceptSimple)p).getContainedConcept());
            if(cc != null)
                nullParent(((ConceptImpl)cc));
        }
        return p;
    }

    public PropertyAtomContainedConcept removePropertyAtom(ContainedConcept instance) {
        return remove(instance);
    }
}