/**
 * 
 */
package com.tibco.cep.webstudio.client.request.model.impl.dataitem;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.Text;
import com.tibco.cep.webstudio.client.model.EmailPreference;
import com.tibco.cep.webstudio.client.model.NotificationDetails;
import com.tibco.cep.webstudio.client.request.model.IRequestDataItem;

/**
 * XML representation of Notification Preferences
 * 
 * @author Yogendra Rajput
 */
public class NotificationPreferenceDataItem implements IRequestDataItem {
	private NotificationDetails notifyPreference;

	public NotificationPreferenceDataItem(NotificationDetails notifyPreference) {
		this.notifyPreference = notifyPreference;
	}

	public void serialize(Document rootDocument, Node rootNode) {
		Node notifyPreferenceItemElement = rootDocument
				.createElement("notificationPreferenceItem");
		rootNode.appendChild(notifyPreferenceItemElement);

		for(EmailPreference emails : notifyPreference.getEmailPreference()){
			
			Node emailPreference = rootDocument
					.createElement("emailPreference");
			notifyPreferenceItemElement.appendChild(emailPreference);
			
			Node project = rootDocument
					.createElement("project");
			emailPreference.appendChild(project);

			Text projectText = rootDocument.createTextNode(emails.getProjectName());
			project.appendChild(projectText);
			
			Node actions = rootDocument
					.createElement("actions");
			emailPreference.appendChild(actions);
            
			String actionList;
			if(!emails.getActions().isEmpty()){
				actionList = emails.getActions();
			}else{
				actionList = "NO_ACTIONS";
			}
			Text actionsText = rootDocument.createTextNode(actionList);
			actions.appendChild(actionsText);
			
			Node emailIds = rootDocument
					.createElement("emailIds");
			emailPreference.appendChild(emailIds);
			
			String emailList;
			if(emails.getEmailIDs()!= null && !emails.getEmailIDs().isEmpty()){
				emailList = emails.getEmailIDs();
			}else{
				emailList = "NO_EMAILS";
			}

			Text emailIdsText = rootDocument.createTextNode(emailList);
			emailIds.appendChild(emailIdsText);
			
		}
		
	}

	public NotificationDetails getNotificationPreference() {
		return notifyPreference;
	}

	public void setUserPreference(NotificationDetails notifyPreference) {
		this.notifyPreference = notifyPreference;
	}
}
