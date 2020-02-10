/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.domain;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.domain.DomainEntry#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.domain.DomainEntry#getParent <em>Parent</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.domain.DomainEntry#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.domain.DomainEntry#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.domain.DomainPackage#getDomainEntry()
 * @model
 * 
 */
public interface DomainEntry extends EObject, Comparable {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see com.tibco.cep.designtime.core.model.domain.DomainPackage#getDomainEntry_Id()
	 * @model required="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.domain.DomainEntry#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see com.tibco.cep.designtime.core.model.domain.DomainPackage#getDomainEntry_Value()
	 * @model
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.domain.DomainEntry#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

	/**
	 * Returns the value of the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' reference.
	 * @see #setParent(Domain)
	 * @see com.tibco.cep.designtime.core.model.domain.DomainPackage#getDomainEntry_Parent()
	 * @model
	 * @generated
	 */
	Domain getParent();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.domain.DomainEntry#getParent <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(Domain value);

	/**
	 * Returns the value of the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Description</em>' attribute.
	 * @see #setDescription(String)
	 * @see com.tibco.cep.designtime.core.model.domain.DomainPackage#getDomainEntry_Description()
	 * @model
	 * @generated
	 */
	String getDescription();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.domain.DomainEntry#getDescription <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Description</em>' attribute.
	 * @see #getDescription()
	 * @generated
	 */
	void setDescription(String value);

} // DomainEntry
