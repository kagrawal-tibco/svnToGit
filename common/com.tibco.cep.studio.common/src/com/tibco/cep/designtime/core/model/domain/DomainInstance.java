/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.domain;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.designtime.core.model.element.BaseInstance;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Instance</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.domain.DomainInstance#getOwnerProperty <em>Owner Property</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.domain.DomainInstance#getGUID <em>GUID</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.domain.DomainInstance#getOwnerProjectName <em>Owner Project Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.domain.DomainPackage#getDomainInstance()
 * @model
 * @generated
 */
public interface DomainInstance extends BaseInstance {
	/**
	 * Returns the value of the '<em><b>Owner Property</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owner Property</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner Property</em>' reference.
	 * @see #setOwnerProperty(PropertyDefinition)
	 * @see com.tibco.cep.designtime.core.model.domain.DomainPackage#getDomainInstance_OwnerProperty()
	 * @model
	 * @generated
	 */
	PropertyDefinition getOwnerProperty();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.domain.DomainInstance#getOwnerProperty <em>Owner Property</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner Property</em>' reference.
	 * @see #getOwnerProperty()
	 * @generated
	 */
	void setOwnerProperty(PropertyDefinition value);

	/**
	 * Returns the value of the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>GUID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>GUID</em>' attribute.
	 * @see #setGUID(String)
	 * @see com.tibco.cep.designtime.core.model.domain.DomainPackage#getDomainInstance_GUID()
	 * @model
	 * @generated
	 */
	String getGUID();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.domain.DomainInstance#getGUID <em>GUID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>GUID</em>' attribute.
	 * @see #getGUID()
	 * @generated
	 */
	void setGUID(String value);

	/**
	 * Returns the value of the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Owner Project Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Owner Project Name</em>' attribute.
	 * @see #setOwnerProjectName(String)
	 * @see com.tibco.cep.designtime.core.model.domain.DomainPackage#getDomainInstance_OwnerProjectName()
	 * @model
	 * @generated
	 */
	String getOwnerProjectName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.domain.DomainInstance#getOwnerProjectName <em>Owner Project Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Owner Project Name</em>' attribute.
	 * @see #getOwnerProjectName()
	 * @generated
	 */
	void setOwnerProjectName(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	EList<DomainEntry> getLocalEntries();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	EList<DomainEntry> getAllEntries();

} // DomainInstance
