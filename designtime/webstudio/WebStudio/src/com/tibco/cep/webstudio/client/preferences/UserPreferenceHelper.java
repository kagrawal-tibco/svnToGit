/**
 * 
 */
package com.tibco.cep.webstudio.client.preferences;

import static com.tibco.cep.webstudio.client.util.ErrorMessageDialog.showError;

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
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.model.DashboardPortlet;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.model.UserPreferences;
import com.tibco.cep.webstudio.client.portal.DASHBOARD_PORTLETS;
import com.tibco.cep.webstudio.client.request.model.impl.dataitem.UserPreferenceDataItem;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.client.util.Base64Utils;
import com.tibco.cep.webstudio.client.util.SerializeArtifact;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;

/**
 * Helper class to fetch / process / store user preferences
 * 
 * @author Vikram Patil
 */
public class UserPreferenceHelper implements HttpSuccessHandler, HttpFailureHandler {
	private static UserPreferenceHelper instance;
	
	private static final int DEFAULT_PORTAL_COLUMNS = 2;
	private static final int DEFAULT_FAVORITE_ARTIFACT_LIMIT = 10;
	private static final int DEFAULT_RECENTLY_OPENED_ARTIFACT_LIMIT = 10;
	private static final String DEFAULT_CUSTOM_WEBPAGE_URL = "www.tibco.com";
	private static final String DEFAULT_ITEM_VIEW = "List";
	private static final int DEFAULT_PORTLET_HEIGHT = 250;
	private static final int DEFAULT_WEBPAGEL_PORTLET_HEIGHT = 400;
	private static final int DEFAULT_PORTLET_COLSPAN = 1;
	
	private static final String SCS_CREDENTIALS_FAILED_ERROR_CODE = "ERR_1508";
	
	private GlobalMessages globalMsgBundle = (GlobalMessages) I18nRegistry
			.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);

	/**
	 * Empty constructor for singleton instance creation
	 */
	private UserPreferenceHelper() {
	}

	/**
	 * Creates a singleton instance for this class
	 * 
	 * @return
	 */
	public static UserPreferenceHelper getInstance() {
		if (instance == null) {
			instance = new UserPreferenceHelper();
		}
		return instance;
	}

	/**
	 * Fetch User Preferences from the server
	 */
	public void getUserPreferences() {
		ArtifactUtil.addHandlers(this);

		HttpRequest request = new HttpRequest();
		request.submit(ServerEndpoints.RMS_GET_USER_PREFERENCES.getURL());
	}
	
	/**
	 * Save/update User Preferences
	 * 
	 * @param userPreferences
	 */
	public void updateUserPreferences(UserPreferences userPreference) {
		UserPreferenceDataItem userPreferencesDataItem = new UserPreferenceDataItem(userPreference);

		Map<String, Object> requestParameters = new HashMap<String, Object>();
		requestParameters.put(RequestParameter.REQUEST_ARTIFACT_DATA_ITEM, userPreferencesDataItem);
		
		@SuppressWarnings("unchecked")
		String xmlData = new SerializeArtifact().generateXML(ServerEndpoints.RMS_UPDATE_USER_PREFERENCES, requestParameters);
		
		HttpRequest request = new HttpRequest();
		request.clearParameters();
		request.setPostData(xmlData);
		request.setMethod(HttpMethod.PUT);
		
		ArtifactUtil.addHandlers(this);
		
		request.submit(ServerEndpoints.RMS_UPDATE_USER_PREFERENCES.getURL());
	}

	@Override
	public void onSuccess(HttpSuccessEvent event) {
		boolean validEvent = false;

		if (event.getUrl().indexOf(ServerEndpoints.RMS_GET_USER_PREFERENCES.getURL()) != -1) {
			Element docElement = event.getData();

			NodeList recordList = docElement.getElementsByTagName("record");
			UserPreferences userPreference = new UserPreferences();
			for (int i = 0; i < recordList.getLength(); i++) {
				if (!recordList.item(i).toString().trim().isEmpty()) {
					NodeList preferenceDetails = recordList.item(i).getChildNodes();
					
					for (int k=0; k<preferenceDetails.getLength();k++) {
						if (!preferenceDetails.item(k).toString().trim().isEmpty()) {
							String nodeName = preferenceDetails.item(k).getNodeName();
							String nodeValue = (nodeName.equals("dashboardPortlets")) ? null : preferenceDetails.item(k)
									.getFirstChild().getNodeValue();

							if (nodeName.equals("portalColumns"))
								userPreference.setPortalColumns(Integer.parseInt(nodeValue));
							else if (nodeName.equals("recentlyOpenedArtifactLimit"))
								userPreference.setRecentlyOpenedArtifactLimit(Integer.parseInt(nodeValue));
							else if (nodeName.equals("favoriteArtifactLimit"))
								userPreference.setFavoriteArtifactLimit(Integer.parseInt(nodeValue));
							else if (nodeName.equals("customURL"))
								userPreference.setCustomURL(nodeValue);
							else if (nodeName.equals("itemView"))
								userPreference.setItemView(nodeValue);
							else if (nodeName.equals("decisionTablePageSize"))
								userPreference.setDecisionTablePageSize(Integer.parseInt(nodeValue));
							else if (nodeName.equals("scsUserName"))
								userPreference.setScsUserName(nodeValue);
							else if (nodeName.equals("scsUserPassword")) {
								byte[] decodedBytes = Base64Utils.fromBase64(nodeValue);
								String userPassword = new String (decodedBytes);
								userPreference.setScsUserPassword(userPassword);
							} else if (nodeName.equals("autoUnLockOnReview"))
								userPreference.setAutoUnLockOnReview(Boolean.parseBoolean(nodeValue));
							else if (nodeName.equals("groupRelatedArtifacts"))
								userPreference.setGroupRelatedArtifacts(Boolean.parseBoolean(nodeValue));
							else if (nodeName.equals("allowCustomDomainValues"))
								userPreference.setAllowCustomDomainValues(Boolean.parseBoolean(nodeValue));
							else if (nodeName.equals("showColumnAliasIfPresent"))
								userPreference.setShowColumnAliasIfPresent(Boolean.parseBoolean(nodeValue));
							else if (nodeName.equals("autoFitColumnsApproch"))
								userPreference.setAutoFitColumnsApproch(nodeValue);
							else if (nodeName.equals("defaultRTIFilterType"))
								userPreference.setRtiDefaultFilterType(nodeValue);	

							else if (nodeName.equals("dashboardPortlets")) {
								NodeList portletList = preferenceDetails.item(k).getChildNodes();

								for (int l = 0; l < portletList.getLength(); l++) {
									if (!portletList.item(l).toString().trim().isEmpty()) {
										NodeList portletDetails = portletList.item(l).getChildNodes();

										DashboardPortlet dashboardPortlet = null;
										for (int j = 0; j < portletDetails.getLength(); j++) {
											if (!portletDetails.item(j).toString().trim().isEmpty()) {
												if (dashboardPortlet == null) {
													dashboardPortlet = new DashboardPortlet();
												}

												String propertyName = portletDetails.item(j).getNodeName();
												String propertyValue = portletDetails.item(j).getFirstChild()
														.getNodeValue();

												if (propertyName.equals("portletId")) {
													dashboardPortlet.setPortletId(propertyValue);
												} else if (propertyName.equals("col")) {
													dashboardPortlet.setColumn(Integer.parseInt(propertyValue));
												} else if (propertyName.equals("rw")) {
													dashboardPortlet.setRow(Integer.parseInt(propertyValue));
												} else if (propertyName.equals("height")) {
													dashboardPortlet.setHeight(Integer.parseInt(propertyValue));
												} else if (propertyName.equals("colSpan")) {
													dashboardPortlet.setColSpan(Integer.parseInt(propertyValue));
												}

											}
										}
										if (dashboardPortlet != null) {
											userPreference.addDashboardPortlets(dashboardPortlet);
										}
									}
								}
							}
						}
					}
				}
			}

			setDefaultPreferences(userPreference);
			WebStudio.get().setUserPreference(userPreference);
			NotificationPreferencesHelper.getInstance().getNotificationPreferences();
			
			validEvent = true;
		} else if (event.getUrl().indexOf(ServerEndpoints.RMS_UPDATE_USER_PREFERENCES.getURL()) != -1) {
			validEvent = true;
		}
		
		if (validEvent) {
			ArtifactUtil.removeHandlers(this);
		}
	}

	@Override
	public void onFailure(HttpFailureEvent event) {
		boolean validEvent = false;
		if (event.getUrl().indexOf(ServerEndpoints.RMS_GET_USER_PREFERENCES.getURL()) != -1) {
			validEvent = true;
		} else if (event.getUrl().indexOf(ServerEndpoints.RMS_UPDATE_USER_PREFERENCES.getURL()) != -1) {
			validEvent = true;
			
			Element docElement = event.getData();
			String errorCode = docElement.getElementsByTagName("errorCode").item(0).getFirstChild().getNodeValue().trim();
			if (errorCode != null && !errorCode.isEmpty() && errorCode.equals(SCS_CREDENTIALS_FAILED_ERROR_CODE)) {
				showError(globalMsgBundle.preferences_SCS_Validation_Error_Message());
				WebStudio.get().getPreferencesPage().clearSCSPreferences();
			}
		}
		
		if (validEvent) {
			ArtifactUtil.removeHandlers(this);
		}
	}
	
	/**
	 * Set default values if no preferences are set
	 * 
	 * @param userPreference
	 */
	private void setDefaultPreferences(UserPreferences userPreference) {
		if (userPreference.getPortalColumns() < 1)
			userPreference.setPortalColumns(DEFAULT_PORTAL_COLUMNS);
		if (userPreference.getRecentlyOpenedArtifactLimit() < 1)
			userPreference.setRecentlyOpenedArtifactLimit(DEFAULT_RECENTLY_OPENED_ARTIFACT_LIMIT);
		if (userPreference.getFavoriteArtifactLimit() < 1)
			userPreference.setFavoriteArtifactLimit(DEFAULT_FAVORITE_ARTIFACT_LIMIT);
		if (userPreference.getCustomURL() == null)
			userPreference.setCustomURL(DEFAULT_CUSTOM_WEBPAGE_URL);
		if (userPreference.getItemView() == null)
			userPreference.setItemView(DEFAULT_ITEM_VIEW);

		if (userPreference.getDashboardPortlets().size() < 1) {
			userPreference.addDashboardPortlets(new DashboardPortlet(DASHBOARD_PORTLETS.TIBBR.getTitle(),
					0,
					0,
					DEFAULT_WEBPAGEL_PORTLET_HEIGHT,
					DEFAULT_PORTLET_COLSPAN));
			userPreference.addDashboardPortlets(new DashboardPortlet(DASHBOARD_PORTLETS.AVALIABLE_PROJECTS.getTitle(),
					1,
					0,
					DEFAULT_PORTLET_HEIGHT,
					DEFAULT_PORTLET_COLSPAN));
			userPreference.addDashboardPortlets(new DashboardPortlet(DASHBOARD_PORTLETS.GROUPS.getTitle(),
					0,
					1,
					DEFAULT_PORTLET_HEIGHT,
					DEFAULT_PORTLET_COLSPAN));
			userPreference.addDashboardPortlets(new DashboardPortlet(DASHBOARD_PORTLETS.WORKLIST.getTitle(),
					1,
					1,
					DEFAULT_PORTLET_HEIGHT,
					DEFAULT_PORTLET_COLSPAN));
			userPreference.addDashboardPortlets(new DashboardPortlet(DASHBOARD_PORTLETS.NOTIFICATIONS.getTitle(),
					0,
					2,
					DEFAULT_PORTLET_HEIGHT,
					DEFAULT_PORTLET_COLSPAN));
			userPreference.addDashboardPortlets(new DashboardPortlet(DASHBOARD_PORTLETS.RECENTLY_OPENED.getTitle(),
					1,
					2,
					DEFAULT_PORTLET_HEIGHT,
					DEFAULT_PORTLET_COLSPAN));
		}
	}
}
