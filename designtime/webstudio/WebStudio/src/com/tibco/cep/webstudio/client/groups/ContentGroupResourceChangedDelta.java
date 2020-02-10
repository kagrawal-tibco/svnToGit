package com.tibco.cep.webstudio.client.groups;

import com.tibco.cep.webstudio.client.model.NavigatorResource;

public class ContentGroupResourceChangedDelta implements IContentChangedDelta {

	private NavigatorResource changedResource;
	private ContentGroupChangeType type;
	
	public ContentGroupResourceChangedDelta(NavigatorResource changedResource, ContentGroupChangeType changeType) {
		this.changedResource = changedResource;
		this.type = changeType;
	}

	@Override
	public ContentGroupChangeType getType() {
		return type;
	}
	
	public NavigatorResource getChangedResource() {
		return changedResource;
	}

}
