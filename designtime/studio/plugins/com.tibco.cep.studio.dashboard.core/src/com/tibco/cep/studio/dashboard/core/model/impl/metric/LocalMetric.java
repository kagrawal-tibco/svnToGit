package com.tibco.cep.studio.dashboard.core.model.impl.metric;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.METRIC_AGGR_TYPE;
import com.tibco.cep.designtime.core.model.METRIC_TYPE;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.TIME_UNITS;
import com.tibco.cep.designtime.core.model.element.ElementFactory;
import com.tibco.cep.designtime.core.model.element.Metric;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.dashboard.core.enums.InternalStatusEnum;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalDataSource;
import com.tibco.cep.studio.dashboard.core.listeners.IMessageProvider;
import com.tibco.cep.studio.dashboard.core.listeners.ISynElementChangeListener;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynOptionalProperty;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynBooleanType;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynLongType;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynStringType;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalEntity;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;
import com.tibco.cep.studio.dashboard.core.model.impl.classifier.LocalClassifier;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class LocalMetric extends LocalClassifier {

	private static final String SYS_FIELD_PREFIX = "_SY_";

	public static final String PROP_KEY_IS_PERSISTENT = PROP_KEY_PREFIX + "Persistent";

	public static final String PROP_KEY_RETENTION_TIME_SIZE = PROP_KEY_PREFIX + "RetentionTimeSize";

	public static final String PROP_KEY_RETENTION_TIME_TYPE = PROP_KEY_PREFIX + "RetentionTimeType";

	public static final String PROP_KEY_IS_ENABLED = PROP_KEY_PREFIX + "Enabled";

	public static final String PROP_KEY_METRIC_TYPE = PROP_KEY_PREFIX + "MetricType";

	public static final String PROP_KEY_WINDOW_TIME_TYPE = PROP_KEY_PREFIX + "WindowTimeType";

	public static final String PROP_KEY_WINDOW_SIZE = PROP_KEY_PREFIX + "WindowSize";

	public static final String PROP_KEY_RECUR_TIME_TYPE = PROP_KEY_PREFIX + "RecurringTimeType";

	public static final String PROP_KEY_RECUR_FREQ = PROP_KEY_PREFIX + "RecurringFrequency";

	public static final String PROP_KEY_START_TIME = PROP_KEY_PREFIX + "StartTime";

	public static final String PROP_KEY_IS_TRANS_ROLLING = PROP_KEY_PREFIX + "TransactionalRolling";

	public static final String ELEMENT_KEY_USER_DEFINED_FIELD = PROP_KEY_PREFIX + "UserDefinedField";

	public static String[] SUPPORTED_METRIC_TYPES = {
        METRIC_TYPE.REGULAR.getName(),
        METRIC_TYPE.ROLLING_TIME.getName(),
//         METRIC_TYPE.ROLLING_COUNT.getName(),
//        METRIC_TYPE.PERIODIC_TIME.getName(),
//        METRIC_TYPE.PERIODIC_COUNT.getName(),
//		 METRIC_TYPE.WINDOW_TIME.getName(),
//		 METRIC_TYPE.WINDOW_COUNT.getName(),
	};

	public static String[] SUPPORTED_METRIC_WINDOW_TIME_TYPES = {
		TIME_UNITS.SECONDS.getName(),
		TIME_UNITS.MINUTES.getName(),
		TIME_UNITS.HOURS.getName(),
		TIME_UNITS.DAYS.getName(),
		TIME_UNITS.WEEK_DAYS.getName(),
		TIME_UNITS.WEEKS.getName(),
		TIME_UNITS.MONTHS.getName(),
		TIME_UNITS.YEARS.getName()
	};

	public static String[] SUPPORTED_METRIC_RECURRING_TIME_TYPES = {
        TIME_UNITS.SECONDS.getName(),
        TIME_UNITS.MINUTES.getName(),
        TIME_UNITS.HOURS.getName(),
        TIME_UNITS.DAYS.getName(),
        TIME_UNITS.WEEK_DAYS.getName(),
        TIME_UNITS.WEEKS.getName(),
        TIME_UNITS.MONTHS.getName(),
        TIME_UNITS.YEARS.getName()
	};

	public static String[] SUPPORTED_METRIC_RETENTION_TIME_TYPES = {
        TIME_UNITS.SECONDS.getName(),
        TIME_UNITS.MINUTES.getName(),
        TIME_UNITS.HOURS.getName(),
        TIME_UNITS.DAYS.getName(),
        TIME_UNITS.WEEKS.getName(),
        TIME_UNITS.MONTHS.getName(),
        TIME_UNITS.YEARS.getName()
	};

	@SuppressWarnings("unused")
	private boolean timeWindowAllowed = false;

	//private String MSG_GROUPBY_FIELD_MISSING = "At least 1 field must be specified as a Group By field in this Metric."
	//        + "\nClick in the Group By column for the field you want to specify as a Group By field.";

	public LocalMetric(LocalElement parentElement, Metric metric) {
		super(parentElement,metric);
	}

	public LocalMetric(LocalEntity parentElement, String name) {
	    super(parentElement, name);
    }

	private LocalMetric() {
		super();
    }

	public void setupProperties() {
		super.setupProperties();
		//addParticle(new LocalParticle(ELEMENT_KEY_FIELD, -2, -1));
		addProperty(this, new SynOptionalProperty(PROP_KEY_METRIC_TYPE, new SynStringType(SUPPORTED_METRIC_TYPES), METRIC_TYPE.REGULAR.getName()));

		addProperty(this, new SynOptionalProperty(PROP_KEY_WINDOW_TIME_TYPE, new SynStringType(SUPPORTED_METRIC_WINDOW_TIME_TYPES), TIME_UNITS.DAYS.getName()));
		addProperty(this, new SynOptionalProperty(PROP_KEY_WINDOW_SIZE, new SynLongType(false), "0"));

		addProperty(this, new SynOptionalProperty(PROP_KEY_RECUR_TIME_TYPE, new SynStringType(SUPPORTED_METRIC_RECURRING_TIME_TYPES), TIME_UNITS.MINUTES.getName()));
		addProperty(this, new SynOptionalProperty(PROP_KEY_RECUR_FREQ, new SynLongType(false), "0"));

		addProperty(this, new SynOptionalProperty(PROP_KEY_START_TIME, new SynLongType(false), "0"));

		addProperty(this, new SynOptionalProperty(PROP_KEY_IS_TRANS_ROLLING, new SynBooleanType(), Boolean.FALSE.toString()));

		addProperty(this, new SynOptionalProperty(PROP_KEY_IS_PERSISTENT, new SynBooleanType(), Boolean.TRUE.toString()));
		addProperty(this, new SynOptionalProperty(PROP_KEY_RETENTION_TIME_SIZE, new SynLongType(false), "0"));
		addProperty(this, new SynOptionalProperty(PROP_KEY_RETENTION_TIME_TYPE, new SynStringType(SUPPORTED_METRIC_RETENTION_TIME_TYPES), TIME_UNITS.DAYS.getName()));

		addProperty(this, new SynOptionalProperty(PROP_KEY_IS_ENABLED, new SynBooleanType(), Boolean.TRUE.toString()));
//		/*
//		 * Overriding the fields particle to require a min of 1
//		 */
//		getParticle(ELEMENT_KEY_FIELD).setMinOccurs(1);

		//add user defined field particle
		addParticle(new LocalParticle(ELEMENT_KEY_USER_DEFINED_FIELD, 0, -1));

		//create a dependent fields manager for this metric
		DependentFieldsManager dependentFieldsManager = new DependentFieldsManager();
		//attach the dependent field manager to all element changes (so we can listen for field removal
		subscribeToAll(dependentFieldsManager);
		//attach the dependent field manager for listening for aggregate function changes,
		//all field changes are propagated to its parent (the metric)
		subscribeForPropertyChange(dependentFieldsManager, LocalMetricField.PROP_KEY_AGG_FUNCTION);
		subscribeForPropertyChange(dependentFieldsManager, LocalMetricField.PROP_KEY_IS_GROUP_BY);
		subscribeForPropertyChange(dependentFieldsManager, LocalMetricField.PROP_KEY_DATA_TYPE);
		subscribeForPropertyChange(dependentFieldsManager, LocalElement.PROP_KEY_NAME);
	}

	public void parseMDProperty(String propertyName) {
		if (true == super.isSuperProperty(propertyName)) {
			super.parseMDProperty(propertyName);
			return;
		}
		Metric metric = (Metric)getEObject();
		if (metric != null) {
			if (true == PROP_KEY_IS_PERSISTENT.equals(propertyName)) {
				setPersistent(metric.isPersistent());
			} else if (true == PROP_KEY_RETENTION_TIME_SIZE.equals(propertyName)) {
				setRententionTimeSize(metric.getRetentionTimeSize());
			} else if (true == PROP_KEY_RETENTION_TIME_TYPE.equals(propertyName)) {
				setRententionTimeType(metric.getRetentionTimeType().getLiteral());
			} else if (true == PROP_KEY_IS_ENABLED.equals(propertyName)) {
				setEnabled(metric.isEnabled());
			} else if (true == PROP_KEY_METRIC_TYPE.equals(propertyName)) {
				setMetricType(metric.getType().getLiteral());
			} else if (true == PROP_KEY_WINDOW_TIME_TYPE.equals(propertyName)) {
				setWindowType(metric.getWindowType().getLiteral());
			} else if (true == PROP_KEY_WINDOW_SIZE.equals(propertyName)) {
				setWindowSize(metric.getWindowSize());
			} else if (true == PROP_KEY_RECUR_TIME_TYPE.equals(propertyName)) {
				setRecurringTimeType(metric.getRecurringTimeType().getLiteral());
			} else if (true == PROP_KEY_RECUR_FREQ.equals(propertyName)) {
				setRecurringFrequency(metric.getRecurringFrequency());
			} else if (true == PROP_KEY_START_TIME.equals(propertyName)) {
				setStartTime(metric.getStartTime());
			} else if (true == PROP_KEY_IS_TRANS_ROLLING.equals(propertyName)) {
				setTransactionalRolling(metric.isTransactionalRolling());
			}
		}
	}

	public void loadChildren(String childrenType) {
		Metric metric = (Metric)getEObject();
		if (metric == null) {
			return;
		}
		if (childrenType.equals(ELEMENT_KEY_FIELD)) {
			setBulkOperation(true);
			setDeliver(false);
			try {
	            EList<PropertyDefinition> fields = metric.getProperties();
	            for (PropertyDefinition field : fields) {
	                addField(new LocalMetricField(this,field));
	            }
            } finally {
            	setDeliver(true);
            	setBulkOperation(false);
            	//reset status
            	setExisting();
            }
		}
		else if (childrenType.equals(ELEMENT_KEY_USER_DEFINED_FIELD)) {
			setBulkOperation(true);
			setDeliver(false);
			try {
	            EList<PropertyDefinition> fields = metric.getUserDefinedFields();
	            for (PropertyDefinition field : fields) {
	                addUserDefinedField(new LocalMetricUserDefinedField(this,field));
	            }
            } finally {
            	setDeliver(true);
            	setBulkOperation(false);
            	//reset status
            	setExisting();
            }
		}
	}

	public void loadChild(String childrenType, String childName) {
		if (childrenType.equals(ELEMENT_KEY_FIELD)) {
			Metric metric = (Metric)getEObject();
			if (metric == null) {
				return;
			}
			PropertyDefinition matchingField = null;
			EList<PropertyDefinition> fields = metric.getAllProperties();
			for (PropertyDefinition field : fields) {
	            if (field.getName().equals(childName) == true) {
	            	matchingField = field;
	            	break;
	            }
            }
			if (matchingField != null) {
				InternalStatusEnum existingStatus = getInternalStatus();
				setDeliver(false);
				try {
	                addField(new LocalMetricField(this, matchingField));
                } finally {
                	setDeliver(true);
                	setInternalStatus(existingStatus);
                }
			}
		}
		else if (childrenType.equals(ELEMENT_KEY_USER_DEFINED_FIELD)) {
			Metric metric = (Metric)getEObject();
			if (metric == null) {
				return;
			}
			PropertyDefinition matchingField = null;
			EList<PropertyDefinition> fields = metric.getUserDefinedFields();
			for (PropertyDefinition field : fields) {
	            if (field.getName().equals(childName) == true) {
	            	matchingField = field;
	            	break;
	            }
            }
			if (matchingField != null) {
				InternalStatusEnum existingStatus = getInternalStatus();
				setDeliver(false);
				try {
	                addUserDefinedField(new LocalMetricUserDefinedField(this, matchingField));
                } finally {
                	setDeliver(true);
                	setInternalStatus(existingStatus);
                }
			}
		}
	}

	public void loadChildByID(String childrenType, String childID) {
		if (childrenType.equals(ELEMENT_KEY_FIELD)) {
			Metric metric = (Metric)getEObject();
			if (metric == null) {
				return;
			}
			PropertyDefinition matchingField = null;
			EList<PropertyDefinition> fields = metric.getAllProperties();
			for (PropertyDefinition field : fields) {
	            if (field.getGUID().equals(childID) == true) {
	            	matchingField = field;
	            	break;
	            }
            }
			if (matchingField != null) {
				InternalStatusEnum existingStatus = getInternalStatus();
				setDeliver(false);
				try {
	                addField(new LocalMetricField(this, matchingField));
                } finally {
                	setDeliver(true);
                	setInternalStatus(existingStatus);
                }
			}
		}
		else if (childrenType.equals(ELEMENT_KEY_USER_DEFINED_FIELD)) {
			Metric metric = (Metric)getEObject();
			if (metric == null) {
				return;
			}
			PropertyDefinition matchingField = null;
			EList<PropertyDefinition> fields = metric.getUserDefinedFields();
			for (PropertyDefinition field : fields) {
	            if (field.getGUID().equals(childID) == true) {
	            	matchingField = field;
	            	break;
	            }
            }
			if (matchingField != null) {
				InternalStatusEnum existingStatus = getInternalStatus();
				setDeliver(false);
				try {
	                addUserDefinedField(new LocalMetricUserDefinedField(this, matchingField));
                } finally {
                	setDeliver(true);
                	setInternalStatus(existingStatus);
                }
			}
		}
	}

	protected void synchronizeElement(EObject eObject) {
		super.synchronizeElement(eObject);
		if (eObject instanceof Metric) {
			Metric mdMetric = (Metric) eObject;
			mdMetric.setType(METRIC_TYPE.get(getMetricType()));
			mdMetric.setWindowType(TIME_UNITS.get(getWindowType()));
			mdMetric.setWindowSize(getWindowSize());
			mdMetric.setRecurringTimeType(TIME_UNITS.get(getRecurringTimeType()));
			mdMetric.setRecurringFrequency(getRecurringFrequency());
			mdMetric.setStartTime(getStartTime());
			mdMetric.setPersistent(isPersistent());
			mdMetric.setTransactionalRolling(isTransactionalRolling());
			mdMetric.setRetentionTimeSize(getRententionTimeSize());
			mdMetric.setRetentionTimeType(TIME_UNITS.get(getRententionTimeType()));
			mdMetric.setEnabled(isEnabled());
		}
	}

	/**
	 * Creates a new LocalElementBase with default values
	 */
	public LocalEntity createLocalElement(String elementType) {
		if (elementType.equals(ELEMENT_KEY_FIELD) == true) {
			return createLocalElement(elementType,getNewFieldName("Field"));
		}
		else if (elementType.equals(ELEMENT_KEY_USER_DEFINED_FIELD) == true){
			return createLocalElement(elementType,getNewUserDefinedFieldName("UserDefinedField"));
		}
		throw new IllegalArgumentException(elementType+" is not supported");
	}

	private LocalEntity createLocalElement(String elementType, String name) {
		if (elementType.equals(ELEMENT_KEY_FIELD) == true){
			LocalMetricField metricField = new LocalMetricField(this, name);
			metricField.setNamespace(getNamespace());
			metricField.setFolder(getFolder());
			metricField.setOwnerProject(getOwnerProject());
			addElement(ELEMENT_KEY_FIELD, metricField);
			return metricField;
		}
		else if (elementType.equals(ELEMENT_KEY_USER_DEFINED_FIELD) == true){
			LocalMetricUserDefinedField userDefinedField = new LocalMetricUserDefinedField(this, name);
			userDefinedField.setNamespace(getNamespace());
			userDefinedField.setFolder(getFolder());
			userDefinedField.setOwnerProject(getOwnerProject());
			addElement(ELEMENT_KEY_USER_DEFINED_FIELD, userDefinedField);
			return userDefinedField;
		}
		throw new IllegalArgumentException(elementType+" is not supported");
	}

	public LocalMetricField createSystemField(LocalMetricField metricField, METRIC_AGGR_TYPE dependentAggregateType) {
		String dependentAggrSuffix = SYS_FIELD_PREFIX+"CT";
		if (dependentAggregateType.compareTo(METRIC_AGGR_TYPE.SUM) == 0){
			dependentAggrSuffix = SYS_FIELD_PREFIX+"SM";
		}
		else if (dependentAggregateType.compareTo(METRIC_AGGR_TYPE.SUM_OF_SQUARES) == 0){
			dependentAggrSuffix = SYS_FIELD_PREFIX+"SS";
		}
		else if (dependentAggregateType.compareTo(METRIC_AGGR_TYPE.COUNT) != 0){
			dependentAggrSuffix = SYS_FIELD_PREFIX+dependentAggregateType.toString();
		}
		LocalMetricField sysField = (LocalMetricField) createLocalElement(ELEMENT_KEY_FIELD, metricField.getName()+dependentAggrSuffix);
		sysField.setSystem(true);
		sysField.setSortingOrder(getChildren(ELEMENT_KEY_FIELD).size());
		sysField.setAggregationFunction(dependentAggregateType.toString());
		sysField.setDataType(PROPERTY_TYPES.DOUBLE.toString());
		sysField.setDescription("Auto-Generated "+dependentAggregateType+" field for " + metricField.getName());
		return sysField;
	}


	@Override
	public EObject createMDChild(LocalElement attribute) {
		Metric metric = (Metric)getEObject();
		if (metric != null) {
			PropertyDefinition persistedField = ElementFactory.eINSTANCE.createPropertyDefinition();
			if (attribute instanceof LocalMetricField){
				metric.getProperties().add(persistedField);
			}
			else if (attribute instanceof LocalMetricUserDefinedField){
				metric.getUserDefinedFields().add(persistedField);
			}
			return persistedField;
		}
		return null;
	}

	public void deleteMDChild(LocalElement attribute) {
		Metric metric = (Metric)getEObject();
		if (metric != null) {
			PropertyDefinition matchingProperty = null;
			List<PropertyDefinition> properties = null;
			if (attribute instanceof LocalMetricField){
				properties = metric.getProperties();
			}
			else if (attribute instanceof LocalMetricUserDefinedField){
				properties = metric.getUserDefinedFields();
			}
			for (PropertyDefinition property : properties) {
	            if (property.getGUID().equals(attribute.getID()) == true) {
	            	if (matchingProperty == null) {
	            		matchingProperty = property;
	            	}
	            	else {
	            		throw new IllegalStateException("Found more then one property with id as "+attribute.getID()+" in "+getName());
	            	}
	            }
            }
			if (matchingProperty == null) {
				throw new IllegalStateException("Found no property with id as "+attribute.getID()+" in "+getName());
			}
			properties.remove(matchingProperty);
		}
	}

	public long getMaxGroupByPosition() {
		long maxPosition = -1;
		/*
		 * gets the max group by position existing
		 */
		for (Iterator<LocalElement> iter = getFields().iterator(); iter.hasNext();) {
			LocalMetricField element = (LocalMetricField) iter.next();
			if (element.isGroupBy() && (element.getGroupByPosition() > maxPosition)) {
				maxPosition = element.getGroupByPosition();
			}
		}

		/*
		 * add 1 to the max and return
		 */
		return maxPosition + 1;
	}

	/**
	 * Ensure that all field positions are contiguous This should be called when
	 * fields are first loaded, add, or removed
	 *
	 * @throws Exception
	 */
	public void normalizeGroupByPosition() {
		List<LocalMetricField> children = new ArrayList<LocalMetricField>();

		for (Iterator<LocalElement> iter = getFields().iterator(); iter.hasNext();) {
			LocalMetricField element = (LocalMetricField) iter.next();
			if (true == element.isGroupBy()) {
				children.add(element);
			}
		}

		Collections.sort(children, new GroupByPositionComparator());

		for (int i = 0; i < children.size(); i++) {
			LocalMetricField element = (LocalMetricField) children.get(i);
			if (element.getGroupByPosition() != i) {
				element.setGroupByPosition(i, false);
				setModified();
			}
		}
	}

	@Override
	public boolean isValid() throws Exception {
		boolean isValid = super.isValid();
		List<LocalElement> fields = getFields();
		if (fields.isEmpty() == true){
			addValidationErrorMessage("A metric should contain atleast one group by field and one aggregate field");
			return false;
		}
		boolean hasGroupBy = false;
		boolean hasAggrFunc = false;
		for (LocalElement field : fields) {
			LocalMetricField metricField = (LocalMetricField) field;
			if (metricField.isGroupBy() == true){
				hasGroupBy = true;
			}
			else if (metricField.getAggregationFunction() != null){
				hasAggrFunc = true;
			}
		}
		if (hasGroupBy == false){
			addValidationErrorMessage("A metric should contain atleast one group by field");
		}
		if (hasAggrFunc == false){
			addValidationErrorMessage("A metric should contain atleast one aggregation field");
		}
		return isValid && hasGroupBy && hasAggrFunc;
	}

	/**
	 * Only Rolling metric types have TimeWindow This is not dynamic
	 * enough, the server should provide a more robust way to determine this.
	 * This current implementation does not allow the addition/deletion of
	 * Rolling Metric types without modifying this source code.
	 *
	 */
	public boolean isTimeWindowAllowed() {
		Metric metric = (Metric)getEObject();
		if (getMetricType() != null) {
			return getMetricType().equalsIgnoreCase(METRIC_TYPE.ROLLING_TIME.getLiteral());
		}
		else if (metric != null) {
			return metric.getType().getValue() == METRIC_TYPE.ROLLING_TIME_VALUE;
		}
		return false;
	}

	/**
	 * Ensure that the cloned metric does not use fields from original one
	 * during cloning operation. See: Bug#6117
	 */
	public Object clone() {
		try {
			LocalMetric eClone = (LocalMetric) super.clone();
			//get the fields
			List<LocalElement> fields = eClone.getParticle(LocalClassifier.ELEMENT_KEY_FIELD).getElements();
			for (LocalElement field : fields) {
				LocalMetricField metricField = (LocalMetricField) field;
				Map<METRIC_AGGR_TYPE, LocalElement> dependingFields = new HashMap<METRIC_AGGR_TYPE, LocalElement>(metricField.getDependingFields());
				for (Entry<METRIC_AGGR_TYPE, LocalElement> entry : dependingFields.entrySet()) {
					LocalElement originalMetricField = entry.getValue();
					LocalElement clonedMetricField = eClone.getElement(LocalClassifier.ELEMENT_KEY_FIELD, originalMetricField.getName(), LocalElement.FOLDER_NOT_APPLICABLE);
					entry.setValue(clonedMetricField);
				}
			}
			//get user defined fields
			//probably don't need to do anything for user defined fields
			return eClone;
		} catch (Exception e) {
			throw new RuntimeException("could not clone "+getName(),e);
		}
	}

	/**
	 * Overriding the default handler for metric fields. First create all fields
	 * that do not have dependency.. then proceed to create those that have
	 * dependency
	 */
	protected void synchronizeChildren() {
		List<LocalElement> fieldsWithDependency = new ArrayList<LocalElement>();
		List<LocalElement> fieldsWithOutDependency = new ArrayList<LocalElement>();
		/*
		 * Separate the fields with dependency from fields without dependency
		 */
		for (Iterator<LocalElement> iter = getChildren(ELEMENT_KEY_FIELD,true).iterator(); iter.hasNext();) {
			LocalMetricField localMetricField = (LocalMetricField) iter.next();
			if (true == localMetricField.requiresDependingFields()) {
				fieldsWithDependency.add(localMetricField);
			} else {
				fieldsWithOutDependency.add(localMetricField);
			}
		}
		synchronizeFields(fieldsWithOutDependency);
		synchronizeFields(fieldsWithDependency);
		//call super to reorder the fields
		synchronizeChildrenOrder(getFields(true),((Metric)getEObject()).getProperties());

		//handle user defined fields
		synchronizeFields(getChildren(ELEMENT_KEY_USER_DEFINED_FIELD,true));
		//call super to reorder the fields
		synchronizeChildrenOrder(getUserDefinedFields(),((Metric)getEObject()).getUserDefinedFields());
	}

	private void synchronizeFields(List<LocalElement> fields) {
		List<LocalElement> childrenToBePurged = new ArrayList<LocalElement>();
        fields = getOrderedElementsByAction(fields);
        for (Iterator<LocalElement> iter = fields.iterator(); iter.hasNext();) {
            LocalElement child = iter.next();
            getLogger().fine("Synchronizing: " + child.getElementType() + ":" + child.getName() + " status: " + child.getInternalStatus().toString());
            if (child.isRemoved()) {
                deleteMDChild(child);
                childrenToBePurged.add(child);
            }
            else if (true == child.isNew()) {
                child.synchronize();
                fireElementAdded(this, child);
            }
            else if (true == child.isModified()) {
                child.synchronize();
            }
        }

        for (Iterator<LocalElement> iter = childrenToBePurged.iterator(); iter.hasNext();) {
            LocalElement orphan = iter.next();
            LocalParticle particle = orphan.getParentParticle();
            particle.removeElementByName(orphan.getName());
        }
	}

	// ==================================================================
	// The following methods are convenience API's that delegates
	// to the reflection style API's for accessing attribute values
	// ==================================================================

	public String getMetricType() {
		return getPropertyValue(PROP_KEY_METRIC_TYPE);
	}

	public void setMetricType(String type) {
		setPropertyValue(PROP_KEY_METRIC_TYPE, type);
	}

	public String getWindowType() {
		return getPropertyValue(PROP_KEY_WINDOW_TIME_TYPE);
	}

	public void setWindowType(String windowType) {
		setPropertyValue(PROP_KEY_WINDOW_TIME_TYPE, windowType);
	}

	public long getWindowSize() {
		return Long.parseLong(getPropertyValue(PROP_KEY_WINDOW_SIZE));
	}

	public void setWindowSize(long value) {
		setPropertyValue(PROP_KEY_WINDOW_SIZE, value + "");
	}

	public String getRecurringTimeType() {
		return getPropertyValue(PROP_KEY_RECUR_TIME_TYPE);
	}

	public void setRecurringTimeType(String recurringTimeType) {
		setPropertyValue(PROP_KEY_RECUR_TIME_TYPE, recurringTimeType);
	}

	public long getRecurringFrequency() {
		return Long.parseLong(getPropertyValue(PROP_KEY_RECUR_FREQ));
	}

	public void setRecurringFrequency(long value) {
		setPropertyValue(PROP_KEY_RECUR_FREQ, value + "");
	}

	public long getStartTime() {
		return Long.parseLong(getPropertyValue(PROP_KEY_START_TIME));
	}

	public void setStartTime(long value) {
		setPropertyValue(PROP_KEY_START_TIME, value + "");
	}

	public boolean isTransactionalRolling() {
		return getPropertyValue(PROP_KEY_IS_TRANS_ROLLING).equalsIgnoreCase("true");
	}

	public void setTransactionalRolling(boolean value) {
		setPropertyValue(PROP_KEY_IS_TRANS_ROLLING, value + "");
	}

	public boolean isPersistent() {
		return getPropertyValue(PROP_KEY_IS_PERSISTENT).equalsIgnoreCase("true");
	}

	public void setPersistent(boolean value) {
		setPropertyValue(PROP_KEY_IS_PERSISTENT, value + "");
	}

	public long getRententionTimeSize() {
		return Long.parseLong(getPropertyValue(PROP_KEY_RETENTION_TIME_SIZE));
	}

	public void setRententionTimeSize(long value) {
		setPropertyValue(PROP_KEY_RETENTION_TIME_SIZE, value + "");
	}

	public String getRententionTimeType() {
		return getPropertyValue(PROP_KEY_RETENTION_TIME_TYPE);
	}

	public void setRententionTimeType(String retentionTimeType) {
		setPropertyValue(PROP_KEY_RETENTION_TIME_TYPE, retentionTimeType);
	}

	public boolean isEnabled() {
		return getPropertyValue(PROP_KEY_IS_ENABLED).equalsIgnoreCase("true");
	}

	public void setEnabled(boolean value) {
		setPropertyValue(PROP_KEY_IS_ENABLED, value + "");
	}

    public void addUserDefinedField(LocalMetricUserDefinedField localUserDefinedField) {
        addElement(ELEMENT_KEY_USER_DEFINED_FIELD, localUserDefinedField);
    }

    public void addUserDefinedField(LocalMetricUserDefinedField localUserDefinedField, int position) {
        /*
         * Increment any existing element with position >= specified position
         */
        for (Iterator<LocalElement> iter = getChildren(ELEMENT_KEY_USER_DEFINED_FIELD).iterator(); iter.hasNext();) {
            LocalElement element = (LocalElement) iter.next();

            if (element.getSortingOrder() >= position) {

                element.setSortingOrder(element.getSortingOrder() + 1);

            }
        }
        /*
         * Then add the element
         */
        localUserDefinedField.setSortingOrder(position);
        addElement(ELEMENT_KEY_USER_DEFINED_FIELD, localUserDefinedField);
    }

    /**
     *
     * @return a <code>List</code> of <code>LocalAttribute</code>
     * @throws Exception
     */
    public List<LocalElement> getUserDefinedFields() {
        return getChildren(ELEMENT_KEY_USER_DEFINED_FIELD);
    }

    public LocalElement getUserDefinedFieldByName(String name) {
        return getElement(ELEMENT_KEY_USER_DEFINED_FIELD, name, LocalElement.FOLDER_NOT_APPLICABLE);
    }

    public void removeUserDefinedField(LocalElement localAttribute) {
        removeElement(ELEMENT_KEY_USER_DEFINED_FIELD, localAttribute.getName(), LocalElement.FOLDER_NOT_APPLICABLE);
    }

    public String getNewUserDefinedFieldName(String nameSeed) {
        return getNewName(ELEMENT_KEY_USER_DEFINED_FIELD, nameSeed);
    }

    public boolean isNameUnique(String name) {
        return super.isNameUnique(ELEMENT_KEY_USER_DEFINED_FIELD, name);
    }

    public LocalElement getUserDefinedField(long position) {
        return getUserDefinedField(position, false);
    }

    public LocalElement getUserDefinedField(long position, boolean useProximity) {
        return getAttribute(getChildren(ELEMENT_KEY_USER_DEFINED_FIELD), position, useProximity);
    }

    public Object cloneThis() {
        return new LocalMetric();
    }

	@Override
    public String getElementType() {
	    return "Metric";
    }

	public List<LocalMetricField> getDependentFieldUsers(LocalMetricField dependentField) {
		List<LocalMetricField> dependentFieldUsers = new LinkedList<LocalMetricField>();
		for (LocalElement field : getChildren(LocalMetric.ELEMENT_KEY_FIELD,false)) {
			LocalMetricField localMetricField = (LocalMetricField) field;
			if (localMetricField.getDependingFields().containsValue(dependentField) == true){
				dependentFieldUsers.add(localMetricField);
			}
		}
		return dependentFieldUsers;
	}

	@Override
	public List<Object> getEnumerations(String propName) {
		if (propName.equals(BEViewsElementNames.DATA_SOURCE)) {
			try {
				List<LocalElement> dataSources = getRoot().getChildren(BEViewsElementNames.DATA_SOURCE);
				List<Object> ownedDataSources = new ArrayList<Object>();
				for (LocalElement dataSource : dataSources) {
					LocalElement srcElement = dataSource.getElement(LocalDataSource.ELEMENT_KEY_SRC_ELEMENT);
					if (srcElement != null && srcElement.getID().equals(this.getID())){
						ownedDataSources.add(dataSource);
					}
				}
				return ownedDataSources;
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return super.getEnumerations(propName);
	}

	public boolean hasValidFields() {
		List<LocalElement> metricFields = getChildren(LocalMetric.ELEMENT_KEY_FIELD);
		boolean hasCategoryField = false;
		boolean hasValueField = false;
		for (LocalElement metricField : metricFields) {
			LocalMetricField localMetricField = (LocalMetricField) metricField;
			if (localMetricField.isGroupBy()) {
				hasCategoryField = true;
			}
			else {
				hasValueField = true;
			}
		}
		return (hasCategoryField && hasValueField);
	}

	public boolean hasValidDataSources() {
		return getEnumerations(BEViewsElementNames.DATA_SOURCE).isEmpty() == false;
	}

	private class DependentFieldsManager implements ISynElementChangeListener {

		@Override
		public void elementAdded(IMessageProvider parent, IMessageProvider newElement) {
			//do nothing
		}

		@Override
		public void elementChanged(IMessageProvider parent, IMessageProvider changedElement) {
			//do nothing
		}

		@Override
		public void elementRemoved(IMessageProvider parent, IMessageProvider removedElement) {
//			System.out.println("DependentFieldsManager.elementRemoved("+((LocalElement)parent).getName()+","+((LocalElement)removedElement).getName()+")");
			if (removedElement instanceof LocalMetricField){
				LocalMetricField removedMetricField = (LocalMetricField) removedElement;
				LocalMetric metric = (LocalMetric) removedMetricField.getParent();
				if (removedMetricField.isSystem() == false){
					List<LocalMetricField> dependentFieldUsers = getDependentFieldUsers(removedMetricField);
					for (LocalMetricField dependentFieldUser : dependentFieldUsers) {
						//replace wherever the field is used as a dependent field by a system field
						METRIC_AGGR_TYPE dependentFieldAggrType = dependentFieldUser.removeDependingField((LocalMetricField) removedElement);
						//since we remove an existing field, we will replace with a equivalent system field
						LocalMetricField systemField = metric.createSystemField(dependentFieldUser, dependentFieldAggrType);
						dependentFieldUser.addDependingField(dependentFieldAggrType, systemField);
					}
					//also remove any system dependent fields in the removed field
					Map<METRIC_AGGR_TYPE, LocalMetricField> dependingFields = removedMetricField.getDependingFields();
					for (LocalMetricField dependentMetricField : dependingFields.values()) {
						if (dependentMetricField.isSystem() == true){
							metric.removeElement(ELEMENT_KEY_FIELD, dependentMetricField.getName(), LocalElement.FOLDER_NOT_APPLICABLE);
						}
					}
				}
			}
		}

		@Override
		public void elementStatusChanged(IMessageProvider parent, InternalStatusEnum status) {
			//do nothing
		}

		@Override
		public String getName() {
			throw new UnsupportedOperationException("getName");

		}

		@Override
		public void propertyChanged(IMessageProvider provider, SynProperty property, Object oldValue, Object newValue) {
//			System.out.println("DependentFieldsManager.propertyChanged("+((LocalElement)provider).getName()+","+property.getName()+","+oldValue+","+newValue+")");
			if ((provider instanceof LocalMetricField) == false){
				return;
			}
			LocalMetricField metricField = (LocalMetricField) provider;
			LocalMetric metric = (LocalMetric) metricField.getParent();
			if (property.getName().equals(LocalMetricField.PROP_KEY_AGG_FUNCTION) == true) {
				//we are dealing with a aggregate change
				//get list of dependent aggregate types
				List<METRIC_AGGR_TYPE> wantedDependentAggregrateTypes = metricField.getDependentAggregrateTypes(newValue.toString());
				//get existing dependent fields
				Map<METRIC_AGGR_TYPE, LocalElement> existingDependingFields = new HashMap<METRIC_AGGR_TYPE, LocalElement>(metricField.getDependingFields());

				if (wantedDependentAggregrateTypes.isEmpty() == true) {
					//we do not have any dependent field need, remove existing ones if necessary
					if (existingDependingFields.isEmpty() == false) {
						metricField.setDependingFields(new HashMap<METRIC_AGGR_TYPE, LocalMetricField>());
						//delete any of the existing dependent fields which are system generated
						for (LocalElement existingDependentField : existingDependingFields.values()) {
							LocalMetricField existingDependentMetricField = (LocalMetricField) existingDependentField;
							if (existingDependentMetricField.isSystem() == true){
								metric.removeElement(ELEMENT_KEY_FIELD, existingDependentMetricField.getName(), LocalElement.FOLDER_NOT_APPLICABLE);
							}
						}
					}
				}
				else {
					//we need some dependent fields, we need to check for unwanted existing/wanted new/no change
					//create the unwanted dependent aggregate list
					List<METRIC_AGGR_TYPE> unwantedExistingDependentAggregrateTypes = new LinkedList<METRIC_AGGR_TYPE>(existingDependingFields.keySet());
					unwantedExistingDependentAggregrateTypes.removeAll(wantedDependentAggregrateTypes);
					//remove the unwanted dependent aggregate list
					for (METRIC_AGGR_TYPE unwantedExistingDependentAggregrateType : unwantedExistingDependentAggregrateTypes) {
						LocalMetricField existingDependentMetricField = (LocalMetricField) existingDependingFields.remove(unwantedExistingDependentAggregrateType);
						//remove the field as dependent metric field
						metricField.removeDependingField(existingDependentMetricField);
						//delete it if is system field
						if (existingDependentMetricField.isSystem() == true){
							metric.removeElement(ELEMENT_KEY_FIELD, existingDependentMetricField.getName(), LocalElement.FOLDER_NOT_APPLICABLE);
						}
					}
					//create the new wanted dependent aggregate list
					List<METRIC_AGGR_TYPE> wantedNewDependentAggregrateTypes = new LinkedList<METRIC_AGGR_TYPE>(wantedDependentAggregrateTypes);
					wantedNewDependentAggregrateTypes.removeAll(existingDependingFields.keySet());
					for (METRIC_AGGR_TYPE wantedNewDependentAggregrateType : wantedNewDependentAggregrateTypes) {
						LocalMetricField systemField = metric.createSystemField(metricField, wantedNewDependentAggregrateType);
						metricField.addDependingField(wantedNewDependentAggregrateType, systemField);
					}
				}
			}
			else if (property.getName().equals(LocalMetricField.PROP_KEY_IS_GROUP_BY) == true){
				if (newValue.toString().equalsIgnoreCase("true") == true){
					//a field has been made group by field
					//find all the fields where it is being used and replace it with a system field
					List<LocalMetricField> dependentFieldUsers = getDependentFieldUsers(metricField);
					for (LocalMetricField dependentFieldUser : dependentFieldUsers) {
						//replace wherever the field is used as a dependent field by a system field
						METRIC_AGGR_TYPE dependentFieldAggrType = dependentFieldUser.removeDependingField(metricField);
						//since we remove an existing field, we will replace with a equivalent system field
						LocalMetricField systemField = metric.createSystemField(dependentFieldUser, dependentFieldAggrType);
						dependentFieldUser.addDependingField(dependentFieldAggrType, systemField);
					}
					//remove the aggregation type on this field and its dependent fields
					List<METRIC_AGGR_TYPE> dependentAggregrateTypes = metricField.getDependentAggregrateTypes();
					for (METRIC_AGGR_TYPE dependentAggregrateType : dependentAggregrateTypes) {
						metricField.removeDependingField(dependentAggregrateType);
					}
					//reset the aggregation type
					metricField.setAggregationFunction("");
				}
				else {
					metricField.setAggregationFunction(metricField.getDefaultSupportedAggregationFunc());
				}
			}
			else if (property.getName().equals(LocalMetricField.PROP_KEY_DATA_TYPE) == true){
				if (metricField.getAggregationFunction().equals(METRIC_AGGR_TYPE.SET.getLiteral()) == true && metricField.isNumeric(newValue.toString()) == false){
					//a set field's datatype has been changed to a non numeric type
					//find all the fields where it is being used and replace it with a system field
					List<LocalMetricField> dependentFieldUsers = getDependentFieldUsers(metricField);
					for (LocalMetricField dependentFieldUser : dependentFieldUsers) {
						//replace wherever the field is used as a dependent field by a system field
						METRIC_AGGR_TYPE dependentFieldAggrType = dependentFieldUser.removeDependingField(metricField);
						//since we remove an existing field, we will replace with a equivalent system field
						LocalMetricField systemField = metric.createSystemField(dependentFieldUser, dependentFieldAggrType);
						dependentFieldUser.addDependingField(dependentFieldAggrType, systemField);
					}
				}
			}
			else if (property.getName().equals(LocalElement.PROP_KEY_NAME) == true){
				for (LocalElement existingDependentField : metricField.getDependingFields().values()) {
					LocalMetricField existingDependentMetricField = (LocalMetricField) existingDependentField;
					if (existingDependentMetricField.isSystem() == true){
						StringBuilder name = new StringBuilder(existingDependentField.getName());
						int i = name.indexOf(SYS_FIELD_PREFIX);
						if (i != -1){
							name.replace(0, i, metricField.getName());
							existingDependentField.setName(name.toString());
						}
					}
				}
			}
		}

	}

}