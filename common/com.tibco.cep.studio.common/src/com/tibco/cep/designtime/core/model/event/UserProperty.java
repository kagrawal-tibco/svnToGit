/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.event;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.PropertyMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>User Property</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.UserProperty#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.UserProperty#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.event.UserProperty#getExtendedProperties <em>Extended Properties</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.event.EventPackage#getUserProperty()
 * @model
 * @generated
 */
public interface UserProperty extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.tibco.cep.designtime.core.model.event.EventPackage#getUserProperty_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.event.UserProperty#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

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
	 * @see com.tibco.cep.designtime.core.model.event.EventPackage#getUserProperty_Type()
	 * @model
	 * @generated
	 */
	PROPERTY_TYPES getType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.event.UserProperty#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.PROPERTY_TYPES
	 * @see #getType()
	 * @generated
	 */
	void setType(PROPERTY_TYPES value);

	/**
	 * Returns the value of the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extended Properties</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extended Properties</em>' containment reference.
	 * @see #setExtendedProperties(PropertyMap)
	 * @see com.tibco.cep.designtime.core.model.event.EventPackage#getUserProperty_ExtendedProperties()
	 * @model containment="true"
	 * @generated
	 */
	PropertyMap getExtendedProperties();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.event.UserProperty#getExtendedProperties <em>Extended Properties</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extended Properties</em>' containment reference.
	 * @see #getExtendedProperties()
	 * @generated
	 */
	void setExtendedProperties(PropertyMap value);

} // UserProperty
