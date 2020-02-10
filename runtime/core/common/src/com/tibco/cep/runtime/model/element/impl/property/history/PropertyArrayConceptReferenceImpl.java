package com.tibco.cep.runtime.model.element.impl.property.history;

import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.PropertyArrayConceptReference;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.PropertyAtomConceptReference;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.property.AbstractPropertyArray;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 23, 2006
 * Time: 9:25:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyArrayConceptReferenceImpl extends PropertyArrayImpl implements PropertyArrayConceptReference {

    public PropertyArrayConceptReferenceImpl(ConceptImpl parent, int initialSize) {
        super(parent, initialSize);
    }

    @Override
    public AbstractPropertyArray copy(Object owner) {
        return _copy(new PropertyArrayConceptReferenceImpl((ConceptImpl)owner, 0));
    }
    
    @Override
    protected PropertyAtomConceptReferenceImpl newEmptyAtom(AbstractPropertyArray newOwner) {
        return new PropertyAtomConceptReferenceImpl(newOwner.getHistorySize(), newOwner);
    }

    public void clearReferences() {
        if (m_properties != null) {
            for(int ii = 0; ii < m_len; ii ++) {
                if (m_properties[ii] != null) {
                    if(m_properties[ii] instanceof PropertyAtomContainedConceptImpl) {
                    ((PropertyAtomContainedConceptImpl) m_properties[ii]).clearReferences();
                    } else if(m_properties[ii] instanceof PropertyAtomConceptReferenceImpl) {
                       ((PropertyAtomConceptReferenceImpl) m_properties[ii]).clearReferences();
                    }
                }
            }
        }
    }

    public boolean maintainReverseRef() {
    	return maintainReverseRef(getMetaProperty(), getParent().getClass());
    }
    
    public Class getType() {
        return getMetaProperty().getType();
    }

    public int add(Object value) {
        return add(PropertyAtomConceptReferenceImpl.objectToConcept(getType(), value));
    }

    public void add(int index, Object value) {
        add(index, PropertyAtomConceptReferenceImpl.objectToConcept(getType(), value));
    }

    public int add(Concept instance) {
        ((ConceptImpl)getParent()).checkSession();
        PropertyAtomConceptReferenceImpl newAtom = new PropertyAtomConceptReferenceImpl(getHistorySize(), this, instance);
        return doAdd(newAtom);
    }

    public void add(int index, Concept instance) {
        ((ConceptImpl)getParent()).checkSession();
        if (index < 0 || index > m_len) {
            throw new IndexOutOfBoundsException();
        }
        PropertyAtomConceptReferenceImpl newAtom = new PropertyAtomConceptReferenceImpl(getHistorySize(), this, instance);
        doAdd(newAtom, index);
    }

    public void set(int index, Concept value) {
        ((ConceptImpl)getParent()).checkSession();
        if (index < 0 || index > m_len) {
            throw new IndexOutOfBoundsException();
        } else if(index == m_len) {
            add(index, value);
        } else {
            ((PropertyAtomConceptReferenceImpl)get(index)).setConcept(value);
        }

    }

    public int put(Concept instance) {
        ((ConceptImpl)getParent()).checkSession();
        if(instance == null) {
            if(m_properties !=null) {
                for(int i=0; i<m_len; i++) {
                    if(((PropertyAtomConceptReferenceImpl)m_properties[i]).getConceptId() == 0) {
                        return i;
                    }
                }
            }
        }
        else {
            if(m_properties != null) {
                for(int i=0; i<m_len; i++) {
                    if(instance.getId() == ((PropertyAtomConceptReferenceImpl)m_properties[i]).getConceptId()) {
                        return i;
                    }
                }
            }
        }
        return add(instance);
    }

    /***  DON'T USE THIS METHOD, ONLY FOR INTERNAL ENGINE USE - DELETING CONCEPT */
    public PropertyAtomConceptReference removeById(long instanceId, boolean removeReverseRef) {
        if(m_properties == null) return null;
        for(int i=0; i<m_len; i++) {
            if(((PropertyAtomConceptReferenceImpl)m_properties[i]).getConceptId() == instanceId) {
                if(removeReverseRef)
                    return (PropertyAtomConceptReferenceImpl) remove(i);
                else
                    return (PropertyAtomConceptReferenceImpl) super.remove(i);
            }
        }
        return null;
    }

    public PropertyAtomConceptReference remove(Concept instance) {
        ((ConceptImpl)getParent()).checkSession();
        if(m_properties == null) return null;
        if(instance == null) {
            for(int i=0; i<m_len; i++) {
                if(((PropertyAtomConceptReferenceImpl)m_properties[i]).getConceptId() == 0) {
                    return (PropertyAtomConceptReferenceImpl) remove(i);
                }
            }
        }
        else {
            for(int i=0; i<m_len; i++) {
                if(instance.getId() == ((PropertyAtomConceptReferenceImpl)m_properties[i]).getConceptId()) {
                    return (PropertyAtomConceptReferenceImpl) remove(i);
                }
            }
        }
        return null;
    }

    public PropertyAtomConceptReference getPropertyAtomConceptReference(Concept instance) {
        if(m_properties == null) return null;
        if (instance == null) {
            for(int i=0; i<m_len; i++) {
                if(((PropertyAtomConceptReferenceImpl)m_properties[i]).getConceptId() == 0) {
                    return (PropertyAtomConceptReferenceImpl) m_properties[i];
                }
            }
        }
        else {
            for(int i=0; i<m_len; i++) {
                if(instance.getId() == ((PropertyAtomConceptReferenceImpl)m_properties[i]).getConceptId()) {
                    return (PropertyAtomConceptReferenceImpl) m_properties[i];
                }
            }
        }
        return null;
    }

    public PropertyAtomConceptReference getPropertyAtomConceptReference(long instanceId) {
        if(m_properties == null) return null;
        for(int i=0; i<m_len; i++) {
            if(((PropertyAtomConceptReferenceImpl)m_properties[i]).getConceptId() == instanceId) {
                return (PropertyAtomConceptReferenceImpl) m_properties[i];
            }
        }
        return null;
    }


    public int getPropertyAtomConceptReferenceIndex(Concept instance) {
        if(m_properties == null) return -1;
        if(instance == null) {
            for(int i=0; i<m_len; i++) {
                if(((PropertyAtomConceptReferenceImpl)m_properties[i]).getConceptId() == 0) {
                    return i;
                }
            }
        }
        else {
            for(int i=0; i<m_len; i++) {
                if(instance.getId() == ((PropertyAtomConceptReferenceImpl)m_properties[i]).getConceptId()) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void clear() {
        ((ConceptImpl)getParent()).checkSession();
        if(m_properties != null && maintainReverseRef()) {
            for(int i=0; i<m_len; i++) {
                ConceptImpl c = (ConceptImpl)((PropertyAtomConceptReferenceImpl)m_properties[i]).getConcept();
                 if(c != null) {
                     c.clearReverseRef(this);
                 }
            }
        }
        super.clear();
    }

    public PropertyAtom remove(int index) {
        ((ConceptImpl)getParent()).checkSession();
        PropertyAtom p =super.remove(index);
        if(p != null && maintainReverseRef()) {
            ConceptImpl c = (ConceptImpl) (((PropertyAtomConceptReferenceImpl)p).getConcept());
            if(c != null) {
                c.clearReverseRef(this);
            }
        }
        return p;
    }

    public PropertyAtomConceptReference removePropertyAtom(Concept instance) {
        return remove(instance);
    }
}
