/**
 * 
 */
package com.tibco.cep.webstudio.client.request.model.impl.dataitem;

import java.util.List;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.Text;
import com.tibco.cep.webstudio.client.request.model.IRequestDataItem;
import com.tibco.cep.webstudio.client.request.model.XMLRequestBuilderConstants;

/**
 * @author vpatil
 */
public class WorklistDelegationDataItem implements IRequestDataItem {
	private List<String> revisionId;
	private List<String> roles;
	
	@Override
	public void serialize(Document rootDocument, Node rootNode) {
		if (revisionId != null) {
			Node revisionsElement = rootDocument.createElement(XMLRequestBuilderConstants.WORKLIST_DELEGATE_REVISIONS_ELEMENT);
			for (String revision : revisionId) {
				Node revisionElement = rootDocument.createElement(XMLRequestBuilderConstants.WORKLIST_REVISIONID_ELEMENT);

				Text revisionValueText = rootDocument.createTextNode(revision);
				revisionElement.appendChild(revisionValueText);

				revisionsElement.appendChild(revisionElement);
			}
			
			rootNode.appendChild(revisionsElement);
		}
		
		if (roles != null) {
			Node rolesElement = rootDocument.createElement(XMLRequestBuilderConstants.WORKLIST_DELEGATE_ROLES_ELEMENT);
			for (String role : roles) {
				Node roleElement = rootDocument.createElement(XMLRequestBuilderConstants.WORKLIST_DELEGATE_ROLE_ELEMENT);

				Text roleValueText = rootDocument.createTextNode(role);
				roleElement.appendChild(roleValueText);

				rolesElement.appendChild(roleElement);
			}
			rootNode.appendChild(rolesElement);
		}
	}

	public List<String> getRevisionId() {
		return revisionId;
	}

	public void setRevisionId(List<String> revisionId) {
		this.revisionId = revisionId;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
}
