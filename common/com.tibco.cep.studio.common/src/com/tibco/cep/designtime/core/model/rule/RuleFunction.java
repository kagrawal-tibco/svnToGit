/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.rule;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Function</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.RuleFunction#getValidity <em>Validity</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.RuleFunction#isVirtual <em>Virtual</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.RuleFunction#getAlias <em>Alias</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getRuleFunction()
 * @model
 * @generated
 */
public interface RuleFunction extends Compilable {
	/**
	 * Returns the value of the '<em><b>Validity</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.rule.Validity}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Validity</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Validity</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.rule.Validity
	 * @see #setValidity(Validity)
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getRuleFunction_Validity()
	 * @model
	 * @generated
	 */
	Validity getValidity();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.rule.RuleFunction#getValidity <em>Validity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Validity</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.rule.Validity
	 * @see #getValidity()
	 * @generated
	 */
	void setValidity(Validity value);

	/**
	 * Returns the value of the '<em><b>Virtual</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Virtual</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Virtual</em>' attribute.
	 * @see #setVirtual(boolean)
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getRuleFunction_Virtual()
	 * @model
	 * @generated
	 */
	boolean isVirtual();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.rule.RuleFunction#isVirtual <em>Virtual</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Virtual</em>' attribute.
	 * @see #isVirtual()
	 * @generated
	 */
	void setVirtual(boolean value);

	/**
	 * Returns the value of the '<em><b>Alias</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Alias</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Alias</em>' attribute.
	 * @see #setAlias(String)
	 * @see com.tibco.cep.designtime.core.model.rule.RulePackage#getRuleFunction_Alias()
	 * @model
	 * @generated
	 */
	String getAlias();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.rule.RuleFunction#getAlias <em>Alias</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Alias</em>' attribute.
	 * @see #getAlias()
	 * @generated
	 */
	void setAlias(String value);

} // RuleFunction
