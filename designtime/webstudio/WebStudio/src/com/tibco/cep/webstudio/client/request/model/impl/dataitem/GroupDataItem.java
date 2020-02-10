/**
 * 
 */
package com.tibco.cep.webstudio.client.request.model.impl.dataitem;

import java.util.List;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.Text;
import com.tibco.cep.webstudio.client.request.model.IRequestDataItem;
import com.tibco.cep.webstudio.client.request.model.XMLRequestBuilderConstants;

/**
 * XML representation of User Group
 * 
 * @author Vikram Patil
 */
public class GroupDataItem implements IRequestDataItem {
	private List<String> artifactList;

	/* (non-Javadoc)
	 * @see com.tibco.cep.webstudio.client.request.model.ISerializableObject#serialize(com.google.gwt.xml.client.Document, com.google.gwt.xml.client.Node)
	 */
	public void serialize(Document rootDocument, Node rootNode) {
		Node groupItemElement = rootDocument.createElement(XMLRequestBuilderConstants.GROUP_ITEM_ELEMENT);
		rootNode.appendChild(groupItemElement);
		
		if (artifactList != null) {
			for (String artifact : artifactList){
				Node artifactElement = rootDocument.createElement(XMLRequestBuilderConstants.GROUP_ARTIFACT_ELEMENT);

				Text artifactValueText = rootDocument.createTextNode(artifact);
				artifactElement.appendChild(artifactValueText);

				groupItemElement.appendChild(artifactElement);
			}
		}
	}

	public List<String> getArtifactList() {
		return artifactList;
	}

	public void setArtifactList(List<String> artifactList) {
		this.artifactList = artifactList;
	}
}
