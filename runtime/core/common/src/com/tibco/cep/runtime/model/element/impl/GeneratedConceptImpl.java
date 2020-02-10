package com.tibco.cep.runtime.model.element.impl;

import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.xml.data.primitive.ExpandedName;

/**
 *  replace default impls with abstract methods to ensure codegen always generates methods
 *  that may change for a hot deploy
 */
abstract public class GeneratedConceptImpl extends BaseGeneratedConceptImpl
{
    protected GeneratedConceptImpl() {
    }

    protected GeneratedConceptImpl(long _id) {
        super(_id);
    }

    protected GeneratedConceptImpl(long id, String extId) {
        super(id, extId);
    }

    protected GeneratedConceptImpl(String extId) {
        super(extId);
    }

    //some children of BaseGeneratedConceptImpl may not use rete so they don't need any dirty bits
    //and can use the impl in ConceptImpl that always returns 0.
    //Children of this class should always allocate dirty bits.
    //This method is overriden when there are contained concept that need 2 dirty bits
    @Override
    protected int _getNumDirtyBits_constructor_only() {
        return _getNumProperties_constructor_only();
    }
    @Override
    abstract public String getType();
    @Override
    abstract public ExpandedName getExpandedName();

    //@Override
    //abstract public boolean hasMainStateMachine();
    //@Override
    //abstract public StateMachineConcept getMainStateMachine();
    //@Override
    //abstract public Property.PropertyStateMachine getMainStateMachineProperty();
    //@Override
    //abstract public boolean isAutoStartupStateMachine();
    //@Override
    //abstract public StateMachineConcept newSMWithIndex(int index, long id);

    //@Override
    //abstract protected Property.PropertyContainedConcept getContainedConceptProperty(String propertyName);
    //@Override
    //abstract protected Property.PropertyConceptReference getConceptReferenceProperty(String propertyName);
    //@Override
    //abstract public Property.PropertyContainedConcept[] getContainedConceptProperties();
    //@Override
    //abstract public Property.PropertyConceptReference[] getConceptReferenceProperties();

    @Override
    protected String getParentPropertyName() {
        return "null";
    }

    //@Override
    //abstract public Property getProperty(String name);
    //@Override
    //abstract public Property[] getProperties();
    //@Override
    //abstract public Property[] getLocalProperties();
    //@Override
    //abstract public Property.PropertyStateMachine[] getStateMachineProperties();
    //@Override
    //abstract public Property.PropertyStateMachine getStateMachineProperty(String stateMachineName);

    /* XML serialization */
    @Override
    abstract public boolean excludeNullProps();
    @Override
    abstract public boolean includeNullProps();
    @Override
    abstract public boolean expandPropertyRefs();
    @Override
    abstract public boolean setNilAttribs();
    @Override
    abstract public boolean treatNullValues();

    @Override
    abstract protected int[] conceptPropIdxs();

    public void startSMAndAssert() {
        try {
            if (isAutoStartupStateMachine() && hasMainStateMachine()) {
                startMainStateMachine();
            }
            RuleSessionManager.getCurrentRuleSession().assertObject(this, false);
        } catch (DuplicateExtIdException deie) {
            throw new java.lang.RuntimeException(deie.getMessage(), deie);
        }
    }
}