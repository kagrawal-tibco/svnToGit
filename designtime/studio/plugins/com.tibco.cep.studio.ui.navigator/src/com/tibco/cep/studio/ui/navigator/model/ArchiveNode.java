package com.tibco.cep.studio.ui.navigator.model;

import com.tibco.cep.designtime.core.model.archive.ArchiveResource;
import com.tibco.cep.studio.ui.StudioNavigatorNode;

public class ArchiveNode extends StudioNavigatorNode {

	private ArchiveResource fWrappedObject;

	public ArchiveNode(ArchiveResource archiveResource) {
		super();
		this.fWrappedObject = archiveResource;
	}
	
	public ArchiveResource getArchiveResource() {
		return fWrappedObject;
	}

	public String getName() {
		return fWrappedObject.getName();
	}

}
