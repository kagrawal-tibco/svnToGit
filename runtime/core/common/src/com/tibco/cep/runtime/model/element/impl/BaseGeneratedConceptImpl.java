package com.tibco.cep.runtime.model.element.impl;


import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.StateMachineConcept;
import com.tibco.cep.runtime.model.element.impl.property.PropertyImpl;
import com.tibco.cep.runtime.model.element.impl.property.metaprop.MetaProperty;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyStateMachineImpl;

/**
 The base for GeneratedConceptImpl.
 It does not override all the default method impls from ConceptImpl with abstract methods
 for generated concepts that will not ever have those methods change during a hot deploy.
 */

abstract public class BaseGeneratedConceptImpl extends ConceptImpl{
    protected PropertyImpl[] m_props = null;

    protected BaseGeneratedConceptImpl() {
        super();
        m_props = new PropertyImpl[_getNumProperties_constructor_only()];
    }

    public BaseGeneratedConceptImpl(long _id) {
        super(_id);
        m_props = new PropertyImpl[_getNumProperties_constructor_only()];
    }

    protected BaseGeneratedConceptImpl(long id, String extId) {
        super(id, extId);
        m_props = new PropertyImpl[_getNumProperties_constructor_only()];
    }

    protected BaseGeneratedConceptImpl(String extId) {
        super(extId);
        m_props = new PropertyImpl[_getNumProperties_constructor_only()];
    }

    abstract protected BaseGeneratedConceptImpl newInstance();

    abstract public int getPropertyIndex(String name);

    abstract protected int _getNumProperties_constructor_only();

    public abstract MetaProperty getMetaProperty(int index);

    protected boolean checkIdx(int idx) {
        return idx >=0 && idx < m_props.length;
    }
    @Override
    public StateMachineConcept newSMWithIndex(int index) {
        throw new java.lang.RuntimeException("Invalid Property Index " + index);
    }

    @Override
    public <T extends Property> T getProperty(int index) {
        if(!checkIdx(index)) {
            throw new java.lang.RuntimeException("Invalid Property Index " + index);
        }

        if(m_props[index] == null) {
            m_props[index] = getMetaProperty(index).newProperty(this, index);
        }
        return (T)m_props[index];
    }

    @Override
    public Property getProperty(String name) {
        int idx = getPropertyIndex(name);
        if(!checkIdx(idx)) return null;
        return getProperty(idx);
    }


    @Override
    public Property getPropertyNullOK(int index) {
        if(!checkIdx(index)) {
            throw new java.lang.RuntimeException("Invalid Property Index " + index);
        }
        return m_props[index];
    }

    @Override
    public Property[] getPropertiesNullOK() {
        if(m_props == null) {
            return new Property[0];
        } else {
            //todo see if it's ok to return the original array
            return m_props.clone();
        }
    }

    @Override
    public Property getPropertyNullOK(String name) {
        int idx = getPropertyIndex(name);
        if(!checkIdx(idx)) return null;
        return m_props[idx];
    }

    @Override
    public int getNumProperties() {
        return m_props.length;
    }

    
    @Override
    public Concept duplicateThis() {
    	return duplicateThis(this.id,this.extId);
    }

    
    public Concept duplicateThis(long cid,String cextId) {
        BaseGeneratedConceptImpl ret = newInstance();
        ret.id = cid;
        ret.extId = cextId;

        if (m_reverseRefs != null) {
            ret.m_reverseRefs = new Object[m_reverseRefs.length];
        }
        ret.m_reverseRefSize = m_reverseRefSize;
        for (int i = rRefStart(); i < m_reverseRefSize*2; i+=2) {
            Object rr = m_reverseRefs[i];
            if (rr instanceof Concept) {
                ret.m_reverseRefs[i] = new Reference(((Concept)rr).getId());
            }
            else {
                ret.m_reverseRefs[i] = rr;
            }
            ret.m_reverseRefs[i+1] = m_reverseRefs[i+1];
        }

        //copy the entire m_reverseRefs array first, and then overwrite
        //the first element (the parent ref) if necessary

        ConceptOrReference parentRef = getParentReference();
        if (parentRef instanceof Concept) {
            ret.setParentReference(new Reference(parentRef.getId()));
        }
        else {
            ret.setParentReference(parentRef);
        }

        ret.m_dirtyBitArray = new int[m_dirtyBitArray.length];
        ret.m_version = m_version;
        //statemachine?

        if(m_props != null && m_props.length > 0) {
            ret.m_props = new PropertyImpl[m_props.length];
            for(int ii = 0; ii < m_props.length; ii++) {
                PropertyImpl copyProp = m_props[ii];
                if(copyProp != null) {
                    ret.m_props[ii] = copyProp.copy(ret);
                }
            }
        }
        return ret;
    }

    @Override
    protected Property.PropertyConceptReference getConceptReferenceProperty(String propertyName) {
        int idx = getPropertyIndex(propertyName);
        if(!checkIdx(idx)) return null;
        MetaProperty mprop = getMetaProperty(idx);
        if(mprop.isConceptReference()) return (Property.PropertyConceptReference) getProperty(idx);
        return null;
    }
    
    @Override
    protected void clearConceptPropertyReferences() {
    	int[] idxs = conceptPropIdxs();

    	if(idxs == null) {
    		super.clearConceptPropertyReferences();
    	} else {
    		int ii = 0;
    		int idx = 0;
    		for(; (idx=idxs[ii]) != -1; ii++) {
    			this.<Property.PropertyConcept>getProperty(idx).clearReferences();
    		}
    		//skip the -1 in the array
    		ii++;
    		//state machine properties
    		for(; ii < idxs.length; ii++) {
    			idx = idxs[ii];
    			PropertyStateMachineImpl smProp = this.<PropertyStateMachineImpl>getProperty(idx);
    			smProp.clearReferences();
    			smProp.clearDirtyBitArray();
    		}
        }
    }

	/* array is list of indexes of concept properties that aren't state machine properties,
	 * then -1 
	 * then the indexes of state machine properties */
    protected int[] conceptPropIdxs() {
    	//clearConceptPropertyReferences checks for null in case the generated concept doesn't override this method
    	return null; 
    }
    
    static protected int[] makeConceptPropIdxs(MetaProperty[] mprops) {
    	//always one entry for -1 marker
    	int count = 1;
    	for(MetaProperty mp : mprops) {
    		if(mp.isConceptReference() || mp.isContainedConcept() || mp.isStateMachine()) {
    			count++;
    		}
    	}
    	
    	int[] idxs = new int[count];
    	int start = 0, end = idxs.length - 1;
    	for(int ii = 0; start != end; ii++) {
    		MetaProperty mp = mprops[ii];
    		if(mp.isStateMachine()) {
    			idxs[end] = ii;
    			end--;
    		} else if(mp.isConceptReference() || mp.isContainedConcept()) {
    			idxs[start] = ii;
    			start++;
    		}
    	}
    	idxs[end] = -1;
    	return idxs;
    }
}