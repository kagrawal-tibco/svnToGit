package com.tibco.cep.studio.dashboard.core.insight.model.configs;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.designtime.core.model.beviewsconfig.DataExtractor;
import com.tibco.cep.designtime.core.model.beviewsconfig.OneDimSeriesConfig;
import com.tibco.cep.studio.dashboard.core.enums.InternalStatusEnum;
import com.tibco.cep.studio.dashboard.core.exception.SynValidationErrorMessage;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.insight.model.helpers.LocalPropertyConfig;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.attribute.LocalAttribute;
import com.tibco.cep.studio.dashboard.core.util.DisplayFormatParser;
import com.tibco.cep.studio.dashboard.core.util.DisplayValueFormat;
import com.tibco.cep.studio.dashboard.core.util.DisplayValueFormatProvider;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public abstract class LocalSeriesConfig extends LocalConfig {

	public LocalSeriesConfig(LocalElement parentElement, String configType, BEViewsElement mdElement) {
		super(parentElement, configType, mdElement);
	}

	public LocalSeriesConfig(LocalElement parentElement, String configType, String name) {
		super(parentElement, configType, name);
	}

	public LocalSeriesConfig(LocalElement parentElement, String configType) {
		super(parentElement, configType);
	}

	public LocalSeriesConfig(String configType) {
		super(configType);
	}

	@Override
	public void parseMDProperty(String propertyName) {
		LocalPropertyConfig propertyHelper = getPropertyHelper(propertyName);
		if (propertyHelper != null && propertyHelper.isElementRef == true){
			EObject propertyContainer = configHelper.getEObject(this, getEObject(), propertyHelper, false);
			if (propertyContainer == null){
				setPropertyValue(propertyName, null);
				return;
			}
			EObject field = extractValue(propertyContainer);
			if (field == null) {
				setPropertyValue(propertyName, null);
				return;
			}
			if (field.eIsProxy()) {
				field = EcoreUtil.resolve(field, getEObject());
			}
			if (field instanceof Entity) {
				String fieldName = ((Entity) field).getName();
				setPropertyValue(propertyName, fieldName);
			}
		}
		else {
			super.parseMDProperty(propertyName);
		}
	}

	protected EObject extractValue(EObject propertyContainer) {
		if (propertyContainer instanceof DataExtractor) {
			DataExtractor dataExtractor = (DataExtractor) propertyContainer;
			return dataExtractor.getSourceField();
		}
		return null;
	}

	@Override
	protected void preSynchronizeElement(BEViewsElement mdElement) {
		//FIXME Horrible hack to reset all value data configs in series config (to ease transition between field formats)
		//the reason we check for isNew is because ChartEditingController recreates a text component so it will be new
		if (isNew() == true || isModified() == true) {
			((OneDimSeriesConfig)mdElement).getValueDataConfig().clear();
		}
	}

	@Override
	protected void synchronizeProperty(BEViewsElement BEViewsElement, SynProperty propertyTemplate) {
		String propertyName = propertyTemplate.getName();
		SynProperty property = (SynProperty) getProperty(propertyName);
//		System.out.println("LocalSeriesConfig.synchronizeProperty("+property+")");
		if (property.getInternalStatus().equals(InternalStatusEnum.StatusRemove) == true) {
			return;
		}
		if (propertyName.endsWith("Field") == true) {
			LocalPropertyConfig propertyHelper = configReader.getPropertyHelper(getInsightType(), propertyName);
			String rawValue = property.getValues().get(0);
			if (isPropertyValueSameAsDefault(propertyName) == false) {
				LocalDataSource dataSource = (LocalDataSource) getElement(BEViewsElementNames.ACTION_RULE).getElement(BEViewsElementNames.DATA_SOURCE);
				if (dataSource != null) {
					LocalElement field = dataSource.getFieldByName(LocalDataSource.ENUM_FIELD, rawValue);
					if (field != null) {
						configHelper.setPropertyValue(this, BEViewsElement, propertyHelper, field.getEObject());
					} /*else {
						configHelper.setPropertyValue(this, BEViewsElement, propertyHelper, null);
					}*/
				}
			}
			return;
		}
		super.synchronizeProperty(BEViewsElement, propertyTemplate);
	}

	@Override
	protected void validateProperty(SynProperty prop) throws Exception {
		if (isToBeValidated(prop) == false) {
			return;
		}
		//let the base validation framework do rudimentary validation
		super.validateProperty(prop);
		String name = prop.getName();
		if (getElement(BEViewsElementNames.ACTION_RULE).getElement(BEViewsElementNames.DATA_SOURCE) == null) {
			//LocalActionRule should handle missing data source
			return;
		}
		else if (name.equals(getValueFieldPropertyName()) == true) {
			validateValueField(name);
		}
		else if (name.equals(getValueDisplayLabelPropertyName()) == true) {
			validateValueDisplayLabel(name);
		}
		else if (name.equals(getTooltipPropertyName()) == true) {
			validateTooltip(name);
		}
	}

	protected abstract boolean isToBeValidated(SynProperty prop);

	protected abstract String getValueFieldPropertyName();

	protected abstract List<Object> getValueFieldEnumeration();

	private void validateValueField(String propertyName) throws Exception {
		String valueField = getPropertyValue(propertyName);
		if (valueField == null || valueField.trim().length() == 0) {
			//this will be handled by the base validation framework
			return;
		}
		if (getValueFieldEnumeration().contains(valueField) == false){
			addValidationMessage(new SynValidationErrorMessage("Unknown value field '"+valueField+"' in "+getDisplayableName(), getURI()));
			return;
		}
	}

	protected abstract String getValueDisplayLabelPropertyName();

	protected void validateValueDisplayLabel(String propertyName) throws Exception {
		LocalDataSource dataSource = (LocalDataSource) getElement(BEViewsElementNames.ACTION_RULE).getElement(BEViewsElementNames.DATA_SOURCE);
		String fieldName = getPropertyValue(getValueFieldPropertyName());
		LocalElement field = dataSource.getFieldByName(LocalDataSource.ENUM_FIELD, fieldName);
		if (field == null) {
			//validateValueField() should handle missing category field
			return;
		}
		String fieldDataType = field.getPropertyValue(LocalAttribute.PROP_KEY_DATA_TYPE);
		String currDisplayLabel = getPropertyValue(propertyName);
		DisplayValueFormat currDisplayValueFormat = DisplayValueFormatProvider.parse(fieldName, fieldDataType, currDisplayLabel);
		if (currDisplayValueFormat == null) {
			//corrupt format or mismatch in datatype
			addValidationMessage(new SynValidationErrorMessage("Incorrect datatype in value field format in "+getDisplayableName(), getURI()));
		}
		else {
			//check if current value field is actually used
			Collection<String> arguments = getArguments(currDisplayLabel);
			//remove all arguments matching all the fields
			arguments.removeAll(dataSource.getEnumerations(LocalDataSource.ENUM_FIELD));
			if (arguments.isEmpty() == false) {
				addValidationMessage(new SynValidationErrorMessage("Incorrect value field format in "+getDisplayableName(), getURI()));
			}
//			String displayValueFormat = currDisplayValueFormat.getDisplayValueFormat(fieldName, currDisplayValueFormat.getPattern(currDisplayLabel));
//			if (displayValueFormat.equals(currDisplayLabel) == false){
//				addValidationErrorMessage("Incorrect value field format in "+getDisplayableName());
//			}
		}
	}

	protected abstract String getTooltipPropertyName();

	protected void validateTooltip(String propertyName) throws Exception {
		LocalDataSource dataSource = (LocalDataSource) getElement(BEViewsElementNames.ACTION_RULE).getElement(BEViewsElementNames.DATA_SOURCE);
		String tooltipFormat = getPropertyValue(propertyName);
		Collection<String> arguments = getArguments(tooltipFormat);
		//remove all arguments matching all the fields
		arguments.removeAll(dataSource.getEnumerations(LocalDataSource.ENUM_FIELD));
		if (arguments.isEmpty() == false) {
			//we have unwanted fields
			addValidationMessage(new SynValidationErrorMessage("Invalid field(s) "+arguments+" in value tooltip format in "+getDisplayableName(), getURI()));
		}
	}

	protected Collection<String> getArguments(String format) {
		if (format == null || format.trim().length() == 0){
			return Collections.emptyList();
		}
		return new DisplayFormatParser(format).getArguments();
	}


}