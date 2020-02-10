package com.tibco.cep.webstudio.client.request.model.impl;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.tibco.cep.webstudio.client.request.model.IRequest;
import com.tibco.cep.webstudio.client.request.model.IRequestData;
import com.tibco.cep.webstudio.client.request.model.IRequestDataItem;
import com.tibco.cep.webstudio.client.request.model.IRequestProject;
import com.tibco.cep.webstudio.client.request.model.XMLRequestBuilderConstants;

/**
 * Default XML implementation of {@link IRequest}
 * @author aathalye
 *
 * @param <I>
 * @param <P>
 * @param <D>
 */
public class DefaultXMLRequest<I extends IRequestDataItem, P extends IRequestProject<I>, D extends IRequestData<I, P>> extends DefaultSerializableObject<D> implements IRequest<I, P, D> {

	@Override
	public void serialize(Document rootDocument, Node rootNode) {
		//Second param should also be document here
		//Create base request node
		Node requestElement = rootDocument.createElement(XMLRequestBuilderConstants.REQUEST_ELEMENT);
		rootDocument.appendChild(requestElement);
		for (D requestData : children) {
			requestData.serialize(rootDocument, requestElement);
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.webstudio.client.request.model.IRequest#addRequestData(com.tibco.cep.webstudio.client.request.model.IRequestData)
	 */
	@Override
	public void addRequestData(D requestData) {
		children.add(requestData);
	}
}
