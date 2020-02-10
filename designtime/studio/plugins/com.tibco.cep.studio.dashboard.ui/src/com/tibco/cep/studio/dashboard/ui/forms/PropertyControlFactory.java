package com.tibco.cep.studio.dashboard.ui.forms;


import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.XSD.components.SynXSDRestrictionSimpleTypeDefinition;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDSimpleTypeDefinition;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynBooleanType;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynDescriptionType;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynDoubleType;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynIntType;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynStringType;

//PATCH redesign the propertycontrols
public class PropertyControlFactory {

	private static PropertyControlFactory instance;

	public static final synchronized PropertyControlFactory getInstance() {
		if (instance == null) {
			instance = new PropertyControlFactory();
		}
		return instance;
	}

	private PropertyControlFactory() {
	}

	public PropertyControl createPropertyControl(SimplePropertyForm parentForm, String displayName, SynProperty property) {
		ISynXSDSimpleTypeDefinition typeDefinition = property.getTypeDefinition();
		if (typeDefinition instanceof SynXSDRestrictionSimpleTypeDefinition) {
			SynXSDRestrictionSimpleTypeDefinition restrictingTypeDefinition = (SynXSDRestrictionSimpleTypeDefinition) property.getTypeDefinition();
			boolean hasEnumerations = !restrictingTypeDefinition.getEnumerations().isEmpty();
			if (typeDefinition instanceof SynStringType) {
				if (hasEnumerations == false) {
					if (typeDefinition instanceof SynDescriptionType) {
						return createTextPropertyControl(parentForm, displayName, property, true);
					}
					return createTextPropertyControl(parentForm, displayName, property, false);
				}
				return createComboPropertyControl(parentForm, displayName, property);
			}
			// if (typeDefinition instanceof SynBooleanType){
			// return getCheckBoxPropertyControl(parentForm, property);
			// }
			if (typeDefinition instanceof SynIntType) {
				return createSpinnerPropertyControl(parentForm, displayName, property);
			}
			if (typeDefinition instanceof SynDoubleType) {
				return createSpinnerPropertyControl(parentForm, displayName, property);
			}
			if (typeDefinition instanceof SynBooleanType) {
				return createCheckBoxPropertyControl(parentForm, displayName, property);
			}
		}
		throw new IllegalArgumentException("No property control found for " + property.getName());
	}

	PropertyControl createTextPropertyControl(SimplePropertyForm parentForm, String displayName, SynProperty property, boolean multiLine) {
		return new TextPropertyControl(parentForm, displayName, property, multiLine);
	}

	PropertyControl createComboPropertyControl(SimplePropertyForm parentForm, String displayName, SynProperty property) {
		return new ComboPropertyControl(parentForm, displayName, property);
	}

	PropertyControl createSpinnerPropertyControl(SimplePropertyForm parentForm, String displayName, SynProperty property) {
		SynXSDRestrictionSimpleTypeDefinition typeDefinition = (SynXSDRestrictionSimpleTypeDefinition) property.getTypeDefinition();
		int precision = 0;
		if (typeDefinition instanceof SynDoubleType) {
			int minPrecision = getPrecision(typeDefinition.getMinInclusive());
			int maxPrecision = getPrecision(typeDefinition.getMaxInclusive());
			if (minPrecision != maxPrecision) {
				throw new IllegalArgumentException(property.getName() + " has unequal precision for min and max values");
			}
			precision = minPrecision;
		}
		return new SpinnerPropertyControl(parentForm, displayName, property, precision);
	}

	private int getPrecision(String value) {
		int dotIdx = value.indexOf(".");
		if (dotIdx == -1) {
			return 0;
		}
		return value.length() - dotIdx - 1;
	}

	PropertyControl createCheckBoxPropertyControl(SimplePropertyForm parentForm, String displayName, SynProperty property) {
		return new CheckBoxPropertyControl(parentForm, displayName, property);
	}
}
