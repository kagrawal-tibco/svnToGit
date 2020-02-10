/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.rule;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.domain.Domain;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Symbol</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.Symbol#getIdName <em>Id Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.Symbol#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.Symbol#getTypeExtension <em>Type Extension</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.Symbol#getDomain <em>Domain</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.Symbol#isArray <em>Array</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getSymbol()
 * @model
 * @generated
 */
public interface Symbol extends EObject {
	/**
	 * Returns the value of the '<em><b>Id Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id Name</em>' attribute.
	 * @see #setIdName(String)
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getSymbol_IdName()
	 * @model required="true"
	 * @generated
	 */
	String getIdName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.rule.Symbol#getIdName <em>Id Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id Name</em>' attribute.
	 * @see #getIdName()
	 * @generated
	 */
	void setIdName(String value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getSymbol_Type()
	 * @model required="true"
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.rule.Symbol#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

	/**
	 * Returns the value of the '<em><b>Type Extension</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type Extension</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type Extension</em>' attribute.
	 * @see #setTypeExtension(String)
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getSymbol_TypeExtension()
	 * @model required="true"
	 * @generated
	 */
	String getTypeExtension();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.rule.Symbol#getTypeExtension <em>Type Extension</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Extension</em>' attribute.
	 * @see #getTypeExtension()
	 * @generated
	 */
	void setTypeExtension(String value);

	/**
	 * Returns the value of the '<em><b>Domain</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Domain</em>' containment reference.
	 * @see #setDomain(Domain)
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getSymbol_Domain()
	 * @model containment="true"
	 * @generated
	 */
	Domain getDomain();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.rule.Symbol#getDomain <em>Domain</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Domain</em>' containment reference.
	 * @see #getDomain()
	 * @generated
	 */
	void setDomain(Domain value);

	/**
	 * Returns the value of the '<em><b>Array</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Array</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Array</em>' attribute.
	 * @see #setArray(boolean)
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getSymbol_Array()
	 * @model required="true"
	 * @generated
	 */
	boolean isArray();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.rule.Symbol#isArray <em>Array</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Array</em>' attribute.
	 * @see #isArray()
	 * @generated
	 */
	void setArray(boolean value);

} // Symbol
