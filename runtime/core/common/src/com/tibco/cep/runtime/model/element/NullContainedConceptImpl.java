package com.tibco.cep.runtime.model.element;

import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.kernel.service.ObjectManager;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;
import com.tibco.cep.runtime.model.element.impl.Reference;
import com.tibco.cep.runtime.model.element.impl.property.PropertyImpl;
import com.tibco.cep.runtime.model.element.impl.property.metaprop.MetaProperty;
import com.tibco.cep.runtime.model.element.stategraph.StateMachineCorrelation;
import com.tibco.cep.util.NullContainedConceptAccessInConditionException;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: 1/18/12
 * Time: 7:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class NullContainedConceptImpl extends ConceptImpl implements NullContainedConcept {
    public static final NullContainedConceptImpl INSTANCE = new NullContainedConceptImpl();

    @Override
    public int getVersion() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public void setVersion(int version) {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public void incrementVersion() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public StateMachineConcept newSMWithIndex(int index) {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public Property getProperty(String name) {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public Property[] getProperties() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public Property[] getLocalProperties() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public PropertyAtom getPropertyAtom(String name) {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public PropertyArray getPropertyArray(String name) {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public void toXiNode(XiNode node) {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public void toXiNode(ConceptToXiNodeFilter filter, XiNode node) {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public void toXiNode(XiNode node, boolean changeOnly) {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    protected void checkForCache() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public void setExtId(String extId) throws ExtIdAlreadyBoundException {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public ExpandedName getExpandedName() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    protected Property.PropertyContainedConcept getContainedConceptProperty(String propertyName) {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    protected Property.PropertyConceptReference getConceptReferenceProperty(String propertyName) {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public Property.PropertyContainedConcept[] getContainedConceptProperties() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public Property.PropertyConceptReference[] getConceptReferenceProperties() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    protected String getParentPropertyName() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public boolean hasMainStateMachine() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public StateMachineConcept getMainStateMachine() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public Property.PropertyStateMachine getMainStateMachineProperty() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public void setId(long id) {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public void clearAllReferences() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public boolean isMarkedDeleted() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public void markDeleted() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public int getNumProperties() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    protected int _getNumDirtyBits_constructor_only() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public boolean isAutoStartupStateMachine() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    protected boolean isAsserted() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public StateMachineConcept startMainStateMachine() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public Property.PropertyStateMachine[] getStateMachineProperties() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public Property.PropertyStateMachine getStateMachineProperty(String stateMachineName) {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public List getAllModifiedProperties(int[] dirtyBitsArray, boolean passive) {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public Property[] getPropertiesNullOK() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public boolean excludeNullProps() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public boolean includeNullProps() {
        throw new NullContainedConceptAccessInConditionException();
    }
    
    @Override
    public boolean expandPropertyRefs() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public boolean setNilAttribs() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public boolean treatNullValues() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public Property getPropertyNullOK(String name) {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public void serialize(ConceptSerializer serializer) {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public void serialize_nullprops(ConceptSerializer serializer) {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public void serialize_nonullprops(ConceptSerializer serializer) {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public Comparable getVersionIndicator() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public void deserialize(ConceptDeserializer deserializer) {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public void deserialize_nullprops(ConceptDeserializer deserializer) {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public void deserialize_nonullprops(ConceptDeserializer deserializer) {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public void initStateMachine() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public PropertyArrayInt getTransitionStatuses() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public StateMachineCorrelation getCorrelationStatuses() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public PropertyAtomBoolean getMachineClosed() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public long[] getReverseReferences() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public void setReverseRef(Property.PropertyConceptReference refByProperty) {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public void clearReverseRef(Property.PropertyConceptReference refByProperty) {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public Concept duplicateThis() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    protected boolean objectBasedStore() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public WorkingMemory getWorkingMemory() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public ObjectManager getObjectManager() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public void checkSession() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public ConceptImpl deReference(Reference ref, Class refClass) {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    protected ConceptImpl deReference(long conceptId, Class refClass, Class refGetClass) {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    protected ConceptImpl deReference(long conceptId, Class refClass, Class refGetClass, boolean isNullOK) {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public ConceptImpl resolveConceptPointer(ConceptOrReference conceptOrReference, Class refClass) {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public ConceptOrReference setConceptPointer(ConceptImpl instance) {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public Property getProperty(int id) {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public Property getPropertyNullOK(int id) {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public String toString() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public boolean equals(Object obj) {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public int hashCode() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    protected void baseXiNode(XiNode node) {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public Concept getParent() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public ConceptOrReference getParentReference() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    protected void modifyParent() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public void setParent(Concept instance) {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public void nullParent() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public List getChildren() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public List getChildIds() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    protected List getContainedInstances(LinkedList list, boolean instanceOrId) {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public void setParentReference(ConceptOrReference parent) {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public void delete() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public boolean isDeleted() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    protected boolean isStarted() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public boolean isModified() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public boolean isNew() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public String getExtId() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public long getId() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public void start(Handle handle) {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public void setHandle(Handle handle) {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public void setLoadedFromCache() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public boolean isLoadedFromCache() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public String getType() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public void setPropertyValue(String name, Object value) throws Exception {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public Object getPropertyValue(String name) throws NoSuchFieldException {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public int[] getDirtyBitArray() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public void clearDirtyBitArray() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public void clearChildrenDirtyBits() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    protected boolean modifyStateMachine(PropertyImpl property) {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public boolean modifyConcept(PropertyImpl property) {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public boolean modifyConcept(PropertyImpl property, boolean noPropertiesModified, boolean modifyParent) {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    protected boolean isContained() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    protected int rRefStart() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public void writeExternal(DataOutput out) throws IOException {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public void readExternal(DataInput in) throws IOException {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public NullContainedConcept getNullContainedConcept() {
        throw new NullContainedConceptAccessInConditionException();
    }

    @Override
    public MetaProperty getMetaProperty(int index) {
        throw new NullContainedConceptAccessInConditionException();
    }
    
    @Override
    public boolean getPersistenceModified() {
    	throw new NullContainedConceptAccessInConditionException();
    }
    
    @Override
    public void setPersistenceModified() {
    	throw new NullContainedConceptAccessInConditionException();
    }
    
    @Override
    public void clearPersistenceModified() {
    	throw new NullContainedConceptAccessInConditionException();
    }
    
    @Override
    protected int persistenceModifiedIdx() {
    	throw new NullContainedConceptAccessInConditionException();
    }
}