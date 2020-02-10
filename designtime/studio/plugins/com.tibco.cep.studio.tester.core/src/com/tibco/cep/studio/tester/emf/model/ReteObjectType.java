/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.tester.emf.model;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rete Object Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <!-- begin-model-doc -->
 * 
 * 				Represents the actual execution object. This wraps
 * 				over
 * 				the BE entity.
 * 			
 * <!-- end-model-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.ReteObjectType#getChangeType <em>Change Type</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.ReteObjectType#getConcept <em>Concept</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.ReteObjectType#getEvent <em>Event</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getReteObjectType()
 * @model extendedMetaData="name='ReteObjectType' kind='elementOnly'"
 * @generated
 */
public interface ReteObjectType extends EObject {
	/**
	 * Returns the value of the '<em><b>Change Type</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.studio.tester.emf.model.ChangeTypeType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Change Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Change Type</em>' attribute.
	 * @see com.tibco.cep.studio.tester.emf.model.ChangeTypeType
	 * @see #isSetChangeType()
	 * @see #unsetChangeType()
	 * @see #setChangeType(ChangeTypeType)
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getReteObjectType_ChangeType()
	 * @model unsettable="true" required="true"
	 *        extendedMetaData="kind='element' name='ChangeType' namespace='##targetNamespace'"
	 * @generated
	 */
	ChangeTypeType getChangeType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.tester.emf.model.ReteObjectType#getChangeType <em>Change Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Change Type</em>' attribute.
	 * @see com.tibco.cep.studio.tester.emf.model.ChangeTypeType
	 * @see #isSetChangeType()
	 * @see #unsetChangeType()
	 * @see #getChangeType()
	 * @generated
	 */
	void setChangeType(ChangeTypeType value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.studio.tester.emf.model.ReteObjectType#getChangeType <em>Change Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetChangeType()
	 * @see #getChangeType()
	 * @see #setChangeType(ChangeTypeType)
	 * @generated
	 */
	void unsetChangeType();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.studio.tester.emf.model.ReteObjectType#getChangeType <em>Change Type</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Change Type</em>' attribute is set.
	 * @see #unsetChangeType()
	 * @see #getChangeType()
	 * @see #setChangeType(ChangeTypeType)
	 * @generated
	 */
	boolean isSetChangeType();

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
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getReteObjectType_Concept()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Concept' namespace='##targetNamespace'"
	 * @generated
	 */
	ConceptType getConcept();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.tester.emf.model.ReteObjectType#getConcept <em>Concept</em>}' containment reference.
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
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getReteObjectType_Event()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Event' namespace='##targetNamespace'"
	 * @generated
	 */
	EventType getEvent();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.tester.emf.model.ReteObjectType#getEvent <em>Event</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Event</em>' containment reference.
	 * @see #getEvent()
	 * @generated
	 */
	void setEvent(EventType value);

} // ReteObjectType
