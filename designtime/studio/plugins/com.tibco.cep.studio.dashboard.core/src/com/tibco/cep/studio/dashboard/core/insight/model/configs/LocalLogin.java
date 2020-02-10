package com.tibco.cep.studio.dashboard.core.insight.model.configs;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class LocalLogin extends LocalConfig {

	private static final String THIS_TYPE = BEViewsElementNames.LOGIN;

	public static final String PROP_KEY_LOGIN_IMAGE = "Image URL";

	public LocalLogin() {
		super(THIS_TYPE);
	}

	public LocalLogin(LocalElement parentElement, String name) {
		super(parentElement, THIS_TYPE, name);
	}

	public LocalLogin(LocalElement parentElement, BEViewsElement mdElement) {
		super(parentElement, THIS_TYPE, mdElement);
	}

	@Override
	protected void validateProperty(SynProperty prop) throws Exception {
		super.validateProperty(prop);
		if (PROP_KEY_LOGIN_IMAGE.equals(prop.getName()) == true) {
			String propertyValue = getPropertyValue(PROP_KEY_LOGIN_IMAGE);
			if (propertyValue != null && propertyValue.trim().length() != 0) {
				if (((LocalECoreFactory)getRoot()).getProject().findMember(propertyValue) == null) {
					addValidationErrorMessage("Non existent image file specified for "+PROP_KEY_LOGIN_IMAGE);
				}
			}
		}
	}

}
