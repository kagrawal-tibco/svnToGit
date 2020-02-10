/**
 * 
 */
package com.tibco.cep.webstudio.client.request.model.impl;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.tibco.cep.webstudio.client.request.model.IRequestDataItem;
import com.tibco.cep.webstudio.client.request.model.IRequestProject;
import com.tibco.cep.webstudio.client.request.model.XMLRequestBuilderConstants;

/**
 * @author aathalye
 *
 */
public class DefaultRequestProject<D extends IRequestDataItem> extends DefaultSerializableObject<D> implements IRequestProject<D> {
	
	private String name;
	
	private String operationName;
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.webstudio.client.request.model.ISerializableObject#serialize(com.google.gwt.xml.client.Document, com.google.gwt.xml.client.Node)
	 */
	@Override
	public void serialize(Document rootDocument, Node rootNode) {
		//Create <project> element
		Element projectElement = rootDocument.createElement(XMLRequestBuilderConstants.PROJECT_ELEMENT);
		
		Element nameElement = rootDocument.createElement(XMLRequestBuilderConstants.NAME_ELEMENT);
		nameElement.appendChild(rootDocument.createTextNode(name));
		projectElement.appendChild(nameElement);
		
		if (operationName != null && !operationName.isEmpty()) {
			Element operationElement = rootDocument.createElement(XMLRequestBuilderConstants.OPERATION_ELEMENT);
			operationElement.appendChild(rootDocument.createTextNode(operationName));
			projectElement.appendChild(operationElement);
		}
		
		rootNode.appendChild(projectElement);
		
		for (D dataItem : children) {
			dataItem.serialize(rootDocument, projectElement);
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.webstudio.client.request.model.IRequestProject#addRequestDataItem(com.tibco.cep.webstudio.client.request.model.IRequestDataItem)
	 */
	@Override
	public void addRequestDataItem(D dataItem) {
		children.add(dataItem);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.webstudio.client.request.model.IRequestProject#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.webstudio.client.request.model.IRequestProject#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.webstudio.client.request.model.IRequestProject#getOperationName()
	 */
	@Override
	public String getOperationName() {
		return operationName;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.webstudio.client.request.model.IRequestProject#setOperationName(java.lang.String)
	 */
	@Override
	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	
	
}
