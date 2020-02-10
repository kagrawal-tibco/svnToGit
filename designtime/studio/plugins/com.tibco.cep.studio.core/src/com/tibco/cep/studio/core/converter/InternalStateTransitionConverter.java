package com.tibco.cep.studio.core.converter;

import java.util.List;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.designtime.core.model.states.StatesFactory;
import com.tibco.cep.designtime.model.element.Concept;

public class InternalStateTransitionConverter extends EntityConverter {

	@Override
	public Entity convertEntity(com.tibco.cep.designtime.model.Entity entity, String projectName) {
		StateMachine newEntity = StatesFactory.eINSTANCE.createStateMachine();
		populateEntity(newEntity, entity, projectName);
		return newEntity;
	}

	@Override
	public Class<? extends com.tibco.cep.designtime.model.Entity> getConverterClass() {
		return com.tibco.cep.designtime.model.element.stategraph.StateMachine.class;
	}

	@Override
	protected void populateEntity(Entity newEntity, com.tibco.cep.designtime.model.Entity entity, String projectName) {
		super.populateEntity(newEntity, entity, projectName);
		StateMachine newMachine = (StateMachine) newEntity;
		com.tibco.cep.designtime.model.element.stategraph.StateMachine machine = (com.tibco.cep.designtime.model.element.stategraph.StateMachine) entity;
		
		newMachine.setFwdCorrelates(machine.forwardCorrelates());
		
		Concept ownerConcept = machine.getOwnerConcept();
		if (ownerConcept != null) {
//			newMachine.setOwnerConcept(ownerConcept); // TODO : model conversion work (partial)
			
			//TODO Implement This on new model
			//newMachine.setOwnerConceptPath(ownerConcept.getFullPath());
		}

		List transitions = machine.getTransitions(); 
		for (Object object : transitions) {
			StateTransition newTransistion = StatesFactory.eINSTANCE.createStateTransition();
			com.tibco.cep.designtime.model.element.stategraph.StateTransition transition = (com.tibco.cep.designtime.model.element.stategraph.StateTransition) object;
			newTransistion.setName(transition.getName());
		}
		List annotationLinks = machine.getAnnotationLinks();
		
	}
}
