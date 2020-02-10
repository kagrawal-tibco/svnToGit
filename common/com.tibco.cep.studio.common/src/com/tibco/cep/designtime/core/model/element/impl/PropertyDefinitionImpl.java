/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.element.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.designtime.core.model.METRIC_AGGR_TYPE;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.RuleParticipant;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.element.ElementPackage;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.impl.EntityImpl;
import com.tibco.cep.designtime.core.model.validation.ModelError;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Property Definition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.PropertyDefinitionImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.PropertyDefinitionImpl#getConceptTypePath <em>Concept Type Path</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.PropertyDefinitionImpl#isArray <em>Array</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.PropertyDefinitionImpl#getOwnerPath <em>Owner Path</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.PropertyDefinitionImpl#getHistoryPolicy <em>History Policy</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.PropertyDefinitionImpl#getHistorySize <em>History Size</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.PropertyDefinitionImpl#getOrder <em>Order</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.PropertyDefinitionImpl#getDefaultValue <em>Default Value</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.PropertyDefinitionImpl#getDisjointSet <em>Disjoint Set</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.PropertyDefinitionImpl#getSuper <em>Super</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.PropertyDefinitionImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.PropertyDefinitionImpl#isTransitive <em>Transitive</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.PropertyDefinitionImpl#getEquivalentProperties <em>Equivalent Properties</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.PropertyDefinitionImpl#getAttributeDefinitions <em>Attribute Definitions</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.PropertyDefinitionImpl#getAggregationType <em>Aggregation Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.PropertyDefinitionImpl#isGroupByField <em>Group By Field</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.PropertyDefinitionImpl#getGroupByPosition <em>Group By Position</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.PropertyDefinitionImpl#isTimeWindowField <em>Time Window Field</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.PropertyDefinitionImpl#getDomainInstances <em>Domain Instances</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PropertyDefinitionImpl extends EntityImpl implements PropertyDefinition {
	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final PROPERTY_TYPES TYPE_EDEFAULT = PROPERTY_TYPES.STRING;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected PROPERTY_TYPES type = TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getConceptTypePath() <em>Concept Type Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConceptTypePath()
	 * @generated
	 * @ordered
	 */
	protected static final String CONCEPT_TYPE_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getConceptTypePath() <em>Concept Type Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConceptTypePath()
	 * @generated
	 * @ordered
	 */
	protected String conceptTypePath = CONCEPT_TYPE_PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #isArray() <em>Array</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isArray()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ARRAY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isArray() <em>Array</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isArray()
	 * @generated
	 * @ordered
	 */
	protected boolean array = ARRAY_EDEFAULT;

	/**
	 * The default value of the '{@link #getOwnerPath() <em>Owner Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnerPath()
	 * @generated
	 * @ordered
	 */
	protected static final String OWNER_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOwnerPath() <em>Owner Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnerPath()
	 * @generated
	 * @ordered
	 */
	protected String ownerPath = OWNER_PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getHistoryPolicy() <em>History Policy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHistoryPolicy()
	 * @generated
	 * @ordered
	 */
	protected static final int HISTORY_POLICY_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getHistoryPolicy() <em>History Policy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHistoryPolicy()
	 * @generated
	 * @ordered
	 */
	protected int historyPolicy = HISTORY_POLICY_EDEFAULT;

	/**
	 * The default value of the '{@link #getHistorySize() <em>History Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHistorySize()
	 * @generated
	 * @ordered
	 */
	protected static final int HISTORY_SIZE_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getHistorySize() <em>History Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHistorySize()
	 * @generated
	 * @ordered
	 */
	protected int historySize = HISTORY_SIZE_EDEFAULT;

	/**
	 * The default value of the '{@link #getOrder() <em>Order</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrder()
	 * @generated
	 * @ordered
	 */
	protected static final int ORDER_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getOrder() <em>Order</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOrder()
	 * @generated
	 * @ordered
	 */
	protected int order = ORDER_EDEFAULT;

	/**
	 * The default value of the '{@link #getDefaultValue() <em>Default Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultValue()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefaultValue() <em>Default Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultValue()
	 * @generated
	 * @ordered
	 */
	protected String defaultValue = DEFAULT_VALUE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDisjointSet() <em>Disjoint Set</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisjointSet()
	 * @generated
	 * @ordered
	 */
	protected EList<String> disjointSet;

	/**
	 * The default value of the '{@link #getSuper() <em>Super</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSuper()
	 * @generated
	 * @ordered
	 */
	protected static final String SUPER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSuper() <em>Super</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSuper()
	 * @generated
	 * @ordered
	 */
	protected String super_ = SUPER_EDEFAULT;

	/**
	 * The cached value of the '{@link #getParent() <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParent()
	 * @generated
	 * @ordered
	 */
	protected PropertyDefinition parent;

	/**
	 * The default value of the '{@link #isTransitive() <em>Transitive</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTransitive()
	 * @generated
	 * @ordered
	 */
	protected static final boolean TRANSITIVE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isTransitive() <em>Transitive</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTransitive()
	 * @generated
	 * @ordered
	 */
	protected boolean transitive = TRANSITIVE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getEquivalentProperties() <em>Equivalent Properties</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEquivalentProperties()
	 * @generated
	 * @ordered
	 */
	protected EList<PropertyDefinition> equivalentProperties;

	/**
	 * The cached value of the '{@link #getAttributeDefinitions() <em>Attribute Definitions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAttributeDefinitions()
	 * @generated
	 * @ordered
	 */
	protected EList<PropertyDefinition> attributeDefinitions;

	/**
	 * The default value of the '{@link #getAggregationType() <em>Aggregation Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAggregationType()
	 * @generated
	 * @ordered
	 */
	protected static final METRIC_AGGR_TYPE AGGREGATION_TYPE_EDEFAULT = METRIC_AGGR_TYPE.SET;

	/**
	 * The cached value of the '{@link #getAggregationType() <em>Aggregation Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAggregationType()
	 * @generated
	 * @ordered
	 */
	protected METRIC_AGGR_TYPE aggregationType = AGGREGATION_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #isGroupByField() <em>Group By Field</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGroupByField()
	 * @generated
	 * @ordered
	 */
	protected static final boolean GROUP_BY_FIELD_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isGroupByField() <em>Group By Field</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGroupByField()
	 * @generated
	 * @ordered
	 */
	protected boolean groupByField = GROUP_BY_FIELD_EDEFAULT;

	/**
	 * The default value of the '{@link #getGroupByPosition() <em>Group By Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroupByPosition()
	 * @generated
	 * @ordered
	 */
	protected static final long GROUP_BY_POSITION_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getGroupByPosition() <em>Group By Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGroupByPosition()
	 * @generated
	 * @ordered
	 */
	protected long groupByPosition = GROUP_BY_POSITION_EDEFAULT;

	/**
	 * The default value of the '{@link #isTimeWindowField() <em>Time Window Field</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTimeWindowField()
	 * @generated
	 * @ordered
	 */
	protected static final boolean TIME_WINDOW_FIELD_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isTimeWindowField() <em>Time Window Field</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTimeWindowField()
	 * @generated
	 * @ordered
	 */
	protected boolean timeWindowField = TIME_WINDOW_FIELD_EDEFAULT;

	/**
	 * The cached value of the '{@link #getDomainInstances() <em>Domain Instances</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDomainInstances()
	 * @generated
	 * @ordered
	 */
	protected EList<DomainInstance> domainInstances;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PropertyDefinitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ElementPackage.Literals.PROPERTY_DEFINITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PROPERTY_TYPES getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(PROPERTY_TYPES newType) {
		PROPERTY_TYPES oldType = type;
		type = newType == null ? TYPE_EDEFAULT : newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.PROPERTY_DEFINITION__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getConceptTypePath() {
		return conceptTypePath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConceptTypePath(String newConceptTypePath) {
		String oldConceptTypePath = conceptTypePath;
		conceptTypePath = newConceptTypePath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.PROPERTY_DEFINITION__CONCEPT_TYPE_PATH, oldConceptTypePath, conceptTypePath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isArray() {
		return array;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArray(boolean newArray) {
		boolean oldArray = array;
		array = newArray;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.PROPERTY_DEFINITION__ARRAY, oldArray, array));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOwnerPath() {
		return ownerPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOwnerPath(String newOwnerPath) {
		String oldOwnerPath = ownerPath;
		ownerPath = newOwnerPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.PROPERTY_DEFINITION__OWNER_PATH, oldOwnerPath, ownerPath));
	}

	/**
	 * <b>
	 * This will not work if the path resolves to anything but 
	 * an event, or concept.
	 * </b>
	 * @generated NOT
	 */
	@SuppressWarnings("unchecked")
	public RuleParticipant getOwner() {
		if (ownerPath != null) {
			return (RuleParticipant)CommonIndexUtils.getEntity(ownerProjectName, ownerPath);
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getHistoryPolicy() {
		return historyPolicy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHistoryPolicy(int newHistoryPolicy) {
		int oldHistoryPolicy = historyPolicy;
		historyPolicy = newHistoryPolicy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.PROPERTY_DEFINITION__HISTORY_POLICY, oldHistoryPolicy, historyPolicy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getHistorySize() {
		return historySize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHistorySize(int newHistorySize) {
		int oldHistorySize = historySize;
		historySize = newHistorySize;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.PROPERTY_DEFINITION__HISTORY_SIZE, oldHistorySize, historySize));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOrder(int newOrder) {
		int oldOrder = order;
		order = newOrder;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.PROPERTY_DEFINITION__ORDER, oldOrder, order));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDefaultValue(String newDefaultValue) {
		String oldDefaultValue = defaultValue;
		defaultValue = newDefaultValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.PROPERTY_DEFINITION__DEFAULT_VALUE, oldDefaultValue, defaultValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getDisjointSet() {
		if (disjointSet == null) {
			disjointSet = new EDataTypeUniqueEList<String>(String.class, this, ElementPackage.PROPERTY_DEFINITION__DISJOINT_SET);
		}
		return disjointSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSuper() {
		return super_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSuper(String newSuper) {
		String oldSuper = super_;
		super_ = newSuper;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.PROPERTY_DEFINITION__SUPER, oldSuper, super_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyDefinition getParent() {
		if (parent != null && parent.eIsProxy()) {
			InternalEObject oldParent = (InternalEObject)parent;
			parent = (PropertyDefinition)eResolveProxy(oldParent);
			if (parent != oldParent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ElementPackage.PROPERTY_DEFINITION__PARENT, oldParent, parent));
			}
		}
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyDefinition basicGetParent() {
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParent(PropertyDefinition newParent) {
		PropertyDefinition oldParent = parent;
		parent = newParent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.PROPERTY_DEFINITION__PARENT, oldParent, parent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isTransitive() {
		return transitive;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTransitive(boolean newTransitive) {
		boolean oldTransitive = transitive;
		transitive = newTransitive;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.PROPERTY_DEFINITION__TRANSITIVE, oldTransitive, transitive));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PropertyDefinition> getEquivalentProperties() {
		if (equivalentProperties == null) {
			equivalentProperties = new EObjectContainmentEList<PropertyDefinition>(PropertyDefinition.class, this, ElementPackage.PROPERTY_DEFINITION__EQUIVALENT_PROPERTIES);
		}
		return equivalentProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<PropertyDefinition> getAttributeDefinitions() {
		if (attributeDefinitions == null) {
			attributeDefinitions = new EObjectContainmentEList<PropertyDefinition>(PropertyDefinition.class, this, ElementPackage.PROPERTY_DEFINITION__ATTRIBUTE_DEFINITIONS);
		}
		return attributeDefinitions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public METRIC_AGGR_TYPE getAggregationType() {
		return aggregationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAggregationType(METRIC_AGGR_TYPE newAggregationType) {
		METRIC_AGGR_TYPE oldAggregationType = aggregationType;
		aggregationType = newAggregationType == null ? AGGREGATION_TYPE_EDEFAULT : newAggregationType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.PROPERTY_DEFINITION__AGGREGATION_TYPE, oldAggregationType, aggregationType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isGroupByField() {
		return groupByField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGroupByField(boolean newGroupByField) {
		boolean oldGroupByField = groupByField;
		groupByField = newGroupByField;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.PROPERTY_DEFINITION__GROUP_BY_FIELD, oldGroupByField, groupByField));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getGroupByPosition() {
		return groupByPosition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGroupByPosition(long newGroupByPosition) {
		long oldGroupByPosition = groupByPosition;
		groupByPosition = newGroupByPosition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.PROPERTY_DEFINITION__GROUP_BY_POSITION, oldGroupByPosition, groupByPosition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isTimeWindowField() {
		return timeWindowField;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTimeWindowField(boolean newTimeWindowField) {
		boolean oldTimeWindowField = timeWindowField;
		timeWindowField = newTimeWindowField;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.PROPERTY_DEFINITION__TIME_WINDOW_FIELD, oldTimeWindowField, timeWindowField));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DomainInstance> getDomainInstances() {
		if (domainInstances == null) {
			domainInstances = new EObjectContainmentEList<DomainInstance>(DomainInstance.class, this, ElementPackage.PROPERTY_DEFINITION__DOMAIN_INSTANCES);
		}
		return domainInstances;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getFullPath() {
		if (ownerPath != null && ownerPath.trim().length() != 0){
			return ownerPath + "/" + this.getName();
		} 

		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<ModelError> getModelErrors() {
		EList<ModelError> errorList = new BasicEList<ModelError>();
//		List<Object> args = new ArrayList<Object>();
//		// check if Domain Instance associated with Property Definition is not dangling
//		for (DomainInstance dmInstance :getDomainInstances()){
//			String dmPath = dmInstance.getResourcePath();
//			if (dmPath == null || dmPath.trim().length() == 0){
//				// DM Path is not valid
//				args.add(this.getFullPath());
//				if (this.eContainer instanceof Concept){
//					args.add("Concept");
//					
//				} else if (this.eContainer instanceof Event){
//					args.add("Event");
//				} else {
//					args.add(" ");
//				}
//				
//				ModelError me = CommonValidationUtils.constructModelError(this, "Property.error.hasDomainReferenceNull", args, false);
//				errorList.add(me);
//				continue;
//				
//			}
//			// resolve DM Resource
//			if (CommonValidationUtils.resolveReference(dmPath, this.getOwnerProjectName()) == null){
//				args.clear();
//				args.add(this.getFullPath());		
//				if (this.eContainer instanceof Concept){
//					args.add("Concept");
//					
//				} else if (this.eContainer instanceof Event){
//					args.add("Event");
//				} else {
//					args.add(" ");
//				}
//				args.add(this.ownerPath);
//				args.add(dmPath);
//				ModelError me = CommonValidationUtils.constructModelError(this, "RuleParticipant.error.property.hasDanglingDomainReference", args, false);
//				errorList.add(me);
//				
//			}
//		}
		return errorList;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ElementPackage.PROPERTY_DEFINITION__EQUIVALENT_PROPERTIES:
				return ((InternalEList<?>)getEquivalentProperties()).basicRemove(otherEnd, msgs);
			case ElementPackage.PROPERTY_DEFINITION__ATTRIBUTE_DEFINITIONS:
				return ((InternalEList<?>)getAttributeDefinitions()).basicRemove(otherEnd, msgs);
			case ElementPackage.PROPERTY_DEFINITION__DOMAIN_INSTANCES:
				return ((InternalEList<?>)getDomainInstances()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ElementPackage.PROPERTY_DEFINITION__TYPE:
				return getType();
			case ElementPackage.PROPERTY_DEFINITION__CONCEPT_TYPE_PATH:
				return getConceptTypePath();
			case ElementPackage.PROPERTY_DEFINITION__ARRAY:
				return isArray();
			case ElementPackage.PROPERTY_DEFINITION__OWNER_PATH:
				return getOwnerPath();
			case ElementPackage.PROPERTY_DEFINITION__HISTORY_POLICY:
				return getHistoryPolicy();
			case ElementPackage.PROPERTY_DEFINITION__HISTORY_SIZE:
				return getHistorySize();
			case ElementPackage.PROPERTY_DEFINITION__ORDER:
				return getOrder();
			case ElementPackage.PROPERTY_DEFINITION__DEFAULT_VALUE:
				return getDefaultValue();
			case ElementPackage.PROPERTY_DEFINITION__DISJOINT_SET:
				return getDisjointSet();
			case ElementPackage.PROPERTY_DEFINITION__SUPER:
				return getSuper();
			case ElementPackage.PROPERTY_DEFINITION__PARENT:
				if (resolve) return getParent();
				return basicGetParent();
			case ElementPackage.PROPERTY_DEFINITION__TRANSITIVE:
				return isTransitive();
			case ElementPackage.PROPERTY_DEFINITION__EQUIVALENT_PROPERTIES:
				return getEquivalentProperties();
			case ElementPackage.PROPERTY_DEFINITION__ATTRIBUTE_DEFINITIONS:
				return getAttributeDefinitions();
			case ElementPackage.PROPERTY_DEFINITION__AGGREGATION_TYPE:
				return getAggregationType();
			case ElementPackage.PROPERTY_DEFINITION__GROUP_BY_FIELD:
				return isGroupByField();
			case ElementPackage.PROPERTY_DEFINITION__GROUP_BY_POSITION:
				return getGroupByPosition();
			case ElementPackage.PROPERTY_DEFINITION__TIME_WINDOW_FIELD:
				return isTimeWindowField();
			case ElementPackage.PROPERTY_DEFINITION__DOMAIN_INSTANCES:
				return getDomainInstances();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ElementPackage.PROPERTY_DEFINITION__TYPE:
				setType((PROPERTY_TYPES)newValue);
				return;
			case ElementPackage.PROPERTY_DEFINITION__CONCEPT_TYPE_PATH:
				setConceptTypePath((String)newValue);
				return;
			case ElementPackage.PROPERTY_DEFINITION__ARRAY:
				setArray((Boolean)newValue);
				return;
			case ElementPackage.PROPERTY_DEFINITION__OWNER_PATH:
				setOwnerPath((String)newValue);
				return;
			case ElementPackage.PROPERTY_DEFINITION__HISTORY_POLICY:
				setHistoryPolicy((Integer)newValue);
				return;
			case ElementPackage.PROPERTY_DEFINITION__HISTORY_SIZE:
				setHistorySize((Integer)newValue);
				return;
			case ElementPackage.PROPERTY_DEFINITION__ORDER:
				setOrder((Integer)newValue);
				return;
			case ElementPackage.PROPERTY_DEFINITION__DEFAULT_VALUE:
				setDefaultValue((String)newValue);
				return;
			case ElementPackage.PROPERTY_DEFINITION__DISJOINT_SET:
				getDisjointSet().clear();
				getDisjointSet().addAll((Collection<? extends String>)newValue);
				return;
			case ElementPackage.PROPERTY_DEFINITION__SUPER:
				setSuper((String)newValue);
				return;
			case ElementPackage.PROPERTY_DEFINITION__PARENT:
				setParent((PropertyDefinition)newValue);
				return;
			case ElementPackage.PROPERTY_DEFINITION__TRANSITIVE:
				setTransitive((Boolean)newValue);
				return;
			case ElementPackage.PROPERTY_DEFINITION__EQUIVALENT_PROPERTIES:
				getEquivalentProperties().clear();
				getEquivalentProperties().addAll((Collection<? extends PropertyDefinition>)newValue);
				return;
			case ElementPackage.PROPERTY_DEFINITION__ATTRIBUTE_DEFINITIONS:
				getAttributeDefinitions().clear();
				getAttributeDefinitions().addAll((Collection<? extends PropertyDefinition>)newValue);
				return;
			case ElementPackage.PROPERTY_DEFINITION__AGGREGATION_TYPE:
				setAggregationType((METRIC_AGGR_TYPE)newValue);
				return;
			case ElementPackage.PROPERTY_DEFINITION__GROUP_BY_FIELD:
				setGroupByField((Boolean)newValue);
				return;
			case ElementPackage.PROPERTY_DEFINITION__GROUP_BY_POSITION:
				setGroupByPosition((Long)newValue);
				return;
			case ElementPackage.PROPERTY_DEFINITION__TIME_WINDOW_FIELD:
				setTimeWindowField((Boolean)newValue);
				return;
			case ElementPackage.PROPERTY_DEFINITION__DOMAIN_INSTANCES:
				getDomainInstances().clear();
				getDomainInstances().addAll((Collection<? extends DomainInstance>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ElementPackage.PROPERTY_DEFINITION__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case ElementPackage.PROPERTY_DEFINITION__CONCEPT_TYPE_PATH:
				setConceptTypePath(CONCEPT_TYPE_PATH_EDEFAULT);
				return;
			case ElementPackage.PROPERTY_DEFINITION__ARRAY:
				setArray(ARRAY_EDEFAULT);
				return;
			case ElementPackage.PROPERTY_DEFINITION__OWNER_PATH:
				setOwnerPath(OWNER_PATH_EDEFAULT);
				return;
			case ElementPackage.PROPERTY_DEFINITION__HISTORY_POLICY:
				setHistoryPolicy(HISTORY_POLICY_EDEFAULT);
				return;
			case ElementPackage.PROPERTY_DEFINITION__HISTORY_SIZE:
				setHistorySize(HISTORY_SIZE_EDEFAULT);
				return;
			case ElementPackage.PROPERTY_DEFINITION__ORDER:
				setOrder(ORDER_EDEFAULT);
				return;
			case ElementPackage.PROPERTY_DEFINITION__DEFAULT_VALUE:
				setDefaultValue(DEFAULT_VALUE_EDEFAULT);
				return;
			case ElementPackage.PROPERTY_DEFINITION__DISJOINT_SET:
				getDisjointSet().clear();
				return;
			case ElementPackage.PROPERTY_DEFINITION__SUPER:
				setSuper(SUPER_EDEFAULT);
				return;
			case ElementPackage.PROPERTY_DEFINITION__PARENT:
				setParent((PropertyDefinition)null);
				return;
			case ElementPackage.PROPERTY_DEFINITION__TRANSITIVE:
				setTransitive(TRANSITIVE_EDEFAULT);
				return;
			case ElementPackage.PROPERTY_DEFINITION__EQUIVALENT_PROPERTIES:
				getEquivalentProperties().clear();
				return;
			case ElementPackage.PROPERTY_DEFINITION__ATTRIBUTE_DEFINITIONS:
				getAttributeDefinitions().clear();
				return;
			case ElementPackage.PROPERTY_DEFINITION__AGGREGATION_TYPE:
				setAggregationType(AGGREGATION_TYPE_EDEFAULT);
				return;
			case ElementPackage.PROPERTY_DEFINITION__GROUP_BY_FIELD:
				setGroupByField(GROUP_BY_FIELD_EDEFAULT);
				return;
			case ElementPackage.PROPERTY_DEFINITION__GROUP_BY_POSITION:
				setGroupByPosition(GROUP_BY_POSITION_EDEFAULT);
				return;
			case ElementPackage.PROPERTY_DEFINITION__TIME_WINDOW_FIELD:
				setTimeWindowField(TIME_WINDOW_FIELD_EDEFAULT);
				return;
			case ElementPackage.PROPERTY_DEFINITION__DOMAIN_INSTANCES:
				getDomainInstances().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ElementPackage.PROPERTY_DEFINITION__TYPE:
				return type != TYPE_EDEFAULT;
			case ElementPackage.PROPERTY_DEFINITION__CONCEPT_TYPE_PATH:
				return CONCEPT_TYPE_PATH_EDEFAULT == null ? conceptTypePath != null : !CONCEPT_TYPE_PATH_EDEFAULT.equals(conceptTypePath);
			case ElementPackage.PROPERTY_DEFINITION__ARRAY:
				return array != ARRAY_EDEFAULT;
			case ElementPackage.PROPERTY_DEFINITION__OWNER_PATH:
				return OWNER_PATH_EDEFAULT == null ? ownerPath != null : !OWNER_PATH_EDEFAULT.equals(ownerPath);
			case ElementPackage.PROPERTY_DEFINITION__HISTORY_POLICY:
				return historyPolicy != HISTORY_POLICY_EDEFAULT;
			case ElementPackage.PROPERTY_DEFINITION__HISTORY_SIZE:
				return historySize != HISTORY_SIZE_EDEFAULT;
			case ElementPackage.PROPERTY_DEFINITION__ORDER:
				return order != ORDER_EDEFAULT;
			case ElementPackage.PROPERTY_DEFINITION__DEFAULT_VALUE:
				return DEFAULT_VALUE_EDEFAULT == null ? defaultValue != null : !DEFAULT_VALUE_EDEFAULT.equals(defaultValue);
			case ElementPackage.PROPERTY_DEFINITION__DISJOINT_SET:
				return disjointSet != null && !disjointSet.isEmpty();
			case ElementPackage.PROPERTY_DEFINITION__SUPER:
				return SUPER_EDEFAULT == null ? super_ != null : !SUPER_EDEFAULT.equals(super_);
			case ElementPackage.PROPERTY_DEFINITION__PARENT:
				return parent != null;
			case ElementPackage.PROPERTY_DEFINITION__TRANSITIVE:
				return transitive != TRANSITIVE_EDEFAULT;
			case ElementPackage.PROPERTY_DEFINITION__EQUIVALENT_PROPERTIES:
				return equivalentProperties != null && !equivalentProperties.isEmpty();
			case ElementPackage.PROPERTY_DEFINITION__ATTRIBUTE_DEFINITIONS:
				return attributeDefinitions != null && !attributeDefinitions.isEmpty();
			case ElementPackage.PROPERTY_DEFINITION__AGGREGATION_TYPE:
				return aggregationType != AGGREGATION_TYPE_EDEFAULT;
			case ElementPackage.PROPERTY_DEFINITION__GROUP_BY_FIELD:
				return groupByField != GROUP_BY_FIELD_EDEFAULT;
			case ElementPackage.PROPERTY_DEFINITION__GROUP_BY_POSITION:
				return groupByPosition != GROUP_BY_POSITION_EDEFAULT;
			case ElementPackage.PROPERTY_DEFINITION__TIME_WINDOW_FIELD:
				return timeWindowField != TIME_WINDOW_FIELD_EDEFAULT;
			case ElementPackage.PROPERTY_DEFINITION__DOMAIN_INSTANCES:
				return domainInstances != null && !domainInstances.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (type: ");
		result.append(type);
		result.append(", conceptTypePath: ");
		result.append(conceptTypePath);
		result.append(", array: ");
		result.append(array);
		result.append(", ownerPath: ");
		result.append(ownerPath);
		result.append(", historyPolicy: ");
		result.append(historyPolicy);
		result.append(", historySize: ");
		result.append(historySize);
		result.append(", order: ");
		result.append(order);
		result.append(", defaultValue: ");
		result.append(defaultValue);
		result.append(", disjointSet: ");
		result.append(disjointSet);
		result.append(", super: ");
		result.append(super_);
		result.append(", transitive: ");
		result.append(transitive);
		result.append(", aggregationType: ");
		result.append(aggregationType);
		result.append(", groupByField: ");
		result.append(groupByField);
		result.append(", groupByPosition: ");
		result.append(groupByPosition);
		result.append(", timeWindowField: ");
		result.append(timeWindowField);
		result.append(')');
		return result.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof PropertyDefinition)) {
			return false;
		}
		PropertyDefinition other = (PropertyDefinition)obj;
		
		/**
		 * We are not taking other attributes into consideration for equals
		 */
		if (this.ownerProjectName != null && !this.ownerProjectName.equals(other.getOwnerProjectName())) {
			return false;
		}
		if (!this.name.equals(other.getName())) {
			return false;
		}
		if (!this.ownerPath.equals(other.getOwnerPath())) {
			return false;
		}
		if (!this.type.equals(other.getType())) {
			return false;
		}
		return true;
	}
	
	
} //PropertyDefinitionImpl
