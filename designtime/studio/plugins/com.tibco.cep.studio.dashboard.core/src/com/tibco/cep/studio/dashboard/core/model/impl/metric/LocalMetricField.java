package com.tibco.cep.studio.dashboard.core.model.impl.metric;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.METRIC_AGGR_TYPE;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.ElementPackage;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.core.adapters.ConceptAdapter;
import com.tibco.cep.studio.dashboard.core.enums.InternalStatusEnum;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynOptionalProperty;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynBooleanType;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynLongType;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynStringType;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalEntity;
import com.tibco.cep.studio.dashboard.core.model.impl.attribute.LocalAttribute;

public class LocalMetricField extends LocalAttribute {

	public static final Logger LOGGER = Logger.getLogger(LocalMetricField.class.getName());

	public static final String PROP_KEY_IS_GROUP_BY = PROP_KEY_PREFIX + "GroupBy";

	public static final String PROP_KEY_GROUP_BY_POSITION = PROP_KEY_PREFIX + "GroupByPosition";

	public static final String PROP_KEY_AGG_FUNCTION = PROP_KEY_PREFIX + "AggregationFunction";

	public static final String PROP_KEY_DEP_FIELD = PROP_KEY_PREFIX + "DependentFields";

	public static final String PROP_KEY_IS_TIME_WINDOW = PROP_KEY_PREFIX + "TimeWindow";

	public static String[] EXPOSED_AGGREGATION_TYPES = {
		METRIC_AGGR_TYPE.SET.getLiteral(),
		METRIC_AGGR_TYPE.MINIMUM.getLiteral(),
		METRIC_AGGR_TYPE.MAXIMUM.getLiteral(),
		METRIC_AGGR_TYPE.COUNT.getLiteral(),
		METRIC_AGGR_TYPE.SUM.getLiteral(),
		METRIC_AGGR_TYPE.AVERAGE.getLiteral(),
		METRIC_AGGR_TYPE.STANDARD_DEVIATION.getLiteral(),
		METRIC_AGGR_TYPE.VARIANCE.getLiteral()
		//METRIC_AGGR_TYPE.SUM_OF_SQUARES.getLiteral()
	};

	public static String[] AGGREGATE_NON_SET_SUPPORTED_DATA_TYPES = {
		PROPERTY_TYPES.INTEGER.getLiteral(),
		PROPERTY_TYPES.LONG.getLiteral(),
		PROPERTY_TYPES.DOUBLE.getLiteral()
	};

	private Map<METRIC_AGGR_TYPE, LocalMetricField> dependentFields;

	public LocalMetricField() {
		super();
	}

	public LocalMetricField(LocalEntity parentElement, String name) {
		super(parentElement, name);
		try {
			long groupByPos = ((LocalMetric) parentElement).getMaxGroupByPosition();
			if (groupByPos == 0) {
				setGroupBy(true);
				setGroupByPosition(groupByPos);
				setDataType(PROPERTY_TYPES.STRING.getLiteral());
			} else {
				setGroupBy(false);
				setDataType(PROPERTY_TYPES.INTEGER.getLiteral());
			}
		} catch (Exception e) {
			getLogger().log(Level.SEVERE, e.getMessage(), e);
		}
	}

	public LocalMetricField(LocalEntity parentElement, Entity mdElement) {
		super(parentElement, mdElement);
	}

	// ---------------
	// LocalAttribute
	// ---------------

	/**
	 * Overridden to allow internal status to be changed even when this field
	 * has already been removed. This is needed to support the case where the
	 * user removes a field and then "undoes" the operation.
	 */
	public void setInternalStatus(InternalStatusEnum newInternalStatus, boolean synchAllChildren) {
		InternalStatusEnum internalStatus = getInternalStatus();
		/*
		 * Only set the 'Modified' status if the element is not new because new
		 * elements, when modified, are still considered new.
		 */
		if (true == newInternalStatus.equals(InternalStatusEnum.StatusModified)) {
			if (false == internalStatus.equals(InternalStatusEnum.StatusNew)) {
				internalSetInternalStatus(newInternalStatus);
				propagateStatus(newInternalStatus, synchAllChildren);
			}
		} else {
			internalSetInternalStatus(newInternalStatus);
			propagateStatus(newInternalStatus, synchAllChildren);
		}

		/*
		 * Only notify if the status is not "new"
		 */
		if (false == newInternalStatus.equals(InternalStatusEnum.StatusNew)) {
			fireStatusChanged(this, getInternalStatus());
		}
	}

	public void setupProperties() {
		super.setupProperties();
		addProperty(this, new SynOptionalProperty(PROP_KEY_IS_SYSTEM, new SynBooleanType(), Boolean.FALSE.toString()));
		addProperty(this, new SynOptionalProperty(PROP_KEY_IS_GROUP_BY, new SynBooleanType(), Boolean.FALSE.toString()));
		// Need to take care of the auto-generated BDR fields.
		// Their group by position is -2 and -1.
		addProperty(this, new SynOptionalProperty(PROP_KEY_GROUP_BY_POSITION, new SynLongType("-2", null), "0"));
		// Default AggregationFuncEnum is not yet in Schema.
		addProperty(this, new SynOptionalProperty(PROP_KEY_AGG_FUNCTION, new SynStringType(EXPOSED_AGGREGATION_TYPES), METRIC_AGGR_TYPE.SET.getLiteral()));
		addProperty(this, new SynOptionalProperty(PROP_KEY_IS_TIME_WINDOW, new SynBooleanType(), Boolean.FALSE.toString()));
		/*
		 * For dependent fields
		 */
		addProperty(this, new SynOptionalProperty(PROP_KEY_DEP_FIELD, new SynStringType(), ""));
		dependentFields = new HashMap<METRIC_AGGR_TYPE, LocalMetricField>();
	}

	public void parseMDProperty(String propertyName) {
		if (true == super.isSuperProperty(propertyName)) {
			super.parseMDProperty(propertyName);
			return;
		}
		if (getEObject() instanceof PropertyDefinition) {
			PropertyDefinition mdField = (PropertyDefinition) getEObject();
			if (true == PROP_KEY_IS_GROUP_BY.equals(propertyName)) {
				setGroupBy(mdField.isGroupByField());
			} else if (true == PROP_KEY_GROUP_BY_POSITION.equals(propertyName)) {
				setGroupByPosition(mdField.getGroupByPosition());
			} else if (true == PROP_KEY_AGG_FUNCTION.equals(propertyName)) {
				if (null != mdField.getAggregationType()) {
					setAggregationFunction(mdField.getAggregationType().getLiteral());
				}
			} else if (true == PROP_KEY_IS_TIME_WINDOW.equals(propertyName)) {
				setTimeWindowField(mdField.isTimeWindowField());
			} else if (true == PROP_KEY_DEP_FIELD.equals(propertyName)){
				Map<String, String> valuesMap = getExtendedPropertyValuesMap(mdField, "dependentfield.*");
				if (valuesMap != null && valuesMap.isEmpty() == false) {
					for (String type : valuesMap.keySet()) {
						String dependentFieldName = valuesMap.get(type);
						LocalMetricField dependentField = (LocalMetricField) getParent().getElement(LocalMetric.ELEMENT_KEY_FIELD, dependentFieldName.trim(), LocalElement.FOLDER_NOT_APPLICABLE);
						//we set the field directly to avoid triggering the setting
						//of the depending field string property
						//repeat setting of depending field will mark the editor
						//as dirty
						dependentFields.put(METRIC_AGGR_TYPE.getByName(type), dependentField);
					}
					setDependingFieldString(generateDependingFieldString());
				}
			}
		}
	}

	public Object cloneThis() {
		return new LocalMetricField();
	}

	/*
	 * Saves the local properties into the remote MDAttribute that is sent in as
	 * input
	 */
	protected void synchronizeElement(EObject eObject) {
		if (eObject instanceof PropertyDefinition) {
			PropertyDefinition mdField = (PropertyDefinition) eObject;
			/*
			 * Effectively nullifies these properties and only set them when
			 * appropriate below in synchronizeChildren(...)
			 */
			mdField.eUnset(ElementPackage.eINSTANCE.getPropertyDefinition_GroupByField());
			mdField.eUnset(ElementPackage.eINSTANCE.getPropertyDefinition_GroupByPosition());
			mdField.eUnset(ElementPackage.eINSTANCE.getPropertyDefinition_TimeWindowField());
			mdField.eUnset(ElementPackage.eINSTANCE.getPropertyDefinition_AggregationType());
			//remove dependent field values if any
			deleteExtendedPropertyValues(mdField,"dependentfield.");
			deleteExtendedPropertyValue(mdField, "supportedfield");
		}
		super.synchronizeElement(eObject);
	}

	protected void synchronizeChildren(EObject eObject) {
		if (eObject instanceof PropertyDefinition) {

			PropertyDefinition mdField = (PropertyDefinition) eObject;
			/*
			 * 0. If it's a system field
			 */
			if (true == isSystem()){
				setExtendedPropertyValue(mdField, "system", "true");
			}
			/*
			 * 1. If it's a group by
			 */
			if (true == isGroupBy()) {
				mdField.setGroupByField(true);
				mdField.setGroupByPosition(getGroupByPosition());
			}
			/*
			 * 2. If it's an aggregation
			 */
			else if ((null != getAggregationFunction()) && ("" != getAggregationFunction())) {
				mdField.setAggregationType(METRIC_AGGR_TYPE.getByName(getAggregationFunction()));
				/*
				 * Add/remove depending fields as required
				 */
				if (true == requiresDependingFields()) {
					for (Entry<METRIC_AGGR_TYPE,LocalMetricField> entry : getDependingFields().entrySet()) {
						setExtendedPropertyValue(mdField, "dependentfield."+entry.getKey(), entry.getValue().getName());
					}
				}
			}
			/*
			 * 3. If it's a time window
			 */
			else if (isTimeWindowField() == true) {
				mdField.setTimeWindowField(true);
			}
		}
	}

	public boolean isValid() throws Exception {
		if (false == super.isValid()) {
			return false;
		}
		//check field name
		if (ConceptAdapter.METRIC_DVM_PARENT_ID_NAME.equals(getName()) == true) {
			addValidationErrorMessage(getName()+" is an internal reserved name");
		}
		if (null != getParent() && true == isTimeWindowField()) {
			List<LocalElement> siblings = getParentParticle().getElements();
			for (Iterator<LocalElement> iter = siblings.iterator(); iter.hasNext();) {
				LocalMetricField element = (LocalMetricField) iter.next();

				if (false == element.equals(this) && true == element.isTimeWindowField()) {
					String message = "Field [" + getName() + "] is not a unique Time Window field.\nThere should be only 1 field set as Time Window.";
					LOGGER.log(Level.FINEST, message);
					addValidationErrorMessage(message);
				}
			}
		}
		try {
			if (true == isTimeWindowAllowed()) {
				if (false == isTimeWindowField() && false == isGroupBy() && null == getAggregationFunction()) {
					String message = "Field [" + getName() + "] must be specified as Group By, Time Window, or has an Aggregation Type.";
					LOGGER.log(Level.FINEST, message);
					addValidationErrorMessage(message);
				}
			} else {

				if (false == isGroupBy() && null == getAggregationFunction()) {
					String message = "Field [" + getName() + "] must be specified as Group By or has an Aggregation Type.";
					LOGGER.log(Level.FINEST, message);
					addValidationErrorMessage(message);
				}
			}
		} catch (Exception e) {
			getLogger().log(Level.SEVERE, e.getMessage(), e);
		}

		if ((false == isGroupBy() && false == isTimeWindowField()) && null != getAggregationFunction() && requiresDependingFields(getAggregationFunction())) {
			checkDependencyFields(false);
		} else if (true == isGroupBy() && false == isGroupByPositionUnique()) {
			String message = "Field [" + getName() + "] does not have a unique Group By Position.";
			LOGGER.log(Level.FINEST, message);
			addValidationErrorMessage(message);
		}
		return null == getValidationMessage();
	}

	public LocalElement createLocalElement() {
		return null;
	}

	/**
	 * There is no versionable element child. No need to subscribe MDXXXX.
	 */
	@Override
	public Entity createMDChild(LocalElement localElement) {
		return null;
	}

	public void deleteMDChild(LocalElement localElement) {
	}

	public boolean checkDependencyFields() {
		return checkDependencyFields(true);
	}

	public boolean checkDependencyFields(boolean clearMessage) {
		String aggregationFunction;
		try {
			aggregationFunction = getAggregationFunction();
		} catch (Exception e) {
			addValidationErrorMessage("could not determine aggregation type of "+getName());
			return false;
		}
		return checkDependencyFields(getDependentAggregrateTypes(aggregationFunction), clearMessage);
	}

	public boolean checkDependencyFields(List<METRIC_AGGR_TYPE> referenceDependencies,boolean clearMessage) {
		/*
		 * Clears out any existing messages
		 */
		if (true == clearMessage) {
			setValidationMessage(null);
		}
		String aggregationFunction;
		try {
			aggregationFunction = getAggregationFunction();
		} catch (Exception e) {
			addValidationErrorMessage("could not determine aggregation type of "+getName());
			return false;
		}
		if (referenceDependencies.isEmpty() == true){
			return true;
		}
		for (LocalElement dependentField : dependentFields.values()) {
			//check if the user is reusing the same field as dependent field
			if (Collections.frequency(dependentFields.values(), dependentField) > 1){
				addValidationErrorMessage(getName()+" has been assigned "+dependentField.getName()+" as a auxiliary field more than once");
				return false;
			}
		}
		List<METRIC_AGGR_TYPE> requiredAggregations = new LinkedList<METRIC_AGGR_TYPE>(referenceDependencies);
		Collections.sort(requiredAggregations);
		List<METRIC_AGGR_TYPE> existingAggregations = new LinkedList<METRIC_AGGR_TYPE>(dependentFields.keySet());
		Collections.sort(existingAggregations);
		if (requiredAggregations.equals(existingAggregations) == true){
			//we are good to go
			return true;
		}
		addValidationErrorMessage(getName()+"'s aggregate type ["+aggregationFunction+"] needs auxiliary fields of type "+requiredAggregations+", found "+(existingAggregations.isEmpty() == true ? "none" : existingAggregations));
		return false;
	}

	public boolean isGroupByPositionUnique() {
		List<LocalElement> siblings = getParentParticle().getElements();
		for (Iterator<LocalElement> iter = siblings.iterator(); iter.hasNext();) {
			LocalMetricField sibling = (LocalMetricField) iter.next();
			if ((false == sibling.equals(this)) && (true == sibling.isGroupBy()) && (sibling.getGroupByPosition() == getGroupByPosition())) {
				return false;
			}
		}

		return true;
	}

	public boolean isTimeWindowAllowed() {
		return ((LocalMetric) getParent()).isTimeWindowAllowed();
	}

	public void setGroupByPosition(long groupByPosition) {
		setGroupByPosition(groupByPosition, false);
	}

	public void setGroupByPosition(long groupByPosition, boolean swapPosition) {
		if (true == swapPosition) {
			for (Iterator<LocalElement> iter = getParentParticle().getElements().iterator(); iter.hasNext();) {
				LocalMetricField element = (LocalMetricField) iter.next();

				if (false == element.equals(this) && true == element.isGroupBy()) {

					if (element.getGroupByPosition() == groupByPosition) {
						element.setGroupByPosition(getGroupByPosition(), true);
						element.setModified();
						break;
					}
				}
			}
			setPropertyValue(PROP_KEY_GROUP_BY_POSITION, groupByPosition + "");
			((LocalMetric) getParent()).normalizeGroupByPosition();
		} else {
			setPropertyValue(PROP_KEY_GROUP_BY_POSITION, groupByPosition + "");
		}
	}

	public boolean requiresDependingFields() {
		return requiresDependingFields(getAggregationFunction());
	}

	public boolean requiresDependingFields(String enumStr) {
		if (enumStr == null || enumStr.trim().length() == 0){
			return false;
		}
		//average
		if (enumStr.equals(METRIC_AGGR_TYPE.AVERAGE.getLiteral()) == true){
			return true;
		}
		//standard deviation
		if (enumStr.equals(METRIC_AGGR_TYPE.STANDARD_DEVIATION.getLiteral()) == true){
			return true;
		}
		//variance
		if (enumStr.equals(METRIC_AGGR_TYPE.VARIANCE.getLiteral()) == true){
			return true;
		}
		return false;
	}

	// ==================================================================
	// The following methods are convenience API's that delegates
	// to the reflection style API's for accessing attribute values
	// ==================================================================

	public String getDependingFieldString() {
		return getPropertyValue(PROP_KEY_DEP_FIELD);
	}

	private void setDependingFieldString(String dependingFieldString) {
		setPropertyValue(PROP_KEY_DEP_FIELD,dependingFieldString);
	}

	private String generateDependingFieldString() {
		//force load the elements
		StringBuilder sb = new StringBuilder();
		try {
			for (METRIC_AGGR_TYPE exposedAggrType : getExposedDependentAggregrateTypes()) {
				LocalMetricField dependentField = dependentFields.get(exposedAggrType);
				if (dependentField.isSystem() == false){
					if (sb.length() > 0){
						sb.append(",");
					}
					sb.append(dependentField.getName());
				}
			}
		} catch (Exception ignoreEx) {
			//INFO ignoring getExposedDependentAggregrateTypes() exception in generateDependingFieldString()
		}
		return sb.toString();
	}

	public void addDependingField(METRIC_AGGR_TYPE designatedType, LocalMetricField value) {
		dependentFields.put(designatedType, value);
		setDependingFieldString(generateDependingFieldString());
	}

	public Map<METRIC_AGGR_TYPE,LocalMetricField> getDependingFields() {
		return Collections.unmodifiableMap(dependentFields);
	}

	public void setDependingFields(Map<METRIC_AGGR_TYPE,LocalMetricField> dependingFields) {
		dependentFields.clear();
		for (METRIC_AGGR_TYPE designatedType : dependingFields.keySet()) {
			addDependingField(designatedType,dependingFields.get(designatedType));
		}
		setDependingFieldString(generateDependingFieldString());
	}

	public METRIC_AGGR_TYPE removeDependingField(LocalMetricField localElement) {
		for (Entry<METRIC_AGGR_TYPE, LocalMetricField> entry : dependentFields.entrySet()) {
			if (entry.getValue().equals(localElement) == true){
				dependentFields.remove(entry.getKey());
				setDependingFieldString(generateDependingFieldString());
				return entry.getKey();
			}
		}
		return null;
	}

	public LocalElement removeDependingField(METRIC_AGGR_TYPE aggregateType) {
		LocalElement localElement = dependentFields.remove(aggregateType);
		if (localElement != null){
			setDependingFieldString(generateDependingFieldString());
		}
		return localElement;
	}

	public List<METRIC_AGGR_TYPE> getDependentAggregrateTypes() {
		return getDependentAggregrateTypes(getAggregationFunction());
	}

	public List<METRIC_AGGR_TYPE> getDependentAggregrateTypes(String aggregateType){
		METRIC_AGGR_TYPE aggregateTypeEnum = METRIC_AGGR_TYPE.getByName(aggregateType);
		if (aggregateTypeEnum == null) {
			return Collections.emptyList();
		}
		switch (aggregateTypeEnum.getValue()) {
			case METRIC_AGGR_TYPE.AVERAGE_VALUE:
				return Arrays.asList(new METRIC_AGGR_TYPE[] { METRIC_AGGR_TYPE.SUM, METRIC_AGGR_TYPE.COUNT });
			case METRIC_AGGR_TYPE.STANDARD_DEVIATION_VALUE:
				return Arrays.asList(new METRIC_AGGR_TYPE[] { METRIC_AGGR_TYPE.SUM, METRIC_AGGR_TYPE.COUNT, METRIC_AGGR_TYPE.SUM_OF_SQUARES });
			case METRIC_AGGR_TYPE.VARIANCE_VALUE:
				return Arrays.asList(new METRIC_AGGR_TYPE[] { METRIC_AGGR_TYPE.SUM, METRIC_AGGR_TYPE.COUNT, METRIC_AGGR_TYPE.SUM_OF_SQUARES });
			default:
				return Collections.emptyList();
		}
	}

	public List<METRIC_AGGR_TYPE> getExposedDependentAggregrateTypes() {
		return getExposedDependentAggregrateTypes(getAggregationFunction());
	}

	public List<METRIC_AGGR_TYPE> getExposedDependentAggregrateTypes(String aggregateType){
		METRIC_AGGR_TYPE aggregateTypeEnum = METRIC_AGGR_TYPE.getByName(aggregateType);
		if (aggregateTypeEnum == null) {
			return Collections.emptyList();
		}
		switch (aggregateTypeEnum.getValue()) {
			case METRIC_AGGR_TYPE.AVERAGE_VALUE:
				return Arrays.asList(new METRIC_AGGR_TYPE[] { METRIC_AGGR_TYPE.SUM, METRIC_AGGR_TYPE.COUNT });
			case METRIC_AGGR_TYPE.STANDARD_DEVIATION_VALUE:
				return Arrays.asList(new METRIC_AGGR_TYPE[] { METRIC_AGGR_TYPE.SUM, METRIC_AGGR_TYPE.COUNT, METRIC_AGGR_TYPE.SUM_OF_SQUARES });
			case METRIC_AGGR_TYPE.VARIANCE_VALUE:
				return Arrays.asList(new METRIC_AGGR_TYPE[] { METRIC_AGGR_TYPE.SUM, METRIC_AGGR_TYPE.COUNT, METRIC_AGGR_TYPE.SUM_OF_SQUARES });
			default:
				return null;
		}
	}


	public boolean isTimeWindowField() {
		return getPropertyValue(PROP_KEY_IS_TIME_WINDOW).equalsIgnoreCase("true");
	}

	/**
	 * @param timeWindowField
	 */
	public void setTimeWindowField(boolean value) {
		setPropertyValue(PROP_KEY_IS_TIME_WINDOW, value + "");
		/*
		 * If this is set to true then set all others to false there can only be
		 * 1 field set as time window field
		 */
		if (true == value && false == getParent().isBulkOperation()) {
			for (Iterator<LocalElement> iter = ((LocalMetric) getParent()).getFields().iterator(); iter.hasNext();) {
				LocalMetricField element = (LocalMetricField) iter.next();
				if (false == element.equals(this) && element.isTimeWindowField()) {
					element.setPropertyValue(PROP_KEY_IS_TIME_WINDOW, "false");
					setModified();
				}
			}
		}
	}

	public long getGroupByPosition() {
		return Long.parseLong(getPropertyValue(PROP_KEY_GROUP_BY_POSITION));
	}

	public boolean isGroupBy() {
		return getPropertyValue(PROP_KEY_IS_GROUP_BY).equalsIgnoreCase("true");
	}

	public void setGroupBy(boolean value) {
		setPropertyValue(PROP_KEY_IS_GROUP_BY, value + "");
	}

	public String getAggregationFunction() {
		return getPropertyValue(PROP_KEY_AGG_FUNCTION);
	}

	public void setAggregationFunction(String value) {
		setPropertyValue(PROP_KEY_AGG_FUNCTION, value);
	}

	@Override
	public String getElementType() {
		return "MetricField";
	}

	public String[] getSupportedDataTypes() {
		if (isGroupBy()) {
			return SUPPORTED_DATA_TYPES;
		} else {
			String aggregationFunction = getAggregationFunction();
			if (aggregationFunction != null && aggregationFunction.equals(METRIC_AGGR_TYPE.SET.getLiteral()) == false) {
				// There is a function which is not a set, return filter list
				return AGGREGATE_NON_SET_SUPPORTED_DATA_TYPES;
			} else {
				// Else return all data types
				return SUPPORTED_DATA_TYPES;
			}
		}
	}

	public int getSupportedDataTypeIndexOf(String dataType) {
		String[] dataTypes = getSupportedDataTypes();
		for (int i = 0; i < dataTypes.length; i++) {
			if (dataTypes[i].equals(dataType)) {
				return i;
			}
		}
		return -1;
	}

	public String getSupportedDataTypeAtIndex(int dataTypeIndex) {
		return getSupportedDataTypes()[dataTypeIndex];
	}

	public String getDefaultSupportedDataType() {
		return getSupportedDataTypeAtIndex(0);
	}

	public boolean hasValidDataType() {
		return (getSupportedDataTypeIndexOf(getDataType()) != -1);
	}

	public boolean isNumeric(String dataType) {
		if (PROPERTY_TYPES.INTEGER.getLiteral().equals(dataType) == true) {
			return true;
		}
		if (PROPERTY_TYPES.LONG.getLiteral().equals(dataType) == true) {
			return true;
		}
		if (PROPERTY_TYPES.DOUBLE.getLiteral().equals(dataType) == true) {
			return true;
		}
		return false;
	}

	public String[] getSupportedAggregateFuncs() {
		if (isNumeric(getDataType()) == true){
			return EXPOSED_AGGREGATION_TYPES;
		}
		return new String[]{METRIC_AGGR_TYPE.SET.getLiteral()};
	}

	public int getSupportedAggregateFuncsIndexOf(String aggrFunc) {
		String[] aggregateFuncs = getSupportedAggregateFuncs();
		for (int i = 0; i < aggregateFuncs.length; i++) {
			if (aggregateFuncs[i].equals(aggrFunc) == true){
				return i;
			}
		}
		return -1;
	}

	public String getSupportedAggregationFuncAtIndex(int aggrFuncIndex) {
		return getSupportedAggregateFuncs()[aggrFuncIndex];
	}

	public String getDefaultSupportedAggregationFunc() {
		return getSupportedAggregationFuncAtIndex(0);
	}

}