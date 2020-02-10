package com.tibco.cep.runtime.model.element.impl.property.simple;

import com.tibco.cep.kernel.helper.BitSet;
import com.tibco.cep.kernel.model.knowledgebase.DuplicateExtIdException;
import com.tibco.cep.runtime.model.element.Concept;
import com.tibco.cep.runtime.model.element.ContainedConcept;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.StateMachineConcept;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.property.PropertyImpl;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.xml.datamodel.XiNode;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Nov 10, 2006
 * Time: 12:03:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyStateMachineImpl<GenType extends StateMachineConcept> extends PropertyAtomContainedConceptSimple implements Property.PropertyStateMachine {
    transient protected int[] m_dirtyBitArray;

    public PropertyStateMachineImpl(Object owner) {
        super(owner);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public PropertyStateMachineImpl(Object owner, ContainedConcept defaultValue) {
        super(owner, defaultValue);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public void fillXiNode(XiNode node, boolean changeOnly) {
        return;  //todo - Puneet fill this later
    }

    public void clearDirtyBitArray() {
//        System.err.println(this.getName() + ": clearDirtyBitArray");
        m_dirtyBitArray=null;
    }

    public int[] getDirtyBitArray() {
        return m_dirtyBitArray;
    }

    public boolean isStateChanged(PropertyStateMachineState state) {
        return state != null && isSet() && getContainedConceptId() > 0
                && BitSet.isBitSet_Safe(m_dirtyBitArray, state.dirtyIndex(), false);
    }

    public void clearChildrenDirtyBits() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    protected boolean setCurrent(ContainedConcept instance) {
//        if (instance != null) {
//            m_dirtyBitArray = new int[((ConceptImpl) instance).getNumProperties()+1];
//        } else {
//            m_dirtyBitArray = null;
//        }
        super.setCurrent(instance);
        return true;
    }

    public boolean modifyChild(Concept child, PropertyImpl property) {
        if (m_dirtyBitArray == null) {
            m_dirtyBitArray = new int[((ConceptImpl) child).getMaxDirtyBitIdx()];
            BitSet.setBit(m_dirtyBitArray, property.dirtyIndex());
            return true;
        } else {
            BitSet.setBit(m_dirtyBitArray, property.dirtyIndex());
            return false;
        }
    }

    public  StateMachineConcept getStateMachineConcept()  {
        return (StateMachineConcept) getConcept();
    }

    public GenType getMachine() {
        return (GenType)getConcept();
    }

    public void startMachine() {
        boolean assertSMConcept = false;
        StateMachineConcept sm = getStateMachineConcept();
        if(sm == null) {
            sm = getParentConceptImpl().newSMWithIndex(getPropertyIndex());
            setValue(sm);
            assertSMConcept = true;
        }
        sm.getMachineRoot().enter(new Object[]{getParent()});
        if(assertSMConcept) {
            try {
                RuleSessionManager.getCurrentRuleSession().assertObject(sm, false);
            } catch (DuplicateExtIdException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
