package com.tibco.cep.studio.core.adapters.mutable;

import java.awt.Rectangle;

import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.states.StateEnd;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StatesFactory;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

public class MutableStateEndAdapter extends MutableStateAdapter<StateEnd> implements
com.tibco.cep.designtime.model.element.stategraph.StateEnd {
	
	public static final String PERSISTENCE_NAME = "StateEnd";
	
	
	


    public MutableStateEndAdapter(StateEnd adapted, Ontology emfOntology) {
		super(adapted, emfOntology);
		// TODO Auto-generated constructor stub
	}


	/**
     * Construct this object within the passed ontology and give it the name passed.
     *
     * @param ontology          The ontology (BE model) to add this entity.
     * @param name              The name of this element.
     * @param bounds            The location of the entity on the main view (only top-left is used for iconic entities).
     * @param ownerStateMachine The state machine that owns this state.
     */
    public MutableStateEndAdapter(
            Ontology ontology,
            String name,
            Rectangle bounds,
            com.tibco.cep.designtime.model.element.stategraph.StateMachine ownerStateMachine) {
    	this(StatesFactory.eINSTANCE.createStateEnd(),ontology);
    	adapted.setName(name);
    	StateMachine sm = (StateMachine) CommonIndexUtils.getEntity(ontology.getName(),ownerStateMachine.getFullPath(),ELEMENT_TYPES.STATE_MACHINE);
		adapted.setOwnerStateMachine(sm);
//        super(ontology, name, bounds, ownerStateMachine);
    }// end DefaultStateEnd


    /**
     * Construct this object within the passed ontology and give it the name passed.
     *
     * @param ontology          The ontology (BE model) to add this entity.
     * @param name              The name of this element.
     * @param bounds            The location of the entity on the main view (only top-left is used for iconic entities).
     * @param ownerStateMachine The state machine that owns this state.
     * @param entryAction       The action to perform on entry to this state.
     * @param exitAction        The action to perform on exit from this state.
     */
    public MutableStateEndAdapter(
            Ontology ontology,
            String name,
            com.tibco.cep.designtime.model.element.stategraph.StateMachine ownerStateMachine,
            Rectangle bounds,
            com.tibco.cep.designtime.model.rule.Rule entryAction,
            com.tibco.cep.designtime.model.rule.Rule exitAction) {
    	this(StatesFactory.eINSTANCE.createStateEnd(),ontology);
    	adapted.setName(name);
    	StateMachine sm = (StateMachine) CommonIndexUtils.getEntity(ontology.getName(),ownerStateMachine.getFullPath(),ELEMENT_TYPES.STATE_MACHINE);
		adapted.setOwnerStateMachine(sm);
		Rule _entryAction = (Rule) CommonIndexUtils.getEntity(ontology.getName(),entryAction.getFullPath(),ELEMENT_TYPES.RULE);
		adapted.setEntryAction(_entryAction);
		Rule _exitAction = (Rule) CommonIndexUtils.getEntity(ontology.getName(),exitAction.getFullPath(),ELEMENT_TYPES.RULE);
		adapted.setExitAction(_exitAction);
//        this(ontology, name, bounds, ownerStateMachine, entryAction, exitAction, null);
    }// end DefaultStateEnd

}
