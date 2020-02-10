/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.element;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.METRIC_AGGR_TYPE;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.RuleParticipant;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Property Definition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getConceptTypePath <em>Concept Type Path</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#isArray <em>Array</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getOwnerPath <em>Owner Path</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getHistoryPolicy <em>History Policy</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getHistorySize <em>History Size</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getOrder <em>Order</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getDefaultValue <em>Default Value</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getDisjointSet <em>Disjoint Set</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getSuper <em>Super</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getParent <em>Parent</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#isTransitive <em>Transitive</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getEquivalentProperties <em>Equivalent Properties</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getAttributeDefinitions <em>Attribute Definitions</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getAggregationType <em>Aggregation Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#isGroupByField <em>Group By Field</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getGroupByPosition <em>Group By Position</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#isTimeWindowField <em>Time Window Field</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getDomainInstances <em>Domain Instances</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getPropertyDefinition()
 * @model
 * @generated
 */
public interface PropertyDefinition  extends Entity {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.PROPERTY_TYPES}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.PROPERTY_TYPES
	 * @see #setType(PROPERTY_TYPES)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getPropertyDefinition_Type()
	 * @model
	 * @generated
	 */
	PROPERTY_TYPES getType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.PROPERTY_TYPES
	 * @see #getType()
	 * @generated
	 */
	void setType(PROPERTY_TYPES value);

	/**
	 * Returns the value of the '<em><b>Concept Type Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Concept Type Path</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Concept Type Path</em>' attribute.
	 * @see #setConceptTypePath(String)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getPropertyDefinition_ConceptTypePath()
	 * @model
	 * @generated
	 */
	String getConceptTypePath();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getConceptTypePath <em>Concept Type Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Concept Type Path</em>' attribute.
	 * @see #getConceptTypePath()
	 * @generated
	 */
	void setConceptTypePath(String value);

	/**
	 * Returns the value of the '<em><b>Array</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Array</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Array</em>' attribute.
	 * @see #setArray(boolean)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getPropertyDefinition_Array()
	 * @model
	 * @generated
	 */
	boolean isArray();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#isArray <em>Array</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Array</em>' attribute.
	 * @see #isArray()
	 * @generated
	 */
	void setArray(boolean value);

	/**
	 * Returns the value of the '<em><b>Owner Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owner Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner Path</em>' attribute.
	 * @see #setOwnerPath(String)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getPropertyDefinition_OwnerPath()
	 * @model
	 * @generated
	 */
	String getOwnerPath();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getOwnerPath <em>Owner Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner Path</em>' attribute.
	 * @see #getOwnerPath()
	 * @generated
	 */
	void setOwnerPath(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owner</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	RuleParticipant getOwner();

	/**
	 * Returns the value of the '<em><b>History Policy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>History Policy</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>History Policy</em>' attribute.
	 * @see #setHistoryPolicy(int)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getPropertyDefinition_HistoryPolicy()
	 * @model
	 * @generated
	 */
	int getHistoryPolicy();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getHistoryPolicy <em>History Policy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>History Policy</em>' attribute.
	 * @see #getHistoryPolicy()
	 * @generated
	 */
	void setHistoryPolicy(int value);

	/**
	 * Returns the value of the '<em><b>History Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>History Size</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>History Size</em>' attribute.
	 * @see #setHistorySize(int)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getPropertyDefinition_HistorySize()
	 * @model
	 * @generated
	 */
	int getHistorySize();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getHistorySize <em>History Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>History Size</em>' attribute.
	 * @see #getHistorySize()
	 * @generated
	 */
	void setHistorySize(int value);

	/**
	 * Returns the value of the '<em><b>Order</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Order</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Order</em>' attribute.
	 * @see #setOrder(int)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getPropertyDefinition_Order()
	 * @model
	 * @generated
	 */
	int getOrder();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getOrder <em>Order</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Order</em>' attribute.
	 * @see #getOrder()
	 * @generated
	 */
	void setOrder(int value);

	/**
	 * Returns the value of the '<em><b>Default Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Value</em>' attribute.
	 * @see #setDefaultValue(String)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getPropertyDefinition_DefaultValue()
	 * @model
	 * @generated
	 */
	String getDefaultValue();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getDefaultValue <em>Default Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default Value</em>' attribute.
	 * @see #getDefaultValue()
	 * @generated
	 */
	void setDefaultValue(String value);

	/**
	 * Returns the value of the '<em><b>Disjoint Set</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Disjoint Set</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Disjoint Set</em>' attribute list.
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getPropertyDefinition_DisjointSet()
	 * @model
	 * @generated
	 */
	EList<String> getDisjointSet();

	/**
	 * Returns the value of the '<em><b>Super</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Super</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Super</em>' attribute.
	 * @see #setSuper(String)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getPropertyDefinition_Super()
	 * @model
	 * @generated
	 */
	String getSuper();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getSuper <em>Super</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Super</em>' attribute.
	 * @see #getSuper()
	 * @generated
	 */
	void setSuper(String value);

	/**
	 * Returns the value of the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' reference.
	 * @see #setParent(PropertyDefinition)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getPropertyDefinition_Parent()
	 * @model
	 * @generated
	 */
	PropertyDefinition getParent();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getParent <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(PropertyDefinition value);

	/**
	 * Returns the value of the '<em><b>Transitive</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Transitive</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Transitive</em>' attribute.
	 * @see #setTransitive(boolean)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getPropertyDefinition_Transitive()
	 * @model
	 * @generated
	 */
	boolean isTransitive();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#isTransitive <em>Transitive</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Transitive</em>' attribute.
	 * @see #isTransitive()
	 * @generated
	 */
	void setTransitive(boolean value);

	/**
	 * Returns the value of the '<em><b>Equivalent Properties</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.element.PropertyDefinition}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Equivalent Properties</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Equivalent Properties</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getPropertyDefinition_EquivalentProperties()
	 * @model containment="true"
	 * @generated
	 */
	EList<PropertyDefinition> getEquivalentProperties();

	/**
	 * Returns the value of the '<em><b>Attribute Definitions</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.element.PropertyDefinition}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Attribute Definitions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Attribute Definitions</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getPropertyDefinition_AttributeDefinitions()
	 * @model containment="true"
	 * @generated
	 */
	EList<PropertyDefinition> getAttributeDefinitions();

	/**
	 * Returns the value of the '<em><b>Aggregation Type</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.METRIC_AGGR_TYPE}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Aggregation Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Aggregation Type</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.METRIC_AGGR_TYPE
	 * @see #setAggregationType(METRIC_AGGR_TYPE)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getPropertyDefinition_AggregationType()
	 * @model
	 * @generated
	 */
	METRIC_AGGR_TYPE getAggregationType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getAggregationType <em>Aggregation Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Aggregation Type</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.METRIC_AGGR_TYPE
	 * @see #getAggregationType()
	 * @generated
	 */
	void setAggregationType(METRIC_AGGR_TYPE value);

	/**
	 * Returns the value of the '<em><b>Group By Field</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Group By Field</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Group By Field</em>' attribute.
	 * @see #setGroupByField(boolean)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getPropertyDefinition_GroupByField()
	 * @model
	 * @generated
	 */
	boolean isGroupByField();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#isGroupByField <em>Group By Field</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Group By Field</em>' attribute.
	 * @see #isGroupByField()
	 * @generated
	 */
	void setGroupByField(boolean value);

	/**
	 * Returns the value of the '<em><b>Group By Position</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Group By Position</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Group By Position</em>' attribute.
	 * @see #setGroupByPosition(long)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getPropertyDefinition_GroupByPosition()
	 * @model
	 * @generated
	 */
	long getGroupByPosition();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#getGroupByPosition <em>Group By Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Group By Position</em>' attribute.
	 * @see #getGroupByPosition()
	 * @generated
	 */
	void setGroupByPosition(long value);

	/**
	 * Returns the value of the '<em><b>Time Window Field</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Time Window Field</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Time Window Field</em>' attribute.
	 * @see #setTimeWindowField(boolean)
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getPropertyDefinition_TimeWindowField()
	 * @model
	 * @generated
	 */
	boolean isTimeWindowField();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.element.PropertyDefinition#isTimeWindowField <em>Time Window Field</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Time Window Field</em>' attribute.
	 * @see #isTimeWindowField()
	 * @generated
	 */
	void setTimeWindowField(boolean value);

	/**
	 * Returns the value of the '<em><b>Domain Instances</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.domain.DomainInstance}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain Instances</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Domain Instances</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.element.ElementPackage#getPropertyDefinition_DomainInstances()
	 * @model containment="true"
	 * @generated
	 */
	EList<DomainInstance> getDomainInstances();

} // PropertyDefinition
