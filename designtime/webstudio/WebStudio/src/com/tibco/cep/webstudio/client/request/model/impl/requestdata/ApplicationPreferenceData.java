package com.tibco.cep.webstudio.client.request.model.impl.requestdata;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.tibco.cep.webstudio.client.request.model.IRequestDataItem;
import com.tibco.cep.webstudio.client.request.model.XMLRequestBuilderConstants;
import com.tibco.cep.webstudio.client.request.model.impl.DefaultRequestProject;

/**
 * 
 * @author apsharma
 *
 * @param <D>
 */
public class ApplicationPreferenceData<D extends IRequestDataItem> extends DefaultRequestProject<D> {

	public void serialize(Document rootDocument, Node rootNode) {
		//Create <userPreference> element
		Element appPreferenceElement = rootDocument.createElement(XMLRequestBuilderConstants.APPLICATION_PREFERENCE_ELEMENT);
		rootNode.appendChild(appPreferenceElement);

		for (D dataItem : children) {
			dataItem.serialize(rootDocument, appPreferenceElement);
		}
	}
}
