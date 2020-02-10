package com.tibco.cep.studio.core.adapters.mutable;

import java.util.ArrayList;
import java.util.List;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.cep.designtime.core.model.TIMEOUT_UNITS;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.stategraph.StateComposite;
import com.tibco.cep.studio.core.adapters.CoreAdapterFactory;
import com.tibco.cep.studio.core.adapters.StateEntityAdapter;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

public class MutableStateEntityAdapter<S extends StateEntity> extends StateEntityAdapter<S> implements
com.tibco.cep.designtime.model.element.stategraph.StateEntity {


	
	public MutableStateEntityAdapter(S adapted, Ontology o) {
		super(adapted, o);
		adapted.setTimeoutUnits(TIMEOUT_UNITS.SECONDS);
	}

    
    
    
    
    /**
     * Get the Least Common Ancester State (the state which encloses both input States
     * while enclosing the smallest scope).
     *
     * @param state1 A state to find the common parent of.
     * @param state2 A second state to find the common parent of.
     */
    public StateComposite getLeastCommonAncester(com.tibco.cep.designtime.model.element.stategraph.StateEntity state1, com.tibco.cep.designtime.model.element.stategraph.StateEntity state2) {
        List<com.tibco.cep.designtime.model.element.stategraph.StateEntity> state1Parents = getParentList(state1);
        List<com.tibco.cep.designtime.model.element.stategraph.StateEntity> state2Parents = getParentList(state2);
        int index = 0;
        while (index < state1Parents.size() && index < state2Parents.size() && state1Parents.get(index) == state2Parents.get((index)))
        {
            index++;
        }//endwhile
        return (com.tibco.cep.designtime.model.element.stategraph.StateComposite) state1Parents.get(index - 1);
    }// end getLeastCommonAncester

	

	@Override
	/**
     * Get the state machine that this state belongs to.
     *
     * @return The state machine that this state belongs to.
     */
    public com.tibco.cep.designtime.model.element.stategraph.StateMachine getOwnerStateMachine() {
        return CoreAdapterFactory.INSTANCE.createAdapter(adapted.getOwnerStateMachine(), emfOntology);
    }// end getOwnerStateMachine

	@Override
	/**
     * Get the parent of this state entity.  Certain entities have no useful parent (e.g.
     * StateMachine has no parent).
     *
     * @return The parent of this state entity.
     */
    public com.tibco.cep.designtime.model.element.stategraph.StateEntity getParent() {
        return CoreAdapterFactory.INSTANCE.createAdapter(adapted.getParent(), emfOntology);
    }// end getParent
	
	/**
     * Get a List containing the parent hierarchy for the state entity passed.
     *
     * @return A List containing the parent hierarchy for the state entity passed.
     */
    protected ArrayList<com.tibco.cep.designtime.model.element.stategraph.StateEntity> getParentList(com.tibco.cep.designtime.model.element.stategraph.StateEntity state) {
        ArrayList<com.tibco.cep.designtime.model.element.stategraph.StateEntity> stateParents 
        		= new ArrayList<com.tibco.cep.designtime.model.element.stategraph.StateEntity>();
        com.tibco.cep.designtime.model.element.stategraph.StateEntity stateParent = state.getParent();
        while (stateParent != null) {
            stateParents.add(0,stateParent);
            stateParent = stateParent.getParent();
        }//endwhile
        return stateParents;
    }// end getParentList
    
    

	
	
	/**
     * Generate a rule name for this state entity.
     */
    public String getRuleName(String qualifier) {
        return ModelNameUtil.getStateRuleName(this, qualifier);
    }// end getRuleName

	
	/**
     * Set the state machine that this state belongs to.
     *
     * @param ownerStateMachine The state machine that this state belongs to.
     */
    public void setOwnerStateMachine(com.tibco.cep.designtime.model.element.stategraph.StateMachine ownerStateMachine) {
    	StateMachine sm = (StateMachine) CommonIndexUtils.getEntity(emfOntology.getName(),ownerStateMachine.getFullPath(),ELEMENT_TYPES.STATE_MACHINE);
		adapted.setOwnerStateMachine(sm);
    }// end setOwnerStateMachine
    
    /**
     * NOTE: This method is not for public consumption, only the State Model classes should
     * call this method, other classes should call the appropriate add method in the parent
     * object, which in turn will call this method.
     * Set the parent of this state entity.  This method is a very simple set, it does NOT
     * perform any consistency updates to related structures.  It doesn't update a previous
     * parent (if any) to notify that this entity is no longer a member and does not add this
     * entity to the new parent.
     *
     * @param parent The new parent of this state entity.
     */
    protected void setParent(com.tibco.cep.designtime.model.element.stategraph.StateComposite parent) {
    	StateEntity value = (StateEntity) CommonIndexUtils.getEntity(emfOntology.getName(), parent.getFullPath());
    	adapted.setParent(value);
//        m_parent = parent;
    }// end setParent
    
    /**
     * Set the timeout units (the time unit e.g. minute, for the timeout value).
     * The value is interpreted as 0 - milliseconds, 1 - seconds, 2 - minutes, 3 - hours, 4 - days
     *
     * @param timeoutUnits The timeout units (the time unit e.g. minute, for the timeout value).
     */
    public void setTimeoutUnits(int timeoutUnits) {
        adapted.setTimeoutUnits(TIMEOUT_UNITS.get(timeoutUnits));
    }// end setTimeoutUnits


    	

}
