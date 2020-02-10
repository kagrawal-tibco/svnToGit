/**
 * 
 */
package com.tibco.cep.webstudio.client.request.model.impl.dataitem;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.Text;
import com.tibco.cep.webstudio.client.model.AclData;
import com.tibco.cep.webstudio.client.model.Entry;
import com.tibco.cep.webstudio.client.model.Permission;
import com.tibco.cep.webstudio.client.model.UserPermission;
import com.tibco.cep.webstudio.client.model.WebUser;
import com.tibco.cep.webstudio.client.request.model.IRequestDataItem;


/**
 * XML representation of acl data
 * 
 * @author Yogendra Rajput
 */
public class AclDataItem implements IRequestDataItem {
	private AclData aclContentData;

	public AclDataItem(AclData aclContentData) {
		this.aclContentData = aclContentData;
	}

	public void serialize(Document rootDocument, Node rootNode) {

		Node userPermissionItemElement = rootDocument
				.createElement("aclSettingsItem");
		rootNode.appendChild(userPermissionItemElement);
		
		Node selectedProject = rootDocument
				.createElement("projectName");
		userPermissionItemElement.appendChild(selectedProject);
		
		Text projectNameText = rootDocument.createTextNode(String.valueOf(aclContentData.getProjectName()));
		selectedProject.appendChild(projectNameText);

		
		Node aclData = rootDocument
				.createElement("aclData");
		userPermissionItemElement.appendChild(aclData);

		Node entries = rootDocument
				.createElement("entries");
		aclData.appendChild(entries);
		
		if(aclContentData.getEntries() != null){

			for(Entry aclEntry : aclContentData.getEntries()){

				Node entry = rootDocument
						.createElement("entry");
				entries.appendChild(entry);

				Node permissions = rootDocument
						.createElement("permissions");
				entry.appendChild(permissions);

				for(Permission aclPermission : aclEntry.getPermission()){

					Node permission = rootDocument
							.createElement("permission");
					permissions.appendChild(permission);

					Node action = rootDocument
							.createElement("action");
					permission.appendChild(action);

					Node aType = rootDocument
							.createElement("type");
					action.appendChild(aType);

					Text atypeText = rootDocument.createTextNode(String.valueOf(aclPermission.getActionType()));
					aType.appendChild(atypeText);

					Node aValue = rootDocument
							.createElement("value");
					action.appendChild(aValue);

					Text avalueText = rootDocument.createTextNode(String.valueOf(aclPermission.getActionValue()));
					aValue.appendChild(avalueText);

					Node resourceref = rootDocument
							.createElement("resourceref");
					permission.appendChild(resourceref);

					Text resourceText = rootDocument.createTextNode(String.valueOf(aclPermission.getResourceRef()));
					resourceref.appendChild(resourceText);
				}

				Node role = rootDocument
						.createElement("role");
				entry.appendChild(role);

				Node rname = rootDocument
						.createElement("name");
				role.appendChild(rname);

				Text nameValue = rootDocument.createTextNode(String.valueOf(aclEntry.getRoleName()));
				rname.appendChild(nameValue);

			}

		}


		Node resources = rootDocument
				.createElement("resources");
		aclData.appendChild(resources);
		
		if(aclContentData.getResources() != null){

			for(com.tibco.cep.webstudio.client.model.Resources aclResources : aclContentData.getResources()){

				Node aclResource = rootDocument
						.createElement("resource");
				resources.appendChild(aclResource);

				Node resourceId = rootDocument
						.createElement("rid");
				aclResource.appendChild(resourceId);

				Text resourceIDText = rootDocument.createTextNode(String.valueOf(aclResources.getResourceID()));
				resourceId.appendChild(resourceIDText);

				Node resourceType = rootDocument
						.createElement("type");
				aclResource.appendChild(resourceType);

				Text resourceTypeText = rootDocument.createTextNode(String.valueOf(aclResources.getResourceType()));
				resourceType.appendChild(resourceTypeText);

			}

		}




	}

}
