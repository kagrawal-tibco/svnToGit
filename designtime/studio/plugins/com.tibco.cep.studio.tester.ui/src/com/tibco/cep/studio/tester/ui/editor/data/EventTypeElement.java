package com.tibco.cep.studio.tester.ui.editor.data;

import com.tibco.cep.studio.tester.core.model.EventType;

public class EventTypeElement extends EntityTypeElement {

	private EventType causalObject;
	

	/**
	 * @param causalObject
	 * @param concept
	 */
	public EventTypeElement(EventType causalObject) {
		super(causalObject);
		this.causalObject = causalObject;
	}

	/**
	 * @return
	 */
	public EventType getEventType() {
		return causalObject;
	}
}



