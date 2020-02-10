package com.tibco.cep.studio.dashboard.core.insight.model.configs;

import java.util.Collection;
import java.util.List;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.attribute.LocalAttribute;
import com.tibco.cep.studio.dashboard.core.util.DisplayValueFormat;
import com.tibco.cep.studio.dashboard.core.util.DisplayValueFormatProvider;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public abstract class LocalTwoDimSeriesConfig extends LocalSeriesConfig {

	public LocalTwoDimSeriesConfig(LocalElement parentElement, String configType, BEViewsElement mdElement) {
		super(parentElement, configType, mdElement);
	}

	public LocalTwoDimSeriesConfig(LocalElement parentElement, String configType, String name) {
		super(parentElement, configType, name);
	}

	public LocalTwoDimSeriesConfig(LocalElement parentElement, String configType) {
		super(parentElement, configType);
	}

	public LocalTwoDimSeriesConfig(String configType) {
		super(configType);
	}

	@Override
	protected void validateProperty(SynProperty prop) throws Exception {
		super.validateProperty(prop);
		String name = prop.getName();
		if (getElement(BEViewsElementNames.ACTION_RULE).getElement(BEViewsElementNames.DATA_SOURCE) == null) {
			//LocalActionRule should handle missing data source
			return;
		}
		if (name.equals(getCategoryFieldPropertyName()) == true) {
			validateCategoryField(name);
		}
		else if (name.equals(getCategoryDisplayLabelPropertyName()) == true) {
			validateCategoryDisplayLabel(name);
		}
	}

	protected abstract String getCategoryFieldPropertyName();

	protected abstract List<Object> getCategoryFieldEnumeration();

	private void validateCategoryField(String propertyName) throws Exception {
		String categoryField = getPropertyValue(propertyName);
		if (categoryField == null || categoryField.trim().length() == 0) {
			//this will be handled by the base validation framework
			return;
		}
		if (getCategoryFieldEnumeration().contains(categoryField) == false){
			addValidationErrorMessage("Unknown category field '"+categoryField+"' in "+getDisplayableName());
			return;
		}
	}

	protected abstract String getCategoryDisplayLabelPropertyName();

	protected void validateCategoryDisplayLabel(String propertyName) throws Exception {
		LocalDataSource dataSource = (LocalDataSource) getElement(BEViewsElementNames.ACTION_RULE).getElement(BEViewsElementNames.DATA_SOURCE);
		String fieldName = getPropertyValue(getCategoryFieldPropertyName());
		LocalElement field = dataSource.getFieldByName(LocalDataSource.ENUM_FIELD, fieldName);
		if (field == null) {
			//validateCategoryField() should handle missing category field
			return;
		}
		//Modified by Anand to fix BE-11706 on 03/17/2011
		if (isPropertyValueSameAsDefault(propertyName) == true) {
			return;
		}
		String fieldDataType = field.getPropertyValue(LocalAttribute.PROP_KEY_DATA_TYPE);
		String currDisplayLabel = getPropertyValue(propertyName);
		DisplayValueFormat currDisplayValueFormat = DisplayValueFormatProvider.parse(fieldName, fieldDataType, currDisplayLabel);
		if (currDisplayValueFormat == null) {
			//corrupt format or mismatch in datatype
			addValidationErrorMessage("Incorrect datatype in category field format in "+getDisplayableName());
		}
		else {
			//check if current category field is actually used
			Collection<String> arguments = getArguments(currDisplayLabel);
			//remove all arguments matching all the fields
			arguments.removeAll(dataSource.getEnumerations(LocalDataSource.ENUM_FIELD));
			if (arguments.isEmpty() == false) {
				addValidationErrorMessage("Incorrect category field format in "+getDisplayableName());
			}
//			String displayValueFormat = currDisplayValueFormat.getDisplayValueFormat(fieldName, currDisplayValueFormat.getPattern(currDisplayLabel));
//			if (displayValueFormat.equals(currDisplayLabel) == false){
//				addValidationErrorMessage("Incorrect category field format in "+getDisplayableName());
//			}
		}
	}


}
