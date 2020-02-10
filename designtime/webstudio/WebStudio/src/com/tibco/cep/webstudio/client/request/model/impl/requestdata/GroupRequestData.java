/**
 * 
 */
package com.tibco.cep.webstudio.client.request.model.impl.requestdata;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.tibco.cep.webstudio.client.request.model.IRequestDataItem;
import com.tibco.cep.webstudio.client.request.model.XMLRequestBuilderConstants;
import com.tibco.cep.webstudio.client.request.model.impl.DefaultRequestProject;

/**
 * XML representation of group request
 * @author Vikram Patil
 */
public class GroupRequestData<D extends IRequestDataItem> extends DefaultRequestProject<D> {
	
	public void serialize(Document rootDocument, Node rootNode) {
		//Create <userGroup> element
		Element groupElement = rootDocument.createElement(XMLRequestBuilderConstants.GROUP_ELEMENT);
		Element nameElement = rootDocument.createElement(XMLRequestBuilderConstants.NAME_ELEMENT);
		nameElement.appendChild(rootDocument.createTextNode(getName()));
		groupElement.appendChild(nameElement);

		rootNode.appendChild(groupElement);

		for (D dataItem : children) {
			dataItem.serialize(rootDocument, groupElement);
		}
	}
}
