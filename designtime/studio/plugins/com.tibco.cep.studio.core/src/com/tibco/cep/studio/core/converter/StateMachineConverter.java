package com.tibco.cep.studio.core.converter;

import java.util.HashMap;
import java.util.List;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.TIMEOUT_UNITS;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.states.StateAnnotationLink;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.designtime.core.model.states.StatesFactory;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.stategraph.StateComposite;
import com.tibco.cep.studio.core.utils.ModelUtils;

public class StateMachineConverter extends StateCompositeConverter {

	public StateMachineConverter(HashMap<String, StateEntity> allStateEntities) {
		super(allStateEntities, null);
	}

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
		newMachine.setTimeoutUnits(TIMEOUT_UNITS.get(machine.getTimeoutUnits()));//Take timeout units from machine, not from the root.
		newMachine.setMain(machine.isMain());
		Concept ownerConcept = machine.getOwnerConcept();
		if (ownerConcept != null) {
			setMachineFolder(machine, newMachine, ownerConcept);
			
			newMachine.setName(newMachine.getName());
			newMachine.setOwnerConceptPath(ownerConcept.getFullPath());
		}
		
		StateComposite machineRoot = machine.getMachineRoot();
		HashMap<String, StateEntity> allStateEntities = new HashMap<String, StateEntity>();
		
		if (machineRoot != null) {
			// the state machine itself acts as the root state
			com.tibco.cep.designtime.core.model.states.StateComposite root = (com.tibco.cep.designtime.core.model.states.StateComposite) new StateCompositeConverter(allStateEntities, newMachine).convertEntity(machineRoot, projectName);
			newMachine.setTimeoutPolicy(root.getTimeoutPolicy());
			newMachine.setTimeoutAction(root.getTimeoutAction());
			RuleFunction timeoutExpression = root.getTimeoutExpression();
			if (timeoutExpression == null) {
				timeoutExpression = ModelUtils.generateDefaultTimeoutExpression(root);
			}
			//force type to Long since it was incorrectly stored as int in many designer projects
			timeoutExpression.setReturnType(RDFTypes.LONG.getName());
			newMachine.setTimeoutExpression(timeoutExpression);
			newMachine.getStateEntities().addAll(root.getStateEntities());
			// might need to better merge properties here...
			newMachine.getExtendedProperties().getProperties().addAll(root.getExtendedProperties().getProperties());
		}
		
		List transitions = machine.getTransitions(); 
		if (transitions != null) {
			StateTransitionConverter transitionConverter = new StateTransitionConverter(allStateEntities, newMachine);
			for (Object object : transitions) {
				StateTransition newTransition = (StateTransition) transitionConverter.convertEntity((com.tibco.cep.designtime.model.Entity) object, projectName);
				newMachine.getStateTransitions().add(newTransition);
			}
		}
		
		List annotationLinks = machine.getAnnotationLinks();
		if (annotationLinks != null) {
			StateAnnotationLinkConverter converter = new StateAnnotationLinkConverter(newMachine);
			for (Object object : annotationLinks) {
				StateAnnotationLink newLink = (StateAnnotationLink) converter.convertEntity((com.tibco.cep.designtime.model.Entity) object, projectName);
				newMachine.getAnnotationLinks().add(newLink);
			}
		}
	}

	protected void setMachineFolder(com.tibco.cep.designtime.model.element.stategraph.StateMachine machine, StateMachine newMachine,
			Concept ownerConcept) {
		String folder = ownerConcept.getFullPath();
		if (!folder.endsWith("/")) {
			folder += "/";
		}
		newMachine.setFolder(folder);
		newMachine.setNamespace(folder);
	}
}
