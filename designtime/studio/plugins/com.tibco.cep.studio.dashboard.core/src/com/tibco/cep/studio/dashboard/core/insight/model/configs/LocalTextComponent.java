package com.tibco.cep.studio.dashboard.core.insight.model.configs;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.dashboard.core.insight.model.helpers.LocalPropertyConfig;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class LocalTextComponent extends LocalPreviewableComponent {

	public LocalTextComponent() {
		super(BEViewsElementNames.TEXT_COMPONENT);
	}

	public LocalTextComponent(LocalElement parentElement, BEViewsElement mdElement) {
		super(parentElement, BEViewsElementNames.TEXT_COMPONENT, mdElement);
	}

	public LocalTextComponent(LocalElement parentConfig, String name) {
		super(parentConfig, BEViewsElementNames.TEXT_COMPONENT, name);
	}

	@Override
	public void parseMDProperty(String propertyName) {
		if ("subType".equals(propertyName) == true){
			LocalPropertyConfig helper = getPropertyHelper(propertyName);
			String subType = getExtendedPropertyValue(getEObject(), helper.leaf);
			if (subType != null && subType.trim().length() != 0){
				setPropertyValue(propertyName, subType);
			}
		}
		else {
			super.parseMDProperty(propertyName);
		}
	}

	@Override
	protected void synchronizeProperty(BEViewsElement BEViewsElement, SynProperty propertyTemplate) {
		if ("subType".equals(propertyTemplate.getName()) == true) {
			LocalPropertyConfig helper = getPropertyHelper(propertyTemplate.getName());
			String propertyValue = getPropertyValue("subType");
			if (propertyValue == null || propertyValue.trim().length() == 0){
				deleteExtendedPropertyValue(BEViewsElement, helper.leaf);
			}
			else {
				setExtendedPropertyValue(BEViewsElement, helper.leaf, propertyValue);
			}
		}
		else {
			super.synchronizeProperty(BEViewsElement, propertyTemplate);
		}
	}

}