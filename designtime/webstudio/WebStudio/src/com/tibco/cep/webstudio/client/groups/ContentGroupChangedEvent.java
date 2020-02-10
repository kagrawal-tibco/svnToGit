package com.tibco.cep.webstudio.client.groups;

public class ContentGroupChangedEvent {

	private ContentGroupChangedDelta delta;

	public ContentGroupChangedEvent(ContentGroupChangedDelta delta) {
		this.delta = delta;
	}

	public ContentGroupChangedDelta getGroupChangedDelta() {
		return this.delta;
	}
	
}
