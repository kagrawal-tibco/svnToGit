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
 * XML representation of worklist
 * 
 * @author Vikram Patil
 */
public class WorklistData<D extends IRequestDataItem> extends DefaultRequestProject<D> {
	private String revisionId;
	
	@Override
	public void serialize(Document rootDocument, Node rootNode) {
		// Create <worklist> element
		Element worklistElement = rootDocument.createElement(XMLRequestBuilderConstants.WORKLIST_ELEMENT);
		rootNode.appendChild(worklistElement);
		
		Node worklistRevisionId = rootDocument.createElement(XMLRequestBuilderConstants.WORKLIST_REVISIONID_ELEMENT);
		worklistRevisionId.appendChild(rootDocument.createTextNode(revisionId));
		worklistElement.appendChild(worklistRevisionId);

		for (D dataItem : children) {
			dataItem.serialize(rootDocument, worklistElement);
		}
	}

	public String getRevisionId() {
		return revisionId;
	}

	public void setRevisionId(String revisionId) {
		this.revisionId = revisionId;
	}
}
