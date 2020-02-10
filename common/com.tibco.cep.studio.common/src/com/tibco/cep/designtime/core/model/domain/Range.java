/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.domain;

import java.io.Serializable;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Range</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.domain.Range#getLower <em>Lower</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.domain.Range#getUpper <em>Upper</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.domain.Range#isLowerInclusive <em>Lower Inclusive</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.domain.Range#isUpperInclusive <em>Upper Inclusive</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.domain.DomainPackage#getRange()
 * @model
 * @generated NOT
 */
public interface Range extends DomainEntry, Serializable {
	/**
	 * Returns the value of the '<em><b>Lower</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lower</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lower</em>' attribute.
	 * @see #setLower(String)
	 * @see com.tibco.cep.designtime.core.model.domain.DomainPackage#getRange_Lower()
	 * @model
	 * @generated
	 */
	String getLower();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.domain.Range#getLower <em>Lower</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lower</em>' attribute.
	 * @see #getLower()
	 * @generated
	 */
	void setLower(String value);

	/**
	 * Returns the value of the '<em><b>Upper</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Upper</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Upper</em>' attribute.
	 * @see #setUpper(String)
	 * @see com.tibco.cep.designtime.core.model.domain.DomainPackage#getRange_Upper()
	 * @model
	 * @generated
	 */
	String getUpper();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.domain.Range#getUpper <em>Upper</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Upper</em>' attribute.
	 * @see #getUpper()
	 * @generated
	 */
	void setUpper(String value);

	/**
	 * Returns the value of the '<em><b>Lower Inclusive</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lower Inclusive</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lower Inclusive</em>' attribute.
	 * @see #setLowerInclusive(boolean)
	 * @see com.tibco.cep.designtime.core.model.domain.DomainPackage#getRange_LowerInclusive()
	 * @model
	 * @generated
	 */
	boolean isLowerInclusive();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.domain.Range#isLowerInclusive <em>Lower Inclusive</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lower Inclusive</em>' attribute.
	 * @see #isLowerInclusive()
	 * @generated
	 */
	void setLowerInclusive(boolean value);

	/**
	 * Returns the value of the '<em><b>Upper Inclusive</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Upper Inclusive</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Upper Inclusive</em>' attribute.
	 * @see #setUpperInclusive(boolean)
	 * @see com.tibco.cep.designtime.core.model.domain.DomainPackage#getRange_UpperInclusive()
	 * @model
	 * @generated
	 */
	boolean isUpperInclusive();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.domain.Range#isUpperInclusive <em>Upper Inclusive</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Upper Inclusive</em>' attribute.
	 * @see #isUpperInclusive()
	 * @generated
	 */
	void setUpperInclusive(boolean value);

} // Range
