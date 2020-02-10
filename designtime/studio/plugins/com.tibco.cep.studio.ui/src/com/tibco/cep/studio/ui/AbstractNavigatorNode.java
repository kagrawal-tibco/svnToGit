package com.tibco.cep.studio.ui;

import org.eclipse.core.runtime.IAdaptable;

import com.tibco.cep.designtime.core.model.Entity;

public abstract class AbstractNavigatorNode implements IAdaptable {

	protected Entity fWrappedEntity;
	
	public AbstractNavigatorNode(){
		
	}
	public AbstractNavigatorNode(Entity wrappedEntity) {
		this.fWrappedEntity = wrappedEntity;
	}
	
	public abstract Entity getEntity();
	
}
