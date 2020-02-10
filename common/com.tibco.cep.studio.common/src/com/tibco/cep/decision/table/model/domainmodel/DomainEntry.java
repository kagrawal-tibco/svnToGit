/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.table.model.domainmodel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Domain Entry</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.table.model.domainmodel.DomainEntry#getEntryName <em>Entry Name</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.domainmodel.DomainEntry#getEntryValue <em>Entry Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decision.table.model.domainmodel.DomainModelPackage#getDomainEntry()
 * @model
 * @generated
 */
public interface DomainEntry extends EObject {
	/**
	 * Returns the value of the '<em><b>Entry Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entry Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entry Name</em>' attribute.
	 * @see #setEntryName(String)
	 * @see com.tibco.cep.decision.table.model.domainmodel.DomainModelPackage#getDomainEntry_EntryName()
	 * @model
	 * @generated
	 */
	String getEntryName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.domainmodel.DomainEntry#getEntryName <em>Entry Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entry Name</em>' attribute.
	 * @see #getEntryName()
	 * @generated
	 */
	void setEntryName(String value);

	/**
	 * Returns the value of the '<em><b>Entry Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entry Value</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entry Value</em>' containment reference.
	 * @see #setEntryValue(EntryValue)
	 * @see com.tibco.cep.decision.table.model.domainmodel.DomainModelPackage#getDomainEntry_EntryValue()
	 * @model containment="true"
	 * @generated
	 */
	EntryValue getEntryValue();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.domainmodel.DomainEntry#getEntryValue <em>Entry Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entry Value</em>' containment reference.
	 * @see #getEntryValue()
	 * @generated
	 */
	void setEntryValue(EntryValue value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	String getDescription();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	String getActualValue();

} // DomainEntry
