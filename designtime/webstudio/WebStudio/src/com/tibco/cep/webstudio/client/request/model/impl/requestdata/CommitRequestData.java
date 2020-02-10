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
 * XML representation of the commit Request
 * 
 * @author Vikram Patil
 */
public class CommitRequestData<D extends IRequestDataItem> extends DefaultRequestProject<D> {
	private String commitComments;
	
	@Override
	public void serialize(Document rootDocument, Node rootNode) {
		Element commitCommentsElement = rootDocument.createElement(XMLRequestBuilderConstants.COMMIT_COMMENTS_ELEMENT);
		if (commitComments != null && !commitComments.isEmpty()) {
			commitCommentsElement.appendChild(rootDocument.createTextNode(commitComments));
		}
		rootNode.appendChild(commitCommentsElement);
		
		super.serialize(rootDocument, rootNode);
	}

	public String getCommitComments() {
		return commitComments;
	}

	public void setCommitComments(String commitComments) {
		this.commitComments = commitComments;
	}
}
