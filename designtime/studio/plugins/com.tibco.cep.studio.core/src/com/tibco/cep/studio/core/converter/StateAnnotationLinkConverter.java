package com.tibco.cep.studio.core.converter;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.states.StateAnnotationLink;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StatesFactory;
import com.tibco.cep.designtime.model.element.stategraph.StateEntity;

public class StateAnnotationLinkConverter extends StateLinkConverter {
	
	protected StateAnnotationLinkConverter(StateMachine ownerStateMachine) {
		super(ownerStateMachine);
	}
	
	@Override
	public Entity convertEntity(com.tibco.cep.designtime.model.Entity entity, String projectName) {
		StateAnnotationLink newEntity = StatesFactory.eINSTANCE.createStateAnnotationLink();
		populateEntity(newEntity, entity, projectName);
		return newEntity;
	}

	@Override
	public Class<? extends com.tibco.cep.designtime.model.Entity> getConverterClass() {
		return com.tibco.cep.designtime.model.element.stategraph.StateAnnotationLink.class;
	}

	@Override
	protected void populateEntity(Entity newEntity, com.tibco.cep.designtime.model.Entity entity, String projectName) {
		super.populateEntity(newEntity, entity, projectName);
		StateAnnotationLink newLink = (StateAnnotationLink) newEntity;
		com.tibco.cep.designtime.model.element.stategraph.StateAnnotationLink link = (com.tibco.cep.designtime.model.element.stategraph.StateAnnotationLink) entity;
		
		newLink.setFromAnnotation(link.getFromAnnotation().getText());
		StateEntity toStateEntity = link.getToStateEntity();
	}
}
