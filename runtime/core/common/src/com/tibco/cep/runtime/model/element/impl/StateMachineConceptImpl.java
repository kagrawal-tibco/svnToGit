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


import java.util.ArrayList;

import com.tibco.cep.kernel.helper.BitSet;
import com.tibco.cep.kernel.model.entity.Element;
import com.tibco.cep.kernel.model.knowledgebase.Handle;
import com.tibco.cep.kernel.model.knowledgebase.WorkingMemory;
import com.tibco.cep.runtime.model.element.ConceptSerializer;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.StateMachineConcept;
import com.tibco.cep.runtime.model.element.StateMachineDeserializer;
import com.tibco.cep.runtime.model.element.StateMachineSerializer;
import com.tibco.cep.runtime.model.element.impl.property.PropertyImpl;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyStateMachineImpl;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyStateMachineState;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Nov 8, 2006
 * Time: 4:36:50 PM
 * To change this template use File | Settings | File Templates.
 */
public  abstract class StateMachineConceptImpl extends BaseGeneratedConceptImpl implements StateMachineConcept {


    public StateMachineConceptImpl() {
        super();    //To change body of overridden methods use File | Settings | File Templates.
    }


    public StateMachineConceptImpl(long _id) {
        super(_id);    //To change body of overridden methods use File | Settings | File Templates.
    }


    protected StateMachineConceptImpl(long id, String uri) {
        super(id, uri);    //To change body of overridden methods use File | Settings | File Templates.
    }
    
    @Override
    protected int _getNumDirtyBits_constructor_only() {
        return _getNumProperties_constructor_only();
    }

    public Handle getOwnerHandle() {
        Element owner = getOwnerElement();  //To change body of implemented methods use File | Settings | File Templates.
        if(owner instanceof ConceptImpl) {
            ConceptImpl pi = (ConceptImpl) owner;
            return pi.m_wmHandle;
        }
        return null;
    }

    public Element getOwnerElement() {
        return this.getParent();
    }

    //ignore noPropertiesModified for now but added it to keep overriding parent method
    //modifyParent=false is used only by self transitions and transition rule guard condition dependency is on the parent class.statemachine contained concept rather than the individual state
    //so setting the dirty bit for the state will allow it be persisted to update the count and not modifying the parent will prevent the self transition from firing again.
    //rete does not need to be updated for this change since the value of the state property has not changed except for the count which the guard condition does not depend on
    @Override
    public boolean modifyConcept(PropertyImpl property, boolean noPropertiesModified, boolean modifyParent) {
        PropertyStateMachineState state= (PropertyStateMachineState) property;

        if (state.isActive()) {
            return true;
        }

        if (isLoadedFromCache())
            checkForCache();

        if (m_wmHandle != null) {
            BitSet.setBit(m_dirtyBitArray, property.dirtyIndex());
            WorkingMemory wm = m_wmHandle.getWorkingMemory();
            wm.modifyObject(m_wmHandle, false, true);
        }

        if (modifyParent) {
	        ConceptImpl parent = resolveConceptPointer(getParentReference(),
	                null);  //todo - should we pass the class?  shouldn't because class is useful only when doing recovery and we shouldn't modify object at recovery time?
	
	        if (parent != null) {
	            if (parent.isAsserted()) {
	                PropertyStateMachineImpl parentProperty =
	                        (PropertyStateMachineImpl) parent.getContainedConceptProperty(getParentPropertyName());
	
	                if (parentProperty.modifyChild(this, property)) {
	                    parent.modifyStateMachine(parentProperty);
	                }
	            }
	        }
        }
        return true;
    }

    /**
     *
     * @return boolean
     */
    public boolean isMachineStarted() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isMachineClosed() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public java.util.List<Integer> getReadyStates() {
        ArrayList ret = new ArrayList();
        Property [] allStates=this.getPropertiesNullOK();
        for (int i=0; i < allStates.length; i++) {
            PropertyStateMachineState state= (PropertyStateMachineState) allStates[i];
            if (state != null) {
                if (state.isReady()) {
                    ret.add(state.getPropertyIndex());
                }
            }
        }
        return ret;
    }

    public boolean isActive(PropertyStateMachineState state) {
        if (state != null) {
            return state.isActive();
        } else {
            return false;
        }
    }

    public boolean isReady(PropertyStateMachineState state) {
        if (state != null) {
            return state.isReady();
        } else {
            return false;
        }
    }

    public boolean isTimeoutSet(PropertyStateMachineState state) {
        if (state != null) {
            return state.isTimeoutSet();
        } else {
            return false;
        }
    }

    public boolean isComplete(PropertyStateMachineState state) {
        if (state != null) {
            return state.isComplete();
        } else {
            return false;
        }
    }

    public boolean isExited(PropertyStateMachineState state) {
        if (state != null) {
            return state.isExited();
        } else {
            return false;
        }
    }
    public boolean isCompleteOrExited(PropertyStateMachineState state) {
        if (state != null) {
            return state.isCompleteOrExited();
        } else {
            return false;
        }

    }
    public boolean isAmbiguous(PropertyStateMachineState state) {
        if (state != null) {
            return state.isAmbiguous();
        } else {
            return false;
        }
    }
    
    @Override
    public void delete() {
    	super.delete();
    	RuleSession rs = RuleSessionManager.getCurrentRuleSession();
    	for(Property p : getPropertiesNullOK()) {
    		if(p != null) {
	    		PropertyStateMachineState state = (PropertyStateMachineState)p;
	    		state.cancelExpiryTimer(rs);
    		}
    	}
    }

    public String toString() {
        StringBuffer sb= new StringBuffer(super.toString() + "&v=" + this.getVersion());
        sb.append("{");
        Property[] allProperties = getPropertiesNullOK();
        for (int i = 0; i < allProperties.length; i++) {
            PropertyStateMachineState prop= (PropertyStateMachineState) allProperties[i];
            if (prop != null) {
                if (prop.isReady()) {
                    sb.append(prop.getPropertyIndex() + ":" + prop.getName() + ": ready,");
                } else if (prop.isCompleteOrExited()) {
                    sb.append(prop.getPropertyIndex() + ":" + prop.getName() + ": completeOrExited,");
                } else {
                     sb.append(prop.getPropertyIndex() + ":"  +prop.getName() + prop.getCurrentState());
                }
            }

        }
        return sb.toString();
    }

    public void serialize(StateMachineSerializer serializer) {
        if (isMarkedDeleted()) {
            serializer.startStateMachine(this.getClass(), getId(), getExtId(),
                    ConceptSerializer.STATE_DELETED, getVersion());
        }
        else {
            serializer.startStateMachine(this.getClass(), getId(), getExtId(),
                    ConceptSerializer.STATE_NEW, getVersion());
        }
        serializer.startParent(getParentReference());
        Property[] allProperties = getPropertiesNullOK();
        for (int i = 0; i < allProperties.length; i++) {
            PropertyStateMachineState prop= (PropertyStateMachineState) allProperties[i];
            if ((prop != null) && (prop.isReady())){
                serializer.addState(i,prop.getInt());
            }
        }
        serializer.endStateMachine();
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deserialize(StateMachineDeserializer deserializer) {

        deserializer.startStateMachine();
        //deserializer
        this.setId(deserializer.getId());
        if (this.getExtId() == null) {
            this.setExtId(deserializer.getExtId());
        }

        if (deserializer.isDeleted()) {
            this.markDeleted();
        }
        this.setVersion(deserializer.getVersion());
        setParentReference(deserializer.getParentConcept());

        while (true) {
            int index=deserializer.nextStateIndex();
            if (index < 0) {
                break;
            }
            int prop_value=deserializer.nextStateValue();
            PropertyStateMachineState ps= (PropertyStateMachineState) getProperty(index);
            ps.setInt(prop_value);
        }
        deserializer.endStateMachine();
    }

    abstract public PropertyStateMachineState newStateWithIndex(int propertyIndex);

    /**
     * An implementation of the relative time event for handling all state timeouts
     */

    public static class StateTimeoutEvent extends AbstractStateTimeoutEvent {
        public final static int STATETIMEOUTEVENT_TYPEID = 1002;
        
        public StateTimeoutEvent(long _id) { super(_id); }
        public StateTimeoutEvent(long _id, String _extId) { super(_id, _extId); }
        
        public StateTimeoutEvent(long _id, long sm_id, String property_name, long next, int count) {
            super(_id, sm_id, property_name, next, count);
        }
	}
}
