/**
 * 
 */
package com.tibco.cep.webstudio.client.request.model.impl.dataitem;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.Text;
import com.tibco.cep.webstudio.client.model.Entry;
import com.tibco.cep.webstudio.client.model.Permission;
import com.tibco.cep.webstudio.client.model.UserData;
import com.tibco.cep.webstudio.client.model.UserPermission;
import com.tibco.cep.webstudio.client.model.WebUser;
import com.tibco.cep.webstudio.client.request.model.IRequestDataItem;


/**
 * XML representation of User data
 * 
 * @author Yogendra Rajput
 */
public class UsersDataItem implements IRequestDataItem {
	private UserData usersData;

	public UsersDataItem(UserData userData) {
		this.usersData = userData;
	}

	public void serialize(Document rootDocument, Node rootNode) {

		Node userItemElement = rootDocument
				.createElement("aclSettingsItem");
		rootNode.appendChild(userItemElement);
		
		
		Node userData = rootDocument
				.createElement("authEntries");
		userItemElement.appendChild(userData);

		Node authType = rootDocument
				.createElement("authType");
		userData.appendChild(authType);

		Text file = rootDocument.createTextNode(String.valueOf(usersData.getAuthType()));
		authType.appendChild(file);

		for(WebUser webUser : usersData.getUserList()){

			Node user = rootDocument
					.createElement("authEntry");
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
					.createElement("roleName");
			user.appendChild(userRoles);

			Text roles = rootDocument.createTextNode(String.valueOf(webUser.getRole()));
			userRoles.appendChild(roles);

		}


		

	}

}
