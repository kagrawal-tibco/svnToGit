package com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown.path;

import com.tibco.cep.designtime.core.model.Entity;

public class InstanceIDDrillDownTreePathElement extends TypeIDDrillDownTreePathElement {
	
	public InstanceIDDrillDownTreePathElement(String token, Entity entity) {
		super(token, entity);
	}
	
	public String getInstanceID() {
		return token;
	}

}