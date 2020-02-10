package com.tibco.cep.studio.core.converter;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.TIMEOUT_UNITS;
import com.tibco.cep.designtime.core.model.event.EVENT_SCHEDULE_TYPE;
import com.tibco.cep.designtime.core.model.event.EVENT_TYPE;
import com.tibco.cep.designtime.core.model.event.EventFactory;
import com.tibco.cep.designtime.core.model.event.TimeEvent;

public class TimeEventConverter extends EventConverter {

	@Override
	public Entity convertEntity(com.tibco.cep.designtime.model.Entity entity, String projectName) {
		TimeEvent newEntity = EventFactory.eINSTANCE.createTimeEvent();
		populateEntity(newEntity, entity, projectName);
		return newEntity;
	}

	@Override
	protected void populateEntity(Entity newEntity, com.tibco.cep.designtime.model.Entity entity, String projectName) {
		super.populateEntity(newEntity, entity, projectName);
		TimeEvent newEvent = (TimeEvent) newEntity;
		com.tibco.cep.designtime.model.event.Event event = (com.tibco.cep.designtime.model.event.Event) entity;
		newEvent.setType(EVENT_TYPE.TIME_EVENT);
		
		// time specific
		newEvent.setInterval(event.getInterval());
		newEvent.setIntervalUnit(TIMEOUT_UNITS.get(event.getIntervalUnit()));
		newEvent.setTimeEventCount(event.getTimeEventCount());
		
		if(event.getSchedule() == 0){
			newEvent.setScheduleType(EVENT_SCHEDULE_TYPE.RULE_BASED);
		}
		if(event.getSchedule() == 1){
			newEvent.setScheduleType(EVENT_SCHEDULE_TYPE.REPEAT);
		}
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
		return event.getType() == com.tibco.cep.designtime.model.event.Event.TIME_EVENT;
	}
}
