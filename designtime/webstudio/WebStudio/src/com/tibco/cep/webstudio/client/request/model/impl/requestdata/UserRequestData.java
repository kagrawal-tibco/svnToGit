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
 * @author Yogendra Rajput
 */
public class UserRequestData<D extends IRequestDataItem> extends DefaultRequestProject<D> {
	
	public void serialize(Document rootDocument, Node rootNode) {
		//Create <userPermissions> element
		Element aclSettingsElement = rootDocument.createElement("aclSettings");
		rootNode.appendChild(aclSettingsElement);

		for (D dataItem : children) {
			dataItem.serialize(rootDocument, aclSettingsElement);
		}
	}

}
