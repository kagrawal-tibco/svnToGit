/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.tester.emf.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Casual Objects Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.CasualObjectsType#getConcept <em>Concept</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.CasualObjectsType#getEvent <em>Event</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getCasualObjectsType()
 * @model extendedMetaData="name='CausalObjectsType' kind='elementOnly'"
 * @generated
 */
public interface CasualObjectsType extends EObject {
	/**
	 * Returns the value of the '<em><b>Concept</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.tester.emf.model.ConceptType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Concept</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Concept</em>' containment reference list.
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getCasualObjectsType_Concept()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Concept' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<ConceptType> getConcept();

	/**
	 * Returns the value of the '<em><b>Event</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.studio.tester.emf.model.EventType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Event</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Event</em>' containment reference list.
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getCasualObjectsType_Event()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='Event' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<EventType> getEvent();

} // CasualObjectsType
