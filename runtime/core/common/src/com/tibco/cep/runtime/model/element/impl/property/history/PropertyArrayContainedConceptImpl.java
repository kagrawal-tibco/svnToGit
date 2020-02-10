package com.tibco.cep.runtime.model.element.impl.property.history;

import com.tibco.cep.runtime.model.element.ContainedConcept;
import com.tibco.cep.runtime.model.element.PropertyArrayContainedConcept;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.PropertyAtomContainedConcept;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.property.AbstractPropertyArray;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 24, 2006
 * Time: 1:35:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyArrayContainedConceptImpl extends PropertyArrayImpl implements PropertyArrayContainedConcept {
    public PropertyArrayContainedConceptImpl(ConceptImpl parent, int initialSize) {
        super(parent, initialSize);
    }

    @Override
    public AbstractPropertyArray copy(Object owner) {
        return _copy(new PropertyArrayContainedConceptImpl((ConceptImpl)owner, 0));
    }

    @Override
    protected PropertyAtomContainedConceptImpl newEmptyAtom(AbstractPropertyArray newOwner) {
        return new PropertyAtomContainedConceptImpl(newOwner.getHistorySize(), newOwner);
    }

    public void clearReferences() {
        if (m_properties != null) {
            for(int ii = 0; ii < m_len; ii ++) {
                if (m_properties[ii] != null)
                    ((PropertyAtomContainedConceptImpl) m_properties[ii]).clearReferences();
            }
        }
    }

    public Class getType() {
        return getMetaProperty().getType();
    }

    public int modifiedIndex() {
        return getParentConceptImpl().getMaxDirtyBitIdx() - getMetaProperty().getContainedPropIndex();
    }

    public int add(Object value) {
        return add(PropertyAtomContainedConceptImpl.objectToContainedConcept(getType(), value));
    }

    public void add(int index, Object value) {
        add(index, PropertyAtomContainedConceptImpl.objectToContainedConcept(getType(), value));
    }

    public int add(ContainedConcept instance) {
        ((ConceptImpl)getParent()).checkSession();
        PropertyAtomContainedConceptImpl newAtom = new PropertyAtomContainedConceptImpl(getHistorySize(), this, instance);
        return doAdd(newAtom);
    }

    public void add(int index, ContainedConcept instance) {
        ((ConceptImpl)getParent()).checkSession();
        if (index < 0 || index > m_len) {
            throw new IndexOutOfBoundsException();
        }
        PropertyAtomContainedConceptImpl newAtom = new PropertyAtomContainedConceptImpl(getHistorySize(), this, instance);
        doAdd(newAtom, index);
    }

    public void set(int index, ContainedConcept value) {
        ((ConceptImpl)getParent()).checkSession();
        if (index < 0 || index > m_len) {
            throw new IndexOutOfBoundsException();
        } else if(index == m_len) {
            add(index, value);
        } else {
            ((PropertyAtomContainedConceptImpl)get(index)).setContainedConcept(value);
        }
    }

    public int put(ContainedConcept instance) {
        ((ConceptImpl)getParent()).checkSession();
        if(m_properties !=null) {
            if(instance == null) {
                for(int i=0; i<m_len; i++) {
                    if(((PropertyAtomContainedConceptImpl)m_properties[i]).getContainedConceptId() == 0) {
                        return i;
                    }
                }
            }
            else {
                for(int i=0; i<m_len; i++) {
                    if(instance.getId() == ((PropertyAtomContainedConceptImpl)m_properties[i]).getContainedConceptId()) {
                        return i;
                    }
                }
            }
        }
        return add(instance);
    }

    public PropertyAtomContainedConcept remove(ContainedConcept instance) {
        ((ConceptImpl)getParent()).checkSession();
        if(m_properties == null) return null;
        if(instance == null) {
            for(int i=0; i<m_len; i++) {
                if(((PropertyAtomContainedConceptImpl)m_properties[i]).getContainedConceptId() == 0) {
                    return (PropertyAtomContainedConceptImpl) remove(i);
                }
            }
        }
        else {
            for(int i=0; i<m_len; i++) {
                if(instance.getId() == ((PropertyAtomContainedConceptImpl)m_properties[i]).getContainedConceptId()) {
                    return (PropertyAtomContainedConceptImpl) remove(i);
                }
            }
        }
        return null;
    }

    public PropertyAtomContainedConcept removeById(long instanceId, boolean removeReverseRef) {
        if(m_properties == null) return null;
        for(int i=0; i<m_len; i++) {
            if(((PropertyAtomContainedConceptImpl)m_properties[i]).getContainedConceptId() == instanceId) {
                if (removeReverseRef)
                    return (PropertyAtomContainedConceptImpl) remove(i);
                else
                    return (PropertyAtomContainedConceptImpl) super.remove(i);
            }
        }
        return null;
    }


    public PropertyAtomContainedConcept getPropertyAtomContainedConcept(ContainedConcept instance) {
        if(m_properties == null) return null;
        if (instance == null) {
            for(int i=0; i<m_len; i++) {
                if(((PropertyAtomContainedConceptImpl)m_properties[i]).getContainedConceptId() == 0) {
                    return (PropertyAtomContainedConceptImpl) m_properties[i];
                }
            }
        }
        else {
            for(int i=0; i<m_len; i++) {
                if(instance.getId() == ((PropertyAtomContainedConceptImpl)m_properties[i]).getContainedConceptId()) {
                    return (PropertyAtomContainedConceptImpl) m_properties[i];
                }
            }
        }
        return null;
    }

    public PropertyAtomContainedConcept getPropertyAtomContainedConcept(long instanceId) {
        if(m_properties == null) return null;
        for(int i=0; i<m_len; i++) {
            if(((PropertyAtomContainedConceptImpl)m_properties[i]).getContainedConceptId() == instanceId) {
                return (PropertyAtomContainedConceptImpl) m_properties[i];
            }
        }
        return null;
    }

    public int getPropertyAtomContainedConceptIndex(ContainedConcept instance) {
        if(m_properties == null) return -1;
        if(instance == null) {
            for(int i=0; i<m_len; i++) {
                if(((PropertyAtomContainedConceptImpl)m_properties[i]).getContainedConceptId() == 0) {
                    return i;
                }
            }
        }
        else {
            for(int i=0; i<m_len; i++) {
                if(instance.getId() == ((PropertyAtomContainedConceptImpl)m_properties[i]).getContainedConceptId()) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void clear() {
        ((ConceptImpl)getParent()).checkSession();
        if (m_properties != null) {
            for(int i=0; i<m_len; i++) {
                ContainedConcept cc = ((PropertyAtomContainedConceptImpl)m_properties[i]).getContainedConcept();
                 if(cc != null) {
                     ((ConceptImpl)cc).nullParent();
                 }
            }
        }
        super.clear();
    }

    public PropertyAtom remove(int index) {
        ((ConceptImpl)getParent()).checkSession();
        PropertyAtom p =super.remove(index);
        if(p != null) {
            ContainedConcept cc = ((ContainedConcept)((PropertyAtomContainedConceptImpl)p).getContainedConcept());
            if(cc != null)
                ((ConceptImpl)cc).nullParent();
        }
        return p;
    }

    public PropertyAtomContainedConcept removePropertyAtom(ContainedConcept instance) {
        return remove(instance);
    }
}