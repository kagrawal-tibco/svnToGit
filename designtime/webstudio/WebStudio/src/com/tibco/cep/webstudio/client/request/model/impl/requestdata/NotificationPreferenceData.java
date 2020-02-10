package com.tibco.cep.webstudio.client.request.model.impl.requestdata;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.tibco.cep.webstudio.client.request.model.IRequestDataItem;
import com.tibco.cep.webstudio.client.request.model.impl.DefaultRequestProject;

/**
 * 
 * @author Yogendra Rajput
 *
 * @param <D>
 */
public class NotificationPreferenceData<D extends IRequestDataItem> extends DefaultRequestProject<D> {

	public void serialize(Document rootDocument, Node rootNode) {
		Element notifyPreferenceElement = rootDocument.createElement("notificationPreference");
		rootNode.appendChild(notifyPreferenceElement);

		for (D dataItem : children) {
			dataItem.serialize(rootDocument, notifyPreferenceElement);
		}
	}
}
