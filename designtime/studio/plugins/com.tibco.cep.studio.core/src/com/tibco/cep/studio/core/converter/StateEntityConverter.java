package com.tibco.cep.studio.core.converter;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.TIMEOUT_UNITS;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateMachine;

public abstract class StateEntityConverter extends EntityConverter {
	
	protected StateMachine ownerStateMachine;
	
		
	protected StateEntityConverter(StateMachine ownerStateMachine) {
		this.ownerStateMachine = ownerStateMachine;
		
	}
	@Override
	public Class<? extends com.tibco.cep.designtime.model.Entity> getConverterClass() {
		return com.tibco.cep.designtime.model.element.stategraph.StateVertex.class;
	}

	@Override
	protected void populateEntity(Entity newEntity, com.tibco.cep.designtime.model.Entity entity, String projectName) {
		super.populateEntity(newEntity, entity, projectName);
		StateEntity newStateEntity = (StateEntity) newEntity;
		com.tibco.cep.designtime.model.element.stategraph.StateEntity stateEntity = (com.tibco.cep.designtime.model.element.stategraph.StateEntity) entity;
		
		newStateEntity.setTimeoutUnits(TIMEOUT_UNITS.get(stateEntity.getTimeoutUnits()));
		
		//stateEntity.getOwnerStateMachine();
		newStateEntity.setOwnerStateMachine(ownerStateMachine);
		//newStateEntity.setParent(parentEntity);
	}
}
