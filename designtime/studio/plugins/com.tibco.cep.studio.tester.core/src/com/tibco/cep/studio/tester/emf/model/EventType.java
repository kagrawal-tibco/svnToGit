/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.tester.emf.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Event Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.EventType#getPayload <em>Payload</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getEventType()
 * @model extendedMetaData="name='EventType' kind='elementOnly'"
 * @generated
 */
public interface EventType extends EntityType {
	/**
	 * Returns the value of the '<em><b>Payload</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Payload</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Payload</em>' attribute.
	 * @see #setPayload(String)
	 * @see com.tibco.cep.studio.tester.emf.model.ModelPackage#getEventType_Payload()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='Payload' namespace='##targetNamespace'"
	 * @generated
	 */
	String getPayload();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.tester.emf.model.EventType#getPayload <em>Payload</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Payload</em>' attribute.
	 * @see #getPayload()
	 * @generated
	 */
	void setPayload(String value);

} // EventType
