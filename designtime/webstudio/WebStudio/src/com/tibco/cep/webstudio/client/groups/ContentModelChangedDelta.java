package com.tibco.cep.webstudio.client.groups;

import java.util.List;

public class ContentModelChangedDelta {

	private List<ContentGroupChangedDelta> changedGroups;
	
	public ContentModelChangedDelta(List<ContentGroupChangedDelta> changedGroups) {
		this.changedGroups = changedGroups;
	}

	public List<ContentGroupChangedDelta> getChangedGroups() {
		return changedGroups;
	}
	
}
