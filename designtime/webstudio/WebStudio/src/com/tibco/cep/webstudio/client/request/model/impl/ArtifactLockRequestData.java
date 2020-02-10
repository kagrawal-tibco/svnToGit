package com.tibco.cep.webstudio.client.request.model.impl;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.tibco.cep.webstudio.client.request.model.IRequestDataItem;

/**
 * XML representation of locking related Requests
 * @author vdhumal
 *
 * @param <D>
 */
public class ArtifactLockRequestData<D extends IRequestDataItem> extends DefaultRequestProject<D> {

	private boolean actionForcibly = false;
	
	public boolean getActionForcibly() {
		return actionForcibly;
	}

	public void setActionForcibly(boolean actionForcibly) {
		this.actionForcibly = actionForcibly;
	}

	@Override
	public void serialize(Document rootDocument, Node rootNode) {
		Element actionForciblyElement = rootDocument.createElement("actionForcibly");
		actionForciblyElement.appendChild(rootDocument.createTextNode(Boolean.toString(actionForcibly)));
		rootNode.appendChild(actionForciblyElement);
		
		super.serialize(rootDocument, rootNode);
	}

}
