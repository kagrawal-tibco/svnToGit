package com.tibco.cep.studio.debug.core.model.impl;

import com.tibco.cep.studio.debug.core.model.IResourcePosition;

public interface IMappedResourcePosition extends IResourcePosition {

	public abstract IMappedResourcePosition getCrossMappedPosition();

	public abstract int getPositionType();

	public abstract int getLineMask();

}
