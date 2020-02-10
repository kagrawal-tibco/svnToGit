package com.tibco.cep.webstudio.client.groups;

public class ContentModelChangedEvent {

	private ContentModelChangedDelta delta;

	public ContentModelChangedEvent(ContentModelChangedDelta delta) {
		this.delta = delta;
	}

	public ContentModelChangedDelta getDelta() {
		return delta;
	}

}
