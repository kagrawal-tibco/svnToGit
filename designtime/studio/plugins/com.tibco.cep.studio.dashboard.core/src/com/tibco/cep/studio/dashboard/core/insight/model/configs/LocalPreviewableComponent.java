package com.tibco.cep.studio.dashboard.core.insight.model.configs;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;

public abstract class LocalPreviewableComponent extends LocalComponent {


	public LocalPreviewableComponent(LocalElement parentElement, String configType, BEViewsElement mdElement) {
		super(parentElement, configType, mdElement);
	}

	public LocalPreviewableComponent(LocalElement parentConfig, String configType, String name) {
		super(parentConfig, configType, name);
	}

	public LocalPreviewableComponent(String configType) {
		super(configType);
	}

	@Override
	public void parseMDProperty(String propertyName) {
		if ("DesignCategories".equals(propertyName) == true) {
			Entity categories = getExtendedProperty(getEObject(), getPropertyHelper(propertyName).leaf);
			if (categories != null) {
				Map<String, String> actualCategories = getExtendedPropertyValuesMap(categories, "*");
				((SynProperty) getProperty(propertyName)).setValues(new LinkedList<String>(actualCategories.keySet()));
			}
		} else if ("MaxValue".equals(propertyName) == true) {
			String value = getExtendedPropertyValue(getEObject(), getPropertyHelper(propertyName).leaf);
			if (value != null && value.trim().length() != 0) {
				setPropertyValue(propertyName, value);
			}
		} else if ("MinValue".equals(propertyName) == true) {
			String value = getExtendedPropertyValue(getEObject(), getPropertyHelper(propertyName).leaf);
			if (value != null && value.trim().length() != 0) {
				setPropertyValue(propertyName, value);
			}
		}
		else {
			super.parseMDProperty(propertyName);
		}
	}

	@Override
	protected void synchronizeProperty(BEViewsElement BEViewsElement, SynProperty propertyTemplate) {
		String propertyName = propertyTemplate.getName();
		if ("DesignCategories".equals(propertyName) == true) {
			SynProperty property = (SynProperty) getProperty(propertyName);
			List<String> values = property.getValues();
			if (values.size() != 1 || values.contains(property.getDefault()) == false) {
				Entity categories = createExtendedProperty(BEViewsElement, getPropertyHelper(propertyName).leaf);
				for (String category : values) {
					setExtendedPropertyValue(categories, category, null);
				}
			}
		} else if ("MaxValue".equals(propertyName) == true) {
			if (isPropertyValueSameAsDefault(propertyName) == false) {
				setExtendedPropertyValue(BEViewsElement, getPropertyHelper(propertyName).leaf, getPropertyValue(propertyName));
			}
		} else if ("MinValue".equals(propertyName) == true && isPropertyValueSameAsDefault(propertyName) == false) {
			if (isPropertyValueSameAsDefault(propertyName) == false) {
				setExtendedPropertyValue(BEViewsElement, getPropertyHelper(propertyName).leaf, getPropertyValue(propertyName));
			}
		}
		else {
			super.synchronizeProperty(BEViewsElement,propertyTemplate);
		}
	}

}