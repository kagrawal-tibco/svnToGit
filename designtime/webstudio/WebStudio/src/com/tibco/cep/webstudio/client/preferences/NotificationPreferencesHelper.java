package com.tibco.cep.webstudio.client.preferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.http.HttpFailureEvent;
import com.tibco.cep.webstudio.client.http.HttpFailureHandler;
import com.tibco.cep.webstudio.client.http.HttpMethod;
import com.tibco.cep.webstudio.client.http.HttpRequest;
import com.tibco.cep.webstudio.client.http.HttpSuccessEvent;
import com.tibco.cep.webstudio.client.http.HttpSuccessHandler;
import com.tibco.cep.webstudio.client.model.ApplicationPreferences;
import com.tibco.cep.webstudio.client.model.EmailPreference;
import com.tibco.cep.webstudio.client.model.NotificationDetails;
import com.tibco.cep.webstudio.client.model.OperatorPreferences;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.model.WebUser;
import com.tibco.cep.webstudio.client.portal.NotificationPortlet;
import com.tibco.cep.webstudio.client.request.model.impl.dataitem.ApplicationPreferenceDataItem;
import com.tibco.cep.webstudio.client.request.model.impl.dataitem.NotificationPreferenceDataItem;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.client.util.SerializeArtifact;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;
import com.tibco.cep.webstudio.model.rule.instance.operators.IBuilderOperator;

public class NotificationPreferencesHelper implements HttpSuccessHandler,
HttpFailureHandler{

	private static NotificationPreferencesHelper instance;
	
	public static NotificationPreferencesHelper getInstance() {
		if (instance == null) {
			instance = new NotificationPreferencesHelper();
		}
		return instance;
	}

	private NotificationDetails notificationData;
	
	public void getNotificationPreferences() {
		ArtifactUtil.addHandlers(this);
		HttpRequest request = new HttpRequest();
		request.setMethod(HttpMethod.GET);
		request.submit(ServerEndpoints.RMS_GET_NOTIFICATION_PREFERENCES.getURL());
	}
	
	@Override
	public void onFailure(HttpFailureEvent event) {
		boolean validEvent = false;
		if (event.getUrl().indexOf(ServerEndpoints.RMS_GET_NOTIFICATION_PREFERENCES.getURL()) != -1) {
			validEvent = true;
		}
		
		if (validEvent) {
			ArtifactUtil.removeHandlers(this);
		}
		
	}

	@Override
	public void onSuccess(HttpSuccessEvent event) {
		
		boolean validEvent = false;
		if (event.getUrl().indexOf(
				ServerEndpoints.RMS_GET_NOTIFICATION_PREFERENCES.getURL()) != -1) {
			
			Element docElement = event.getData();
			
			ArrayList<EmailPreference> emailPreferenceList = new ArrayList<EmailPreference>();
			
			NotificationDetails notificationData = new NotificationDetails();
			
			NodeList recordList = docElement.getElementsByTagName("record");
			
			for (int i = 0; i < recordList.getLength(); i++) {
				if (!recordList.item(i).toString().trim().isEmpty()) {
					
					NodeList preferenceDetails = recordList.item(i).getChildNodes();

					for (int k = 0; k < preferenceDetails.getLength(); k++) {
						if (!preferenceDetails.item(k).toString().trim().isEmpty()) {
							NodeList childNodes = preferenceDetails.item(k).getChildNodes();
								EmailPreference epreference = new EmailPreference();
								for (int j = 0; j < childNodes.getLength(); j++) {
									String childNodeName = childNodes.item(j).getNodeName();
									String nodeValue = " ";
									if(childNodes.item(j).getFirstChild() != null){
										nodeValue = childNodes.item(j).getFirstChild().getNodeValue();
									}
									if(childNodeName.equalsIgnoreCase("project")){
										epreference.setProjectName(nodeValue);
									}else if(childNodeName.equalsIgnoreCase("actions")){
										epreference.setActions(nodeValue);
									}else if(childNodeName.equalsIgnoreCase("emailIds")){
										epreference.setEmailIDs(nodeValue);
									}
								}
								emailPreferenceList.add(epreference);
							}
							
						}
					notificationData.setEmailPreference(emailPreferenceList);
				}
			}
			WebStudio.get().setNotifyObject(notificationData);
			this.notificationData = notificationData;
            ApplicationPreferenceHelper.getInstance().getApplicationPreferences();
			
			validEvent = true;
		}if (event.getUrl().indexOf(
				ServerEndpoints.RMS_UPDATE_NOTIFICATION_PREFERENCES.getURL()) != -1) {
			validEvent = true;
			NotificationPortlet.disableApplyButton();
		}
		
		if (validEvent) {
			ArtifactUtil.removeHandlers(this);
		}
	}
	
	public List<String> getProjectList(){
		List<String> projectlist = new ArrayList<String>();
		for(EmailPreference email : notificationData.getEmailPreference()){
			projectlist.add(email.getProjectName());
		}
		return projectlist;
		
	}
	
	public Map<String, Boolean> getActionsMap(String fieldLabel){
		List<String> defaultActions = new ArrayList<String>();
		defaultActions.add("commit");
		defaultActions.add("approve");
		defaultActions.add("reject");
		defaultActions.add("deploy");
		
		Map<String, Boolean> actionsmap = new LinkedHashMap<String, Boolean>();
		
		for(EmailPreference email : notificationData.getEmailPreference()){
			if(fieldLabel.equals(email.getProjectName())){
				for(String add : defaultActions){
					if(email.getActions().contains(add)){
						actionsmap.put(add, true);
					}else{
						actionsmap.put(add, false);
					}
				}
				break;
			}
		}
		
		return actionsmap;
		
	}
		
	public String[] getEmailIDsArray(String fieldLabel){

		for(EmailPreference email : notificationData.getEmailPreference()){
			if(fieldLabel.equals(email.getProjectName())){
				if(email.getEmailIDs() == null){
					String[] empty = {};
					return empty;
				}else{
					return email.getEmailIDs().split(",");
				}
			}
		}
		return null;
	}
	
	public void updateProjectActionsString(String projectLabel, String actionLabel, boolean value){
		for(EmailPreference email : notificationData.getEmailPreference()){
			if(email.getProjectName().equals(projectLabel)){
				if(value){
					if(email.getActions().isEmpty()){
						String actions = actionLabel;
						email.setActions(actions);
					}else{
						String actions = email.getActions()+","+actionLabel;
						email.setActions(actions);
					}
				}else{
					String newactions = "";
					String[] actions = email.getActions().split(",");
					for(String a : actions){
						if(!a.equals(actionLabel)){
							if(!newactions.isEmpty()){
							 newactions = newactions+","+a;
							}else{
								newactions = a;
							}
						}
						
					}
					email.setActions(newactions);
				}
				
				break;
				 
			}
			
		}
		
	}
	
	public void addOperationProjectEmailsString(String projectLabel, String emailString, String oldEmailString){
			if(!emailString.equalsIgnoreCase(oldEmailString)){
				for(EmailPreference email : notificationData.getEmailPreference()){
					if(email.getProjectName().equals(projectLabel)){
						
					if(email.getEmailIDs()!= null){

						if(email.getEmailIDs().contains(oldEmailString) && !emailString.equals("@New_Null_Value")){
							String newEmailString = "";
							String[] elist = email.getEmailIDs().split(",");
							for(String e: elist){
								if(newEmailString.isEmpty()){
									if(e.equalsIgnoreCase(oldEmailString)){
										newEmailString = emailString;
									}else{
										newEmailString = e;
									}
								}else{
									if(e.equalsIgnoreCase(oldEmailString)){
										newEmailString = newEmailString+","+emailString;
									}else{
										newEmailString = newEmailString+","+e;
									}
								}
							}
							email.setEmailIDs(newEmailString);

						}else if(!emailString.equals("@New_Null_Value")){
							if(email.getEmailIDs().equalsIgnoreCase(" ")){
								email.setEmailIDs(emailString);
							}else{
								String newEmailString = email.getEmailIDs() +","+ emailString;
								email.setEmailIDs(newEmailString);
							}
						}
						
					  }else if(!emailString.equals("@New_Null_Value")){
							
							email.setEmailIDs(emailString);
					  }

					}
				}
			}
	}
	
	public void deleteOperationProjectEmailsString(String projectLabel, String emailString){

		for(EmailPreference email : notificationData.getEmailPreference()){
			if(email.getProjectName().equals(projectLabel)){
				String newEmailString = "";
				String[] eString = email.getEmailIDs().split(",");
				for(String entry : eString){
					   if(!entry.equalsIgnoreCase(emailString)){
						if(newEmailString.isEmpty() || newEmailString.equalsIgnoreCase("")){
							newEmailString = entry;
						}else{
							newEmailString = newEmailString+","+entry;
						}
				   }
				}
				email.setEmailIDs(newEmailString);
			}

		}
	}
	
	public void updateNotificationPreferences() {
		NotificationPreferenceDataItem notifyPrefenceDataItem = new NotificationPreferenceDataItem(
				notificationData);

		Map<String, Object> requestParameters = new HashMap<String, Object>();
		requestParameters.put(RequestParameter.REQUEST_ARTIFACT_DATA_ITEM,
				notifyPrefenceDataItem);

		@SuppressWarnings({ "rawtypes", "unchecked" })
		String xmlData = new SerializeArtifact().generateXML(
				ServerEndpoints.RMS_UPDATE_NOTIFICATION_PREFERENCES,
				requestParameters);

		HttpRequest request = new HttpRequest();
		request.clearParameters();
		request.setPostData(xmlData);
		request.setMethod(HttpMethod.PUT);

		ArtifactUtil.addHandlers(this);

		request.submit(ServerEndpoints.RMS_UPDATE_NOTIFICATION_PREFERENCES
				.getURL());
	}

}
