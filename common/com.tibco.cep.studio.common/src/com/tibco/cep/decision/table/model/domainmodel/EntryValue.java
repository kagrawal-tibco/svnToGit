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
 * A representation of the model object '<em><b>Entry Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.table.model.domainmodel.EntryValue#isMultiple <em>Multiple</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.domainmodel.EntryValue#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decision.table.model.domainmodel.DomainModelPackage#getEntryValue()
 * @model abstract="true"
 * @generated
 */
public interface EntryValue extends EObject {
	/**
	 * Returns the value of the '<em><b>Multiple</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Multiple</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Multiple</em>' attribute.
	 * @see #setMultiple(boolean)
	 * @see com.tibco.cep.decision.table.model.domainmodel.DomainModelPackage#getEntryValue_Multiple()
	 * @model
	 * @generated
	 */
	boolean isMultiple();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.domainmodel.EntryValue#isMultiple <em>Multiple</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Multiple</em>' attribute.
	 * @see #isMultiple()
	 * @generated
	 */
	void setMultiple(boolean value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see com.tibco.cep.decision.table.model.domainmodel.DomainModelPackage#getEntryValue_Value()
	 * @model
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.domainmodel.EntryValue#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model kind="operation"
	 * @generated
	 */
	String getDescription();

} // EntryValue
