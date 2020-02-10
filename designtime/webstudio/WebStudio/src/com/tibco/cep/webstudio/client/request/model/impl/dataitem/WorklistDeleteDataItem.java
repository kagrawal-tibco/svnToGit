package com.tibco.cep.webstudio.client.request.model.impl.dataitem;

import java.util.List;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.Text;
import com.tibco.cep.webstudio.client.request.model.IRequestDataItem;
import com.tibco.cep.webstudio.client.request.model.XMLRequestBuilderConstants;

/**
 * @author moshaikh
 */
public class WorklistDeleteDataItem implements IRequestDataItem {
	private List<String> revisionId;

	@Override
	public void serialize(Document rootDocument, Node rootNode) {
		if (revisionId != null) {
			Node revisionsElement = rootDocument
					.createElement(XMLRequestBuilderConstants.WORKLIST_DELEGATE_REVISIONS_ELEMENT);
			for (String revision : revisionId) {
				Node revisionElement = rootDocument
						.createElement(XMLRequestBuilderConstants.WORKLIST_REVISIONID_ELEMENT);

				Text revisionValueText = rootDocument.createTextNode(revision);
				revisionElement.appendChild(revisionValueText);

				revisionsElement.appendChild(revisionElement);
			}

			rootNode.appendChild(revisionsElement);
		}
	}

	public List<String> getRevisionId() {
		return revisionId;
	}

	public void setRevisionId(List<String> revisionId) {
		this.revisionId = revisionId;
	}
}
