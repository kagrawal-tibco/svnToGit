package com.tibco.cep.studio.dashboard.core.insight.model.configs;

import java.text.ParseException;

import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.dashboard.core.exception.SynValidationErrorMessage;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.query.BEViewsQueryDateTypeInterpreter;
import com.tibco.cep.studio.dashboard.core.variables.VariableExpression;
import com.tibco.cep.studio.dashboard.core.variables.VariableParser;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class LocalQueryParam extends LocalConfig {

	private static final String THIS_TYPE = BEViewsElementNames.QUERY_PARAM;

	public static final String PROP_KEY_TYPE = "Type";

	public static final String PROP_KEY_VALUE = "Value";

	public LocalQueryParam() {
		super(THIS_TYPE);
	}

	public LocalQueryParam(LocalElement parentElement, String insightType, BEViewsElement mdElement) {
		super(parentElement, THIS_TYPE, mdElement);
	}

	public LocalQueryParam(LocalElement parentElement, BEViewsElement mdElement) {
		super(parentElement, THIS_TYPE, mdElement);
	}

	public LocalQueryParam(LocalElement parentElement, String name) {
		super(parentElement, THIS_TYPE, name);
	}

	public String getDataType() {
		return getPropertyValueWithNoException(PROP_KEY_TYPE);
	}

	public String getValue() {
		return getPropertyValueWithNoException(PROP_KEY_VALUE);
	}

	public void setDataType(String dataType) {
		setPropertyValueWithNoException(PROP_KEY_TYPE, dataType);
	}

	public void setValue(String value) {
		setPropertyValueWithNoException(PROP_KEY_VALUE, value);
	}

	@Override
	public boolean isValid() throws Exception {
		setValidationMessage(null);
		if (getParent() instanceof LocalDataSource) {
			return super.isValid();
		}
		if (isPropertyValueSameAsDefault(PROP_KEY_VALUE) == false) {
			String value = getValue();
			try {
				VariableExpression variableExpression = VariableParser.getInstance().parse(value);
				if (variableExpression == null || variableExpression.getVariables().isEmpty() == true) {
					PROPERTY_TYPES dataType = PROPERTY_TYPES.get(getDataType());
					switch (dataType) {
						case STRING:
							return true;
						case INTEGER:
							try {
								Integer.parseInt(value);
								return true;
							} catch (IllegalArgumentException e) {
								addValidationMessage(new SynValidationErrorMessage("Invalid integer value for " + getName() + " in " + getParent().getParent().getDisplayableName()));
								return false;
							}
						case LONG:
							try {
								Long.parseLong(value);
								return true;
							} catch (IllegalArgumentException e) {
								addValidationMessage(new SynValidationErrorMessage("Invalid long value for " + getName() + " in " + getParent().getParent().getDisplayableName()));
								return false;
							}
						case DOUBLE:
							try {
								Double.parseDouble(value);
								return true;
							} catch (IllegalArgumentException e) {
								addValidationMessage(new SynValidationErrorMessage("Invalid double value for " + getName() + " in " + getParent().getParent().getDisplayableName()));
								return false;
							}
						case BOOLEAN:
							return true;
						case DATE_TIME:
							//PATCH We are using isValidBindValue to maintian backward compatibility, we should replace it with isValidDate
							boolean validBindValue = BEViewsQueryDateTypeInterpreter.isValidBindValue(value);
							if (validBindValue == false) {
								addValidationMessage(new SynValidationErrorMessage("Invalid datetime value for " + getName() + " in " + getParent().getParent().getDisplayableName()));
							}
							return validBindValue;
						default:
							// do nothing
					}
				}
			} catch (ParseException e) {
				addValidationMessage(new SynValidationErrorMessage(e.getMessage() + " in value for " + getName() + " in " + getParent().getParent().getDisplayableName()));
				return false;
			}
		}
		return super.isValid();
	}


}
