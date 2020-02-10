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
 * @author vpatil
 */
public class WorklistDelegationData<D extends IRequestDataItem> extends DefaultRequestProject<D> {
	
	@Override
	public void serialize(Document rootDocument, Node rootNode) {
		// Create <userPreference> element
		Element userPreferenceElement = rootDocument
				.createElement(XMLRequestBuilderConstants.WORKLIST_ELEMENT);
		rootNode.appendChild(userPreferenceElement);

		for (D dataItem : children) {
			dataItem.serialize(rootDocument, userPreferenceElement);
		}
	}
}
