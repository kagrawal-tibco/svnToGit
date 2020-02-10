package com.tibco.cep.studio.core.converter;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.states.StateLink;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StatesFactory;

public class StateLinkConverter extends StateEntityConverter {
	
	protected StateLinkConverter(StateMachine ownerStateMachine) {
		super(ownerStateMachine);
	}
	@Override
	public Entity convertEntity(com.tibco.cep.designtime.model.Entity entity, String projectName) {
		StateLink newEntity = StatesFactory.eINSTANCE.createStateLink();
		populateEntity(newEntity, entity, projectName);
		return newEntity;
	}

	@Override
	public Class<? extends com.tibco.cep.designtime.model.Entity> getConverterClass() {
		return com.tibco.cep.designtime.model.element.stategraph.StateLink.class;
	}

	@Override
	protected void populateEntity(Entity newEntity, com.tibco.cep.designtime.model.Entity entity, String projectName) {
		super.populateEntity(newEntity, entity, projectName);
		StateLink newLink = (StateLink) newEntity;
		com.tibco.cep.designtime.model.element.stategraph.StateLink link = (com.tibco.cep.designtime.model.element.stategraph.StateLink) entity;
		
	}
}
