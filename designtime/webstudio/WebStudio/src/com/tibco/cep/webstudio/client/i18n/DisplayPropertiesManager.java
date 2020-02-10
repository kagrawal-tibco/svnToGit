package com.tibco.cep.webstudio.client.i18n;

import java.util.HashMap;
import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.tibco.cep.webstudio.client.http.HttpFailureEvent;
import com.tibco.cep.webstudio.client.http.HttpFailureHandler;
import com.tibco.cep.webstudio.client.http.HttpRequest;
import com.tibco.cep.webstudio.client.http.HttpSuccessEvent;
import com.tibco.cep.webstudio.client.http.HttpSuccessHandler;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.preferences.RoleConfigurationHelper;
import com.tibco.cep.webstudio.client.preferences.UserPreferenceHelper;
import com.tibco.cep.webstudio.client.portal.RoleConfigurationPortlet;
import com.tibco.cep.webstudio.client.util.ArtifactUtil;
import com.tibco.cep.webstudio.client.util.ServerEndpoints;
import com.tibco.cep.webstudio.model.rule.instance.DisplayModel;
import com.tibco.cep.webstudio.model.rule.instance.DisplayProperty;
import com.tibco.cep.webstudio.model.rule.instance.impl.DisplayModelImpl;
import com.tibco.cep.webstudio.model.rule.instance.impl.DisplayPropertyImpl;

public class DisplayPropertiesManager implements HttpSuccessHandler, HttpFailureHandler {

	private static DisplayPropertiesManager instance;
	private boolean onLogin;
	private HashMap<String, HashMap<String, DisplayModel>> projNameToDisplayPropsMap = new HashMap<String, HashMap<String, DisplayModel>>();
	
	public static DisplayPropertiesManager getInstance() {
		if (instance == null) {
			instance = new DisplayPropertiesManager();
		}
		return instance;
	}
	
	private void loadDisplayProperties(String projectName) {
		ArtifactUtil.addHandlers(this);

		HttpRequest request = new HttpRequest();
		if (projectName != null && !projectName.isEmpty()) {
			request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_PROJECT_NAME, projectName));
		}
		String locale = Window.Location.getParameter("locale");
		if (locale == null) {//TODO: also handle case if language pack for region is not available.
			locale = "en_US";
		}
		request.addRequestParameters(new RequestParameter(RequestParameter.REQUEST_LOCALE_CODE, locale));
		request.submit(ServerEndpoints.RMS_GET_DISPLAY_PROPERTIES.getURL());
	}
	
	public HashMap<String, DisplayModel> getDisplayProperties() {
		return getDisplayProperties(null);
	}

	public HashMap<String, DisplayModel> getDisplayProperties(String projectName) {
		HashMap<String,DisplayModel> displayProperties = projNameToDisplayPropsMap.get(projectName);
		if (displayProperties != null) {
			return displayProperties;
		} else {
			loadDisplayProperties(projectName);
		}
		return null;
	}
	
	public DisplayModel getDisplayModel(String projectName, String entityPath) {
		HashMap<String,DisplayModel> displayProperties = getDisplayProperties(projectName);
		if (displayProperties != null) {
			return displayProperties.get(entityPath);
		}
		return null;
	}

	@Override
	public void onFailure(HttpFailureEvent event) {
		boolean validEvent = false;

		if (event.getUrl().equals(ServerEndpoints.RMS_GET_DISPLAY_PROPERTIES.getURL())) {
			validEvent = true;
		}

		if (validEvent) {
			ArtifactUtil.removeHandlers(this);
		}
	}

	@Override
	public void onSuccess(HttpSuccessEvent event) {
		boolean validEvent = false;

		if (event.getUrl().indexOf(ServerEndpoints.RMS_GET_DISPLAY_PROPERTIES.getURL()) != -1) {
			HashMap<String, DisplayModel> props = new HashMap<String, DisplayModel>();
			Element docElement = event.getData();
			NodeList displayElements = docElement.getElementsByTagName("project");
			for (int i = 0; i < displayElements.getLength(); i++) {
				NodeList dispPropDetails = displayElements.item(i).getChildNodes();
				for (int j = 0; j < dispPropDetails.getLength(); j++) {
					if (!dispPropDetails.item(j).toString().trim().isEmpty()) {
						Node item = dispPropDetails.item(j);
						String nodeName = item.getNodeName();
						if (nodeName.equals("projectName")) {
							String projName = item.getFirstChild().getNodeValue();
							projNameToDisplayPropsMap.put(projName, props);
							continue;
						}
						parseDisplayProperty(item, props);
					}
				}
			}
			validEvent = true;
		}

		if (validEvent) {
			ArtifactUtil.removeHandlers(this);
			if (onLogin) {
				UserPreferenceHelper.getInstance().getUserPreferences();
				onLogin = false;
			}
		}
	}

	private void parseDisplayProperty(Node item,
			HashMap<String, DisplayModel> props) {
		NodeList dispPropDetails = item.getChildNodes();
		DisplayModel dispModel = null;
		for (int i = 0; i < dispPropDetails.getLength(); i++) {
			if (!dispPropDetails.item(i).toString().trim().isEmpty()) {
				Node childItem = dispPropDetails.item(i);
				String nodeName = childItem.getNodeName();
				
				if ("propInfo".equals(nodeName)) {
					parsePropInfo(childItem, dispModel);
				} else if ("displayText".equals(nodeName)) {
					if (dispModel != null) {
						dispModel.setDisplayText(childItem.getFirstChild().getNodeValue());
					}
				} else if ("path".equals(nodeName)) {
					String entity = childItem.getFirstChild().getNodeValue();
					dispModel = props.get(entity);
					if (dispModel == null) {
						dispModel = new DisplayModelImpl();
						dispModel.setEntity(entity);
						props.put(entity, dispModel);
					}
				}
			}
		}
	}

	private void parsePropInfo(Node item, DisplayModel model) {
		NodeList propInfoDetails = item.getChildNodes();
		DisplayProperty prop = null;
		boolean baseDisplayText = false;
		for (int i=0; i<propInfoDetails.getLength(); i++) {
			if (!propInfoDetails.item(i).toString().trim().isEmpty()) {
				Node childItem = propInfoDetails.item(i);
				String nodeName = childItem.getNodeName();
				
				if ("key".equals(nodeName)) {
					String keyItem = childItem.getFirstChild().getNodeValue();
					int idx = keyItem.lastIndexOf('.');
					if (idx >= 0) {
						String propName = keyItem.substring(0, idx);
						prop = getDisplayProp(propName, model);
					} else if ("displayText".equals(keyItem)) {
						baseDisplayText = true;
					}
				} else if ("value".equals(nodeName)) {
					if (baseDisplayText && model != null) {
						model.setDisplayText(childItem.getFirstChild().getNodeValue());
					} else if (prop != null) {
						prop.setValue(childItem.getFirstChild().getNodeValue());
					}
				} else if ("hidden".equals(nodeName)) {
					if (prop != null) {
						prop.setHidden(Boolean.parseBoolean(childItem.getFirstChild().getNodeValue()));
					}
				}
			}
		}
	}

	private DisplayProperty getDisplayProp(String propName, DisplayModel model) {
		List<DisplayProperty> displayProperties = model.getDisplayProperties();
		for (DisplayProperty displayProperty : displayProperties) {
			if (displayProperty.getId().equals(propName)) {
				return displayProperty;
			}
		}
		DisplayProperty prop = new DisplayPropertyImpl();
		prop.setId(propName);
		model.getDisplayProperties().add(prop);
		return prop;
	}
	
	public boolean isOnLogin() {
		return onLogin;
	}

	public void setOnLogin(boolean onLogin) {
		this.onLogin = onLogin;
	}
}
