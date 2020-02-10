package com.tibco.cep.studio.dashboard.core.model.impl.metric;

import java.util.logging.Level;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalEntity;
import com.tibco.cep.studio.dashboard.core.model.impl.attribute.LocalAttribute;

public class LocalMetricUserDefinedField extends LocalAttribute {

	private static String[] REFERENCE_TYPES = {
		"Event",
		"Concept",
		"Metric"
	};

	public static String[] SUPPORTED_DATA_TYPES = {
		PROPERTY_TYPES.STRING.getLiteral(),
		PROPERTY_TYPES.INTEGER.getLiteral(),
		PROPERTY_TYPES.LONG.getLiteral(),
		PROPERTY_TYPES.DOUBLE.getLiteral(),
		PROPERTY_TYPES.BOOLEAN.getLiteral(),
		PROPERTY_TYPES.DATE_TIME.getLiteral(),
//		"Event Reference",
//		"Concept Reference",
//		"Metric Reference"
	};

	//INFO 6 is hard-coded based on property type above reference types see SUPPORTED_DATA_TYPES/REFERENCE_TYPES
	private static int REFERENCE_START_INDEX = 6;

	public LocalMetricUserDefinedField() {
		super();
	}

	public LocalMetricUserDefinedField(LocalEntity parentElement, String name) {
		super(parentElement, name);
		try {
			setDataType(PROPERTY_TYPES.STRING.getLiteral());
		} catch (Exception e) {
			getLogger().log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public LocalMetricUserDefinedField(LocalEntity parentElement, Entity entity) {
		super(parentElement, entity);
	}

	@Override
	public void parseMDProperty(String propertyName) {
		if (propertyName.equals(PROP_KEY_DATA_TYPE) == true){
			String referenceType = getExtendedPropertyValue(getEObject(), "referencetype");
			if (referenceType != null && referenceType.trim().length() != 0){
				for (int i = 0; i < REFERENCE_TYPES.length; i++) {
					if (REFERENCE_TYPES[i].equals(referenceType) == true){
						setDataType(SUPPORTED_DATA_TYPES[REFERENCE_START_INDEX+i]);
						return;
					}
				}
			}
		}
		super.parseMDProperty(propertyName);
	}

	@Override
	protected void synchronizeElement(EObject mdElement) {
		//get the original data-type
		String dataType = getDataType();
		try {
			//check if user has set a reference data type
			String referenceType = getReferenceType(dataType);
			if (referenceType != null){
				//yes, we have a reference data type
				//lets set the actual data type to be LONG
				setDataType(PROPERTY_TYPES.LONG.getLiteral());
			}
			//we will let LocalAttribute synchronize all values
			super.synchronizeElement(mdElement);
			//now we set/unset the reference type extended property
			Entity entity = (Entity) mdElement;
			deleteExtendedPropertyValue(entity, "referencetype");
			if (referenceType != null && referenceType.trim().length() != 0){
				setExtendedPropertyValue(entity, "referencetype", referenceType);
			}
		} finally {
			//we revert back the data-type
			setDataType(dataType);
		}
	}

	protected String getReferenceType(String dataType){
		for (int i = REFERENCE_START_INDEX; i < SUPPORTED_DATA_TYPES.length; i++) {
			if (SUPPORTED_DATA_TYPES[i].equals(dataType) == true){
				return REFERENCE_TYPES[i-REFERENCE_START_INDEX];
			}
		}
		return null;
	}

	@Override
	public Object cloneThis() throws Exception {
		return new LocalMetricUserDefinedField();
	}

	@Override
	public String getElementType() {
		return "MetricUserDefinedField";
	}

	public String toString() {
		try {
			return super.toString() + "[" + getName() + "/" + getInternalStatus() + "]";
		} catch (Exception e) {
			getLogger().log(Level.SEVERE, e.getMessage(), e);
		}
		return super.toString();
	}

	@Override
	public String[] getSupportedDataTypes() {
		return SUPPORTED_DATA_TYPES;
	}
}
