package com.tibco.cep.webstudio.client.groups;

import java.util.List;

public class ContentGroupChangedDelta extends ContentGroup implements IContentChangedDelta {

	private List<ContentGroupResourceChangedDelta> affectedResources;
	private ContentGroupChangeType type;
	
	public ContentGroupChangedDelta(String groupId, List<ContentGroupResourceChangedDelta> affectedResources, ContentGroupChangeType type) {
		super(groupId);
		this.affectedResources = affectedResources;
		this.type = type;
	}

	@Override
	public ContentGroupChangeType getType() {
		return type;
	}

	public List<ContentGroupResourceChangedDelta> getAffectedResources() {
		return affectedResources;
	}
	
	
	
}
