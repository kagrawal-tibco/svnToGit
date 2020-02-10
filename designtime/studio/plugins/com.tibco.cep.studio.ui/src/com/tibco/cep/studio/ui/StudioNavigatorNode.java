package com.tibco.cep.studio.ui;

import com.tibco.cep.designtime.core.model.Entity;

public abstract class StudioNavigatorNode extends AbstractNavigatorNode {
	
	protected boolean isSharedElement; // whether this node is from a shared element

	public StudioNavigatorNode(){
		
	}

	public StudioNavigatorNode(Entity wrappedEntity, boolean isSharedElement) {
		super(wrappedEntity);
		this.isSharedElement = isSharedElement;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.AbstractNavigatorNode#getEntity()
	 */
	public Entity getEntity() {
		return fWrappedEntity;
	}

	public boolean isSharedElement() {
		return isSharedElement;
	}

	/**
	 * @return
	 */
	public String getName() {
		return fWrappedEntity.getName();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class adapter) {
		if (Entity.class.equals(adapter)) {
			return getEntity();
		}
		return null;
	}

}
