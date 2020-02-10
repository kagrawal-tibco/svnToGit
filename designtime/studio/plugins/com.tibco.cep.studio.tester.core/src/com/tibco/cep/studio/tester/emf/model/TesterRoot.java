/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.tester.emf.model;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Tester Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.TesterRoot#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.TesterRoot#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.TesterRoot#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.TesterRoot#getConcept <em>Concept</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.TesterRoot#getEvent <em>Event</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.TesterRoot#getExecutionObject <em>Execution Object</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.TesterRoot#getNewValue <em>New Value</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.TesterRoot#getOldValue <em>Old Value</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.TesterRoot#getOperationObject <em>Operation Object</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.TesterRoot#getReteObject <em>Rete Object</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.TesterRoot#getTesterResults <em>Tester Results</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getTesterRoot()
 * @model extendedMetaData="name='' kind='mixed'"
 * @generated
 */
public interface TesterRoot extends EObject {
	/**
	 * Returns the value of the '<em><b>Mixed</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mixed</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mixed</em>' attribute list.
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getTesterRoot_Mixed()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='elementWildcard' name=':mixed'"
	 * @generated
	 */
	FeatureMap getMixed();

	/**
	 * Returns the value of the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>XMLNS Prefix Map</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>XMLNS Prefix Map</em>' map.
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getTesterRoot_XMLNSPrefixMap()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>" transient="true"
	 *        extendedMetaData="kind='attribute' name='xmlns:prefix'"
	 * @generated
	 */
	EMap<String, String> getXMLNSPrefixMap();

	/**
	 * Returns the value of the '<em><b>XSI Schema Location</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>XSI Schema Location</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>XSI Schema Location</em>' map.
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getTesterRoot_XSISchemaLocation()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>" transient="true"
	 *        extendedMetaData="kind='attribute' name='xsi:schemaLocation'"
	 * @generated
	 */
	EMap<String, String> getXSISchemaLocation();

	/**
	 * Returns the value of the '<em><b>Concept</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Concept</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Concept</em>' containment reference.
	 * @see #setConcept(ConceptType)
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getTesterRoot_Concept()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='Concept' namespace='##targetNamespace'"
	 * @generated
	 */
	ConceptType getConcept();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.tester.emf.model.TesterRoot#getConcept <em>Concept</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Concept</em>' containment reference.
	 * @see #getConcept()
	 * @generated
	 */
	void setConcept(ConceptType value);

	/**
	 * Returns the value of the '<em><b>Event</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Event</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Event</em>' containment reference.
	 * @see #setEvent(EventType)
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getTesterRoot_Event()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='Event' namespace='##targetNamespace'"
	 * @generated
	 */
	EventType getEvent();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.tester.emf.model.TesterRoot#getEvent <em>Event</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Event</em>' containment reference.
	 * @see #getEvent()
	 * @generated
	 */
	void setEvent(EventType value);

	/**
	 * Returns the value of the '<em><b>Execution Object</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Execution Object</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Execution Object</em>' containment reference.
	 * @see #setExecutionObject(ExecutionObjectType)
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getTesterRoot_ExecutionObject()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='ExecutionObject' namespace='##targetNamespace'"
	 * @generated
	 */
	ExecutionObjectType getExecutionObject();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.tester.emf.model.TesterRoot#getExecutionObject <em>Execution Object</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Execution Object</em>' containment reference.
	 * @see #getExecutionObject()
	 * @generated
	 */
	void setExecutionObject(ExecutionObjectType value);

	/**
	 * Returns the value of the '<em><b>New Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>New Value</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>New Value</em>' containment reference.
	 * @see #setNewValue(ValueType)
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getTesterRoot_NewValue()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='newValue' namespace='##targetNamespace'"
	 * @generated
	 */
	ValueType getNewValue();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.tester.emf.model.TesterRoot#getNewValue <em>New Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>New Value</em>' containment reference.
	 * @see #getNewValue()
	 * @generated
	 */
	void setNewValue(ValueType value);

	/**
	 * Returns the value of the '<em><b>Old Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Old Value</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Old Value</em>' containment reference.
	 * @see #setOldValue(ValueType)
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getTesterRoot_OldValue()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='oldValue' namespace='##targetNamespace'"
	 * @generated
	 */
	ValueType getOldValue();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.tester.emf.model.TesterRoot#getOldValue <em>Old Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Old Value</em>' containment reference.
	 * @see #getOldValue()
	 * @generated
	 */
	void setOldValue(ValueType value);

	/**
	 * Returns the value of the '<em><b>Operation Object</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operation Object</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operation Object</em>' containment reference.
	 * @see #setOperationObject(OperationObjectType)
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getTesterRoot_OperationObject()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='OperationObject' namespace='##targetNamespace'"
	 * @generated
	 */
	OperationObjectType getOperationObject();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.tester.emf.model.TesterRoot#getOperationObject <em>Operation Object</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operation Object</em>' containment reference.
	 * @see #getOperationObject()
	 * @generated
	 */
	void setOperationObject(OperationObjectType value);

	/**
	 * Returns the value of the '<em><b>Rete Object</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rete Object</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rete Object</em>' containment reference.
	 * @see #setReteObject(ReteObjectType)
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getTesterRoot_ReteObject()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='ReteObject' namespace='##targetNamespace'"
	 * @generated
	 */
	ReteObjectType getReteObject();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.tester.emf.model.TesterRoot#getReteObject <em>Rete Object</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rete Object</em>' containment reference.
	 * @see #getReteObject()
	 * @generated
	 */
	void setReteObject(ReteObjectType value);

	/**
	 * Returns the value of the '<em><b>Tester Results</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tester Results</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tester Results</em>' containment reference.
	 * @see #setTesterResults(TesterResultsType)
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getTesterRoot_TesterResults()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='TesterResults' namespace='##targetNamespace'"
	 * @generated
	 */
	TesterResultsType getTesterResults();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.tester.emf.model.TesterRoot#getTesterResults <em>Tester Results</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tester Results</em>' containment reference.
	 * @see #getTesterResults()
	 * @generated
	 */
	void setTesterResults(TesterResultsType value);

} // TesterRoot
