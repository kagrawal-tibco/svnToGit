package com.tibco.cep.dashboard.plugin.beviews.nextgendrilldown.path;

import com.tibco.cep.designtime.core.model.Entity;

public class TypeIDDrillDownTreePathElement extends DrillDownTreePathElement {
	
	private Entity entity;

	public TypeIDDrillDownTreePathElement(String token, Entity entity) {
		super(token);
		this.entity = entity;
	}
	
	public Entity getEntity() {
		return entity;
	}

}
