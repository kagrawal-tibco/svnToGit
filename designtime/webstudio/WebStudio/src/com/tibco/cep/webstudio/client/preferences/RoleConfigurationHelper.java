package com.tibco.cep.webstudio.client.preferences;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.tibco.cep.webstudio.client.model.WebUser;
import com.tibco.cep.webstudio.client.portal.RoleConfigurationPortlet;
import com.tibco.cep.webstudio.client.request.model.impl.dataitem.AclDataItem;
import com.tibco.cep.webstudio.client.request.model.impl.dataitem.UserPermissionsDataItem;
import com.tibco.cep.webstudio.client.request.model.impl.dataitem.UsersDataItem;
import com.tibco.cep.webstudio.client.model.AclData;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.model.UserData;
import com.tibco.cep.webstudio.client.model.UserPermission;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.client.util.SerializeArtifact;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;

/**
 * @author Rohini Jadhav
 */

public class RoleConfigurationHelper implements HttpSuccessHandler, HttpFailureHandler {

	private static RoleConfigurationHelper instance;
	private static boolean portletPremission = false;

	/**
	 * Empty constructor for singleton instance creation
	 */
	private RoleConfigurationHelper() {
	}

	/**
	 * Creates a singleton instance for this class
	 * 
	 * @return
	 */
	public static RoleConfigurationHelper getInstance() {
		if (instance == null) {
			instance = new RoleConfigurationHelper();
		}
		return instance;
	}

	/**
	 * Fetch Role Preferences from the server
	 */
	public void getRoleConfigurationPreferences() {
		ArtifactUtil.addHandlers(this);

		HttpRequest request = new HttpRequest();
		request.submit(ServerEndpoints.RMS_GET_USER_DATA.getURL());
	}

	@SuppressWarnings("rawtypes")
	public void updateUserPermission(UserPermission userPermission) {
		UserPermissionsDataItem userPermissionsDataItem = new UserPermissionsDataItem(userPermission);

		Map<String, Object> requestParameters = new HashMap<String, Object>();
		requestParameters.put(RequestParameter.REQUEST_ARTIFACT_DATA_ITEM, userPermissionsDataItem);

		@SuppressWarnings("unchecked")
		String xmlData = new SerializeArtifact().generateXML(ServerEndpoints.RMS_UPDATE_USER_PERMISSIONS,
				requestParameters);

		HttpRequest request = new HttpRequest();
		request.clearParameters();
		request.setPostData(xmlData);
		request.setMethod(HttpMethod.PUT);

		ArtifactUtil.addHandlers(this);

		request.submit(ServerEndpoints.RMS_UPDATE_USER_PERMISSIONS.getURL());
	}

	@SuppressWarnings("rawtypes")
	public void updateUsersData(UserData userData) {
		UsersDataItem usersDataItem = new UsersDataItem(userData);

		Map<String, Object> requestParameters = new HashMap<String, Object>();
		requestParameters.put(RequestParameter.REQUEST_ARTIFACT_DATA_ITEM, usersDataItem);

		@SuppressWarnings("unchecked")
		String xmlData = new SerializeArtifact().generateXML(ServerEndpoints.RMS_UPDATE_USER_DATA, requestParameters);

		HttpRequest request = new HttpRequest();
		request.clearParameters();
		request.setPostData(xmlData);
		request.setMethod(HttpMethod.PUT);

		ArtifactUtil.addHandlers(this);

		request.submit(ServerEndpoints.RMS_UPDATE_USER_DATA.getURL());
	}

	@SuppressWarnings("rawtypes")
	public void updateAclData(AclData aclData) {
		AclDataItem aclDataItem = new AclDataItem(aclData);

		Map<String, Object> requestParameters = new HashMap<String, Object>();
		requestParameters.put(RequestParameter.REQUEST_ARTIFACT_DATA_ITEM, aclDataItem);

		@SuppressWarnings("unchecked")
		String xmlData = new SerializeArtifact().generateXML(ServerEndpoints.RMS_UPDATE_ACL_DATA, requestParameters);

		HttpRequest request = new HttpRequest();
		request.clearParameters();
		request.setPostData(xmlData);
		request.setMethod(HttpMethod.PUT);

		ArtifactUtil.addHandlers(this);

		request.submit(ServerEndpoints.RMS_UPDATE_ACL_DATA.getURL());
	}

	@Override
	public void onSuccess(HttpSuccessEvent event) {
		boolean validEvent = false;
		UserData userDataObject = new UserData();
		if (event.getUrl().indexOf(ServerEndpoints.RMS_GET_USER_DATA.getURL()) != -1) {

			Element docElement = event.getData();

			ArrayList<WebUser> userNameList = new ArrayList<WebUser>();

			NodeList recordList = docElement.getElementsByTagName("record");

			for (int i = 0; i < recordList.getLength(); i++) {
				if (!recordList.item(i).toString().trim().isEmpty()) {
					NodeList preferenceDetails = recordList.item(i).getChildNodes();

					for (int k = 0; k < preferenceDetails.getLength(); k++) {
						if (!preferenceDetails.item(k).toString().trim().isEmpty()) {
							NodeList childNodes = preferenceDetails.item(k).getChildNodes();
							String tagName = preferenceDetails.item(k).getNodeName();
							if (tagName.equals("authType")) {
								userDataObject.setAuthType(preferenceDetails.item(k).getFirstChild().getNodeValue());
							} else if(tagName.equals("authEntry")) {
								WebUser userDetails = new WebUser();
								for (int j = 0; j < childNodes.getLength(); j++) {
									String childNodeName = childNodes.item(j).getNodeName();
									if (childNodeName.equals("userName")) {
										userDetails.setUserName(childNodes.item(j).getFirstChild().getNodeValue());
									} else if (childNodeName.equals("userPassword")) {
										userDetails.setPassword(childNodes.item(j).getFirstChild().getNodeValue());
									} else if (childNodeName.equals("roleName")) {
										userDetails.setRole(childNodes.item(j).getFirstChild().getNodeValue());
									}

								}
								userNameList.add(userDetails);
							}

						}
					}

					userDataObject.setUserList(userNameList);

				}
			}

			WebStudio.get().setUserData(userDataObject);

			validEvent = true;
			setPortletPremission(true);
			WebStudio.get().createWebStudioPages(WebStudio.get().getUser());
		} else if (event.getUrl().indexOf(ServerEndpoints.RMS_UPDATE_USER_DATA.getURL()) != -1) {

			updateAclData(WebStudio.get().getAclData());
			validEvent = true;
			RoleConfigurationPortlet.disableApplyButton();
		}

		if (validEvent) {
			ArtifactUtil.removeHandlers(this);
		}
	}

	@Override
	public void onFailure(HttpFailureEvent event) {
		boolean validEvent = false;
		if (event.getUrl().indexOf(ServerEndpoints.RMS_GET_USER_DATA.getURL()) != -1) {
			validEvent = true;
		} else if (event.getUrl().indexOf(ServerEndpoints.RMS_UPDATE_USER_PREFERENCES.getURL()) != -1) {
			validEvent = true;
		}
		setPortletPremission(false);

		if (validEvent) {
			ArtifactUtil.removeHandlers(this);
		}
	}

	/**
	 * Method to check for role configuration portlet view permission.
	 * 
	 * @return
	 */
	public static boolean hasPortletPremission() {
		return portletPremission;
	}

	/**
	 * Method to set the role configuration portlet view permission.
	 * 
	 * @param portletPremission
	 */
	public static void setPortletPremission(boolean portletPremission) {
		RoleConfigurationHelper.portletPremission = portletPremission;
	}

}