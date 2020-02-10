package com.tibco.cep.webstudio.client.request.model.impl.dataitem;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.Text;
import com.tibco.cep.webstudio.client.request.model.XMLRequestBuilderConstants;

public class ArtifactLockDataItem extends ArtifactDataItem {

	private String lockRequestor = null;
	private String lockOwner = null;
	private boolean lockAcquired = false;
	private boolean lockReleased = false;
	
	public String getLockRequestor() {
		return lockRequestor;
	}

	public void setLockRequestor(String lockRequestor) {
		this.lockRequestor = lockRequestor;
	}
	
	public String getLockOwner() {
		return lockOwner;
	}

	public void setLockOwner(String lockOwner) {
		this.lockOwner = lockOwner;
	}

	public boolean isLockAcquired() {
		return lockAcquired;
	}

	public void setLockAcquired(boolean lockAcquired) {
		this.lockAcquired = lockAcquired;
	}

	public boolean isLockReleased() {
		return lockReleased;
	}

	public void setLockReleased(boolean lockReleased) {
		this.lockReleased = lockReleased;
	}

	@Override
	public void serialize(Document rootDocument, Node rootNode) {
		super.serialize(rootDocument, rootNode);
		NodeList nodesList = rootNode.getChildNodes();
		for (int i = 0; i < nodesList.getLength(); i++) {
			Node node = nodesList.item(i);
			if (XMLRequestBuilderConstants.ARTIFACT_ITEM_ELEMENT.equals(node.getNodeName())) {
				Element lockRequestorElement = rootDocument.createElement("lockRequestor");
				Text lockRequestorText = rootDocument.createTextNode(lockRequestor);
				lockRequestorElement.appendChild(lockRequestorText);
				node.appendChild(lockRequestorElement);				
			}
		}
	}	
}
