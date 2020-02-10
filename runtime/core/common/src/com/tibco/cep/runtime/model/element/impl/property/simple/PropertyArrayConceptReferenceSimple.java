package com.tibco.cep.runtime.model.element.impl.property.simple;

import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.PropertyArrayConceptReference;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.PropertyAtomConceptReference;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Jun 26, 2006
 * Time: 2:02:49 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyArrayConceptReferenceSimple extends PropertyArraySimple implements PropertyArrayConceptReference
{
    public PropertyArrayConceptReferenceSimple(ConceptImpl parent, int len) {
        super(parent, len);
    }

    @Override
    public PropertyArraySimple copy(Object newOwner) {
        return _copy(new PropertyArrayConceptReferenceSimple((ConceptImpl)newOwner, 0));
    }

    @Override
    protected PropertyAtomConceptReferenceSimple newEmptyAtom(PropertyArraySimple newOwner) {
        return new PropertyAtomConceptReferenceSimple(newOwner);
    }

    public void clearReferences() {
        for(int ii = 0; ii < m_len; ii ++) {
            if (m_properties[ii] != null)
                ((PropertyAtomConceptReferenceSimple) m_properties[ii]).clearReferences();
        }
    }

    public boolean maintainReverseRef() {
    	return maintainReverseRef(getMetaProperty(), getParent().getClass());
    }

    public Class getType() {
        return getMetaProperty().getType();
    }

    public int add(Object value) {
        return add(PropertyAtomConceptReferenceSimple.objectToConcept(getType(), value));
    }

    public void add(int index, Object value) {
        add(index, PropertyAtomConceptReferenceSimple.objectToConcept(getType(), value));
    }

    private PropertyAtomConceptReferenceSimple addToEnd(boolean setValue, Concept value) {
        pre_addToEnd();
        PropertyAtomConceptReferenceSimple atom;
        if(setValue)
            atom = new PropertyAtomConceptReferenceSimple(this, value);
        else
            atom = new PropertyAtomConceptReferenceSimple(this);
        m_properties[m_len] = atom;
        m_len++;
        setConceptModified();
        return atom;
    }

    public int add(Concept instance) {
        ((ConceptImpl)getParent()).checkSession();
        addToEnd(true, instance);
        return m_len -1;
    }

    public void add(int index, Concept instance) {
        pre_add(index);
        m_properties[index] = new PropertyAtomConceptReferenceSimple(this, instance);
        m_len++;
        setConceptModified();
    }

    public PropertyAtom add() {
        ((ConceptImpl)getParent()).checkSession();
        return addToEnd(false, null);
    }

    public void set(int index, Concept value) {
        ((ConceptImpl)getParent()).checkSession();
        if (index < 0 || index > m_len) {
            throw new IndexOutOfBoundsException();
        } else if(index == m_len) {
            add(value);
        } else {
            ((PropertyAtomConceptReferenceSimple)get(index)).setConcept(value);
        }
    }

    public int put(Concept instance) {
        ((ConceptImpl)getParent()).checkSession();
        if(instance == null) {
            if(m_len !=0) {
                for(int i=0; i<m_len; i++) {
                    if(((PropertyAtomConceptReferenceSimple)m_properties[i]).getConceptId() == 0) {
                        return i;
                    }
                }
            }
        }
        else {
            if(m_len != 0) {
                for(int i=0; i<m_len; i++) {
                    if(instance.getId() == ((PropertyAtomConceptReferenceSimple)m_properties[i]).getConceptId()) {
                        return i;
                    }
                }
            }
        }
        return add(instance);
    }

    public PropertyAtomConceptReference remove(Concept instance) {
        ((ConceptImpl)getParent()).checkSession();
        if(m_len == 0) return null;
        if(instance == null) {
            for(int i=0; i<m_len; i++) {
                if(((PropertyAtomConceptReferenceSimple)m_properties[i]).getConceptId() == 0) {
                    return (PropertyAtomConceptReferenceSimple) remove(i);
                }
            }
        }
        else {
            for(int i=0; i<m_len; i++) {
                if(instance.getId() == ((PropertyAtomConceptReferenceSimple)m_properties[i]).getConceptId()) {
                    return (PropertyAtomConceptReferenceSimple) remove(i);
                }
            }
        }
        return null;
    }

    /***  DON'T USE THIS METHOD, ONLY FOR INTERNAL ENGINE USE - DELETING CONCEPT */
    public PropertyAtomConceptReference removeById(long instanceId, boolean removeReverseRef) {
        if(m_len == 0) return null;
        for(int i=0; i<m_len; i++) {
            if(((PropertyAtomConceptReferenceSimple)m_properties[i]).getConceptId() == instanceId) {
                if(removeReverseRef)
                    return (PropertyAtomConceptReferenceSimple) remove(i);
                else
                    return (PropertyAtomConceptReferenceSimple) super.remove(i);
            }
        }
        return null;
    }


    public PropertyAtomConceptReference getPropertyAtomConceptReference(Concept instance) {
        if(m_len == 0) return null;
        if (instance == null) {
            for(int i=0; i<m_len; i++) {
                if(((PropertyAtomConceptReferenceSimple)m_properties[i]).getConceptId() == 0) {
                    return (PropertyAtomConceptReferenceSimple) m_properties[i];
                }
            }
        }
        else {
            for(int i=0; i<m_len; i++) {
                if(instance.getId() == ((PropertyAtomConceptReferenceSimple)m_properties[i]).getConceptId()) {
                    return (PropertyAtomConceptReferenceSimple) m_properties[i];
                }
            }
        }
        return null;
    }

    public PropertyAtomConceptReference getPropertyAtomConceptReference(long instanceId) {
        if(m_len == 0) return null;
        for(int i=0; i<m_len; i++) {
            if(((PropertyAtomConceptReferenceSimple)m_properties[i]).getConceptId() == instanceId) {
                return (PropertyAtomConceptReferenceSimple) m_properties[i];
            }
        }
        return null;
    }

    public int getPropertyAtomConceptReferenceIndex(Concept instance) {
        if(m_len == 0) return -1;
        if(instance == null) {
            for(int i=0; i<m_len; i++) {
                if(((PropertyAtomConceptReferenceSimple)m_properties[i]).getConceptId() == 0) {
                    return i;
                }
            }
        }
        else {
            for(int i=0; i<m_len; i++) {
                if(instance.getId() == ((PropertyAtomConceptReferenceSimple)m_properties[i]).getConceptId()) {
                    return i;
                }
            }
        }
        return -1;
    }

    public void clear() {
        ((ConceptImpl)getParent()).checkSession();
        if(m_len != 0 && maintainReverseRef()) {
            for(int i=0; i<m_len; i++) {
                ConceptImpl c = (ConceptImpl) ((PropertyAtomConceptReferenceSimple)m_properties[i]).getConcept();
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
            ConceptImpl c = (ConceptImpl) (((PropertyAtomConceptReferenceSimple)p).getConcept());
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