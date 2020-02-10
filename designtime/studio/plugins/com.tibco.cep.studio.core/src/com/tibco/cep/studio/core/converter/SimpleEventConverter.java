package com.tibco.cep.studio.core.converter;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.event.EVENT_TYPE;
import com.tibco.cep.designtime.core.model.event.EventFactory;
import com.tibco.cep.designtime.core.model.event.SimpleEvent;

public class SimpleEventConverter extends EventConverter {

	@Override
	public Entity convertEntity(com.tibco.cep.designtime.model.Entity entity, String projectName) {
		SimpleEvent newEntity = EventFactory.eINSTANCE.createSimpleEvent();
		populateEntity(newEntity, entity, projectName);
		return newEntity;
	}

	@Override
	protected void populateEntity(Entity newEntity, com.tibco.cep.designtime.model.Entity entity, String projectName) {
		super.populateEntity(newEntity, entity, projectName);
		SimpleEvent newEvent = (SimpleEvent) newEntity;
		com.tibco.cep.designtime.model.event.Event event = (com.tibco.cep.designtime.model.event.Event) entity;
		newEvent.setType(EVENT_TYPE.SIMPLE_EVENT);
		
		// simple event specific
		// TODO : what attributes are simple event specific?
	}

	@Override
	public Class<? extends com.tibco.cep.designtime.model.Entity> getConverterClass() {
		return com.tibco.cep.designtime.model.event.Event.class;
	}

	@Override
	public boolean canConvert(Object obj) {
		if (!getInstance().getConverterClass().isAssignableFrom(obj.getClass())) {
			return false;
		}
		com.tibco.cep.designtime.model.event.Event event = (com.tibco.cep.designtime.model.event.Event) obj;
		return event.getType() == com.tibco.cep.designtime.model.event.Event.SIMPLE_EVENT;
	}
}
