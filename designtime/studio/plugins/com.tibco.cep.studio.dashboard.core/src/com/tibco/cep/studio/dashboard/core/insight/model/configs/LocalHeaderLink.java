package com.tibco.cep.studio.dashboard.core.insight.model.configs;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;

public class LocalHeaderLink extends LocalConfig {

	public static final String PROP_KEY_URL_NAME = LocalElement.PROP_KEY_PREFIX + "URLName";

	public static final String PROP_KEY_URL_LINK = LocalElement.PROP_KEY_PREFIX + "URLLink";
	
	public LocalHeaderLink(LocalElement parentElement, String insightType, BEViewsElement BEViewsElement) {
		super(parentElement, insightType, BEViewsElement);
	}

	public LocalHeaderLink(LocalElement parentElement, String insightType, String name) {
		super(parentElement, insightType, name);
	}

	public LocalHeaderLink(LocalElement parentElement, String insightType) {
		super(parentElement, insightType);
	}

	public LocalHeaderLink(String insightType) {
		super(insightType);
	}

	public String getURLName() throws Exception {
		return getPropertyValue(PROP_KEY_URL_NAME);
	}
	
	public void setURLName(String value) throws Exception {
		setPropertyValue(PROP_KEY_URL_NAME, value);
	}
	
	public String getURLLink() throws Exception {		
		return getPropertyValue(PROP_KEY_URL_LINK);
	}

	public void setURLLink(String value) throws Exception {
		setPropertyValue(PROP_KEY_URL_LINK, value);
	}
	
	public String getDisplayableName() {
		try {
			if(!("".equals(getURLName()))){
				return "Header Link: "+getURLName();
			}
			else if(!("".equals(getURLLink()))){
				return "Header Link: "+getURLLink();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Header Link";
	}	
}