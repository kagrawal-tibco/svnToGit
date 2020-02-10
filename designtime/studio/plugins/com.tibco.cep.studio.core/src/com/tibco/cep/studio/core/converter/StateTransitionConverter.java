package com.tibco.cep.studio.core.converter;

import java.util.HashMap;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.designtime.core.model.states.StatesFactory;

public class StateTransitionConverter extends StateLinkConverter {

	private HashMap<String, StateEntity> fAllStateEntities;

	public StateTransitionConverter(HashMap<String, StateEntity> allStateEntities, StateMachine ownerStateMachine) {
		super(ownerStateMachine);
		this.fAllStateEntities = allStateEntities;
	}

	@Override
	public Entity convertEntity(com.tibco.cep.designtime.model.Entity entity, String projectName) {
		StateTransition newEntity = StatesFactory.eINSTANCE.createStateTransition();
		populateEntity(newEntity, entity, projectName);
		return newEntity;
	}

	@Override
	public Class<? extends com.tibco.cep.designtime.model.Entity> getConverterClass() {
		return com.tibco.cep.designtime.model.element.stategraph.StateTransition.class;
	}

	@Override
	protected void populateEntity(Entity newEntity, com.tibco.cep.designtime.model.Entity entity, String projectName) {
		super.populateEntity(newEntity, entity, projectName);
		StateTransition newTransition = (StateTransition) newEntity;
		com.tibco.cep.designtime.model.element.stategraph.StateTransition transition = (com.tibco.cep.designtime.model.element.stategraph.StateTransition) entity;
		
		if(transition.getLabel() != null && !transition.getLabel().trim().equalsIgnoreCase("")){
			newTransition.setLabel(transition.getLabel());
		}
		else{
			newTransition.setLabel(transition.getName());
		}
		newTransition.setLambda(transition.isLambda());
		
		if(transition.getGuardRule(false) != null){
			Rule guardRule = (Rule) new RuleConverter().convertEntity(transition.getGuardRule(false), projectName);
			newTransition.setGuardRule(guardRule);
		}
		
		com.tibco.cep.designtime.model.element.stategraph.State toState = transition.getToState();
		com.tibco.cep.designtime.model.element.stategraph.State fromState = transition.getFromState();
		
		if (fAllStateEntities != null) {
			// look up the already converted state, and simply refer to it
			State emfToState = (State) fAllStateEntities.get(toState.getGUID());
			if (emfToState == null) {
				// do not have a list of the entities, convert it directly
				StateConverter stateConverter = new StateConverter(fAllStateEntities, ownerStateMachine);
				emfToState = (State) stateConverter.convertEntity(toState, projectName);
			}
			newTransition.setToState(emfToState);
			emfToState.getIncomingTransitions().add(newTransition);
			
			State emfFromState = (State) fAllStateEntities.get(fromState.getGUID());
			if (emfFromState == null) {
				StateConverter stateConverter = new StateConverter(fAllStateEntities, ownerStateMachine);
				emfFromState = (State) stateConverter.convertEntity(fromState, projectName);
			}
			newTransition.setFromState(emfFromState);
			emfFromState.getOutgoingTransitions().add(newTransition);
		} else {
			// do not have a list of the entities, convert it directly
			StateConverter stateConverter = new StateConverter(new HashMap<String, StateEntity>(), ownerStateMachine);
			State emfToState = (State) stateConverter.convertEntity(toState, projectName);
			newTransition.setToState(emfToState);
			emfToState.getIncomingTransitions().add(newTransition);
			State emfFromState = (State) stateConverter.convertEntity(fromState, projectName);
			newTransition.setFromState(emfFromState);
			emfFromState.getOutgoingTransitions().add(newTransition);
		}
		
	}
}
