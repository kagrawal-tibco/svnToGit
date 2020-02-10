/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.common.configuration;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Name Prefix</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.common.configuration.NamePrefix#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.NamePrefix#getPrefix <em>Prefix</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getNamePrefix()
 * @model
 * @generated
 */
public interface NamePrefix extends EObject {
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
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getNamePrefix_Name()
	 * @model id="true" required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.NamePrefix#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Prefix</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Prefix</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Prefix</em>' attribute.
	 * @see #setPrefix(String)
	 * @see com.tibco.cep.studio.common.configuration.ConfigurationPackage#getNamePrefix_Prefix()
	 * @model required="true"
	 * @generated
	 */
	String getPrefix();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.common.configuration.NamePrefix#getPrefix <em>Prefix</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Prefix</em>' attribute.
	 * @see #getPrefix()
	 * @generated
	 */
	void setPrefix(String value);

} // NamePrefix
