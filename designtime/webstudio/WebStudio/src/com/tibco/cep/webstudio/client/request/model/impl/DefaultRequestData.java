package com.tibco.cep.webstudio.client.request.model.impl;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.tibco.cep.webstudio.client.request.model.IRequestData;
import com.tibco.cep.webstudio.client.request.model.IRequestDataItem;
import com.tibco.cep.webstudio.client.request.model.IRequestProject;
import com.tibco.cep.webstudio.client.request.model.XMLRequestBuilderConstants;

public class DefaultRequestData<D extends IRequestDataItem, P extends IRequestProject<D>> extends DefaultSerializableObject<P> implements IRequestData<D, P> {
	
	@Override
	public void serialize(Document rootDocument, Node rootNode) {
		//Create <data> element
		Element dataElement = rootDocument.createElement(XMLRequestBuilderConstants.DATA_ELEMENT);
		rootNode.appendChild(dataElement);
		
		for (P requestProject : children) {
			requestProject.serialize(rootDocument, dataElement);
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.webstudio.client.request.model.IRequestData#addRequestDataItem(com.tibco.cep.webstudio.client.request.model.IRequestDataItem)
	 */
	@Override
	public void addRequestProject(P requestProject) {
		children.add(requestProject);
	}
}
