package com.tibco.cep.webstudio.client.preferences;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.util.Page;
import com.tibco.cep.webstudio.client.CommandOperatorUtils;
import com.tibco.cep.webstudio.client.FilterOperatorUtils;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.http.HttpFailureEvent;
import com.tibco.cep.webstudio.client.http.HttpFailureHandler;
import com.tibco.cep.webstudio.client.http.HttpMethod;
import com.tibco.cep.webstudio.client.http.HttpRequest;
import com.tibco.cep.webstudio.client.http.HttpSuccessEvent;
import com.tibco.cep.webstudio.client.http.HttpSuccessHandler;
import com.tibco.cep.webstudio.client.model.ApplicationPreferences;
import com.tibco.cep.webstudio.client.model.OperatorPreferences;
import com.tibco.cep.webstudio.client.model.RTIFieldType;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.portal.ApplicationPreferencePortlet;
import com.tibco.cep.webstudio.client.request.model.impl.dataitem.ApplicationPreferenceDataItem;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.client.util.SerializeArtifact;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;
import com.tibco.cep.webstudio.model.rule.instance.operators.CommandOperator;
import com.tibco.cep.webstudio.model.rule.instance.operators.FilterOperator;
import com.tibco.cep.webstudio.model.rule.instance.operators.IBuilderOperator;

/**
 * Helper class to fetch / process / store application preferences
 * 
 * @author apsharma
 */
public class ApplicationPreferenceHelper implements HttpSuccessHandler,
		HttpFailureHandler {

	private static ApplicationPreferenceHelper instance;
	private static ApplicationPreferences applicationPreferences;
	private static boolean portletPremission = false;
	private static Map<String, String> iconMap;

	public static final String COMMAND_OPERATOR = "commandOperators";
	public static final String FILTER_OPERATOR = "filterOperators";

	/**
	 * Path for images
	 */
	protected String iconPath = Page.getAppImgDir() + "icons/16/";

	/**
	 * Get the instance of ApplicationPreferenceHelper
	 * 
	 * @return
	 */
	public static ApplicationPreferenceHelper getInstance() {
		if (instance == null) {
			instance = new ApplicationPreferenceHelper();
		}
		return instance;
	}

	/**
	 * Fetch Application Preferences from the server
	 */
	public void getApplicationPreferences() {
		ArtifactUtil.addHandlers(this);
		HttpRequest request = new HttpRequest();
		request.setMethod(HttpMethod.GET);
		request.submit(ServerEndpoints.RMS_GET_APPLICATION_PREFERENCES.getURL());
	}

	/**
	 * Method to check whether the logged in user has permission to view
	 * Application Preference Portlet.
	 */
	public void hasPermissionForAppPrefPortlet() {
		ArtifactUtil.addHandlers(this);
		HttpRequest request = new HttpRequest();
		request.setMethod(HttpMethod.GET);
		request.submit(ServerEndpoints.RMS_CHECK_APPLICATION_PREFERENCES_PROTLET_PERMISSION
				.getURL());
	}

	@Override
	public void onSuccess(HttpSuccessEvent event) {
		boolean validEvent = false;

		if (event.getUrl().indexOf(
				ServerEndpoints.RMS_GET_APPLICATION_PREFERENCES.getURL()) != -1) {
			ApplicationPreferences applicationPreferences = parseApplicationPreferences(event
					.getData());
			WebStudio.get().setApplicationPreferences(applicationPreferences);
			ApplicationPreferenceHelper.applicationPreferences = new ApplicationPreferences(
					applicationPreferences);
			buildApplicationPreferences(ApplicationPreferenceHelper.applicationPreferences);
			ApplicationPreferenceHelper.getInstance()
					.hasPermissionForAppPrefPortlet();
			validEvent = true;
		} else if (event.getUrl().indexOf(
				ServerEndpoints.RMS_UPDATE_APPLICATION_PREFERENCES.getURL()) != -1) {
			validEvent = true;
			WebStudio.get().setApplicationPreferences(
					new ApplicationPreferences(applicationPreferences));
			ApplicationPreferencePortlet.disableApplyButton();
		} else if (event
				.getUrl()
				.indexOf(
						ServerEndpoints.RMS_CHECK_APPLICATION_PREFERENCES_PROTLET_PERMISSION
								.getURL()) != -1) {
			validEvent = true;
			setPortletPremission(true);
			RoleConfigurationHelper.getInstance().getRoleConfigurationPreferences();
		}

		if (validEvent) {
			ArtifactUtil.removeHandlers(this);
		}
	}

	/**
	 * Method to parse Application Preferences from data received.
	 * 
	 * @param docElement
	 * @return
	 */
	private ApplicationPreferences parseApplicationPreferences(
			Element docElement) {
		NodeList recordList = docElement.getElementsByTagName("record");
		ApplicationPreferences applicationPreferences = new ApplicationPreferences();
		List<OperatorPreferences> operatorPreferencesList = new ArrayList<OperatorPreferences>();
		for (int i = 0; i < recordList.getLength(); i++) {
			NodeList operatorPrefList = recordList.item(i).getChildNodes();

			for (int opPrefCount = 0; opPrefCount < operatorPrefList
					.getLength(); opPrefCount++) {
				if (!operatorPrefList.item(opPrefCount).toString().trim()
						.isEmpty()) {
					NodeList details = operatorPrefList.item(opPrefCount)
							.getChildNodes();
					OperatorPreferences operatorPreferences = new OperatorPreferences();
					List<IBuilderOperator> filterOperators = new ArrayList<IBuilderOperator>();
					List<IBuilderOperator> commandOperators = new ArrayList<IBuilderOperator>();
					for (int cnt = 0; cnt < details.getLength(); cnt++) {
						if (!details.item(cnt).toString().trim().isEmpty()) {
							String nodeName = details.item(cnt).getNodeName();
							String nodeValue = details.item(cnt)
									.getFirstChild().getNodeValue();
							if ("fieldType".equals(nodeName)) {
								operatorPreferences.setFieldType(nodeValue);
							} else if (FILTER_OPERATOR.equals(nodeName)) {
								filterOperators.add(FilterOperator
										.fromValue(nodeValue));
							} else if (COMMAND_OPERATOR.equals(nodeName)) {
								commandOperators.add(CommandOperator
										.fromValue(nodeValue));
							}
						}
					}
					operatorPreferences.setFilterOperators(filterOperators);
					operatorPreferences.setCommandOperators(commandOperators);
					operatorPreferencesList.add(operatorPreferences);
				}
			}
		}
		applicationPreferences.setOperatorPreferences(operatorPreferencesList);
		return applicationPreferences;
	}

	private void buildApplicationPreferences(
			ApplicationPreferences appPreferences) {
		applicationPreferences = new ApplicationPreferences();
		List<OperatorPreferences> opPreferencesList = new ArrayList<OperatorPreferences>();
		for (RTIFieldType fieldType : RTIFieldType.values()) {
			OperatorPreferences operatorPreferences = new OperatorPreferences();
			operatorPreferences.setFieldType(fieldType.getDisplayText());
			boolean fieldTypeExist = false;
			for (OperatorPreferences opPreferences : appPreferences
					.getOperatorPreferences()) {
				if (opPreferences.getFieldType().equals(
						fieldType.getDisplayText())) {
					fieldTypeExist = true;
					operatorPreferences.setFilterOperators(opPreferences
							.getFilterOperators());
					operatorPreferences.setCommandOperators(opPreferences
							.getCommandOperators());
				}
			}

			if (!fieldTypeExist) {
				FieldType type = getType(fieldType.getValue());
				operatorPreferences.setFilterOperators(FilterOperatorUtils
						.getOperatorsForType(type));
				operatorPreferences.setCommandOperators(CommandOperatorUtils
						.getOperatorsForType(type));
			}
			opPreferencesList.add(operatorPreferences);
		}
		applicationPreferences.setOperatorPreferences(opPreferencesList);
	}

	@Override
	public void onFailure(HttpFailureEvent event) {
		boolean validEvent = false;
		if (event.getUrl().indexOf(
				ServerEndpoints.RMS_GET_APPLICATION_PREFERENCES.getURL()) != -1) {
			validEvent = true;
		} else if (event.getUrl().indexOf(
				ServerEndpoints.RMS_UPDATE_APPLICATION_PREFERENCES.getURL()) != -1) {
			validEvent = true;
		} else if (event
				.getUrl()
				.indexOf(
						ServerEndpoints.RMS_CHECK_APPLICATION_PREFERENCES_PROTLET_PERMISSION
								.getURL()) != -1) {
			validEvent = true;
			setPortletPremission(false);
			WebStudio.get().createWebStudioPages(WebStudio.get().getUser());
		}
		
		if (validEvent) {
			ArtifactUtil.removeHandlers(this);
		}
	}

	/**
	 * Method to get default operator list for fieldType.
	 * 
	 * @param fieldType
	 * @param operatorType
	 * @return
	 */
	public List<IBuilderOperator> getDefaultOperatorsList(String fieldType,
			String operatorType) {
		List<IBuilderOperator> operatorsList = new ArrayList<IBuilderOperator>();
		FieldType type = getType(fieldType);
		if (operatorType.equals(FILTER_OPERATOR)) {
			operatorsList = FilterOperatorUtils.getDefaultOperators(type);
		} else {
			operatorsList = CommandOperatorUtils.getDefaultOperators(type);
		}
		return operatorsList;
	}

	/**
	 * Method to get operator preference for the given fieldType and for the
	 * given operator type.
	 * 
	 * @param appPreferences
	 * @param fieldType
	 * @param operatorType
	 * @return
	 */
	public List<IBuilderOperator> getOperatorPreference(
			ApplicationPreferences appPreferences, String fieldType,
			String operatorType) {
		List<IBuilderOperator> operatorsList = new ArrayList<IBuilderOperator>();
		if (appPreferences != null) {
			List<OperatorPreferences> operatorPreferences = appPreferences
					.getOperatorPreferences();
			if (operatorPreferences != null) {
				for (OperatorPreferences opPreferences : operatorPreferences) {
					if (opPreferences.getFieldType().equals(fieldType)) {
						if (operatorType.equals(FILTER_OPERATOR)) {
							operatorsList = opPreferences.getFilterOperators();
						} else {
							operatorsList = opPreferences.getCommandOperators();
						}
					}
				}
			}
		}
		return operatorsList;
	}

	/**
	 * Method to get operators for the given fieldType and operator type.
	 * 
	 * @param fieldType
	 * @param operatorType
	 * @return
	 */
	public List<IBuilderOperator> getOperators(FieldType fieldType,
			String operatorType) {
		String rtiFieldType = null;
		if (fieldType != null) {
			rtiFieldType = fieldType.getValue();
		}
		return getOperatorPreference(WebStudio.get()
				.getApplicationPreferences(), getFieldType(rtiFieldType),
				operatorType);
	}

	/**
	 * Method to get the operator preference map for the given operator type and
	 * field Type.
	 * 
	 * @param operatorType
	 * @param fieldLabel
	 * @param fieldType
	 * @return
	 */
	public Map<IBuilderOperator, Boolean> getOperatorsMap(String operatorType,
			String fieldLabel, String fieldType) {
		List<IBuilderOperator> operatorPreferenceList = null;
		Map<IBuilderOperator, Boolean> operatorsMap = new LinkedHashMap<IBuilderOperator, Boolean>();
		if (fieldLabel != null) {
			operatorPreferenceList = ApplicationPreferenceHelper.getInstance()
					.getOperatorPreference(applicationPreferences, fieldLabel,
							operatorType);
		}
		List<IBuilderOperator> defaultOperatorList = ApplicationPreferenceHelper
				.getInstance().getDefaultOperatorsList(fieldType, operatorType);

		// If the operator preference list is not empty mark other operators as
		// false and add to the map.
		if (operatorPreferenceList != null && operatorPreferenceList.size() > 0) {
			for (IBuilderOperator defaultOperator : defaultOperatorList) {
				if (operatorPreferenceList.contains(defaultOperator)) {
					operatorsMap.put(defaultOperator, true);
				} else {
					operatorsMap.put(defaultOperator, false);
				}
			}
		} else {
			for (IBuilderOperator operator : defaultOperatorList) {
				operatorsMap.put(operator, true);
			}
		}
		return operatorsMap;
	}

	/**
	 * Method to get the fieldT Type
	 * 
	 * @param rtiFieldType
	 * @return
	 */
	private String getFieldType(String rtiFieldType) {
		if (rtiFieldType == "custom") {
			rtiFieldType = null;
		}
		String fieldType = null;
		RTIFieldType[] values = RTIFieldType.values();
		for (RTIFieldType type : values) {
			String value = type.getValue();
			if (value == null && rtiFieldType == null) {
				fieldType = RTIFieldType.CONCEPT_EVENT.getDisplayText();
				break;
			} else {
				if (type.getValue().equals(rtiFieldType)) {
					fieldType = type.getDisplayText();
					break;
				}
			}
		}
		return fieldType;
	}

	/**
	 * Method to get field type.
	 * 
	 * @param type
	 * @return
	 */
	public FieldType getType(String type) {
		FieldType[] fieldTypes = FieldType.values();
		for (FieldType field : fieldTypes) {
			if (field.getValue().equals(type)) {
				return field;
			}
		}
		return null;
	}

	/**
	 * Method to update operator preference when user checks/unchecks operator
	 * checkboxes.
	 * 
	 * @param fieldType
	 * @param operator
	 * @param operatorType
	 * @param toAdd
	 */
	public void updateOperatorPreference(String fieldType, String operator,
			String operatorType, boolean toAdd) {
		List<OperatorPreferences> operatorPreferences = applicationPreferences
				.getOperatorPreferences();
		OperatorPreferences opPreferences = null;
		if (operatorPreferences.size() > 0) {
			for (OperatorPreferences opPreference : operatorPreferences) {
				if (opPreference.getFieldType().equals(fieldType)) {
					opPreferences = opPreference;
					if (toAdd) {
						if (operatorType.equals(FILTER_OPERATOR)) {
							opPreferences.getFilterOperators().add(
									FilterOperator.fromValue(operator));
						} else {
							opPreferences.getCommandOperators().add(
									CommandOperator.fromValue(operator));
						}
					} else {
						if (operatorType.equals(FILTER_OPERATOR)) {
							opPreferences.getFilterOperators().remove(
									FilterOperator.fromValue(operator));
						} else {
							opPreferences.getCommandOperators().remove(
									CommandOperator.fromValue(operator));
						}
					}
					break;
				}
			}
		}
	}

	/**
	 * Save/update Application Preferences
	 * 
	 * @param applicationPreferences
	 */
	public void updateApplicationPreferences() {
		ApplicationPreferenceDataItem appPrefenceDataItem = new ApplicationPreferenceDataItem(
				applicationPreferences);

		Map<String, Object> requestParameters = new HashMap<String, Object>();
		requestParameters.put(RequestParameter.REQUEST_ARTIFACT_DATA_ITEM,
				appPrefenceDataItem);

		@SuppressWarnings({ "rawtypes", "unchecked" })
		String xmlData = new SerializeArtifact().generateXML(
				ServerEndpoints.RMS_UPDATE_APPLICATION_PREFERENCES,
				requestParameters);

		HttpRequest request = new HttpRequest();
		request.clearParameters();
		request.setPostData(xmlData);
		request.setMethod(HttpMethod.PUT);

		ArtifactUtil.addHandlers(this);

		request.submit(ServerEndpoints.RMS_UPDATE_APPLICATION_PREFERENCES
				.getURL());
	}

	/**
	 * Method to check for application preference portlet view permission.
	 * 
	 * @return
	 */
	public static boolean hasPortletPremission() {
		return portletPremission;
	}

	/**
	 * Method to set the application preference portlet view permission.
	 * 
	 * @param portletPremission
	 */
	public static void setPortletPremission(boolean portletPremission) {
		ApplicationPreferenceHelper.portletPremission = portletPremission;
	}

	public Map<String, String> getFieldTypeValueIconMap() {
		if (iconMap == null) {
			iconMap = new LinkedHashMap<String, String>();
			iconMap.put("String", iconPath + "iconString16.gif");
			iconMap.put("integer", iconPath + "iconInteger16.gif");
			iconMap.put("long", iconPath + "iconLong16.gif");
			iconMap.put("double", iconPath + "iconReal16.gif");
			iconMap.put("boolean", iconPath + "iconBoolean16.gif");
			iconMap.put("Concept/Event", iconPath + "iconConcept16.gif");
			iconMap.put("DateTime", iconPath + "iconDate16.gif");
		}
		return iconMap;
	}

}
