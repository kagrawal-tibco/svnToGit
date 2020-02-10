/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.domain;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES;
import com.tibco.cep.designtime.core.model.Entity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Domain</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.domain.Domain#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.domain.Domain#getEntries <em>Entries</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.domain.Domain#getDataType <em>Data Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.domain.Domain#getSuperDomainPath <em>Super Domain Path</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.domain.Domain#getSuperDomain <em>Super Domain</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.domain.DomainPackage#getDomain()
 * @model
 * @generated
 */
public interface Domain extends Entity {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(long)
	 * @see com.tibco.cep.designtime.core.model.domain.DomainPackage#getDomain_Id()
	 * @model required="true"
	 * @generated
	 */
	long getId();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.domain.Domain#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(long value);

	/**
	 * Returns the value of the '<em><b>Entries</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.domain.DomainEntry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entries</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.domain.DomainPackage#getDomain_Entries()
	 * @model containment="true"
	 * @generated
	 */
	EList<DomainEntry> getEntries();

	/**
	 * Returns the value of the '<em><b>Data Type</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data Type</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES
	 * @see #setDataType(DOMAIN_DATA_TYPES)
	 * @see com.tibco.cep.designtime.core.model.domain.DomainPackage#getDomain_DataType()
	 * @model
	 * @generated
	 */
	DOMAIN_DATA_TYPES getDataType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.domain.Domain#getDataType <em>Data Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Data Type</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES
	 * @see #getDataType()
	 * @generated
	 */
	void setDataType(DOMAIN_DATA_TYPES value);

	/**
	 * Returns the value of the '<em><b>Super Domain Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Super Domain Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Super Domain Path</em>' attribute.
	 * @see #setSuperDomainPath(String)
	 * @see com.tibco.cep.designtime.core.model.domain.DomainPackage#getDomain_SuperDomainPath()
	 * @model
	 * @generated
	 */
	String getSuperDomainPath();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.domain.Domain#getSuperDomainPath <em>Super Domain Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Super Domain Path</em>' attribute.
	 * @see #getSuperDomainPath()
	 * @generated
	 */
	void setSuperDomainPath(String value);

	/**
	 * Returns the value of the '<em><b>Super Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Super Domain</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Super Domain</em>' reference.
	 * @see #setSuperDomain(Domain)
	 * @see com.tibco.cep.designtime.core.model.domain.DomainPackage#getDomain_SuperDomain()
	 * @model transient="true"
	 * @generated
	 */
	Domain getSuperDomain();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.domain.Domain#getSuperDomain <em>Super Domain</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Super Domain</em>' reference.
	 * @see #getSuperDomain()
	 * @generated
	 */
	void setSuperDomain(Domain value);

	/**
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Instances</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	EList<DomainInstance> getInstances();

} // Domain
