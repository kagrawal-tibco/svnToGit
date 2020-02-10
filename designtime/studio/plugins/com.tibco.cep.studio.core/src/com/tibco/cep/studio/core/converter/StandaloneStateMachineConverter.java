package com.tibco.cep.studio.core.converter;

import java.util.HashMap;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StatesFactory;
import com.tibco.cep.designtime.model.element.Concept;

public class StandaloneStateMachineConverter extends StateMachineConverter {

	/**
	 * @param allStateEntities
	 * @param projectName
	 */
	public StandaloneStateMachineConverter(
			HashMap<String, StateEntity> allStateEntities) {
		super(allStateEntities);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.converter.StateMachineConverter#convertEntity(com.tibco.cep.designtime.model.Entity, java.lang.String)
	 */
	@Override
	public Entity convertEntity(com.tibco.cep.designtime.model.Entity entity, String projectName) {
		StateMachine newEntity = StatesFactory.eINSTANCE.createStateMachine();
		populateEntity(newEntity, entity, projectName);
		return newEntity;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.converter.StateMachineConverter#getConverterClass()
	 */
	@Override
	public Class<? extends com.tibco.cep.designtime.model.Entity> getConverterClass() {
		return com.tibco.cep.designtime.model.element.stategraph.StandaloneStateMachine.class;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.converter.StateMachineConverter#populateEntity(com.tibco.cep.designtime.core.model.Entity, com.tibco.cep.designtime.model.Entity, java.lang.String)
	 */
	@Override
	protected void populateEntity(Entity newEntity, com.tibco.cep.designtime.model.Entity entity, String projectName) {
		StateMachine newMachine = (StateMachine) newEntity;
		com.tibco.cep.designtime.model.element.stategraph.StandaloneStateMachine machine = (com.tibco.cep.designtime.model.element.stategraph.StandaloneStateMachine) entity;
		super.populateEntity(newMachine, machine, projectName);
		newMachine.setName(machine.getName());
		newMachine.setFolder(machine.getFolderPath());
		newMachine.setNamespace(machine.getFolderPath());
	}
	
	protected void setMachineFolder(com.tibco.cep.designtime.model.element.stategraph.StateMachine machine, StateMachine newMachine,
			Concept ownerConcept) {
		String folder = machine.getFolderPath();
		if (!folder.endsWith("/")) {
			folder += "/";
		}
		newMachine.setFolder(folder);
	}

}
