package com.tibco.cep.studio.core.converter;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateVertex;
import com.tibco.cep.designtime.core.model.states.StatesFactory;

public class StateVertexConverter extends StateEntityConverter {
	
	protected StateVertexConverter(StateMachine ownerStateMachine) {
		super(ownerStateMachine);
	}

	@Override
	public Entity convertEntity(com.tibco.cep.designtime.model.Entity entity, String projectName) {
		StateVertex newEntity = StatesFactory.eINSTANCE.createStateVertex();
		populateEntity(newEntity, entity, projectName);
		return newEntity;
	}

	@Override
	public Class<? extends com.tibco.cep.designtime.model.Entity> getConverterClass() {
		return com.tibco.cep.designtime.model.element.stategraph.StateVertex.class;
	}

	@Override
	protected void populateEntity(Entity newEntity, com.tibco.cep.designtime.model.Entity entity, String projectName) {
		super.populateEntity(newEntity, entity, projectName);
		if (entity instanceof com.tibco.cep.designtime.model.element.stategraph.StateVertex) {
			StateVertex newVertex = (StateVertex) newEntity;
			com.tibco.cep.designtime.model.element.stategraph.StateVertex vertex = (com.tibco.cep.designtime.model.element.stategraph.StateVertex) entity;
		} else {
			// entity is likely a StateMachine
		}
		
	}
}
