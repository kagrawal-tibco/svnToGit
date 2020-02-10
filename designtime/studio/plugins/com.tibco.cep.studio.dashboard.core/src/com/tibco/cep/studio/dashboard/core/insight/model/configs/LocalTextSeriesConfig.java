package com.tibco.cep.studio.dashboard.core.insight.model.configs;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class LocalTextSeriesConfig extends LocalTwoDimSeriesConfig {

	private static final String PROPS_KEY_INDICATOR_PREFIX = "Indicator";

	private static final String PROPS_KEY_TEXT_PREFIX = "Text";

	private static final String NONE = "";

	private String propertyNamePrefixToSkip = NONE;

	public LocalTextSeriesConfig() {
		super(BEViewsElementNames.TEXT_SERIES_CONFIG);
	}

	public LocalTextSeriesConfig(LocalElement parentElement) {
		super(parentElement, BEViewsElementNames.TEXT_SERIES_CONFIG);
	}

	public LocalTextSeriesConfig(LocalElement parentElement, String name) {
		super(parentElement, BEViewsElementNames.TEXT_SERIES_CONFIG, name);
	}

	public LocalTextSeriesConfig(LocalElement parentElement, BEViewsElement mdElement) {
		super(parentElement, BEViewsElementNames.TEXT_SERIES_CONFIG, mdElement);
	}

	@Override
	protected synchronized void synchronize(EObject entity) {
		try {
			if (isPropertyValueSameAsDefault("TextValueColumnField") == true){
				propertyNamePrefixToSkip = PROPS_KEY_TEXT_PREFIX;
			}
			else if (isPropertyValueSameAsDefault("IndicatorValueColumnField") == true){
				propertyNamePrefixToSkip = PROPS_KEY_INDICATOR_PREFIX;
			}
			else {
				throw new IllegalStateException("No valid value field in "+getDisplayableName());
			}
			super.synchronize(entity);
		} finally {
			propertyNamePrefixToSkip = NONE;
		}
	}

	@Override
	protected void synchronizeProperty(BEViewsElement BEViewsElement, SynProperty propertyTemplate) {
		String name = propertyTemplate.getName();
		if (propertyNamePrefixToSkip != NONE && name.startsWith(propertyNamePrefixToSkip) == true){
			//skip the sync
			return;
		}
		super.synchronizeProperty(BEViewsElement, propertyTemplate);
	}

	@Override
	public boolean isValid() throws Exception {
		try {
			if (areAllPropertyValueSameAsDefault(PROPS_KEY_TEXT_PREFIX) == true){
				propertyNamePrefixToSkip = PROPS_KEY_TEXT_PREFIX;
			}
			else if (areAllPropertyValueSameAsDefault(PROPS_KEY_INDICATOR_PREFIX) == true){
				propertyNamePrefixToSkip = PROPS_KEY_INDICATOR_PREFIX;
			}
			else {
				throw new IllegalStateException("No valid value field in "+getDisplayableName());
			}
			return super.isValid();
		} finally {
			propertyNamePrefixToSkip = NONE;
		}
	}

	private boolean areAllPropertyValueSameAsDefault(String prefix) throws Exception{
		for (String propertyName : getPropertyNames()) {
			if (propertyName.startsWith(prefix) == true) {
//				System.out.println("LocalTextSeriesConfig.areAllPropertyValueSameAsDefault("+prefix+")::isPropertyValueSameAsDefault("+propertyName+")="+isPropertyValueSameAsDefault(propertyName));
				if (isPropertyValueSameAsDefault(propertyName) == false){
					return false;
				}
			}
		}
		return true;
	}

	@Override
	protected boolean isToBeValidated(SynProperty prop) {
		String name = prop.getName();
		if (propertyNamePrefixToSkip != NONE && name.startsWith(propertyNamePrefixToSkip) == true){
			//skip the validate
			return false;
		}
		return true;
	}

	@Override
	protected String getCategoryDisplayLabelPropertyName() {
		return "CategoryColumnFieldDisplayFormat";
	}

	@Override
	protected String getCategoryFieldPropertyName() {
		return "CategoryColumnField";
	}

	@Override
	protected List<Object> getValueFieldEnumeration() {
		LocalDataSource dataSource = (LocalDataSource) getElement(BEViewsElementNames.ACTION_RULE).getElement(BEViewsElementNames.DATA_SOURCE);
		return dataSource.getEnumerations(LocalDataSource.ENUM_FIELD);
	}

	@Override
	protected String getValueFieldPropertyName() {
		if (propertyNamePrefixToSkip == PROPS_KEY_TEXT_PREFIX) {
			return "IndicatorValueColumnField";
		}
		return "TextValueColumnField";
	}

	@Override
	protected String getValueDisplayLabelPropertyName() {
		if (propertyNamePrefixToSkip == PROPS_KEY_TEXT_PREFIX) {
			return "IndicatorValueColumnFieldDisplayFormat";
		}
		return "TextValueColumnFieldDisplayFormat";
	}

	@Override
	protected String getTooltipPropertyName() {
		if (propertyNamePrefixToSkip == PROPS_KEY_TEXT_PREFIX) {
			return "IndicatorValueColumnFieldTooltipFormat";
		}
		return "TextValueColumnFieldTooltipFormat";
	}

	@Override
	protected List<Object> getCategoryFieldEnumeration() {
		LocalDataSource dataSource = (LocalDataSource) getElement(BEViewsElementNames.ACTION_RULE).getElement(BEViewsElementNames.DATA_SOURCE);
		return dataSource.getEnumerations(LocalDataSource.ENUM_GROUP_BY_FIELD);
	}
}