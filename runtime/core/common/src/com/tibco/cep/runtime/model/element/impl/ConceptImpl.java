/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 27/8/2010
 */

package com.tibco.cep.runtime.model.element.impl;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import com.tibco.cep.kernel.core.base.BaseHandle;
import com.tibco.cep.kernel.core.base.BaseObjectManager;
import com.tibco.cep.kernel.core.base.PersistenceStatusHolder;
import com.tibco.cep.kernel.core.base.WorkingMemoryImpl;
import com.tibco.cep.kernel.core.rete.ReteWM;
import com.tibco.cep.kernel.helper.BitSet;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.kernel.service.ObjectManager;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.ConceptDeserializer;
import com.tibco.cep.runtime.model.element.ConceptSerializer;
import com.tibco.cep.runtime.model.element.ConceptToXiNodeFilter;
import com.tibco.cep.runtime.model.element.ContainedConcept;
import com.tibco.cep.runtime.model.element.ContainedConceptException;
import com.tibco.cep.runtime.model.element.ExtIdAlreadyBoundException;
import com.tibco.cep.runtime.model.element.NullContainedConcept;
import com.tibco.cep.runtime.model.element.NullContainedConceptImpl;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.Property.PropertyConceptReference;
import com.tibco.cep.runtime.model.element.Property.PropertyContainedConcept;
import com.tibco.cep.runtime.model.element.PropertyArray;
import com.tibco.cep.runtime.model.element.PropertyArrayConceptReference;
import com.tibco.cep.runtime.model.element.PropertyArrayContainedConcept;
import com.tibco.cep.runtime.model.element.PropertyArrayInt;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.PropertyAtomBoolean;
import com.tibco.cep.runtime.model.element.PropertyAtomConceptReference;
import com.tibco.cep.runtime.model.element.PropertyAtomContainedConcept;
import com.tibco.cep.runtime.model.element.StateMachineConcept;
import com.tibco.cep.runtime.model.element.VersionedObject;
import com.tibco.cep.runtime.model.element.impl.property.PropertyImpl;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyArrayConceptReferenceImpl;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyArrayContainedConceptImpl;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomConceptReferenceImpl;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomContainedConceptImpl;
import com.tibco.cep.runtime.model.element.impl.property.metaprop.MetaProperty;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyArrayConceptReferenceSimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyArrayContainedConceptSimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomConceptReferenceSimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomContainedConceptSimple;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyStateMachineImpl;
import com.tibco.cep.runtime.model.serializers.Serializer;
import com.tibco.cep.runtime.model.serializers.SerializerFactory;
import com.tibco.cep.runtime.model.serializers.SerializerFactoryFactory;
import com.tibco.cep.runtime.service.om.ObjectBasedStore;
import com.tibco.cep.runtime.service.om.impl.mem.InMemoryObjectManager;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.cep.runtime.util.SystemProperty;
import com.tibco.cep.util.ResourceManager;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;

/**
 * Created by IntelliJ IDEA. User: nleong Date: Jun 23, 2006 Time: 8:15:14 PM To change this
 * template use File | Settings | File Templates.
 */
abstract public class ConceptImpl extends EntityImpl
        implements Concept, ConceptOrReference, VersionedObject, PersistenceStatusHolder
{
    final static protected int PERSITENCE_MODIFIED_OFFSET = 1;
    final static protected int NUM_PERSITENCE_BITS = 1;

    //only for cache mode
    //highest bit is used to implement isMarkedDeleted
    protected int m_version = 0;

    transient protected BaseHandle m_wmHandle = null;

    //move to base handle?
    //structure is {one dirty bit per property starting at the 0th bit, possible empty space, one extra persistence dirty bit per contained concept property, PERSITENCE_MODIFIED}
    //the empty space is due to the fact that the index for the extra contained concept dirty bits are calculated by
    //subtracting an offset from the end of the dirty bits array, which is an array of ints so it may have up to 31 bits of empty space
    transient protected int[] m_dirtyBitArray = null;

    //if the concept is contained, the first element is the parent ref
    //array alternates ConceptOrReference, String (property name of reverse ref of previous element), ConceptOrReference, String ...
    protected Object[] m_reverseRefs;

    protected int m_reverseRefSize = 0;

    /**
     * Not required to be thread safe. Each thread will eventually get the value from the property.
     */
    static boolean checkSessionInitialized = false;
    static boolean checkSession;
    protected static Logger gLogger = null;
    
    protected static final boolean trackUnmodifiedCCPropValue = Boolean.parseBoolean(
    		System.getProperty(SystemProperty.DONT_PERSIST_UNMODIFIED_CONTAINED_CONCEPT_PROPERTY_VALUE.getPropertyName()
    				, "false").trim());

    public static Logger getLogger() {
    	if(gLogger == null) {
    		gLogger = LogManagerFactory.getLogManager().getLogger(Concept.class);
    	}
    	return gLogger;
    }
    
    protected ConceptImpl() {
        super();
        m_dirtyBitArray      = new int[(_getNumDirtyBits_constructor_only() + NUM_PERSITENCE_BITS)/32 + 1];
    }

    public ConceptImpl(long _id) {
        super(_id);
        m_dirtyBitArray      = new int[(_getNumDirtyBits_constructor_only() + NUM_PERSITENCE_BITS)/32 + 1];
    }

    protected ConceptImpl(long id, String extId) {
        super(id, extId);
        m_dirtyBitArray      = new int[(_getNumDirtyBits_constructor_only() + NUM_PERSITENCE_BITS)/32 + 1];
    }

    protected ConceptImpl(String extId) {
        super(extId);
        m_dirtyBitArray      = new int[(_getNumDirtyBits_constructor_only() + NUM_PERSITENCE_BITS)/32 + 1];
    }

    @Override
    public void setId(long id) {
        super.setId(id);
    }

    public void clearAllReferences() {
        ConceptOrReference parentContainer = getParentReference();
        if (parentContainer != null) {
            if (parentContainer instanceof Concept) {
                setParentReference(new Reference(parentContainer.getId()));
            }
        }

        for (RevRefItr itr = rrItr(); itr.next();) {
            if (itr.reverseRef() instanceof Concept) {
                itr.setReverseRef(new Reference(itr.reverseRef().getId()));
            }
        }
        clearConceptPropertyReferences();
        clearDirtyBitArray();
    }
    
    protected void clearConceptPropertyReferences() {
        PropertyConceptReference[] pcr = getConceptReferenceProperties();
        if(pcr != null) {
	        for(Property.PropertyConcept prop : pcr) {
	        	prop.clearReferences();
	        }
        }
        
        PropertyContainedConcept[] pcc = getContainedConceptProperties();
        if(pcc != null) {
	        for(Property.PropertyConcept prop : pcc) {
	        	prop.clearReferences();
	        	if (prop instanceof PropertyStateMachineImpl) {
	                ((PropertyStateMachineImpl)prop).clearDirtyBitArray();
	            }
	        }
        }
    }

    public boolean isMarkedDeleted() {
        return (m_version & 0x80000000) != 0;
    }

    public void markDeleted() {
        m_version |= 0x80000000;
    }
    
    public boolean getPersistenceModified() {
    	if(m_dirtyBitArray == null) return false;
    	return BitSet.isBitSet(m_dirtyBitArray, persistenceModifiedIdx());
    }
    
    public void setPersistenceModified() {
    	if(m_dirtyBitArray != null)
    		BitSet.setBit(m_dirtyBitArray, persistenceModifiedIdx());
    }
    
    public void clearPersistenceModified() {
    	if(m_dirtyBitArray != null)
    		BitSet.clearBit(m_dirtyBitArray, persistenceModifiedIdx());
    }
    
    public int getNumProperties() {
        throw new UnsupportedOperationException("ConceptImpl.getNumProperties unimplemented");
    }
    
    protected int persistenceModifiedIdx() {
    	return getMaxDirtyBitIdx() + PERSITENCE_MODIFIED_OFFSET;
    }

    //used to create the dirty bit array in the constructor
    protected int _getNumDirtyBits_constructor_only() {
        return 0;
    }

    final public int getMaxDirtyBitIdx() {
    	if(m_dirtyBitArray == null) return -1;
        return m_dirtyBitArray.length * 32 - 1 - NUM_PERSITENCE_BITS;
    }

    public boolean isAutoStartupStateMachine() {
        return true;
    }

    protected boolean isAsserted() {
        return (m_wmHandle != null) && (m_wmHandle.isAsserted());
    }

    public Concept duplicateThis() {
        return this;
    }

    protected boolean objectBasedStore() {
        return (m_wmHandle instanceof ObjectBasedStore.ObjectBasedStoreHandle);
    }

    public WorkingMemory getWorkingMemory() {
        return m_wmHandle.getWorkingMemory();
    }

    public ObjectManager getObjectManager() {
        if (m_wmHandle == null) {
            return null;
        }
        return m_wmHandle.getWorkingMemory().getObjectManager();
    }

    public void checkSession() {
        if(m_wmHandle != null) {
        	if(!checkSessionInitialized){
                readCheckSessionProperty();
            }

        	if (checkSession) {
	            if (!ReteWM.executingInside(m_wmHandle.getWorkingMemory())) {
	                throw new RuntimeException("Modifying instance <" + this +
	                        "> outside the rule session it belongs to is not allowed.");
	            }
	        }
        }
        //else handle == null is ok, can be modified anywhere.
    }

    private static void readCheckSessionProperty() {
        RuleSession session = RuleSessionManager.getCurrentRuleSession();
        if(session!=null){
			RuleServiceProvider rsp = session.getRuleServiceProvider();
			Properties properties = rsp.getProperties();

			String checkSessionString = properties.getProperty(
					SystemProperty.PERFORM_CONCEPT_SESSION_CHECK
							.getPropertyName(), Boolean.TRUE.toString());

			checkSession = Boolean.valueOf(checkSessionString);
			checkSessionInitialized = true;
        }
    }
    
    public ConceptImpl deReference(Reference ref, Class refClass) {
        return deReference(ref.getId(), refClass, ref.getClass());
    }

    protected ConceptImpl deReference(long conceptId, Class refClass, Class refGetClass) {
        return deReference(conceptId, refClass, refGetClass, false);
    }

    protected ConceptImpl deReference(long conceptId, Class refClass, Class refGetClass,
                                      boolean isNullOK) {
        //make it more thread safe, since post-rtc can set m_wmHandle to null at any time.
        //really m_wmHandle should be volatile.

        WorkingMemory wm = null;
        {
            BaseHandle handle = m_wmHandle;
            if (handle != null) {
                wm = handle.getWorkingMemory();
            }
        }


        if (wm == null) {
            //throw new RuntimeException("ERROR: handle inside ConceptImpl can't be null");
            RuleSession session = RuleSessionManager.getCurrentRuleSession();
            if (session != null) {
                if (session.getObjectManager() instanceof ObjectBasedStore) {
                    ConceptImpl c = (ConceptImpl) ((ObjectBasedStore) session.getObjectManager())
                            .getElement(conceptId, true);
                    
                    return c;
                    
                
                } 
                // case for in-memory
                else if (session.getObjectManager() instanceof InMemoryObjectManager) {
                	ConceptImpl c = (ConceptImpl) ((InMemoryObjectManager) session.getObjectManager())
                            .getElement(conceptId, true);
                	return c;
                }
            }
            else {
                throw new RuntimeException(
                        "deReference: " + conceptId + ", " + refGetClass + " Rule Session is NULL");
            }
            return null;
        }
        else {
            ConceptImpl c = (ConceptImpl) ((BaseObjectManager) wm.getObjectManager())
                    .getElement(conceptId, refClass, false, true);

            if (c == null && !isNullOK) //just in case
            //throw new RuntimeException("Reference to Id <" + conceptId + "> class <" + refClass + "> is null for instance <" + this + ">");
            {
                if (getLogger().isEnabledFor(Level.DEBUG)) {
                    getLogger().log(Level.DEBUG, "Reference to Id <" + conceptId + "> class <" + refClass 
                            + "> is null for instance <" + this + ">");
                }
            }
            return c;
        }
    }

    //don't use!!! internal method
//    private ConceptImpl deReference(long conceptId, Class refClass) {
//        Logger logger = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getLogger();
//        if(m_wmHandle == null) logger.logError("deReference m_wmHandle is null for id " + conceptId);
//        else if(m_wmHandle.getWorkingMemory() == null) logger.logError("deReference m_wmHandle.getWorkingMemory() is null for id " + conceptId);
//        else if(m_wmHandle.getWorkingMemory().getObjectManager() == null) logger.logError("deReference m_wmHandle.getWorkingMemory().getObjectManager() is null for id " + conceptId);
//        return (ConceptImpl) ((BaseObjectManager)m_wmHandle.getWorkingMemory().getObjectManager()).getElement(conceptId, refClass);
//    }

    private ConceptImpl resolveConceptPointer_nullOK(ConceptOrReference conceptOrReference,
                                                     Class refClass) {

        if (conceptOrReference instanceof Reference) {  //todo - should optimize for propertyBasedStore to convert to concept
            return deReference(conceptOrReference.getId(), refClass, conceptOrReference.getClass(),
                    true);
        }
        else {
            return (ConceptImpl) conceptOrReference;
        }
    }

    public ConceptImpl resolveConceptPointer(ConceptOrReference conceptOrReference,
                                             Class refClass) {

        if (conceptOrReference instanceof Reference) {  //todo - should optimize for propertyBasedStore to convert to concept
            return deReference((Reference) conceptOrReference, refClass);
        }
        else {
            return (ConceptImpl) conceptOrReference;
        }
    }

    public ConceptOrReference setConceptPointer(ConceptImpl instance) {
    	return instance;
//        if (instance == null) {
//            return null;
//        }
//        else if (objectBasedStore()) {
//            return new Reference(instance.getId());
//        }
//        else {
//            return instance;
//        }
    }

    public void setExtId(String extId) throws ExtIdAlreadyBoundException {
        if ((this.extId != null && this.extId.length() != 0) || isStarted()) {
            throw new ExtIdAlreadyBoundException("Instance already has ExtId or is asserted to WM");
        }
        this.extId = extId;
    }

    public ExpandedName getExpandedName() {
        throw new RuntimeException("Trying to get the name of an Abstract Concept");
    }

    protected Property.PropertyContainedConcept getContainedConceptProperty(String propertyName) {
        return null;
    }

    protected Property.PropertyConceptReference getConceptReferenceProperty(String propertyName) {
        return null;
    }

    public Property.PropertyContainedConcept[] getContainedConceptProperties() {
        return null;
    }

    public Property.PropertyConceptReference[] getConceptReferenceProperties() {
        return null;
    }

    protected String getParentPropertyName() {
        return null;
    }

    public Property getProperty(String name) {
        return null;
    }

    public Property[] getProperties() {
        return new Property[0];
    }

    public Property[] getLocalProperties() {
        return new Property[0];
    }

//    //methods should be implemented in subclass--
//
//    public void writeToDataOutput(DataOutput output) throws Exception {
//        Property[] allProperties = getProperties();
//        for (int i=0; i < allProperties.length; i++) {
//            allProperties[i].writeToDataOutput(output);
//        }
//    }
//
//    public void readFromDataInput(DataInput input) throws Exception {
//        Property[] allProperties = getProperties();
//        for (int i=0; i < allProperties.length; i++) {
//            allProperties[i].readFromDataInput(input);
//        }
//    }

    public PropertyAtom getPropertyAtom(String name) {
        Property p = getProperty(name);
        if (p instanceof PropertyAtom) {
            return (PropertyAtom) p;
        }
        else {
            return null;
        }
    }

    public PropertyArray getPropertyArray(String name) {
        Property p = getProperty(name);
        if (p instanceof PropertyArray) {
            return (PropertyArray) p;
        }
        else {
            return null;
        }
    }

    public int[] getDirtyBitArray() {
        return m_dirtyBitArray;
    }

    public void clearDirtyBitArray() {
        if(m_dirtyBitArray != null && m_dirtyBitArray.length > 0){
        	boolean persistenceModified = getPersistenceModified();
            for (int i = 0; i < m_dirtyBitArray.length; i++) {
                m_dirtyBitArray[i] = 0;
            }
            //restore the value since it shouldn't be cleared here
            if(persistenceModified) setPersistenceModified();
        }
    }
    
    public void start(Handle handle) {
    	//clear references to concepts that may be old versions
    	//TODO verify this isn't called with Memory OM
        //TODO don't clear references to memory only concepts
        //but references from memory only concepts to cache concepts must be cleared
    	clearAllReferences();
        m_wmHandle = (BaseHandle) handle;
        initStateMachine();
    }

    public void setHandle(Handle handle) {
        m_wmHandle = (BaseHandle) handle;
    }

    public void delete() {
        m_dirtyBitArray = null;
        clearChildrenDirtyBits();
        clearAllReferenced_ReverseRef();
        clearAllReverseRef_ConceptReferenceProperties_AndParentProperty();
    }

    public boolean isDeleted() {
        return m_dirtyBitArray == null;
    }

    protected boolean isStarted() {
        return m_wmHandle != null;
    }

    /**
     * This flag will set to true when an instance is modified in Conflict Resolution.  It will then
     * reset at the end of the Conflict Resolution. It is not recommend to use this flag in rule
     * function or depends on it for firing a rule.  The result could be unexpected by doing that.
     *
     * @return true is this instance is modified in the Conflict Resolution.
     */
    public boolean isModified() {
        if (m_wmHandle == null) {
        	return BitSet.isAnyBitSet(m_dirtyBitArray);
        }
        return m_wmHandle.isRtcModified();
    }

    public boolean isNew() {
        if (m_wmHandle == null) {
            return getVersion() == 0;
        }
        return m_wmHandle.isRtcAsserted();
    }

    /**
     * This threadlocal and related logic is added to prevent infinite recursion in case of cyclic
     * references. A reference is expanded only the first time. if its encountered again in the same
     * hierarchy, dont expand the node -- doing so will create infinite recursion instead, only put
     * its reference in the xml
     */
    private static final ThreadLocal serializedNodes = new ThreadLocal() {
        public Object initialValue() {
            return new HashSet();
        }
    };

    public void toXiNode(XiNode node) {
        baseXiNode(node); 
        node.setAttributeStringValue(ExpandedName.makeName(ATTRIBUTE_TYPE), String.valueOf(getType()));

        Set nodes = (Set) serializedNodes.get();
        if (nodes.contains(this)) {
            // then already its being serialized, so skip
        }
        else {
            nodes.add(this);
            Property[] p = null;
            if (excludeNullProps()) {
                p = getPropertiesNullOK();
            }
            else {
                p = getProperties();
            }

            for (int i = 0; i < p.length; i++) {
                if (p[i] != null) {
                    ((PropertyImpl) p[i]).fillXiNode(node, false);
                }
            }
            nodes.remove(this);
        }
    }

    public void toXiNode(ConceptToXiNodeFilter filter, XiNode node) {
        baseXiNode(node);
        node.setAttributeStringValue(ExpandedName.makeName(ATTRIBUTE_TYPE), String.valueOf(getType()));

        Set nodes = (Set) serializedNodes.get();
        if (nodes.contains(this)) {
            // then already its being serialized, so skip
        }
        else {
            nodes.add(this);
            Property[] p = null;
            if (excludeNullProps()) {
                p = getPropertiesNullOK();
            }
            else {
                p = getProperties();
            }

            Object session = filter.beginSession();
            for (int i = 0; i < p.length; i++) {
                if (p[i] != null && filter.allow(session, p[i])) {
                    ((PropertyImpl) p[i]).fillXiNode(node, false);
                }
            }
            filter.endSession(session);

            nodes.remove(this);
        }
    }

    public void toXiNode(XiNode node, boolean changeOnly) {
        node.setAttributeStringValue(ExpandedName.makeName(ATTRIBUTE_ID), String.valueOf(id));
        if (extId != null) {
            node.setAttributeStringValue(ExpandedName.makeName(ATTRIBUTE_EXTID), extId);
        }
        node.setAttributeStringValue(ExpandedName.makeName(ATTRIBUTE_TYPE), String.valueOf(getType()));
        Property[] p = getProperties();
        for (int i = 0; i < p.length; i++) {
            ((PropertyImpl) p[i]).fillXiNode(node, changeOnly);
        }
    }

    protected void checkForCache() {
        try {
            if (m_wmHandle == null) {
                if (isLoadedFromCache()) {
                    // This concept was downloaded from the cache using a query or some other op
                    RuleSession session = RuleSessionManager.getCurrentRuleSession();
                    if (session != null) {
                        //following should only be true during an RTC
                        if (WorkingMemoryImpl.executingInside(((RuleSessionImpl) session).getWorkingMemory())) {
                            if (session.getObjectManager() instanceof ObjectBasedStore) {
                                if ((getExtId() != null) && (getExtId().length() > 0))
                                    m_wmHandle=((ObjectBasedStore)session.getObjectManager()).getNewElementExtHandle(this,null,true);
                                else
                                    m_wmHandle=((ObjectBasedStore)session.getObjectManager()).getNewElementHandle(this,null,true);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void clearChildrenDirtyBits() {
        Property[] allProperties = getPropertiesNullOK();
        for (int i = 0; i < allProperties.length; i++) {
            if (allProperties[i] instanceof PropertyStateMachineImpl) {
                ((PropertyStateMachineImpl) allProperties[i]).clearDirtyBitArray();
            }
        }
    }

    protected boolean modifyStateMachine(PropertyImpl property) {
        if(isLoadedFromCache())
            checkForCache();

        BitSet.setBit(m_dirtyBitArray, property.dirtyIndex());

        if (m_wmHandle != null) {
            WorkingMemory wm = m_wmHandle.getWorkingMemory();
            if (wm.modifyObject(m_wmHandle, false, true)) {
                modifyParent();
            }
        }
        return true;
    }

    //should NOT use this method!!!!!!!!
    // return true if the instance is already asserted, else true
    public boolean modifyConcept(PropertyImpl property) {
        return modifyConcept(property, false, true);
    }
    //modifyParent = false is only used by PropertyStateMachineState / StateMachineConceptImpl
    public boolean modifyConcept(PropertyImpl property, boolean noPropertiesModified, boolean modifyParent) {
        if(isLoadedFromCache())
            checkForCache();

        BitSet.setBit(m_dirtyBitArray, property.dirtyIndex());
        if(trackUnmodifiedCCPropValue && !noPropertiesModified) {
            BitSet.setBit(m_dirtyBitArray, property.modifiedIndex());
        }

        if (m_wmHandle == null) {
            return false;
        }

        boolean recordThis = !trackUnmodifiedCCPropValue || !noPropertiesModified;
        if (m_wmHandle.getWorkingMemory().modifyObject(m_wmHandle, false, recordThis)) {
            modifyParent();
        }
        if(recordThis) {
        	setPersistenceModified();
        }
        return true;
    }

    protected boolean isContained() {
        return this instanceof ContainedConcept;
    }

    protected int rRefStart() {
        return isContained() ? 1 : 0;
    }

    public long[] getReverseReferences() {
        long[] ret = new long[m_reverseRefSize];
        if (m_reverseRefSize != 0) {
            int i = 0;
            for(RevRefItr itr = rrItr(); itr.next(); i++) {
                ret[i] = itr.reverseRef().getId();
            }
        }
        return ret;
    }

    private void incrReverseRef(String propertyName, ConceptOrReference conceptOrReference) {
    	int rRefStart = rRefStart();
        if (m_reverseRefSize == 0) {
            Object[] newRefs = new Object[4 + rRefStart];
            if (m_reverseRefs != null && m_reverseRefs.length > 0 && rRefStart != 0) {
                newRefs[0] = m_reverseRefs[0];
            }
            m_reverseRefs = newRefs;
        }
        else if (m_reverseRefs.length / 2 <= m_reverseRefSize) {
        	int newlen = 4;
        	if (m_reverseRefSize > 1) {
	            //multiply reverse ref capacity by 1.5
	            newlen = m_reverseRefSize * 3;
        	}
            //ensure it's odd if rRefStart is 1 else it's even
            if(newlen % 2 != rRefStart)newlen++;

            Object[] newRefs = new Object[newlen];
            System.arraycopy(m_reverseRefs, 0, newRefs, 0, m_reverseRefs.length);

            m_reverseRefs = newRefs;
        }
        int newIdx = m_reverseRefSize*2 + rRefStart;
        m_reverseRefs[newIdx + 1] = propertyName;
        m_reverseRefs[newIdx] = conceptOrReference;
        m_reverseRefSize++;
    }

    private void clearAllReferenced_ReverseRef() {
        Property.PropertyConceptReference[] refs = getConceptReferenceProperties();
        if (refs != null) {
            for (int i = 0; i < refs.length; i++) {
                if (refs[i] instanceof PropertyArrayConceptReference) {
                    PropertyArrayConceptReference prefs = (PropertyArrayConceptReference) refs[i];
                    if (prefs.maintainReverseRef()) {
	                    for (int j = 0; j < prefs.length(); j++) {
	                        PropertyAtomConceptReference pref =
	                                (PropertyAtomConceptReference) prefs.get(j);
	                        ConceptImpl concept = (ConceptImpl) pref.getConcept();
	                        //ConceptImpl concept = deReference(pref.getConceptId());
	                        if (concept != null && !concept.isDeleted()) {
	                            concept.clearReverseRef(pref);
	                        }
	                    }
	                }	
                }
                else {
                    PropertyAtomConceptReference pref = (PropertyAtomConceptReference) refs[i];
                    if (pref.maintainReverseRef()) {
                        ConceptImpl concept = (ConceptImpl) pref.getConcept();
                        //ConceptImpl concept = deReference(pref.getConceptId());
                        if (concept != null && !concept.isDeleted()) {
                            concept.clearReverseRef(pref);
                        }
                    }
                }
            }
        }
    }

    public void setReverseRef(Property.PropertyConceptReference refByProperty) {
        checkSession();
        _setReverseRef(refByProperty.getName(), setConceptPointer((ConceptImpl) refByProperty.getParent()));
    }
    
    public void setReverseRef(String property, ConceptOrReference parent) {
        checkSession();
        _setReverseRef(property, parent);
    }
    protected void _setReverseRef(String property, ConceptOrReference parent) {
        incrReverseRef(property, parent);
        WorkingMemoryImpl.recordReverseRef(m_wmHandle);
        setPersistenceModified();
    }

    public void clearReverseRef(Property.PropertyConceptReference refByProperty) {
        checkSession();
        if (m_reverseRefSize > 0) {
        	int rRefStart = rRefStart();
            for (int i = rRefStart; i < m_reverseRefSize*2; i+=2) {
                if (Reference.idEquals(((ConceptOrReference)m_reverseRefs[i]), refByProperty.getParent())
                		&& m_reverseRefs[i+1].equals(refByProperty.getName()))
                {
                    if (m_reverseRefSize > 1) {
                        //i and i + 1 to be removed, shrink array by 2 elements into the empty space 
                        int numMoved = (m_reverseRefSize * 2) - (i - rRefStart) - 2;
                        assert numMoved >= 0;
                        if (numMoved > 0) {
                        	System.arraycopy(m_reverseRefs, i + 2, m_reverseRefs, i, numMoved);
                        }
                        m_reverseRefSize--;
                        //null out the 2 elements after the end of the region that was shifted back
                        //or the elements to be removed if they were at the end of the array
                        m_reverseRefs[rRefStart + m_reverseRefSize*2] = null;
                        m_reverseRefs[rRefStart + m_reverseRefSize*2 + 1] = null;
                    } else {
                        m_reverseRefSize = 0;
                        if (isContained() && m_reverseRefs[0] != null) {
                            m_reverseRefs = new Object[]{m_reverseRefs[0]};
                        } else {
                            m_reverseRefs = null;
                        }
                    }
                    WorkingMemoryImpl.recordReverseRef(m_wmHandle);
                    clearPersistenceModified();
                    return;
                }
            }
        }
    }

    private void clearAllReverseRef_ConceptReferenceProperties_AndParentProperty() {
        if (m_reverseRefSize > 0) {
            Object[] reverseRefs = m_reverseRefs;
            m_reverseRefs = null;
            int size = m_reverseRefSize;
            m_reverseRefSize = 0;
            clearAllReverseRef_ConceptReferenceProperties(size, reverseRefs);
            if(isContained()) {
                clearParentProperty((ConceptOrReference)reverseRefs[0]);
            }
        } else if(isContained() && m_reverseRefs != null && m_reverseRefs.length > 0) {
            ConceptOrReference parent = (ConceptOrReference)m_reverseRefs[0];
            m_reverseRefs = null;
            clearParentProperty(parent);
        }
    }

    private void clearAllReverseRef_ConceptReferenceProperties(int size, Object[]reverseRefs ) {
        // Nick - dont need to check for maintainReverseRef for each property
        for (RevRefItr itr = new RevRefItr(reverseRefs, size, rRefStart()); itr.next();) {
            //ConceptImpl concept =  resolveConceptPointer(temp_referBy_Instance[i]);
            ConceptImpl concept = resolveConceptPointer_nullOK(itr.reverseRef(), null);
            if (concept != null && !concept.isDeleted()) {
                Property property = concept.getConceptReferenceProperty(itr.propName());
                if (property instanceof PropertyAtomConceptReference) {
                    if (property instanceof PropertyAtomConceptReferenceImpl) {
                        ((PropertyAtomConceptReferenceImpl) property).setNull();
                    }
                    else {
                        ((PropertyAtomConceptReferenceSimple) property).setNull();
                    }
                }
                else if (property instanceof PropertyArrayConceptReference) {
                    if (property.getHistorySize() <= 1) {
                        if (property instanceof PropertyArrayConceptReferenceImpl) {
                            ((PropertyArrayConceptReferenceImpl) property)
                                    .removeById(getId(), false);
                        }
                        else {
                            ((PropertyArrayConceptReferenceSimple) property)
                                    .removeById(getId(), false);
                        }
                    }
                    else {
                        if (property instanceof PropertyArrayConceptReferenceImpl) {
                            PropertyAtomConceptReferenceImpl ref =
                                    (PropertyAtomConceptReferenceImpl) ((PropertyArrayConceptReferenceImpl) property)
                                            .getPropertyAtomConceptReference(getId());
                            if (ref != null) {
                                ref.setNull();
                            }
                            // ((PropertyAtomConceptReferenceImpl)((PropertyArrayConceptReferenceImpl)property).getPropertyAtomConceptReference(getId())).setNull();
                        }
                        else {
                            PropertyAtomConceptReferenceSimple ref =
                                    (PropertyAtomConceptReferenceSimple) ((PropertyArrayConceptReferenceSimple) property)
                                            .getPropertyAtomConceptReference(getId());
                            if (ref != null) {
                                ref.setNull();
                            }
                            // ((PropertyAtomConceptReferenceSimple)((PropertyArrayConceptReferenceSimple)property).getPropertyAtomConceptReference(getId())).setNull();
                        }
                    }
                }
            }
        }
        WorkingMemoryImpl.recordReverseRef(m_wmHandle);
        setPersistenceModified();
    }
    //methods related to reverse reference--

    public List getChildren() {
        return getContainedInstances(null, true);
    }

    public List getChildIds() {
        return getContainedInstances(null, false);
    }

    protected List getContainedInstances(LinkedList list, boolean instanceOrId) {
        Property.PropertyContainedConcept[] property = getContainedConceptProperties();
        if (property == null) {
            return null;
        }
        if(list == null) list = new LinkedList();

        for (int i = 0; i < property.length; i++) {
            if (property[i] instanceof PropertyAtomContainedConcept) {
                ContainedConcept instance =
                        ((PropertyAtomContainedConcept) property[i]).getContainedConcept();
                if (instance != null) {
                    list.addFirst(instanceOrId ? instance : new Long(instance.getId()));
                    ((ConceptImpl) instance).getContainedInstances(list, instanceOrId);
                }
            }
            else if (property[i] instanceof PropertyArrayContainedConcept) {
                PropertyArrayContainedConcept arr = ((PropertyArrayContainedConcept) property[i]);
                for (int j = 0; j < arr.length(); j++) {
                    ContainedConcept instance =
                            ((PropertyAtomContainedConcept) arr.get(j)).getContainedConcept();
                    if (instance != null) {
                        list.addFirst(instanceOrId ? instance : new Long(instance.getId()));
                        ((ConceptImpl) instance).getContainedInstances(list, instanceOrId);
                    }
                }
            }
        }
        return list;
    }

    public void setParentReference(ConceptOrReference parent) {
        if (isContained()) {
            if (parent == null) {
                if(m_reverseRefSize == 0) m_reverseRefs = null;
                else m_reverseRefs[0] = null;
            } else {
                if(m_reverseRefs == null) m_reverseRefs = new Object[]{parent};
                else m_reverseRefs[0] = parent;
            }
        }
    }

    //implements method in ContainedConcept interface
    public void setParent(Concept instance) {
        checkSession();
        if (instance == null) {
            throw new ContainedConceptException(ResourceManager.getInstance().formatMessage(
                    "concept.parent.null.exception", this));
        } else if (isDeleted()) {
            throw new ContainedConceptException(
                    ResourceManager.getInstance().formatMessage("concept.deleted.parent.exception",
                            instance, this));
        } else if (getParentReference() == null) {
            setParentReference(setConceptPointer((ConceptImpl) instance));
            WorkingMemoryImpl.recordContainerRef(m_wmHandle);  //todo - I need to do this for all the OM type - in memory/property/objectbased
            setPersistenceModified();
        } else {
            throw new ContainedConceptException(
                    ResourceManager.getInstance().formatMessage("concept.parent.defined.exception",
                            instance, this, resolveConceptPointer(getParentReference(),
                            null))); //todo - should we pass the class?  shouldn't because class is useful only when doing recovery and we shouldn't modify object at recovery time?
        }
    }

    //should NOT use this method!!!!!!!!
    public void nullParent() {
        checkSession();
        setParentReference(null);
        if (m_wmHandle != null) {
            getWorkingMemory().retractObject(this, true);   //todo - what todo if m_wmHandle is null
        }
        WorkingMemoryImpl.recordContainerRef(m_wmHandle);
        setPersistenceModified();
    }

    public Concept getParent() {
        return resolveConceptPointer(getParentReference(), null);
    }

    public ConceptOrReference getParentReference() {
        if (isContained() && m_reverseRefs != null && m_reverseRefs.length > 0) {
            return (ConceptOrReference)m_reverseRefs[0];
        } else {
            return null;
        }
    }

    protected void modifyParent() {
        ConceptImpl parent = resolveConceptPointer(getParentReference(), null);  //todo - should we pass the class?  shouldn't because class is useful only when doing recovery and we shouldn't modify object at recovery time?

        if (parent != null) {
            String parentPropertyName = getParentPropertyName();
            PropertyImpl property = (PropertyImpl) parent.getContainedConceptProperty(parentPropertyName);
            parent.modifyConcept(property, true, true);
        }
    }

    private void clearParentProperty(ConceptOrReference parentContainer) {
		//ConceptImpl parent = resolveConceptPointer(m_parentContainer);
        ConceptImpl parent = resolveConceptPointer_nullOK(parentContainer,
                null);  //todo - should we pass the class?  shouldn't because class is useful only when doing recovery and we shouldn't modify object at recovery time?
        if (parent != null && !parent.isDeleted()) {
            Property property = parent.getContainedConceptProperty(getParentPropertyName());
            if (property instanceof PropertyAtomContainedConcept) {
                if (property instanceof PropertyAtomContainedConceptSimple) {
                    ((PropertyAtomContainedConceptSimple) property).setNull(this);
                }
                else {
                    ((PropertyAtomContainedConceptImpl) property).setNull(this);
                }
            }
            else if (property instanceof PropertyArrayContainedConcept) {
                if (property.getHistorySize() <= 1) {
                    if (property instanceof PropertyArrayContainedConceptImpl) {
                        ((PropertyArrayContainedConceptImpl) property).removeById(getId(), false);
                    }
                    else {
                        ((PropertyArrayContainedConceptSimple) property).removeById(getId(), false);
                    }
                }
                else {
                    if (property instanceof PropertyArrayContainedConceptImpl) {
                        ((PropertyAtomContainedConceptImpl) ((PropertyArrayContainedConceptImpl) property)
                                .getPropertyAtomContainedConcept(getId())).setNull(this);
                    }
                    else {
                        ((PropertyAtomContainedConceptSimple) ((PropertyArrayContainedConceptSimple) property)
                                .getPropertyAtomContainedConcept(getId())).setNull(this);
                    }
                }
            }
        }
    }

    /**
     *
     * @param serializer
     */
    public void serialize(ConceptSerializer serializer) {
        if (serializer.areNullPropsSerialized()) {
            serialize_nullprops(serializer);
        } else {
            serialize_nonullprops(serializer);
        }
    }

    private void serialize_start(ConceptSerializer serializer) {
        if (isMarkedDeleted()) {
            serializer.startConcept(this.getClass(), getId(), getExtId(),
                                    ConceptSerializer.STATE_DELETED, getVersion());
        }
        else {
            serializer.startConcept(this.getClass(), getId(), getExtId(),
                                    ConceptSerializer.STATE_NEW, getVersion());
        }
        serializer.startParentConcept(this.getParentReference());
        serializer.endParentConcept();
        serializer.startReverseReferences(rrItr(), m_reverseRefSize);
        serializer.endReverseReferences();
    }

    public void serialize_nullprops(ConceptSerializer serializer) {
        serialize_start(serializer);
        Property[] allProperties = getProperties();
        for (int i = 0; i < allProperties.length; i++) {
            allProperties[i].serialize(serializer, i);
            serializer.endProperty();
        }
        serializer.endConcept();
    }

    /**
     * @param serializer
     */
    public void serialize_nonullprops(ConceptSerializer serializer) {
        serialize_start(serializer);
        Property[] allProperties = getPropertiesNullOK();
        for (int i = 0; i < allProperties.length; i++) {
            if (allProperties[i] != null){
                if (allProperties[i] instanceof PropertyAtom) {
                    if (((PropertyAtom) allProperties[i]).isSet()) {
                        serializer.startProperty(null,i,true);
                        allProperties[i].serialize(serializer, i);
                    } else {
                        serializer.startProperty(null,i,false);
                    }
                } else if (allProperties[i] instanceof PropertyArray) {
                    if (((PropertyArray) allProperties[i]).length() > 0) {
                        serializer.startProperty(null,i,true);
                        allProperties[i].serialize(serializer, i);
                    } else {
                        serializer.startProperty(null,i,false);
                    }
                } else {
                    throw new RuntimeException("Unknown Property Type ... " + allProperties[i]);
                }
            } else {
                serializer.startProperty(null,i,false);
            }
            serializer.endProperty();
        }
        serializer.endConcept();
    }


    /**
     * @return
     */
    public Comparable getVersionIndicator() {
        return 0x7FFFFFFF & m_version;
    }

    public int getVersion() {
        return 0x7FFFFFFF & m_version;
    }

    public void setVersion(int version) {
        assert version >= 0;
        m_version &= 0x80000000;
        version &= 0x7FFFFFFF;
        m_version |= version;
    }

    /**
     *
     */
    public void incrementVersion() {
        assert (m_version & 0x7FFFFFFF) != 0x7FFFFFFF;
        m_version++;
    }

    public StateMachineConcept newSMWithIndex(int index) {
        throw new UnsupportedOperationException("newSMWithIndex() not implemeted");
    }

    public void deserialize(ConceptDeserializer deserializer) {
        if (deserializer.areNullPropsSerialized()) {
            deserialize_nullprops(deserializer);
        } else {
            deserialize_nonullprops(deserializer);
        }
    }

    private void deserialize_start(ConceptDeserializer deserializer) {
        deserializer.startConcept();
        //deserializer
        this.setId(deserializer.getId());
        if (this.getExtId() == null) {
            this.setExtId(deserializer.getExtId());
        }

        if (deserializer.isDeleted()) {
            this.markDeleted();
        }
        setVersion(deserializer.getVersion());
        ConceptOrReference parent = deserializer.startParentConcept();
        deserializer.endParentConcept();

        m_reverseRefSize = deserializer.startReverseReferences();
        if (m_reverseRefSize > 0) {
            m_reverseRefs = new Object[m_reverseRefSize*2 + rRefStart()];
            deserializer.getReverseReferences(rrItr());
            deserializer.endReverseReferences();
        }

        setParentReference(parent);
    }

    public void deserialize_nullprops(ConceptDeserializer deserializer) {
        deserialize_start(deserializer);

        Property[] allProperties = getProperties();
        for (int i = 0; i < allProperties.length; i++) {
            allProperties[i].deserialize(deserializer, i);
            deserializer.endProperty();
        }
        deserializer.endConcept();
    }

    /**
     * @param deserializer
     */
    public void deserialize_nonullprops(ConceptDeserializer deserializer) {
        deserialize_start(deserializer);

        for (int i=0; i < getNumProperties(); i++) {
            boolean isSet=deserializer.startProperty(null,i);
            if (isSet) {
                Property prop=getProperty(i);
                if (prop != null) {
                    prop.deserialize(deserializer,i);
                }
            }
            deserializer.endProperty();
        }
        deserializer.endConcept();
    }

    //function specific for contained Concept--

    //stuff for SM only++  todo - cleanup!!!

    public com.tibco.cep.runtime.model.element.stategraph.impl.StateMachineImpl sm = null;

    public void initStateMachine() {
    }

    public PropertyArrayInt getTransitionStatuses() {
        throw new RuntimeException("Concept does not have a state machine");
    }

    public com.tibco.cep.runtime.model.element.stategraph.StateMachineCorrelation getCorrelationStatuses() {
        throw new RuntimeException("Forward correlation is not supported");
    }

    public PropertyAtomBoolean getMachineClosed() {
        throw new RuntimeException("Concept does not have a state machine");
    }

    public String getType() {
        return null;
    }
    //stuff for SM only--

    public boolean hasMainStateMachine() {
        return false;
    }

    public StateMachineConcept getMainStateMachine() {
        return null;
    }

    public Property.PropertyStateMachine getMainStateMachineProperty() {
        return null;
    }

    @Override
    public StateMachineConcept startMainStateMachine() {
        PropertyStateMachineImpl psm = (PropertyStateMachineImpl) getMainStateMachineProperty();
        if (psm != null) {
            if (psm.getContainedConceptId() <= 0) {
                psm.startMachine();
                return (StateMachineConcept) psm.getConcept();
            }
        }
        return null;
    }

    public Property.PropertyStateMachine[] getStateMachineProperties() {
        return new Property.PropertyStateMachine[0];
    }

    public Property.PropertyStateMachine getStateMachineProperty(String stateMachineName) {
        return null;
    }

    public List getAllModifiedProperties(int[] dirtyBitsArray, boolean passive) {
        List ret = new LinkedList();
        Property[] all;
        if (passive) {
            all = this.getProperties();
        }
        else {
            all = this.getPropertiesNullOK();
        }
        if (null == dirtyBitsArray || dirtyBitsArray.length == 0) {
            return ret;
        }
        for (int i = 0; i < all.length; i++) {
            if (all[i] != null) {
                PropertyImpl prop = (PropertyImpl) all[i];
                if (BitSet.isBitSet(dirtyBitsArray, prop.dirtyIndex())) {
                	if(!trackUnmodifiedCCPropValue || BitSet.isBitSet(dirtyBitsArray, prop.modifiedIndex())) {
                		ret.add(prop);
                	}
                }
            }
        }
        return ret;
    }

    public Property[] getPropertiesNullOK() {
        return new Property[0];
    }

    /**
     * @return indicates if a null valued properties must be included in the xml view. overridden in
     *         generated classes based on a system property "tibco.be.schema.exclude.null.props"
     */
    public boolean excludeNullProps() {
        return false;
    }

    /**
     * @return indicates if a null valued properties must be included during xml import. overridden in
     *         generated classes based on a system property "tibco.be.schema.include.null.props"
     */
    public boolean includeNullProps() {
        return true;
    }

    /**
     * @return indicates if a property reference needs to be expanded to its full XML rather than
     *         including a "refId" attribute.
     *         <p/>
     *         overridden in generated classes based on a system property
     *         "tibco.be.schema.ref.expand"
     */
    public boolean expandPropertyRefs() {
        return false;
    }

    /**
     * @return indicates if xsi:nil attribute is to be set on the xml instance document
     *         <p/>
     *         overridden in generated classes based on system property tibco.be.schema.nil.attribs
     */
    public boolean setNilAttribs() {
        return false;
    }

    /**
     * @return indicates if during xml->concept conversion, null values have to be recognized and
     *         where "0" cannot be treated as a null value (for numeric primitives) Feature
     *         currently used in db concepts
     *         <p/>
     *         overridden in generated classes based on system property tibco.be.schema.treat.null.values
     */
    public boolean treatNullValues() {
        return false;
    }

    /**
     * @param name Property name
     * @return property reference (may or may not be null)
     */
    public Property getPropertyNullOK(String name) {
        return getProperty(name); //default behavior
    }

    /**
     * Gets the property by its Id
     *
     * @param id propertyId of the property
     * @return Property
     */
    public <T extends Property> T getProperty(int id) {
        return (T)getProperties()[id];
    }
    public Property getPropertyNullOK(int id) {
        return getProperty(id);
    }

    public String toString() {
        return super.toString() + "&v=" + this.getVersion();
    }

    public void setPropertyValue(String name, Object value) throws Exception{
        Property p = getProperty(name);
        if (p == null) {
            throw new NoSuchFieldException("No " + name + " field for " + this);
        }
        if (p instanceof PropertyAtom) {
            ((PropertyAtom)p).setValue(value);
        }
        else { //propertyArray
            ((PropertyArray)p).add(value);
        }
    }

    public Object getPropertyValue(String name) throws NoSuchFieldException {
        Property p = getProperty(name);
        if (p == null) {
            throw new NoSuchFieldException("No " + name + " field for " + this);
        }
        if (p instanceof PropertyAtom) {
            return ((PropertyAtom)p).getValue();
        }
        else { //propertyArray
            int l = ((PropertyArray)p).length();
            if (l <= 0) {
                return null;   
            }
            else {
                return ((PropertyArray)p).get(l - 1).getValue();
            }
        }
    }

    public void writeExternal(DataOutput out) throws IOException {
        SerializerFactory factory = SerializerFactoryFactory.getInstance();
        Serializer serializer = factory.newSerializer();
        serializer.serialize(Serializer.Type.CONCEPT, this, out, null);
    }

    public void readExternal(DataInput in) throws IOException {
        SerializerFactory factory = SerializerFactoryFactory.getInstance();
        Serializer serializer = factory.newSerializer();
        serializer.deserialize(Serializer.Type.CONCEPT, in, this, null);
    }

    //will be called by ContainedConcept properties
    public NullContainedConcept getNullContainedConcept() {
        return NullContainedConceptImpl.INSTANCE;
    }

    public MetaProperty getMetaProperty(int index) {
        throw new UnsupportedOperationException("ConceptImpl.getMetaProperty");
    }

    public void clearInRete() {
    	if(m_wmHandle != null) m_wmHandle.clearInRete();
    }
    
    public final RevRefItr rrItr() {
        return new RevRefItr(m_reverseRefs, m_reverseRefSize, rRefStart());
    }

    final public static class RevRefItr
    {
        private int idx;
        private final int size;
        private final Object[] arr;

        protected RevRefItr(Object[] reverseRefs, int reverseRefsSize, int rrStart) {
            idx = rrStart - 2;
            arr = reverseRefs;
            size = reverseRefsSize*2 + rrStart;
        }

        final public boolean next() {
            idx+=2;
            return idx < size;
        }

        final public String propName() {
            return (String)arr[idx+1];
        }

        final public void setPropName(String name) {
            arr[idx+1] = name;
        }

        final public ConceptOrReference reverseRef() {
            return (ConceptOrReference)arr[idx];
        }

        final public void setReverseRef(ConceptOrReference reverseRef) {
            arr[idx] = reverseRef;
        }
        
        final public int numReverseRefs() {
        	return size/2;
        }
    }
}