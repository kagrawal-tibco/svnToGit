package com.tibco.cep.studio.dashboard.core.insight.model.configs;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalECoreFactory;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class LocalHeader extends LocalConfig {

	private static final String THIS_TYPE = BEViewsElementNames.HEADER;

	public static final String PROP_KEY_HEADER_IMAGE = "Image URL";

	public static final String ELEMENT_KEY_HEADER_LINK = BEViewsElementNames.HEADER_LINK;

	public LocalHeader() {
		super(THIS_TYPE);
	}

	public LocalHeader(LocalElement parentElement, BEViewsElement mdElement) {
		super(parentElement, THIS_TYPE, mdElement);
	}

	public LocalHeader(LocalElement parentElement, String name) {
		super(parentElement, THIS_TYPE, name);
	}

	@Override
	public LocalElement createLocalElement(String childrenType) {
		LocalElement localElement = super.createLocalElement(childrenType);
		if (ELEMENT_KEY_HEADER_LINK.equals(childrenType) == true) {
			localElement.setPropertyValue(LocalHeaderLink.PROP_KEY_URL_NAME, "Link");
		}
		return localElement;
	}

	@Override
	protected void validateProperty(SynProperty prop) throws Exception {
		super.validateProperty(prop);
		if (PROP_KEY_HEADER_IMAGE.equals(prop.getName()) == true) {
			String propertyValue = getPropertyValue(PROP_KEY_HEADER_IMAGE);
			if (propertyValue != null && propertyValue.trim().length() != 0) {
				if (((LocalECoreFactory) getRoot()).getProject().findMember(propertyValue) == null) {
					addValidationErrorMessage("Non existent image file specified for " + PROP_KEY_HEADER_IMAGE);
				}
			}
		}
	}
}