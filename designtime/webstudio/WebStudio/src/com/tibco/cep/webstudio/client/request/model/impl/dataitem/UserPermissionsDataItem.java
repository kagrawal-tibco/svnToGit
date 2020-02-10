/**
 * 
 */
package com.tibco.cep.webstudio.client.request.model.impl.dataitem;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.Text;
import com.tibco.cep.webstudio.client.model.Entry;
import com.tibco.cep.webstudio.client.model.Permission;
import com.tibco.cep.webstudio.client.model.UserPermission;
import com.tibco.cep.webstudio.client.model.WebUser;
import com.tibco.cep.webstudio.client.request.model.IRequestDataItem;


/**
 * XML representation of User Permissions
 * 
 * @author Yogendra Rajput
 */
public class UserPermissionsDataItem implements IRequestDataItem {
	private UserPermission userPermission;

	public UserPermissionsDataItem(UserPermission userPermission) {
		this.userPermission = userPermission;
	}

	public void serialize(Document rootDocument, Node rootNode) {

		Node userPermissionItemElement = rootDocument
				.createElement("userPermissionsItem");
		rootNode.appendChild(userPermissionItemElement);
		
		Node selectedProject = rootDocument
				.createElement("projectName");
		userPermissionItemElement.appendChild(selectedProject);
		
	//	Text projectNameText = rootDocument.createTextNode(String.valueOf(userPermission.getProjectName()));
	//	selectedProject.appendChild(projectNameText);

		Node userPermissionsData = rootDocument
				.createElement("userPermissionsData");
		userPermissionItemElement.appendChild(userPermissionsData);

		Node userData = rootDocument
				.createElement("userData");
		userPermissionsData.appendChild(userData);

		Node authType = rootDocument
				.createElement("authType");
		userData.appendChild(authType);

		Text file = rootDocument.createTextNode(String.valueOf(userPermission.getUserData().getAuthType()));
		authType.appendChild(file);

		for(WebUser webUser : userPermission.getUserData().getUserList()){

			Node user = rootDocument
					.createElement("user");
			userData.appendChild(user);

			Node userName = rootDocument
					.createElement("userName");
			user.appendChild(userName);

			Text name = rootDocument.createTextNode(String.valueOf(webUser.getUserName()));
			userName.appendChild(name);

			Node userpwd = rootDocument
					.createElement("userPassword");
			user.appendChild(userpwd);

			Text pwd = rootDocument.createTextNode(String.valueOf(webUser.getPassword()));
			userpwd.appendChild(pwd);

			Node userRoles = rootDocument
					.createElement("userRoles");
			user.appendChild(userRoles);

			Text roles = rootDocument.createTextNode(String.valueOf(webUser.getRole()));
			userRoles.appendChild(roles);

		}


		Node aclData = rootDocument
				.createElement("aclData");
		userPermissionsData.appendChild(aclData);

		Node entries = rootDocument
				.createElement("entries");
		aclData.appendChild(entries);

		
		if(userPermission.getAclData().getEntries() != null){	
		for(Entry aclEntry : userPermission.getAclData().getEntries()){

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

		if(userPermission.getAclData().getResources() != null){
		
		for(com.tibco.cep.webstudio.client.model.Resources aclResources : userPermission.getAclData().getResources()){

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
